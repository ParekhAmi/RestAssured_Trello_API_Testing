package com.trelloapi.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	public static RequestSpecification httpRequest;
	public static Response response;

	public static final String key = "1ff3778e3c7f7e0a06fdc7cf708b211a";
	public static final String token = "ATTAe51b9f4fd970fc8fca9316f8f54df43e375901ddab29c4ec1ec80df5c420a2018C5C5967";

	public static String baseURI = "https://api.trello.com";
	public JsonPath jsonpath;

	public Logger logger;

	@BeforeClass
	public void setup() {
		logger = Logger.getLogger("TrelloRestAPI");
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
	}
}
