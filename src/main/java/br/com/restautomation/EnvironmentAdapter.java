package br.com.restautomation;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.HelperAdapter;

public class EnvironmentAdapter {

	private final HelperAdapter helper = new HelperAdapter();

	public List<Environment> adapt(List<Document> docs) {

		if (docs == null) {
			return null;
		}

		List<Environment> envs = new ArrayList<>();
		for (Document doc : docs) {
			envs.add(adapt(doc));
		}

		return envs;
	}

	public Environment adapt(Document doc) {

		if (doc == null) {
			return null;
		}

		Environment env = new Environment();
		env.setId(doc.getString("_id"));
		env.setEntries(helper.adaptMap(doc.get("entries")));
		return env;
	}

	public Document adapt(Environment env) {

		if (env == null) {
			return null;
		}

		Document doc = new Document();
		doc.append("_id", env.getId());
		doc.append("entries", helper.adaptMap(env.getEntries()));

		return doc;
	}
}
