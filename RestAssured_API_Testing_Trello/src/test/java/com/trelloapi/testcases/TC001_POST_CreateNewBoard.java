package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC001_POST_CreateNewBoard extends TestBase {

	public static String boardId;

	@BeforeClass

	void postCreateNewBoard() throws InterruptedException {

		logger.info("***********Strated TC001_POST_CreateNewBoard*************");

		// Specify Base URI
		RestAssured.baseURI = baseURI;

		// Request Object
		httpRequest = RestAssured.given();
		// Request Parameters/ Request payload sending along with post request
		JSONObject requestParams = new JSONObject();

		requestParams.put("key", key);
		requestParams.put("token", token);
		requestParams.put("name", "TrelloRestAssuredBoard");

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString()); // Attach above data to the request

		// Response Object
		response = httpRequest.request(Method.POST, "/1/boards/");

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
	void getBoardId() {
		logger.info("***********Getting Board Id*************");
		boardId = response.jsonPath().get("id");

		logger.info("Board ID--> " + boardId);
		Assert.assertTrue(boardId != null);
	}

	@Test
	void getContentTypeHeaderValue() {
		logger.info("***********Checking ContentType Header*************");
		String contentType = response.header("Content-Type"); // Get reponse header, content-type
		logger.info("Content type--> " + contentType);

		Headers allHeaders = response.headers(); // Capture All Headers from response

		for (Header header : allHeaders) {
			logger.info(header.getName() + "    " + header.getValue());
		}

		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}

	@Test
	void checkStatusCode() {
		logger.info("***********Checking Status Code*************");

		// status code validation
		int responseStatuscode = response.getStatusCode();
		logger.info("Response Status Code---> " + responseStatuscode);

		Assert.assertEquals(responseStatuscode, 200);
	}

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC001_POST_CreateNewBoard*************");
	}
}
