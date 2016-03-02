package br.com.restautomation;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Environment {

	private String id;

	private Map<String, String> entries;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getEntries() {
		return entries;
	}

	public void setEntries(Map<String, String> entries) {
		this.entries = entries;
	}

	public String getValue(String param) {

		if (entries == null) {
			return "";
		}
		if (entries.containsKey(param)) {
			return entries.get(param);
		}
		return "";
	}

	public SortedSet<String> getSortedKeys() {

		return new TreeSet<String>(getEntries().keySet());
	}
}
