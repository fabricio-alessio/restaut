package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.script.Script;
import br.com.restautomation.script.ScriptService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ScriptPreRemoveRoute extends LoggedRoute {

	private final ScriptService scriptService;

	public ScriptPreRemoveRoute(MongoDatabase db) throws IOException {
		super("/scripts/remove/:id", "script_pre_remove.ftl", db);
		scriptService = new ScriptService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String scriptId = request.params(":id");

		Script script = scriptService.getById(scriptId);

		SimpleHash root = new SimpleHash();
		root.put("script", script);

		template.process(root, writer);
	}
}
