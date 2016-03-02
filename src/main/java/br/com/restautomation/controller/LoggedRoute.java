package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.infra.CookieHelper;
import br.com.restautomation.user.Session;
import br.com.restautomation.user.SessionService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.TemplateException;

public abstract class LoggedRoute extends FreemarkerBasedRoute {

	private final SessionService sessionService;

	protected LoggedRoute(String path, String templateName, MongoDatabase db) throws IOException {
		super(path, templateName);
		sessionService = new SessionService(db);
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
		String sessionId = CookieHelper.getSessionCookieValue(request);
		if (sessionId == null) {
			response.redirect("/signup");
			return;
		}

		Session session = sessionService.getById(sessionId);
		if (session == null) {
			response.redirect("/signup");
			return;
		}

		doHandleLogged(request, response, writer);
	}

	protected abstract void doHandleLogged(final Request request, final Response response, final Writer writer) throws IOException,
			TemplateException;
}
