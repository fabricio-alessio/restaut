package br.com.restautomation.user;

import org.bson.Document;

public class SessionAdapter {

	public Session adapt(Document doc) {

		Session session = new Session();
		session.setId(doc.getString("_id"));
		session.setUsername(doc.getString("username"));

		return session;
	}

	public Document adapt(Session session) {

		Document doc = new Document();

		doc.append("_id", session.getId());
		doc.append("username", session.getUsername());

		return doc;
	}
}
