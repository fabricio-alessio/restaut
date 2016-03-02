package br.com.restautomation;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.AutomationException;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;

public class EnvironmentDAO {

	private final MongoCollection<Document> coll;

	public EnvironmentDAO(final MongoDatabase db) {
		coll = db.getCollection("environments");
	}

	public Document findById(String id) {
		return coll.find(Filters.eq("_id", id)).first();
	}

	public List<Document> findAll() {
		return coll.find().sort(Sorts.descending("_id")).into(new ArrayList<Document>());
	}

	public long update(Document doc) {

		UpdateResult result = coll.replaceOne(Filters.eq("_id", doc.getString("_id")), doc);
		return result.getModifiedCount();
	}

	public void insert(Document doc) throws AutomationException {

		try {
			coll.insertOne(doc);
		} catch (MongoWriteException e) {
			String reason = "";
			if (e.getCode() == 11000) {
				reason = " Reason: duplicate id.";
			}
			throw new AutomationException("Can't save script." + reason, e);
		}
	}

	public void remove(String envId) throws AutomationException {

		try {
			coll.deleteOne(Filters.eq("_id", envId));
		} catch (MongoWriteException e) {
			throw new AutomationException("Can't remove environment.", e);
		}
	}
}
