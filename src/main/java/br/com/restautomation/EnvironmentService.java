package br.com.restautomation;

import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.AutomationException;

import com.mongodb.client.MongoDatabase;

public class EnvironmentService {

	private final EnvironmentDAO dao;

	private final EnvironmentAdapter adapter = new EnvironmentAdapter();

	public EnvironmentService(MongoDatabase db) {
		dao = new EnvironmentDAO(db);
	}

	public Environment getById(String id) {
		Document doc = dao.findById(id);
		return adapter.adapt(doc);
	}

	public List<Environment> getAll() {
		List<Document> docs = dao.findAll();
		return adapter.adapt(docs);
	}

	public long update(Environment environment) {

		Document doc = adapter.adapt(environment);
		return dao.update(doc);
	}

	public void insert(Environment environment) throws AutomationException {

		Document doc = adapter.adapt(environment);
		dao.insert(doc);
	}

	public void remove(String envId) throws AutomationException {
		dao.remove(envId);
	}
}
