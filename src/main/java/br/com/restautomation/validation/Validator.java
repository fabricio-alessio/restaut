package br.com.restautomation.validation;

import static br.com.restautomation.validation.ValidationStatus.ERROR;
import static br.com.restautomation.validation.ValidationStatus.FAILURE;
import static br.com.restautomation.validation.ValidationStatus.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.json.JsonParseException;

import br.com.restautomation.script.Response;

public class Validator {

	public List<Validation> validate(Response response, ResponseCheck responseCheck) {

		List<Validation> validations = new ArrayList<>();
		validations.add(validateHttpCode(response, responseCheck.getHttpCode()));

		if (response.getBody().equals("")) {
			return validations;
		}

		Document doc = null;
		try {
			doc = Document.parse(response.getBody());
		} catch (JsonParseException e) {
			validations.add(parseError());
			return validations;
		} catch (BsonInvalidOperationException e) {
			// Retorno não eh um json, mas algo válido como um double
			return validations;
		}
		if (doc == null) {
			validations.add(parseError());
		} else {
			if (responseCheck.getFieldChecks() != null) {
				validations.addAll(validateFields(doc, responseCheck.getFieldChecks()));
			}
		}

		return validations;
	}

	private Validation parseError() {

		Validation validation = new Validation();
		validation.setTest("JsonParse");
		validation.setStatus(ERROR);
		validation.setError("Não foi possível traduzir o json da resposta da API");
		return validation;
	}

	private List<Validation> validateFields(final Document doc, Map<String, FieldCheck> fieldChecks) {

		Document firstDoc = getFirstDocument(doc.get("list"));
		if (firstDoc == null) {
			firstDoc = doc;
		}

		List<Validation> validations = new ArrayList<>();
		for (Entry<String, FieldCheck> entry : fieldChecks.entrySet()) {
			validations.add(validateField(firstDoc, entry));
		}
		return validations;
	}

	private Document getFirstDocument(Object objlist) {

		if (objlist == null) {
			return null;
		}

		if (objlist != null && objlist instanceof ArrayList) {
			List list = ((ArrayList) objlist);
			if (list.isEmpty()) {
				return null;
			}
			if (list.get(0) instanceof Document) {
				return (Document) list.get(0);
			}
		}

		return null;
	}

	private Validation validateField(Document doc, Entry<String, FieldCheck> entry) {

		Validation validation = new Validation();

		Check check = entry.getValue().getCheck();
		String expectedValue = entry.getValue().getExpectedValue();
		String received = getAsString(doc, entry.getKey());

		validation.setTest("Check field " + entry.getKey() + " is " + check + " " + expectedValue + ". Value received: " + received);
		validation.setError(check.getError(received, expectedValue));
		validation.setStatus(validation.getError() == null ? SUCCESS : FAILURE);

		return validation;
	}

	private String getAsString(Document doc, String key) {

		Object obj = doc.get(key);
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	private Validation validateHttpCode(Response response, int httpCode) {

		Validation validation = new Validation();
		validation.setTest("Check HttpCode response is " + httpCode);
		validation.setStatus(response.getHttpCode() == httpCode ? SUCCESS : FAILURE);
		if (validation.getStatus().equals(FAILURE)) {
			validation.setError("O código http da resposta é diferente do esperado");
		}
		return validation;
	}
}
