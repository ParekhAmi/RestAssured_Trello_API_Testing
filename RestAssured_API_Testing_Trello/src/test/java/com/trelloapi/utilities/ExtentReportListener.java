package com.trelloapi.utilities;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentReportListener extends TestListenerAdapter {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	public void onStart(ITestContext testContext) {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/extent_report.html");

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("RestAPI Testing Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Environment", "QA");
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, "Test case PASEED IS " + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());

		test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
		test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());

		test.log(Status.SKIP, "TEST CASE SKIPPED IS " + result.getName());
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}