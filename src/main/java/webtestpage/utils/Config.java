package webtestpage.utils;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.SessionId;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.asserts.SoftAssert;


import java.lang.reflect.Method;


/**
 * This utility helps in configuring the cached data and reading the data from file
 */
public class Config
{

	public enum SheetToBeUsed
	{
		Common
	}

	// parameters that can be overridden through command line and are same for
	// all executing tests
	//public static ChromeDriverService service;
	public static String BrowserName;
	public static String Environment;
	public static String ProjectName;
	public static String fileSeparator = File.separator;
	public static Properties staticProperties = null;
	// parameters different for every test
     public WebDriver driver;
		public boolean logToStandardOut = true;
	Properties runtimeProperties;

	
	public SoftAssert softAssert;
	

	String testEndTime;
	
	public String testLog;
	public String testName;
	
	public boolean testResult;
	
	// package fields
	String testStartTime;
	
	public String previousPage="";
	public SessionId session=null;
	public String uniqueId = null;
	public List<String> listOfMerchants;
	
	public Config(Method method)
	{
						this.softAssert = new SoftAssert();
		// override environment if declared in @TestVariables
		this.runtimeProperties = new Properties();
		Enumeration<Object> em = staticProperties.keys();
		while (em.hasMoreElements())
		{
			String str = (String) em.nextElement();
			putRunTimeProperty(str, (String) staticProperties.get(str));
		}
				Properties property = new Properties();
					property.put("Environment", "test");
	}


	/**
	 * closes the browser
	 */
	  @AfterSuite
	public void closeBrowser()
	{
		logToStandardOut = true;
		
		Browser.quitBrowser(this);
		driver = null;
	}

	/**
	 * This method closes the browser based on the ITest Result passed
	 * @param result - ITest result
	 */
	public void closeBrowser(ITestResult result)
	{
		try
		{
			Browser.closeBrowser(this);
		}
		catch (Exception e)
		{
		}
		
		try
		{
			Browser.quitBrowser(this);
		}
		catch (Exception ex)
		{
		}
		
		try
		{
			driver.switchTo().defaultContent();
			Browser.closeBrowser(this);
		}
		catch (Exception e)
		{
		}
		
		try
		{
			driver.switchTo().defaultContent();
			Browser.quitBrowser(this);
		}
		catch (Exception ex)
		{
		}
		
		driver = null;
	}
	

	

	

	

	
	
	
	/**
	 * Get the Run Time Property value
	 * 
	 * @param key - key name whose value is needed
	 *
	 * @return value of the specified key
	 */
	public Object getObjectRunTimeProperty(String key)
	{
		String keyName = key.toLowerCase();
		Object value = "";
		try
		{
			value = runtimeProperties.get(keyName);
				System.out.println("Reading Run-Time key-" + keyName + " value:-'" + value + "'");
		}
		catch (Exception e)
		{
			
				System.out.println(e.toString());
				System.out.println("'" + key + "' not found in Run Time Properties");
			
			return null;
		}
		return value;
	}
	
	
	
	/**
	 * Get the Run Time Property value
	 * @param key - key name whose value is needed
	 * @return value of the specified key
	 */
	public String getRunTimeProperty(String key)
	{
		String keyName = key.toLowerCase();
		String value = "";
		try
		{
			value = runtimeProperties.get(keyName).toString();
			value = Helper.replaceArgumentsWithRunTimeProperties(this, value);
			
				System.out.println("Reading Run-Time key-" + keyName + " value:-'" + value + "'");
		}
		catch (Exception e)
		{
		
				System.out.println(e.toString());
			
			
			
			return null;
		}
		return value;
	}
	
	public String getTestName()
	{
		return testName;
	}
	
	public boolean getTestCaseResult()
	{
		return testResult;
	}


	
	/**
	 * Opens the web browser
	 */

	public void openBrowser()
	{
			try
			{
				this.driver = Browser.openBrowser(this);
				driver.manage().window().maximize();
			}
			catch (Exception e)
			{
				Log.Warning("Unable to  launch browser :-" + e.getLocalizedMessage(), this);
			}
			/*
			if (this.driver == null)
			{
				if (!this.getRunTimeProperty("RemoteExecution").equals("No"))
				{
					Log.Comment("Deleting temporary files from folder for : "+getTestName(), this);
					TemporaryFilesystem tempFS = TemporaryFilesystem.getTmpFsBasedOn(new File((Config.SharedDirectory + Config.ProjectName + fileSeparator + "SeleniumTemp" + fileSeparator + Config.BuildId + fileSeparator)+this.getTestName()));
					tempFS.deleteTemporaryFiles();
					Browser.wait(this, 30);
				}
			}

			 */
	
	}


	
	/**
	 * Add the given key value pair in the Run Time Properties
	 * 
	 * @param key
	 * @param value
	 */
	public void putRunTimeProperty(String key, Object value)
	{
		String keyName = key.toLowerCase();
		runtimeProperties.put(keyName, value);
		
			System.out.println("Putting Run-Time key-" + keyName + " value:-'" + value + "'");
	}
	
	/**
	 * Add the given key value pair in the Run Time Properties
	 * 
	 * @param key
	 * @param value
	 */
	public void putRunTimeProperty(String key, String value)
	{
		String keyName = key.toLowerCase();
		runtimeProperties.put(keyName, value);
		System.out.println("Putting Run-Time key-" + keyName + " value:-'" + value + "'");
	}
	
	/**
	 * Removes the given key from the Run Time Properties
	 * 
	 * @param key
	 */
	public void removeRunTimeProperty(String key)
	{
		String keyName = key.toLowerCase();
		
		System.out.println("Removing Run-Time key-" + keyName);
		runtimeProperties.remove(keyName);
	}

	
	
	
	
	
	protected static void loadPropertiesFile(){
		// Read the Config file

		staticProperties = new Properties();
		String path =null;
		String configPropertiesInResources = System.getProperty("user.dir") + fileSeparator+ "src" +fileSeparator+  "main" +fileSeparator +  "resources"+ fileSeparator +"parameters"+ fileSeparator + "Config.properties";
		File filePath = new File(configPropertiesInResources);
		if(filePath.isFile()){
			path = configPropertiesInResources;
		}else{
            path = System.getProperty("user.dir") + fileSeparator +"Parameters"+ fileSeparator + "Config.properties";
		}
		String message = "Reading  the config file" + path;
	
		System.out.println(message);
		Reporter.log(message);
		FileInputStream fn = null;
		try {
			fn = new FileInputStream(path);
			staticProperties.load(fn);
			fn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// override the environment value if passed through ant command line
		if (!(Environment == null || Environment.isEmpty()))
			staticProperties.put("Environment", Environment.toUpperCase());


		if (!(BrowserName == null || BrowserName.isEmpty()))
			staticProperties.put("Browser", BrowserName);


		String environmentPropertiesInResources = System.getProperty("user.dir") + fileSeparator+ "src" +fileSeparator+  "main" +fileSeparator +  "resources" + fileSeparator +"parameters"+ fileSeparator + staticProperties.get("Environment") + ".properties";
		if(filePath.isFile()){
			path = environmentPropertiesInResources;
		}else{
			path = System.getProperty("user.dir") + fileSeparator +"Parameters"+ fileSeparator + staticProperties.get("Environment") + ".properties";
		}
		message = "Read the environment file:- " + path;
	
		System.out.println(message);
		Reporter.log(message);
		try {
			fn = new FileInputStream(path);
			staticProperties.load(fn);
			fn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}



