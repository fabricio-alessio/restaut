package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class UserSignupShowRoute extends FreemarkerBasedRoute {

	protected UserSignupShowRoute(MongoDatabase db) throws IOException {
		super("/signup", "signup.ftl");
	}

	@Override
	protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

		SimpleHash root = new SimpleHash();

		root.put("username", "");
		root.put("password", "");
		root.put("email", "");
		root.put("password_error", "");
		root.put("username_error", "");
		root.put("email_error", "");
		root.put("verify_error", "");

		template.process(root, writer);
	}
}
