package br.com.restautomation.execution;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class ExecutionDAO {

	private final MongoCollection<Document> coll;

	public ExecutionDAO(final MongoDatabase db) {
		coll = db.getCollection("executions");
	}

	public void save(Document doc) {

		try {
			coll.insertOne(doc);
		} catch (MongoWriteException e) {
			e.printStackTrace();
		}
	}

	public List<Document> findByScriptId(String scriptId) {

		return coll.find(Filters.eq("scriptId", scriptId)).sort(Sorts.descending("date")).into(new ArrayList<Document>());
	}

	public List<Document> findByMasterId(String masterId) {

		return coll.find(Filters.eq("masterId", masterId)).sort(Sorts.descending("date")).into(new ArrayList<Document>());
	}
}
