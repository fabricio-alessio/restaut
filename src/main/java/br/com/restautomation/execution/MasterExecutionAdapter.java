package br.com.restautomation.execution;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.restautomation.validation.ValidationStatus;

public class MasterExecutionAdapter {

	public Document adapt(MasterExecution execution) {

		if (execution == null) {
			return null;
		}

		Document doc = new Document();
		doc.append("_id", execution.getId());
		doc.append("date", execution.getDate());
		if (execution.getValidationStatus() != null) {
			doc.append("validationStatus", execution.getValidationStatus().name());
		}

		return doc;
	}

	public MasterExecution adapt(Document doc) {

		if (doc == null) {
			return null;
		}

		MasterExecution execution = new MasterExecution();
		execution.setId(doc.getString("_id"));
		execution.setDate(doc.getDate("date"));
		String status = doc.getString("validationStatus");
		if (status != null) {
			execution.setValidationStatus(ValidationStatus.valueOf(status));
		}

		return execution;
	}

	public List<MasterExecution> adapt(List<Document> docs) {

		if (docs == null) {
			return null;
		}

		List<MasterExecution> executions = new ArrayList<>();
		for (Document doc : docs) {
			executions.add(adapt(doc));
		}

		return executions;
	}
}
