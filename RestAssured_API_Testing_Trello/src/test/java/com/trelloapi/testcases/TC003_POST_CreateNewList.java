package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC003_POST_CreateNewList extends TestBase {

	public static String listId;

	@BeforeClass
	void createNewList() throws InterruptedException {
		logger.info("***********Started TC003_POST_CreateNewList*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("name", "Trello Rest Assured List");
		requestParams.put("idBoard", TC001_POST_CreateNewBoard.boardId);
		requestParams.put("key", key);
		requestParams.put("token", token);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/1/lists");
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
	void getListId() {
		logger.info("***********Getting List Id*************");
		listId = response.jsonPath().get("id");

		logger.info("List ID--> " + listId);
		Assert.assertTrue(listId != null);
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
	void checkListName() {
		logger.info("***********Checking List Name*************");

		jsonpath = response.jsonPath();
		logger.info("ListName--> " + jsonpath.get("name"));// Get name using response

		Assert.assertEquals(jsonpath.get("name"), "Trello Rest Assured List");
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC003_POST_CreateNewList*************");
	}
}
