package webtestpage.utils;



import org.testng.Reporter;

public class Log {
	
	

	/**
	 * Utility to log failure reasons, page information, warnings.
	 */
		
		private static Boolean escapeOutput = false;

		/**
		 *
		 * Log comment
		 * @param message
		 * @param testConfig
		 * @param color
		 */
		public static void Comment(String message, Config testConfig, String color)
		{
			if (testConfig.logToStandardOut)
				logToStandard(message);
			if (!escapeOutput)
			{
				message = "<font color='" + color + "'>" + message + "</font></br>";
			}
			Reporter.log(message);
			testConfig.testLog = testConfig.testLog.concat(message);
		}
		
		public static void Comment(String message, Config testConfig)
		{
			Comment(message, testConfig, "Black");
		}
		
		//@Step("Fail:\"{0}\"")
		public static void Fail(String message, Config testConfig)
		{

			failure(message, testConfig);
		}
		
		//@Step("Fail:\"{0}\"")
		public static void FailWithoutPageInfoLogging(String message, Config testConfig)
		{
			failure(message, testConfig);
		}

		/**
		 * Log failure messages
		 * @param message
		 * @param testConfig
		 */
		public static void failure(String message, Config testConfig)
		{

			testConfig.softAssert.fail(message);
			if (testConfig.logToStandardOut)
				logToStandard(message);
			if (!escapeOutput)
			{
				message = "<font color='Red'>" + message + "</font></br>";
			}
			Reporter.log(message);
			testConfig.testLog = testConfig.testLog.concat(message);
			
		
		}

		
		
		private static void logToStandard(String message)
		{
			System.out.println(message);
		}

		

		/**
		 * Log pass message
		 * @param message
		 * @param testConfig
		 */
		//@Step("Pass:\"{0}\"")
		public static void Pass(String message, Config testConfig)
		{
			if (testConfig.logToStandardOut)
				logToStandard(message);
			if (!escapeOutput)
			{
				message = "<font color='Green'>" + message + "</font></br>";
			}
			Reporter.log(message);
			testConfig.testLog = testConfig.testLog.concat(message);
		}

		/**
		 * Log warnings
		 * @param message
		 * @param testConfig
		 */
		//@Step("Warning:\"{0}\"")
		public static void Warning(String message, Config testConfig)
		{
			if (testConfig.logToStandardOut)
				logToStandard(message);
			if (!escapeOutput)
			{
				message = "<font color='Orange'>" + message + "</font></br>";
			}
			Reporter.log(message);
			testConfig.testLog = testConfig.testLog.concat(message);
		}
		
		public static void Warning(String message, Config testConfig, boolean logPageInfo)
		{
			if (logPageInfo)

			
			Warning(message, testConfig);
		}
		
	}



