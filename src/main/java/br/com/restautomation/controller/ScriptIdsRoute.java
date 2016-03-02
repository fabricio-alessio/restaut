package br.com.restautomation.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import spark.Request;
import spark.Response;
import spark.Route;
import br.com.restautomation.script.ScriptService;

import com.mongodb.client.MongoDatabase;

public class ScriptIdsRoute extends Route {

	private final ScriptService scriptService;

	public ScriptIdsRoute(MongoDatabase db) {
		super("/scripts/ids");
		scriptService = new ScriptService(db);
	}

	public Object handle(Request request, Response response) {

		String tags = request.queryParams("tags").trim();
		if (tags.equals(StringUtils.EMPTY)) {
			return scriptService.getAllIds();
		}
		List<String> list = Arrays.asList(tags.split(" "));

		return scriptService.getIdsByTags(list);
	}
}
