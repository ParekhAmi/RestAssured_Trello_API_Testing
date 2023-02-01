package com.trelloapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trelloapi.base.TestBase;
import com.trelloapi.utilities.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC009_POST_CreateCheckItems_DataDrivenTest extends TestBase {

	public static TestUtil testUtil;
	String sheetName = "Data1";
	JSONObject requestParams = new JSONObject();

	@BeforeClass
	void verifyCheckListCreated() throws InterruptedException {
		logger.info("***********Started TC009_POST_CreateCheckItems_DataDrivenTest*************");

		RestAssured.baseURI = baseURI;

		httpRequest = RestAssured.given();
	}

	@Test(dataProvider = "getCheckItemsTestDataPro")
	void getCheckItemsTestData(String checkItemName, String pos) {
		requestParams.put("key", key);
		requestParams.put("token", token);
		requestParams.put("name", checkItemName);
		requestParams.put("pos", pos);

		// Checklist Id
		httpRequest.pathParam("id", TC007_CreateCheckList.checkListId);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/1/checklists/{id}/checkItems");
		
	}

	@DataProvider
	public Object[][] getCheckItemsTestDataPro() {
		Object data[][] = testUtil.getTestData(sheetName);
		return data;
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
		logger.info("***********FInished TC009_POST_CreateCheckItems_DataDrivenTest*************");
	}
}
//	@DataProvider(name = "checkitemsdataprovider")
//	Object[][] createCheckItems() throws IOException {
//
//		int rowCount = xlsfile.getRowCount("Data1");
//		int colCount = xlsfile.getColumnCount("Data1");
//
//		System.out.println("Row count " + rowCount);
//		System.out.println("Column count " + colCount);
//
//		Object[][] checkItems = new Object[rowCount][colCount];
//
//		for (int i = 1; i <= rowCount; i++) {
//
//			for (int j = 0; j < colCount; j++) {
//
//				checkItems[i - 1][j] = xlsfile.getCellData("Data1", j, i);
//			}
//		}
//		return checkItems;
//	}
