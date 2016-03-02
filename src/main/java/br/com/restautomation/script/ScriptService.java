package br.com.restautomation.script;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.AutomationException;
import br.com.restautomation.infra.HelperAdapter;
import br.com.restautomation.validation.ResponseCheck;

import com.mongodb.client.MongoDatabase;

public class ScriptService {

	private final ScriptDAO dao;

	private final ScriptAdapter adapter = new ScriptAdapter();

	private final HelperAdapter helperAdapter = new HelperAdapter();

	public ScriptService(MongoDatabase db) {
		this.dao = new ScriptDAO(db);
	}

	public Script getById(String id) {

		Document doc = dao.findById(id);
		return adapter.adapt(doc);
	}

	public List<Script> getAll() {

		List<Document> docs = dao.findAll();
		return adapter.adapt(docs);
	}

	public List<String> getAllIds() {

		List<Document> docs = dao.findAllIds();

		return helperAdapter.adaptIds(docs);
	}

	public List<String> getAllIdsWithoutPreConditions(Script script) {

		List<String> allIds = getAllIds();
		if (script.getPreConditions() == null || script.getPreConditions().isEmpty()) {
			return allIds;
		}

		List<String> ids = new ArrayList<String>();
		for (String id : allIds) {
			if (!script.getPreConditions().contains(id)) {
				ids.add(id);
			}
		}

		return ids;
	}

	public List<String> getIdsByTags(List<String> tags) {

		List<Document> docs = dao.findIdsByTags(tags);

		return helperAdapter.adaptIds(docs);
	}

	public void insertOrUpdate(Script script) throws AutomationException {

		if (dao.exists(script.getId())) {
			update(script);
		} else {
			insert(script);
		}
	}

	public long update(Script script) {

		Document doc = adapter.adapt(script);
		return dao.update(doc);
	}

	public void insert(Script script) throws AutomationException {

		Document doc = adapter.adapt(script);
		dao.insert(doc);
	}

	public Script newScript() {

		Script script = new Script();
		Request request = new Request();
		request.setBody("");
		request.setUrl("");
		request.setMethod("GET");
		script.setRequest(request);
		script.setDescription("");
		ResponseCheck responseCheck = new ResponseCheck();
		responseCheck.setHttpCode(200);
		script.setResponseCheck(responseCheck);
		script.setId("ScriptName");

		return script;

	}

	public void remove(String scriptId) throws AutomationException {

		dao.remove(scriptId);
	}
}
