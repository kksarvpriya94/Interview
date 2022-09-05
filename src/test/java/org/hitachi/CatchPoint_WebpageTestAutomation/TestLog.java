package org.hitachi.CatchPoint_WebpageTestAutomation;

import org.testng.annotations.Test;

import webpagetest.pageobjects.HomePage;
import webpagetest.pageobjects.TestLogPage;
import webtestpage.utils.Config;
import webtestpage.utils.TestBase;



public class TestLog extends TestBase{

	   
		@Test(enabled=true, description = "Verify Test History webpage for a test website", dataProvider = "GetTestConfig", timeOut = 3000000)
		public void testLogPage(Config testConfig) throws Exception
		{
			 HomePage homePage=new HomePage(testConfig);
			TestLogPage testLogPage = homePage.goToTestHistoryPage(testConfig);
			testLogPage.validateTestResultTable().verifyFooter().validateComparetext();
		}
		
		
}
