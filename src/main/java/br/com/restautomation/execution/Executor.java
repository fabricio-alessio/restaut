package br.com.restautomation.execution;

import static br.com.restautomation.validation.ValidationStatus.ERROR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.restautomation.Environment;
import br.com.restautomation.EnvironmentService;
import br.com.restautomation.assembly.Assembler;
import br.com.restautomation.infra.AutomationException;
import br.com.restautomation.script.Request;
import br.com.restautomation.script.Response;
import br.com.restautomation.script.Script;
import br.com.restautomation.script.ScriptService;
import br.com.restautomation.validation.Validation;
import br.com.restautomation.validation.ValidationStatus;
import br.com.restautomation.validation.Validator;

import com.mongodb.client.MongoDatabase;

public class Executor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ScriptService scriptService;

	private final ExecutionService executionService;

	private final MasterExecutionService masterExecutionService;

	private final EnvironmentService environmentService;

	private final Validator validator = new Validator();

	private final Assembler assembler = new Assembler();

	public Executor(MongoDatabase db) {
		scriptService = new ScriptService(db);
		executionService = new ExecutionService(db);
		environmentService = new EnvironmentService(db);
		masterExecutionService = new MasterExecutionService(db);
	}

	public Execution executeScriptId(String id, String masterId, String envId, String userName) {

		logger.info("Executando script [" + id + "] em masterExecution [" + masterId + "] com o ambiente [" + envId + "], usuario ["
				+ userName + "]");

		Script script = scriptService.getById(id);
		Environment env = environmentService.getById(envId);

		MasterExecution masterExecution = null;
		if (masterId != null) {
			masterExecution = masterExecutionService.getById(masterId);
		}

		Execution execution = null;
		if (script == null) {
			execution = createErrorExecution(id, userName, "O script " + id + " não foi encontrado");
		} else if (env == null) {
			execution = createErrorExecution(id, userName, "O environment " + envId + " não foi encontrado");
		} else if (masterExecution == null && masterId != null) {
			execution = createErrorExecution(id, userName, "A execução master " + masterId + " não foi encontrada");
		} else {
			List<Execution> previousExecutions = new ArrayList<>();
			execution = executeScript(script, masterExecution, env, userName, previousExecutions);
		}
		executionService.save(execution);
		return execution;
	}

	private Execution createErrorExecution(String id, String userName, String msg) {

		Execution execution = new Execution();
		execution.setScriptId(id);
		execution.setDate(new Date());
		execution.setUserName(userName);
		execution.setValidations(createErrorValidations(msg));
		return execution;
	}

	private Execution executeScript(Script script, Environment env, String userName, List<Execution> previousExecutions) {

		return executeScript(script, null, env, userName, previousExecutions);
	}

	private Execution executeScript(Script script, MasterExecution masterExecution, Environment env, String userName,
			List<Execution> previousExecutions) {

		List<Execution> preExecutions = executePreConditions(script, env, userName, previousExecutions);

		Execution execution = new Execution();
		execution.setScriptId(script.getId());
		if (masterExecution != null) {
			execution.setMasterId(masterExecution.getId());
		}
		execution.setDate(new Date());
		execution.setUserName(userName);
		execution.setPreExecutions(preExecutions);

		if (hasValidationError(preExecutions)) {
			execution.setValidations(createErrorValidations("Existem erros nas pré-execuções"));
			return execution;
		}

		try {
			Request request = assembler.assembleRequest(script.getRequest(), env, preExecutions);
			execution.setRequest(request);
			Response response = executeRequest(request);
			execution.setResponse(response);
			execution.setValidations(validator.validate(response, script.getResponseCheck()));
		} catch (AutomationException e) {
			execution.setValidations(createErrorValidations(e));
		}

		return execution;
	}

	private boolean hasValidationError(List<Execution> preExecutions) {

		if (preExecutions == null) {
			return false;
		}

		for (Execution execution : preExecutions) {
			if (!execution.getStatus().equals(ValidationStatus.SUCCESS)) {
				return true;
			}
		}
		return false;
	}

	private List<Execution> executePreConditions(Script script, Environment env, String userName, List<Execution> previousExecutions) {

		if (script.getPreConditions() == null) {
			return null;
		}

		List<Execution> executions = new ArrayList<>();
		for (String preCondition : script.getPreConditions()) {

			Execution previousExecution = getExecutionByScriptId(previousExecutions, preCondition);
			if (previousExecution != null) {
				logger.info("Pre-condicao [" + preCondition + "] jah executada. Adicionando um clone dela.");
				executions.add(executionService.clone(previousExecution));
			} else {
				logger.info("Executando pre-condicao. script [" + preCondition + "] com o ambiente [" + env.getId() + "], usuario ["
						+ userName + "]");

				Script preScript = scriptService.getById(preCondition);
				Execution execution = null;
				if (preScript == null) {
					execution = createErrorExecution(preCondition, userName, "O script " + preCondition + " não foi encontrado");
				} else {
					execution = executeScript(preScript, env, userName, previousExecutions);
				}
				executions.add(execution);
				previousExecutions.add(execution);
			}
		}
		return executions;
	}

	private Execution getExecutionByScriptId(List<Execution> executions, String executionName) {

		for (Execution execution : executions) {
			if (execution.getScriptId().equals(executionName)) {
				return execution;
			}
		}
		return null;
	}

	private List<Validation> createErrorValidations(AutomationException e) {

		return createErrorValidations(e.getMessage());
	}

	private List<Validation> createErrorValidations(String msg) {

		List<Validation> validations = new ArrayList<>();
		Validation validation = new Validation();
		validation.setStatus(ERROR);
		validation.setError(msg);
		validations.add(validation);
		return validations;
	}

	private Response executeRequest(Request request) throws AutomationException {

		Response response = new Response();

		URL url;
		try {
			url = new URL(request.getFullUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(request.getMethod());
			for (Entry<String, String> header : request.getHeaders().entrySet()) {
				conn.setRequestProperty(header.getKey(), header.getValue());
			}
			if (request.getBody() != null && !request.getBody().equals("")) {

				logger.info("request body [" + request.getBody() + "]");
				conn.setDoOutput(true);
				OutputStream os = conn.getOutputStream();
				os.write(request.getBody().getBytes());
				os.flush();
			}

			response.setHttpCode(conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
				response.setBody((extractBody(conn.getInputStream())));
			} else {
				response.setBody((extractBody(conn.getErrorStream())));
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			throw new AutomationException("Não foi possível acessar a URL " + request.getFullUrl(), e);
		} catch (IOException e) {
			throw new AutomationException("Não foi possível acessar a URL " + request.getFullUrl(), e);
		}

		return response;
	}

	private String extractBody(InputStream stream) throws IOException {

		if (stream == null) {
			return "";
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String output;
		String body = "";
		while ((output = br.readLine()) != null) {
			body += output;
		}

		logger.info("response body [" + body + "]");

		body = correctListBody(body);
		return body;
	}

	private String correctListBody(String body) {

		if (body.equals("")) {
			return "";
		}

		if (body.charAt(0) == '[') {
			return "{list :" + body + "}";
		}

		return body;
	}
}
