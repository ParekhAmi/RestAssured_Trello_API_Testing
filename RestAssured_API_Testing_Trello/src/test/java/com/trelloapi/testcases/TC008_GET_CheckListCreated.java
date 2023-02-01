package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC008_GET_CheckListCreated extends TestBase {

	@BeforeClass
	void verifyCheckListCreated() throws InterruptedException {

		logger.info("***********Started TC008_GET_CheckListCreated*************");
		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("key", key);
		requestParams.put("token", token);

		// Checklist Id
		httpRequest.pathParam("id", TC007_CreateCheckList.checkListId);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.GET, "/1/checklists/{id}");
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

	@AfterClass
	void teaarDown() {
		logger.info("***********FInished TC008_GET_CheckListCreated*************");
	}
}
