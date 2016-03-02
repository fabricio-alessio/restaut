package br.com.restautomation.user;

import org.bson.Document;

public class UserAdapter {

	public User adapt(Document doc) {

		User user = new User();
		user.setId(doc.getString("_id"));
		user.setPassword(doc.getString("password"));
		user.setEmail(doc.getString("email"));

		return user;
	}

	public Document adapt(User user) {

		Document doc = new Document();
		doc.append("_id", user.getId());
		doc.append("password", user.getPassword());
		doc.append("email", user.getEmail());

		return doc;
	}
}
