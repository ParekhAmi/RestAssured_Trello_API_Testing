package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC007_CreateCheckList extends TestBase {

	public static String checkListId;

	@BeforeClass
	void createCheckList() throws InterruptedException {
		logger.info("***********Started TC007_CreateCheckList*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("name", "Trello_Checklist");
		requestParams.put("key", key);
		requestParams.put("token", token);

		httpRequest.pathParam("id", TC005_POST_CreateNewCard.cardId);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/1/cards/{id}/checklists");
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
	void checkChecklistName() {
		logger.info("***********Checking Checklist Name*************");

		jsonpath = response.jsonPath();
		logger.info("ChecklistName--> " + jsonpath.get("name"));// Get name using response

		Assert.assertEquals(jsonpath.get("name"), "Trello_Checklist");
	}

	@Test
	void getChecklistId() {
		logger.info("***********Getting Checklist Id*************");
		checkListId = response.jsonPath().get("id");
		System.out.println("Checklist Id is: " + checkListId);
		logger.info("Checklist ID--> " + checkListId);
		Assert.assertTrue(checkListId != null);
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC007_CreateCheckList*************");
	}
}
