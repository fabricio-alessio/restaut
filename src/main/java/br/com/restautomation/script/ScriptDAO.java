package br.com.restautomation.script;

import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import br.com.restautomation.infra.AutomationException;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;

public class ScriptDAO {

	private final MongoCollection<Document> coll;

	public ScriptDAO(final MongoDatabase db) {
		coll = db.getCollection("scripts");
	}

	public boolean exists(String id) {
		return coll.find(Filters.eq("_id", id)).projection(include("_id")).first() != null;
	}

	public Document findById(String id) {
		return coll.find(Filters.eq("_id", id)).first();
	}

	public List<Document> findAll() {

		return coll.find().sort(Sorts.ascending("_id")).into(new ArrayList<Document>());
	}

	public List<Document> findAllIds() {

		return coll.find().sort(Sorts.ascending("_id")).projection(include("_id")).into(new ArrayList<Document>());
	}

	public List<Document> findIdsByTags(List<String> tags) {

		ArrayList<Bson> eqs = new ArrayList<Bson>();
		for (String tag : tags) {
			eqs.add(Filters.eq("tags", tag));
		}

		return coll.find(Filters.and(eqs)).sort(Sorts.ascending("_id")).projection(include("_id")).into(new ArrayList<Document>());
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

	public void remove(String scriptId) throws AutomationException {

		try {
			coll.deleteOne(Filters.eq("_id", scriptId));
		} catch (MongoWriteException e) {
			throw new AutomationException("Can't remove script.", e);
		}
	}
}
