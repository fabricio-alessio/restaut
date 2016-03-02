
db = db.getSiblingDB("automation");

db.scripts.drop();

db.scripts.insert(
{
	"_id" : "RapiddoCriacaoDepositoSemInformarEstado",
	"request" : {
		"headers" : {
			"Authorization" : "Basic ZXh0ZXJubzpqaFZEMDE4VFZhZGhNUw==",
			"Content-Type" : "application/json"
		},
		"url" : "https://astgapifrete.bcash.com.br/rapiddo/service/clients/34708/depots",
		"method" : "POST",
		"body" : {
			"contactPhone" : "999884545",
			"contactEmail" : "teste@gmail.com",
			"alias" : "teste",
			"location" : {
				"street" : "Av Paulista",
				"neighborhood" : "Bela Vista",
				"number" : "234",
				"reference" : "",
				"city" : "São Paulo",
				"country" : "Brasil",
				"complement" : "Ap. 332",
				"zipCode" : "08742330",
				"province" : ""
			},
			"contactName" : "Roberto Paiva"
		}
	},
	responseCheck: {
		httpCode: NumberInt(400),
		fieldChecks: {
			code: {check: "EQUALS", expectedValue: "RPD_0043"}
		}
	}
});

db.scripts.insert(
{
	"_id" : "RapiddoCriacaoDeposito",
	"request" : {
		"headers" : {
			"Authorization" : "Basic ZXh0ZXJubzpqaFZEMDE4VFZhZGhNUw==",
			"Content-Type" : "application/json"
		},
		"url" : "https://astgapifrete.bcash.com.br/rapiddo/service/clients/34708/depots",
		"method" : "POST",
		"body" : {
			"contactPhone" : "999884545",
			"contactEmail" : "teste@gmail.com",
			"alias" : "teste",
			"location" : {
				"street" : "Av Paulista",
				"neighborhood" : "Bela Vista",
				"number" : "234",
				"reference" : "",
				"city" : "São Paulo",
				"country" : "Brasil",
				"complement" : "Ap. 332",
				"zipCode" : "08742330",
				"province" : "SP"
			},
			"contactName" : "Roberto Paiva"
		}
	},
	responseCheck: {
		httpCode: NumberInt(200),
		fieldChecks: {
			code: {check: "EQUALS", expectedValue: "RPD_0043"}
		}
	}
});

db.scripts.insert(
{
	"_id" : "RapiddoPegaCliente",
	"request" : {
		"headers" : {
			"Authorization" : "Basic ZXh0ZXJubzpqaFZEMDE4VFZhZGhNUw==",
			"Content-Type" : "application/json"
		},
		"url" : "https://astgapifrete.bcash.com.br/rapiddo/service/clients/34708",
		"method" : "GET"
	},
	responseCheck: {
		httpCode: NumberInt(200),
		fieldChecks: {
			code: {check: "EQUALS", expectedValue: "RPD_0043"}
		}
	}
});

db.scripts.insert(
{
	"_id" : "ApiCriacaoCliente",
	"request" : {
		"headers" : {
			"Authorization" : "Basic {{tokenBO}}",
			"Content-Type" : "application/json"
		},
		"url" : "{{hostApi}}/service/internal/customers",
		"method" : "POST",
		"body" : {
			"owner": {
				"email": "{{TIME_MILLIS}}automm@buscapecompany.com",
				"gender": "M",
				"name": "Tania Fontes",
				"cpf": "{{CPF}}",
				"rg": {
					"issueRgDate": "08/04/2000",
					"organCosignorRg": "SP",
					"rg": "402382444",
					"stateConsignorRg": "SP"
				},
				"birthDate": "04/02/1990",
				"password": null
			},
			"legalPerson": {
				"companyName": "Mega Ofertas",
				"tradingName": "Mega Ofertas",
				"cnpj": "{{CNPJ}}"
			},
			"address": {
				"address": "Rua Alpha",
				"number": "651",
				"complement": "Ap. 32",
				"neighborhood": "Vila Maria",
				"city": "São Paulo",
				"state": "SP",
				"zipCode": "01311000"
			},
			"contact": {
				"phoneNumber": "1135264152",
				"mobilePhoneNumber": "11985744152",
				"commercialPhoneNumber": "1135264152"
			},
			"url": "www.google.com",
			"category": NumberInt(1),
			"subcategory": NumberInt(134),
			"bank": {
				"number": "652471",
				"branch": "1551",
				"account": "666000",
				"accountType": "CC"
			},
			"campaign": NumberInt(1),
			"platform": NumberInt(13),
			"notifyingUrl": "www.google.com",
			"callbackUrl": null,
			"automaticApprovalAmount": NumberInt(1),
			"fareTable": NumberInt(66),
			"analysisSubnetwork": NumberInt(882),
			"transactionMode": null,
			"freightType": null,
			"freightAmount": NumberInt(6)
		}
	},
	responseCheck: {
		httpCode: NumberInt(200)
	}
});



db.scripts.insert(
{
	"_id" : "ApiAtivacaoCliente",
	"preConditions": ["ApiCriacaoCliente"],
	"request" : {
		"headers" : {
			"Authorization" : "Basic {{tokenBO}}",
			"Content-Type" : "application/json"
		},
		"url" : "{{hostApi}}/service/internal/customers/{{id}}/activate",
		"method" : "PUT",
		"body" : {
			"activationCode": "{{activationCode}}",
			"password": "123senha",
			"passwordConfirmation": "123senha"
		}
	},
	responseCheck: {
		httpCode: NumberInt(200)
	}
});

