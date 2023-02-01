package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC012_GET_VerifyBoardDeleted extends TestBase{
	
	@BeforeClass
	void verifyBoardDeleted() throws InterruptedException {
		logger.info("***********Started TC012_GET_VerifyBoardDeleted*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("key", key);
		requestParams.put("token", token);

		// Card Id 
		httpRequest.pathParam("id", TC001_POST_CreateNewBoard.boardId);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

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

		Assert.assertEquals(responseStatuscode, 404);
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC012_GET_VerifyBoardDeleted*************");
	}
}
