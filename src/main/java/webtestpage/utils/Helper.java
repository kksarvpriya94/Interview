package webtestpage.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Helper {
	
	/**
	 * Replaces the arguments like {$someArg} present in input string with its
	 * value from RuntimeProperties
	 * 
	 * @param input
	 *            string in which some Argument is present
	 * @return replaced string
	 */
	public static String replaceArgumentsWithRunTimeProperties(Config testConfig, String input)
	{
		if (input.contains("{$"))
		{
			int index = input.indexOf("{$");
			input.length();
			input.indexOf("}", index + 2);
			String key = input.substring(index + 2, input.indexOf("}", index + 2));
			String value = testConfig.getRunTimeProperty(key);
			input = input.replace("{$" + key + "}", value);
			return replaceArgumentsWithRunTimeProperties(testConfig, input);
		}
		else
		{
			return input;
		}

	}
	
	public static String getCurrentDateTime(String format)
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone("IST"));
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

}
