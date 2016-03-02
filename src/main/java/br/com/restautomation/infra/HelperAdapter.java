package br.com.restautomation.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.json.JsonParseException;

public class HelperAdapter {

	public Map<String, String> adaptMap(Object obj) {

		Document document = adaptDocument(obj);
		if (document == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();
		for (Entry<String, Object> objEntry : document.entrySet()) {
			map.put(objEntry.getKey(), (String) objEntry.getValue());
		}
		return map;
	}

	public Document adaptMap(Map<String, String> map) {

		if (map == null) {
			return null;
		}
		Document document = new Document();
		for (Entry<String, String> entry : map.entrySet()) {
			document.put(entry.getKey(), entry.getValue());
		}

		return document;
	}

	public Document adaptDocument(Object obj) {

		if (obj == null || !(obj instanceof Document)) {
			return null;
		}
		return (Document) obj;
	}

	public String adaptBody(Object obj) {

		Document doc = adaptDocument(obj);
		if (doc == null) {
			return "";
		}
		return doc.toJson();
	}

	public Document adaptBody(String json) {

		if (json == null || json.equals("")) {
			return null;
		}
		try {
			return Document.parse(json);

		} catch (JsonParseException e) {
			throw new AdapterException("Can't parse body", e);
		} catch (BsonInvalidOperationException e) {
			// retorna algo diferente de json mas válido com um double
			Document doc = new Document();
			doc.append("value", json);
			return doc;
		}
	}

	public List<String> adaptStrings(ArrayList listObjs) {

		if (listObjs == null) {
			return null;
		}

		List<String> list = new ArrayList<>();
		for (Object obj : listObjs) {
			if (!(obj instanceof String)) {
				throw new AdapterException("Esperando um objeto String");
			}
			list.add((String) obj);
		}

		return list;
	}

	public List<Document> adaptDocuments(Object listObjs) {

		if (listObjs == null) {
			return null;
		}

		if (!(listObjs instanceof ArrayList)) {
			throw new AdapterException("Esperando um objeto ArrayList");
		}

		List<Document> list = new ArrayList<>();
		for (Object obj : (ArrayList) listObjs) {
			if (!(obj instanceof Document)) {
				throw new AdapterException("Esperando um objeto Document");
			}
			list.add((Document) obj);
		}

		return list;
	}

	public List<String> adaptIds(List<Document> docs) {

		List<String> ids = new ArrayList<>();
		docs.forEach(doc -> ids.add(adaptId(doc)));
		return ids;
	}

	public String adaptId(Document doc) {

		return doc.get("_id").toString();
	}
}
