package webtestpage.utils;

import java.lang.reflect.Method;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;




public class TestBase {
	
	/**
	 * Setting up environmental configurations
	 * @param environment
	 */
	protected final static long DEFAULT_TEST_TIMEOUT = 600000;
	protected static ThreadLocal<Config[]> threadLocalConfig = new ThreadLocal<Config[]>();
	@BeforeSuite(alwaysRun = true)
	@Parameters({ "browser", "environment","projectName" })
	public void beforeSuiteSetup(@Optional String browser, @Optional String environment,@Optional String projectName)
	{
		System.out.println("Current Free Memory Before Suite Execution Starting " + Runtime.getRuntime().freeMemory());
		Config.BrowserName = browser;
		Config.Environment = environment;
		Config.ProjectName = projectName;
		System.out.println("Thread ID  "+Thread.currentThread().getId() + " Inside before suite......");
		try
		{
			
			Config.loadPropertiesFile();
			projectName = Config.staticProperties.getProperty("projectName");
			browser = Config.staticProperties.getProperty("Browser");
			environment = Config.staticProperties.getProperty("Environment");

			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Get configurations
	 * @param method
	 * @return
	 */
	@DataProvider(name = "GetTestConfig")
	public Object[][] GetTestConfig(Method method)
	{
		Config testConf = new Config(method);
		testConf.testName = method.getDeclaringClass().getName() + "." + method.getName();
		testConf.testStartTime = Helper.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
		threadLocalConfig.set(new Config[] { testConf });
		return new Object[][] { { testConf } };
	}
	
	
	

}
