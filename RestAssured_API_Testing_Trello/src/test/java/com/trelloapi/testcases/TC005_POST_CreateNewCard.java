package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC005_POST_CreateNewCard extends TestBase {

	public static String cardId;

	@BeforeClass
	void createNewCard() throws InterruptedException {
		logger.info("***********Started TC005_POST_CreateNewCard*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("name", "Trello Rest Assured Card");
		requestParams.put("idList", TC003_POST_CreateNewList.listId);
		requestParams.put("key", key);
		requestParams.put("token", token);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/1/cards");
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

	@Test
	void getCardId() {
		logger.info("***********Getting Card Id*************");
		cardId = response.jsonPath().get("id");
		System.out.println("Card Id is: " + cardId);
		logger.info("Card ID--> " + cardId);
		Assert.assertTrue(cardId != null);
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC005_POST_CreateNewCard*************");
	}
}
