package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.Cookie;

import spark.Request;
import spark.Response;
import br.com.restautomation.infra.CookieHelper;
import br.com.restautomation.user.SessionService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.TemplateException;

public class UserLogoutRoute extends FreemarkerBasedRoute {

	private final SessionService sessionService;

	protected UserLogoutRoute(MongoDatabase db) throws IOException {
		super("/logout", "signup.ftl");
		sessionService = new SessionService(db);
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String sessionID = CookieHelper.getSessionCookieValue(request);

		if (sessionID == null) {
			// no session to end
			response.redirect("/login");
		} else {
			// deletes from session table
			sessionService.endSession(sessionID);

			// this should delete the cookie
			Cookie c = CookieHelper.getSessionCookie(request);
			c.setMaxAge(0);

			response.raw().addCookie(c);

			response.redirect("/login");
		}
	}
}
