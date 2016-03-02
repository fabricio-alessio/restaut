package br.com.restautomation.controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;

import java.io.IOException;

import spark.Spark;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class AutomationController {

	private final MongoDatabase db;

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			new AutomationController("mongodb://localhost", 8082);
		} else {
			new AutomationController(args[0], Integer.parseInt(args[1]));
		}
	}

	public AutomationController(String mongoURIString, int port) throws IOException {

		MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
		db = mongoClient.getDatabase("automation");

		Spark.staticFileLocation("/public");
		setPort(port);
		initializeRoutes();
	}

	private void initializeRoutes() throws IOException {

		get(new DashboardRoute(db));

		get(new MasterExecutionNewRoute(db));

		get(new ScriptIdsRoute(db));
		get(new ScriptExecuteRoute(db));
		get(new ScriptConfigureRoute(db));
		get(new ScriptDuplicateRoute(db));
		get(new ScriptPreRemoveRoute(db));
		post(new ScriptRemoveRoute(db));
		get(new ScriptNewRoute(db));
		post(new ScriptUpdateRoute(db));

		get(new EnvironmentConfigureRoute(db));
		get(new EnvironmentDuplicateRoute(db));
		get(new EnvironmentNewRoute(db));
		get(new EnvironmentPreRemoveRoute(db));
		post(new EnvironmentRemoveRoute(db));
		post(new EnvironmentUpdateRoute(db));

		get(new ExecutionByScriptRoute(db));
		get(new ExecutionByMasterRoute(db));
		get(new ExecutionNewRoute(db));

		get(new InternalErrorRoute(db));

		post(new UserSignupCreateRoute(db));
		get(new UserSignupShowRoute(db));
		get(new UserLogoutRoute(db));
		get(new UserLoginShowRoute(db));
		post(new UserLoginEnterRoute(db));
	}
}
