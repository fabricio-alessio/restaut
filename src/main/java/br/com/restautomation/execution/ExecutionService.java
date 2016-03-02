package br.com.restautomation.execution;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

public class ExecutionService {

	private final ExecutionDAO dao;

	private final ExecutionAdapter adapter;

	public ExecutionService(MongoDatabase db) {
		dao = new ExecutionDAO(db);
		adapter = new ExecutionAdapter();
	}

	public void save(Execution execution) {

		Document doc = adapter.adapt(execution);
		dao.save(doc);
	}

	public Execution clone(Execution execution) {

		Document doc = adapter.adapt(execution);
		Execution clone = adapter.adapt(doc);
		Execution randomExecution = new Execution();
		clone.setId(randomExecution.getId());
		return clone;
	}

	public List<Execution> getByScriptIdAndUserName(String scriptId, String userName) {

		return null;
	}

	public List<Execution> getByScriptId(String scriptId) {

		List<Document> docs = dao.findByScriptId(scriptId);
		return adapter.adaptDocs(docs);
	}

	public List<Execution> getByMasterId(String masterId) {

		List<Document> docs = dao.findByMasterId(masterId);
		return adapter.adaptDocs(docs);
	}
}
