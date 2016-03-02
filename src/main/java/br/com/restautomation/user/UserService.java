package br.com.restautomation.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.mongodb.client.MongoDatabase;

public class UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserDAO dao;

	private final UserAdapter adapter;

	private Random random = new SecureRandom();

	public UserService(MongoDatabase db) {
		dao = new UserDAO(db);
		adapter = new UserAdapter();
	}

	public boolean addUser(String username, String password, String email) {

		Document docLoad = dao.findById(username);
		if (docLoad != null) {
			return false;
		}

		String passwordHash = makePasswordHash(password, Integer.toString(random.nextInt()));

		User user = new User();

		user.setId(username);
		user.setPassword(passwordHash);
		user.setEmail(email);

		Document doc = adapter.adapt(user);
		dao.save(doc);
		return true;
	}

	private User getById(String id) {

		Document doc = dao.findById(id);
		return adapter.adapt(doc);
	}

	public User validateLogin(String username, String password) {

		User user = getById(username);

		if (user == null) {
			logger.info("User not in database");
			return null;
		}

		String hashedAndSalted = user.getPassword();

		String salt = hashedAndSalted.split(",")[1];

		if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
			logger.info("Submitted password is not a match");
			return null;
		}

		return user;
	}

	private String makePasswordHash(String password, String salt) {
		try {
			String saltedAndHashed = password + "," + salt;
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(saltedAndHashed.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
			return encoder.encode(hashedBytes) + "," + salt;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 is not available", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 unavailable? Not a chance", e);
		}
	}
}
