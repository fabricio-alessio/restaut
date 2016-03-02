package br.com.restautomation.execution;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

public class MasterExecutionService {

	private final MasterExecutionDAO dao;

	private final MasterExecutionAdapter adapter = new MasterExecutionAdapter();

	public MasterExecutionService(MongoDatabase db) {
		this.dao = new MasterExecutionDAO(db);
	}

	public List<MasterExecution> getAll() {

		List<Document> docs = dao.findAll();
		return adapter.adapt(docs);
	}

	public MasterExecution createNewMaster() {

		MasterExecution execution = new MasterExecution();
		execution.setDate(new Date());

		dao.save(adapter.adapt(execution));

		return execution;
	}

	public MasterExecution getById(String masterId) {

		Document doc = dao.findById(masterId);
		return adapter.adapt(doc);
	}
}
