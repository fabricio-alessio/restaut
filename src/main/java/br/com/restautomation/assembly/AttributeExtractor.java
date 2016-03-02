package br.com.restautomation.assembly;

import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.HelperAdapter;

public class AttributeExtractor {

	public String getAsString(Document doc, String key) {

		String[] keys = key.split("\\.");

		Object obj = getInside(0, keys, doc);
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	private Object getInside(int pos, String[] keys, Object obj) {

		if (pos == keys.length) {
			return obj;
		}

		String key = keys[pos];
		if (!(obj instanceof Document)) {
			return null;
		}

		Document doc = (Document) obj;
		Object insideObj = doc.get(key);
		if (insideObj instanceof List) {
			List list = (List) insideObj;
			insideObj = list.get(0);
		}
		pos++;
		return getInside(pos, keys, insideObj);
	}

	public static void main(String[] args) {

		HelperAdapter helperAdapter = new HelperAdapter();

		Document doc = helperAdapter.adaptBody("{list :[37780,37859,37860,37861,37862,37863,37864,37865,37866,37867,37868,36763]}");

		AttributeExtractor attributeExtractor = new AttributeExtractor();
		String value = attributeExtractor.getAsString(doc, "list");

		System.out.println(value);
	}
}
