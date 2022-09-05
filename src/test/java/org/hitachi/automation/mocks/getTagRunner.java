package org.hitachi.automation.mocks;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.netty.MockServer;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

@TestInstance(Lifecycle.PER_CLASS)
public class getTagRunner {
    public static MockServer server;
	public static MockServerClient client;
    @Test
	public Karate runTest()
	{
    	mockResponse200Ok();
    //	mockResponse404NotFound();
		return Karate.run("getTagMockPositiveCase").relativeTo(getClass());
	}
    

	@BeforeAll
	public void setUp()
	{
		server= new MockServer(8082);
		client = new MockServerClient("localhost", server.getLocalPort());
	}
	public static void mockResponse200Ok()
	{
		String body= "{\"name\":\"TestSensivity\",\"description\":\"Intel Data.CRM.MAG Account\", \"domainKey\":\"ra6a3959e9bde14993\"}";
		HttpRequest interceptReq = HttpRequest.request("/api/v2/tag/byname").withQueryStringParameter("tagname","TestSensivity");
		HttpResponse interceptRes = HttpResponse.response(body).withContentType(MediaType.APPLICATION_JSON).withStatusCode(200);
		client.when(interceptReq).respond(interceptRes);
	}
	
	public static void mockResponse404NotFound()
	{
		String body= "{\r\n"
				+ "  \"errors\": {\r\n"
				+ "    \"message\": [\r\n"
				+ "      \"Invalid tagname value\"\r\n"
				+ "    ]\r\n"
				+ "  }\r\n"
				+ "}";
		HttpRequest interceptReq = HttpRequest.request("/api/v2/tag/byname").withQueryStringParameter("tagname","tagname");
		HttpResponse interceptRes = HttpResponse.response(body).withContentType(MediaType.APPLICATION_JSON).withStatusCode(404);
		System.out.println("Sarvpriya "+interceptRes.toString());
		client.when(interceptReq).respond(interceptRes);
	}
	
	@AfterAll
	public void tearDown()
	{
		if(server.isRunning())
			server.stop();
		if(!client.hasStopped())
		client.stop();
	}
	
	
}
