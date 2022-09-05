package webtestpage.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;








public class Element {

	/**
	 * Locator technique
	 */
	public static enum How
	{
		className, css, id, linkText, name, partialLinkText, tagName, xPath, accessibility
	};

	public static enum IFrameHow {
		className, css, name, id
	}
	
	/**
	 * This method checks if the element exists in th DOM or not
	 * @param testConfig
	 *            Config instance used for logging
	 * @param element
	 *            WebElement to be checked
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void check(Config testConfig, WebElement element, String description)
	{
		System.out.println("Check '" + description + "'");
		if (!element.isSelected())
		{
			try
			{
				click(testConfig, element, description);
				
			}
			catch (StaleElementReferenceException e)
			{
				System.out.println("Stale element reference exception. Trying again...");
				Browser.waitForPageLoad(testConfig, element);
				click(testConfig, element, description);
			}
			
		}
	}
	
	/**
	 * This method clears the data from the field
	 * @param testConfig
	 *            Config instance used for logging
	 * @param element
	 *            WebElement to be cleared
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void clear(Config testConfig, WebElement element, String description)
	{
		System.out.println("Clear data of '" + description + "'");
		
		element.clear();
		
	}
	
	/**
	 * This method enables cursor to click
	 * @param testConfig
	 *            Config instance used for logging
	 * @param element
	 *            WebElement to be clicked
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void click(Config testConfig, WebElement element, String description)
	{
		if (testConfig.getRunTimeProperty("browser").equalsIgnoreCase("android_web"))
		{
			clickThroughJS(testConfig, element, description);
		}
		else
		{
			System.out.println("Click on '" + description + "'");
			
			try
			{
				JavascriptExecutor jse = (JavascriptExecutor)testConfig.driver;
				jse.executeScript("arguments[0].scrollIntoView(false)", element);
			}
			catch(WebDriverException wde)
			{}
			
			try
			{
				element.click();
			}
			catch (StaleElementReferenceException e)
			{
				System.out.println("Stale element reference exception. Trying again...");
				
				element.click();
				
			}
			catch (UnreachableBrowserException e)
			{
				// testConfig.endExecutionOnfailure = true;
				System.out.println(e);
			}
		}
		
	}
	
	/**
	 * Clicks on element using JavaScript
	 * 
	 * @param testConfig - For logging
	 *
	 * @param elementToBeClicked - Element to be clicked
	 *
	 * @param description -  For logging
	 *
	 */
	public static void clickThroughJS(Config testConfig, WebElement elementToBeClicked, String description)
	{
		JavascriptExecutor js = (JavascriptExecutor) testConfig.driver;
		
		js.executeScript("arguments[0].click();", elementToBeClicked);
		System.out.println("Clicked on " + description);
		
	}
	
	/**
	 * This method double clicks on a particular element
	 * @param testConfig
	 *            test config instance for the driver
	 * @param element
	 *            WebElement to be double clicked
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void doubleClick(Config testConfig, WebElement element, String description)
	{
		System.out.println("Double Click on '" + description + "'");
		Actions action = new Actions(testConfig.driver);
		action.doubleClick(element).perform();
	}
	
	/**
	 * Enters the given value in the specified WebElement
	 * 
	 * @param testConfig - Config instance used for logging
	 *
	 * @param element - WebElement where data needs to be entered
	 *
	 * @param value - value to the entered
	 *
	 * @param description -  logical name of specified WebElement, used for Logging  purposes in report
	  */
	public static void enterData(Config testConfig, WebElement element, String value, String description)
	{
		if (!value.equalsIgnoreCase("{skip}"))
		{
			
			// encode the html characters so that they get printed correctly
			String message = StringUtils.replaceEach(value, new String[] { "&", "\"", "<", ">" }, new String[] { "&amp;", "&quot;", "&lt;", "&gt;" });
			System.out.println("Enter the " + description + " as '" + message + "'");
			element.clear();
			element.sendKeys(value);
			
		}
		else
		{
			System.out.println("Skipped data entry for " + description);
		}
	}
	
	/**
	 * Enters the given  value in the specified WebElement after clicking on it
	 * 
	 * @param testConfig
	 *            Config instance used for logging
	 * @param element
	 *            WebElement where data needs to be entered
	 * @param value
	 *            value to the entered
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void enterDataAfterClick(Config testConfig, WebElement element, String value, String description)
	{
		if (!value.equalsIgnoreCase("{skip}"))
		{
			// encode the html characters so that they get printed correctly
			String message = StringUtils.replaceEach(value, new String[] { "&", "\"", "<", ">" }, new String[] { "&amp;", "&quot;", "&lt;", "&gt;" });
			System.out.println("Enter the " + description + " as '" + message + "'");
			click(testConfig, element, description);
			element.clear();

			element.sendKeys(value);
			
		}
		else
		{
			System.out.println("Skipped data entry for " + description);
		}
	}
	
	
	/**
	 * Wait for element to be stale on the page
	 * 
	 * @param testConfig
	 *            test config instance for the driver instance on which element
	 *            is to be searched
	 * @param element
	 *            element to be searched
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void waitForStaleness(Config testConfig, WebElement element, String description)
	{
		System.out.print("Wait for element '" + description + "' to be stable on the page.");
		Long ObjectWaitTime = Long.parseLong(testConfig.getRunTimeProperty("ObjectWaitTime"));
		WebDriverWait wait = new WebDriverWait(testConfig.driver, ObjectWaitTime);
		try
		{
			wait.until(ExpectedConditions.stalenessOf(element));
		}
		catch (org.openqa.selenium.TimeoutException tm)
		{
			throw new TimeoutException("Waited for element " + description + " to get stale for " + ObjectWaitTime + " seconds");
		}
	}
	
	/**
	 * Wait for element to be visible on the page
	 * 
	 * @param testConfig
	 *            test config instance for the driver instance on which element
	 *            is to be searched
	 * @param element
	 *            element to be searched
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 * @param timeInSeconds
	 *            Polling time
	 */
	public static void waitForVisibility(Config testConfig, WebElement element, int timeInSeconds, String description)
	{
		System.out.print("Wait for element '" + description + "' to be visible on the page.");
		WebDriverWait wait = new WebDriverWait(testConfig.driver,timeInSeconds);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (org.openqa.selenium.TimeoutException tm)
		{
			throw new TimeoutException(description + " not found after waiting for " + timeInSeconds + " seconds");
		}
	}
	
	/**
	 * Wait for element to be visible on the page
	 * 
	 * @param testConfig
	 *            test config instance for the driver instance on which element
	 *            is to be searched
	 * @param element
	 *            element to be searched
	 * @param description
	 *            logical name of specified WebElement, used for Logging
	 *            purposes in report
	 */
	public static void waitForVisibility(Config testConfig, WebElement element, String description)
	{
		System.out.print("Wait for element '" + description + "' to be visible on the page.");
		Long ObjectWaitTime = Long.parseLong(testConfig.getRunTimeProperty("ObjectWaitTime"));
		WebDriverWait wait = new WebDriverWait(testConfig.driver, ObjectWaitTime);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (TimeoutException tm)
		{
			throw new TimeoutException(description + " not found after waiting for " + ObjectWaitTime + " seconds");
		}
	}
	
	public static void verifyTitle(Config testConfig,String title)
	{
		if(testConfig.driver.getTitle().equals(title))
			System.out.println("Verified title");
		else
			System.out.println("Invalid title name");
			
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
	

	/**
	 * This method confirms if the element is displayed or not
	 * @param testConfig
	 * @param element
	 * @return
	 */
	public static Boolean IsElementDisplayed(Config testConfig, WebElement element)
	{
		Boolean visible = true;
		if (element == null)
			return false;
		//String objectWaitTime = testConfig.getRunTimeProperty("ObjectWaitTime");
		//Long ObjectWaitTime = Long.parseLong(objectWaitTime);
		//WebDriverWait wait = new WebDriverWait(testConfig.driver,Duration.ofSeconds(ObjectWaitTime));
		try
		{
			//wait.until(ExpectedConditions.visibilityOf(element));
			testConfig.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			visible = element.isDisplayed();
		}
		catch (StaleElementReferenceException e)
		{
			System.out.println("Stale element reference exception. Trying again...");
			//wait.until(ExpectedConditions.visibilityOf(element));
			visible = element.isDisplayed();
			
		}
		catch (NoSuchElementException e)
		{
			visible = false;
		}
		catch (ElementNotVisibleException e)
		{
			visible = false;
		}
		return visible;
	}
	
	/**
	 * This function is used to scroll an element into view
	 * @param element
	 * @param testConfig
	 */
	
	public static void scrollToView(Config testConfig, WebElement element)
	{
		((JavascriptExecutor) testConfig.driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor jse = (JavascriptExecutor)testConfig.driver;
		jse.executeScript("arguments[0].scrollIntoView(false)", element);
		
	}
	
	public static void pressEnter(Config testConfig)
	{
		Actions action = new Actions(testConfig.driver);
		action.sendKeys(Keys.ENTER).perform();
	}

}
