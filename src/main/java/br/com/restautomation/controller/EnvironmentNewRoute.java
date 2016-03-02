package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.Environment;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class EnvironmentNewRoute extends LoggedRoute {

	public EnvironmentNewRoute(MongoDatabase db) throws IOException {
		super("/environments/new", "environment_edit.ftl", db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		Environment environment = new Environment();
		environment.setId("NewEnvironment");

		SimpleHash root = new SimpleHash();
		root.put("new", true);
		root.put("environment", environment);

		template.process(root, writer);
	}
}
