package br.com.restautomation.user;

import java.security.SecureRandom;

import org.bson.Document;

import sun.misc.BASE64Encoder;

import com.mongodb.client.MongoDatabase;

public class SessionService {

	private SessionDAO dao;

	private SessionAdapter adapter;

	public SessionService(MongoDatabase db) {
		dao = new SessionDAO(db);
		adapter = new SessionAdapter();
	}

	public Session getById(String id) {

		Document doc = dao.findById(id);
		return adapter.adapt(doc);
	}

	public String startSession(String username) {

		SecureRandom generator = new SecureRandom();
		byte randomBytes[] = new byte[32];
		generator.nextBytes(randomBytes);

		BASE64Encoder encoder = new BASE64Encoder();
		String sessionID = encoder.encode(randomBytes);

		Session session = new Session();
		session.setId(sessionID);
		session.setUsername(username);

		Document document = adapter.adapt(session);

		dao.save(document);

		return session.getId();
	}

	public void endSession(String sessionId) {

		dao.removeById(sessionId);
	}
}