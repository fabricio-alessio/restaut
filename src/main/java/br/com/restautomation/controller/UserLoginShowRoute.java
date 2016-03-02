package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class UserLoginShowRoute extends FreemarkerBasedRoute {

	protected UserLoginShowRoute(MongoDatabase db) throws IOException {
		super("/login", "login.ftl");
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
		SimpleHash root = new SimpleHash();

		root.put("username", "");
		root.put("login_error", "");

		template.process(root, writer);
	}
}
