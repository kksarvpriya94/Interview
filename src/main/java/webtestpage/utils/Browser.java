package webtestpage.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



import io.github.bonigarcia.wdm.WebDriverManager;



public class Browser {
	
	/**
	 * Opens the new browser instance using the given config
	 * 
	 * @return new browser instance
	 * @throws IOException
	 */
	static WebDriver driver;
	@SuppressWarnings("unchecked")
	public static WebDriver openBrowser(Config testConfig)
	{
		
		String browser = testConfig.getRunTimeProperty("Browser");
		switch (browser.toLowerCase())
		{
			case "firefox":
				driver =  Browser.launchFirefox(testConfig);
				if(driver == null){
					System.out.println("Unable to launch firefox switching to chrome  browser");
					driver = Browser.launchChrome(testConfig);
					browser = "chrome";
				}
				break;
			case "chrome":
				driver = Browser.launchChrome(testConfig);
				if(driver == null){
					System.out.println("Unable to launch chrome switching to firefox  browser");
					driver = Browser.launchFirefox(testConfig);
					browser = "firefox";
				}
				break;

			
			default:
				Assert.fail(browser + "- is not supported");
		}
		return driver;
	}
	
	/**
	 * Opens the new firefox instance using the given config
	 *
	 * @return new firefox instance
	 *
	 */
	@SuppressWarnings("unchecked")
	public static WebDriver launchFirefox(Config testConfig){
		ThreadLocal<WebDriver> driver=null;
		int retryCnt = 3;
		while (driver == null && retryCnt > 0)
		{
			try {
				driver=new ThreadLocal<WebDriver>();
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
			}catch (Exception e) {
				System.out.println("Unable to  launch browser :-" + e.getLocalizedMessage());
				System.out.println("Unable to launch firefox browser Retrying !!!!! ");
			}
			retryCnt--;
		}
		return driver.get();
	}

	@SuppressWarnings("unchecked")
	public static WebDriver launchChrome(Config testConfig){
		ThreadLocal<WebDriver> driver = null;
		int retryCnt = 3;
		while (driver == null && retryCnt > 0)
		{
			try {
				driver=new ThreadLocal<WebDriver>();
				 WebDriverManager.chromedriver().setup();
				 driver.set(new ChromeDriver());
				 return driver.get(); 
			}catch (Exception e) {
				System.out.println("Unable to  launch browser :-" + e.getLocalizedMessage());
				System.out.println("Unable to launch chrome browser Retrying !!!!! ");
			}
			retryCnt--;
		}
		return driver.get();
	}
	
	public static void closeBrowser(Config testConfig)
	{
		try
		{
			if (testConfig.driver != null)
			{
	
				((WebDriver) testConfig.driver).close();
			}
		}
		catch (UnreachableBrowserException e)
		{
			System.out.println(e);
		}
	}
	
	public static void quitBrowser(Config testConfig)
	{
		try
		{
			if (testConfig.driver != null)
			{
				((WebDriver) testConfig.driver).quit();
			}
		}
		catch (UnreachableBrowserException e)
		{
			System.out.println(e);
		}
	}
	
	public static void waitForPageLoad(Config testConfig, WebElement element)
	{
		waitForPageLoad(testConfig, element, testConfig.getRunTimeProperty("ObjectWaitTime"));
	}
	/**
	 * This method waits for the given WebElement to appear on the specified browser instance
	 * 
	 * @param testConfig
	 *            test config instance
	 * @param element
	 *            element to be searched
	 * @param objectWaitTime
	 *            - max time to wait for the object
	 */
	public static void waitForPageLoad(Config testConfig, WebElement element, String objectWaitTime)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date startDate = new Date();
		double timeTaken = 0;
		
		// Increase the timeout value
		Long ObjectWaitTime = (long) Float.parseFloat(objectWaitTime);
		
		System.out.println("Started waiting for '"  + "' to load at:- " + dateFormat.format(startDate) + ". Wait upto " + ObjectWaitTime + " seconds.");
		
		// We should not use implicit and explicit wait together, so resetting the implicit wait prior to using explicit wait
		//testConfig.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		//WebDriverWait wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(ObjectWaitTime));
		WebDriverWait wait=new WebDriverWait(testConfig.driver, ObjectWaitTime);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (StaleElementReferenceException e)
		{
			System.out.println("StaleElementReferenceException occured, wait upto additional " + ObjectWaitTime + " seconds.");
			
			try
			{
				wait.until(ExpectedConditions.visibilityOf(element));
			}
			catch (Exception exc)
			{
			
				throw exc;
			}
		}
		catch (TimeoutException e)
		{
			// adding to have extra stability, specifically for test response page load
			System.out.println("'" + "' still not loaded, so wait upto additional " + ObjectWaitTime + " seconds.");
			try
			{
				wait.until(ExpectedConditions.visibilityOf(element));
			}
			catch (TimeoutException tm)
			{
				
				throw new TimeoutException("'"  + "' did not load after waiting for " + 2 * ObjectWaitTime + " seconds");//approximate time
			}
			catch (Exception ee)
			{
				
				System.out.println("'"  + "' NOT loaded even after :- " + timeTaken + " seconds. Exiting...");
				throw ee;
			}
		}
		catch(WebDriverException webDriverException)
		{
			System.out.println("\nWebDriverException or InterruptedException appeared, So trying again...");
			Thread.interrupted();
			
			for (int i = 1; i <= 5; i++)
			{
	            try 
	            {
	                wait.until(ExpectedConditions.visibilityOf(element));
	            } 
	            catch (Throwable exception)
	            {
	            	if(exception.getClass().toString().contains("InterruptedException"))
	            	{
	            		System.out.println("InterruptedException appeared "+(i+1)+" times, So trying again...");
	            		Thread.interrupted();
	            		
	            	}
	            	
	            	else
	            	{
	            		System.out.println("\n<-----Exception in Browser.waitForPageLoad----->");
	            		throw exception;
	            	}
	            }
			}
		}
		catch(Exception e)
		{
			Date date = new Date();
			System.out.println("\n<-----New Exception in Browser.waitForPageLoad----->:-"+date.getTime());
		}
		
	}
	
	public static void navigateToURL(Config testConfig, String url)
	{
		if (testConfig.driver == null)
		{
			testConfig.openBrowser();
		}
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date startDate = new Date();
		System.out.println("Navigate to web page- '" + url + "' at:- "+dateFormat.format(startDate) + " for : " +testConfig.getTestName());
		try
		{
			testConfig.driver.get(url);
		}
		catch(Exception ua)
		{
			System.out.println(ua);
		}
}
	
	/**
	 * This method pause the execution for given interval of time(seconds)
	 * 
	 * @param seconds
	 */
	public static void wait(Config testConfig, int seconds)
	{
		int milliseconds = seconds * 1000;
		try
		{
			Thread.sleep(milliseconds);
			System.out.println("Wait for '" + seconds + "' seconds");
			
		}
		catch (InterruptedException e)
		{
			
		}
	}
	
	/**
	 * this method waits for page title to contain particular title
	 * @param testConfig
	 * @param title
	 */
	public static void waitForPageTitleToContain(Config testConfig, String title)
	{
		System.out.println("Wait for page title to contain '" + title + "'.");
		Long ObjectWaitTime = Long.parseLong(testConfig.getRunTimeProperty("ObjectWaitTime"));
		WebDriverWait wait = new WebDriverWait(testConfig.driver, ObjectWaitTime);
		wait.until(ExpectedConditions.titleContains(title));
	}
	
	
	
}
