package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import br.com.restautomation.user.SessionService;
import br.com.restautomation.user.User;
import br.com.restautomation.user.UserService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class UserLoginEnterRoute extends FreemarkerBasedRoute {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;

	private final SessionService sessionService;

	protected UserLoginEnterRoute(MongoDatabase db) throws IOException {
		super("/login", "login.ftl");
		userService = new UserService(db);
		sessionService = new SessionService(db);
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String username = request.queryParams("username");
		String password = request.queryParams("password");

		logger.info("Login: User submitted: " + username + "  " + password);

		User user = userService.validateLogin(username, password);

		if (user != null) {

			// valid user, let's log them in
			String sessionID = sessionService.startSession(user.getId());

			if (sessionID == null) {
				response.redirect("/internal_error");
			} else {
				// set the cookie for the user's browser
				response.raw().addCookie(new Cookie("session", sessionID));

				response.redirect("/");
			}
		} else {
			SimpleHash root = new SimpleHash();

			root.put("username", StringEscapeUtils.escapeHtml4(username));
			root.put("password", "");
			root.put("login_error", "Invalid Login");
			template.process(root, writer);
		}
	}
}
