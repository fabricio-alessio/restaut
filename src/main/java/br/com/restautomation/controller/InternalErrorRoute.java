package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class InternalErrorRoute extends FreemarkerBasedRoute {

	public InternalErrorRoute(MongoDatabase db) throws IOException {
		super("/internal_error", "error_template.ftl");
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
		SimpleHash root = new SimpleHash();

		root.put("error", "System has encountered an error.");
		template.process(root, writer);
	}
}
