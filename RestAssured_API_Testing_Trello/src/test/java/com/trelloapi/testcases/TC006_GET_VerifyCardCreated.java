package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC006_GET_VerifyCardCreated extends TestBase {

	@BeforeClass
	void verifyCardCreated() throws InterruptedException {

		logger.info("***********Started TC006_GET_VerifyCardCreated*************");
		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("key", key);
		requestParams.put("token", token);

		// Card Id
		httpRequest.pathParam("id", TC005_POST_CreateNewCard.cardId);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.GET, "/1/cards/{id}");
		Thread.sleep(2000);
	}

	@Test
	void checkResponseBody() {

		logger.info("***********Checking Response Body*************");
		// Print response in logs
		String responseBody = response.getBody().asString();
		logger.info("Response Body---> " + responseBody);

		Assert.assertTrue(responseBody != null);
	}

	@Test
	void checkStatusCode() {
		logger.info("***********Checking Status Code*************");

		// status code validation
		int responseStatuscode = response.getStatusCode();
		logger.info("Response Status Code---> " + responseStatuscode);

		Assert.assertEquals(responseStatuscode, 200);
	}

	@Test
	void checkCardName() {
		logger.info("***********Checking Card Name*************");

		jsonpath = response.jsonPath();
		logger.info("CardName--> " + jsonpath.get("name"));// Get name using response

		Assert.assertEquals(jsonpath.get("name"), "Trello Rest Assured Card");
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC006_GET_VerifyCardCreated*************");
	}

}
