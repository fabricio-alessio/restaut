package br.com.restautomation.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import spark.Request;
import spark.Response;
import spark.Route;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class FreemarkerBasedRoute extends Route {

	protected final Template template;

	protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
		super(path);
		template = createFreemarkerConfiguration().getTemplate(templateName);
	}

	@Override
	public Object handle(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			doHandle(request, response, writer);
		} catch (Exception e) {
			e.printStackTrace();
			response.redirect("/internal_error");
		}
		return writer;
	}

	protected abstract void doHandle(final Request request, final Response response, final Writer writer) throws IOException,
			TemplateException;

	private Configuration createFreemarkerConfiguration() {
		Configuration retVal = new Configuration();
		retVal.setClassForTemplateLoading(AutomationController.class, "/views");
		return retVal;
	}
}