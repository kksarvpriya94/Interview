package org.hitachi.automation;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class getTagRunner {
    @Test
	public Karate runTest()
	{
		return Karate.run("getTag").relativeTo(getClass());
	}
}
