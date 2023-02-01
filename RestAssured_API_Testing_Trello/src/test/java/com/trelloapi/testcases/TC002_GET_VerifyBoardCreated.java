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

public class TC002_GET_VerifyBoardCreated extends TestBase {

	@BeforeClass
	void verifyBoardCreated() throws InterruptedException {
		logger.info("***********Started TC002_GET_VerifyBoardCreated*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("key", key);
		requestParams.put("token", token);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		// Board Id
		httpRequest.pathParam("id", TC001_POST_CreateNewBoard.boardId);

		response = httpRequest.request(Method.GET, "/1/boards/{id}");
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
	void checkBoardName() {
		logger.info("***********Checking Board Name*************");

		jsonpath = response.jsonPath();
		logger.info("BoardName--> " + jsonpath.get("name"));// Get name using response

		Assert.assertEquals(jsonpath.get("name"), "TrelloRestAssuredBoard");

		//Assert.assertEquals(responseBody.contains("TrelloRestAssuredBoard"), true); // Verifying "name" value

	}

	@Test
	void checkBoardId() {
		logger.info("***********Checking Board Id*************");

		jsonpath = response.jsonPath();
		logger.info("BoardId--> " + jsonpath.get("id"));
	}
	
	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC002_GET_VerifyBoardCreated*************");
	}
}
