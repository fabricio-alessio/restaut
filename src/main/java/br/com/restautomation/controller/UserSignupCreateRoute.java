package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import br.com.restautomation.user.SessionService;
import br.com.restautomation.user.UserService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.TemplateException;

public class UserSignupCreateRoute extends FreemarkerBasedRoute {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;

	private final SessionService sessionService;

	protected UserSignupCreateRoute(MongoDatabase db) throws IOException {
		super("/signup", "signup.ftl");
		userService = new UserService(db);
		sessionService = new SessionService(db);
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
		String email = request.queryParams("email");
		String username = request.queryParams("username");
		String password = request.queryParams("password");
		String verify = request.queryParams("verify");

		HashMap<String, String> root = new HashMap<String, String>();
		root.put("username", StringEscapeUtils.escapeHtml4(username));
		root.put("email", StringEscapeUtils.escapeHtml4(email));

		if (validateSignup(username, password, verify, email, root)) {
			logger.info("Signup: Creating user with: " + username + " " + password);
			if (!userService.addUser(username, password, email)) {
				root.put("username_error", "Username already in use, Please choose another");
				template.process(root, writer);
			} else {
				String sessionID = sessionService.startSession(username);
				logger.info("Session ID is" + sessionID);

				response.raw().addCookie(new Cookie("session", sessionID));
				response.redirect("/");
			}
		} else {
			logger.info("User Registration did not validate");
			template.process(root, writer);
		}
	}

	public boolean validateSignup(String username, String password, String verify, String email, HashMap<String, String> errors) {
		String USER_RE = "^[a-zA-Z0-9_-]{3,20}$";
		String PASS_RE = "^.{3,20}$";
		String EMAIL_RE = "^[\\S]+@[\\S]+\\.[\\S]+$";

		errors.put("username_error", "");
		errors.put("password_error", "");
		errors.put("verify_error", "");
		errors.put("email_error", "");

		if (!username.matches(USER_RE)) {
			errors.put("username_error", "invalid username. try just letters and numbers");
			return false;
		}

		if (!password.matches(PASS_RE)) {
			errors.put("password_error", "invalid password.");
			return false;
		}

		if (!password.equals(verify)) {
			errors.put("verify_error", "password must match");
			return false;
		}

		if (!email.equals("")) {
			if (!email.matches(EMAIL_RE)) {
				errors.put("email_error", "Invalid Email Address");
				return false;
			}
		}

		return true;
	}
}
