package webpagetest.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import webtestpage.utils.Browser;
import webtestpage.utils.Config;
import webtestpage.utils.Element;

public class TestLogPage {
	
	@FindBy(xpath = "//div[@class='history_hed']/h1[.='Test History']")
	private WebElement testHistoryHeader;

	@FindBy(xpath = "//a[.='Contact Us']")
	private WebElement contactUs;
	
	@FindBy(id = "CompareBtn")
	private WebElement compareButton;
	
	@FindBy(id = "filter")
	private WebElement searchTextField;

	@FindBy(xpath = "//a[.='Terms of Service']")
	private WebElement termsOfService;

	@FindBy(xpath = "//a[.=' Privacy Policy']") 
	private WebElement linkPrivacyPo;

	@FindBy(xpath = "//a[.='Catchpoint Systems, Inc.") 
	private WebElement linkCatchPoint;

	@FindBy(xpath = "//footer/a[@class='logo']")
	private WebElement logoFooter;

	@FindBy(xpath = "//li[@class='footer_social']/a[1]")
	private WebElement gitHub;

	@FindBy(xpath = "//li[@class='footer_social']/a[2]")
	private WebElement twitter;

	@FindBy(xpath = "//li[@class='footer_social']/a[3]") 
	private WebElement youTube;

	@FindBy(xpath = "//li[@class='footer_social']/a[4]") 
	private WebElement linkedIn;
	//li[@class='footer_social']/a[1]
	

	public Config testConfig;


	public TestLogPage(Config testConfig) {
		this.testConfig = testConfig;
		PageFactory.initElements(testConfig.driver, this);
		Browser.waitForPageTitleToContain(testConfig,"WebPageTest - Test History");
		Browser.waitForPageLoad(testConfig, testHistoryHeader);
	}
	
	
	/**
	 * Method to validate TestLogPage
	 * @param testConfig
	 * @return object of same page
	 * 	 * @author sarvpriya.arora
	
	 */
	
	
	
    public TestLogPage searchButton(String text)
    {
    	Element.enterData(testConfig, searchTextField, text, "Filter test History");
    	Element.pressEnter(testConfig);
    	Assert.assertEquals(searchTextField.getText(), "");
    	return this;
    }
    
    // Method to validate Filter Test history result. As i dont have data for this so i could not write this
    
    public TestLogPage validateTestResultTable()
    {
    	searchButton("test");     // This can be picked up from file also using apache poi excel reader
    	validateRecordCount();
    	validateData();
    	return this;
    }
	private void validateData() {
		// TODO Auto-generated method stub
		
	}


	private void validateRecordCount() {
		// TODO Auto-generated method stub
		
	}


	public TestLogPage verifyFooter()
	{
		checkListOfFooterTags().verifyContactUs().verifyGitHubLink().verifylinkCatchPoint().verifyLinkedinLink().verifyPrivacyPolicy().verifyTermsOfService().verifyTwitterLink().verifyYouTubeLink().logoFooter();
		return this;
	}
	
	private TestLogPage verifyContactUs()
	{
		Element.waitForVisibility(testConfig, contactUs, "contact Us link");
		Element.scrollToView(testConfig, contactUs);
		Element.click(testConfig, contactUs, "Click on contact Us link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.product.webpagetest.org/contact");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifyTermsOfService()
	{
		Element.waitForVisibility(testConfig, termsOfService, "termsOfService link");
		Element.scrollToView(testConfig, termsOfService);
		Element.click(testConfig, termsOfService, "Click on terms of service link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.webpagetest.org/terms.php");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifyPrivacyPolicy()
	{
		Element.waitForVisibility(testConfig, linkPrivacyPo, "linkPrivacyPo link");
		Element.click(testConfig, linkPrivacyPo, "Click on linkPrivacyPo link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.catchpoint.com/trust#privacy");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifylinkCatchPoint()
	{
		Element.waitForVisibility(testConfig, linkCatchPoint, "linkCatchPoint link");
		Element.click(testConfig, linkCatchPoint, "Click on linkCatchPoint link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.catchpoint.com/");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage checkListOfFooterTags()
	{
		Element.waitForVisibility(testConfig, gitHub, "gitHub");
		List<WebElement> listSize = testConfig.driver.findElements(By.xpath("//footer/ul/li"));
		Assert.assertEquals(listSize.size(), 5);
		listSize = testConfig.driver.findElements(By.xpath("//li[@class='footer_social']/a"));
		Assert.assertEquals(listSize.size(), 4);
		System.out.println(listSize.get(0));
		return this;
	}
	
	private TestLogPage verifyGitHubLink()
	{
		Element.waitForVisibility(testConfig, gitHub, "gitHub link");
		Element.click(testConfig, gitHub, "Click on gitHub link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://github.com/WPO-Foundation/webpagetest/");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifyTwitterLink()
	{
		Element.waitForVisibility(testConfig, twitter, "twitter link");
		Element.click(testConfig, twitter, "Click on twitter");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://twitter.com/RealWebPageTest");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifyYouTubeLink()
	{
		Element.waitForVisibility(testConfig, youTube, "youTube link");
		Element.click(testConfig, youTube, "Click on youTubelink");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.youtube.com/channel/UC5CqJ9V7cQddZDf1DKXcy7Q");
		testConfig.driver.navigate().back();
		return this;
	}
	
	private TestLogPage verifyLinkedinLink()
	{
		Element.waitForVisibility(testConfig, linkedIn, "linkedIn link");
		Element.click(testConfig, linkedIn, "Click on linkedIn link");
		Assert.assertEquals(testConfig.driver.getCurrentUrl(), "https://www.linkedin.com/company/webpagetest/");
		testConfig.driver.navigate().back();
		return this;
	}
	
	public void logoFooter()
	{
		Element.IsElementDisplayed(testConfig, logoFooter);
	}
	
	public VideoComparePage validateComparetext()
	{
		Element.click(testConfig, compareButton, "Click on compare selected Tests");
		return new VideoComparePage(testConfig);
	}
	
	
	// Various methods correspond to location and size of element can be written like this - 
	
	/*
	 * @FindBy(className = "test_results-content") private WebElement resultContent;
	 * 
	 * public void validateTestResultPage() {
	 * System.out.println(resultContent.getText());
	 * System.out.println(resultContent.getLocation());
	 * System.out.println(resultContent.getSize()); }
	 */
}