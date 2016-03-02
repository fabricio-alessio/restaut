package br.com.restautomation.execution;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class MasterExecutionDAO {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final MongoCollection<Document> coll;

	public MasterExecutionDAO(final MongoDatabase db) {
		coll = db.getCollection("masterExecutions");
	}

	public void save(Document doc) {

		try {
			coll.insertOne(doc);
		} catch (MongoWriteException e) {
			logger.error("Não foi possível salvar a execução master", e);
		}
	}

	public List<Document> findAll() {
		return coll.find().sort(Sorts.descending("date")).into(new ArrayList<Document>());
	}

	public Document findById(String id) {
		return coll.find(Filters.eq("_id", id)).first();
	}
}
