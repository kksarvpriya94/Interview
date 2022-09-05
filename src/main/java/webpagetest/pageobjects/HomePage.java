package webpagetest.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webtestpage.utils.Browser;
import webtestpage.utils.Config;
import webtestpage.utils.Element;



public class HomePage {
	
	@FindBy(xpath="//a[@class='wptheader_logo']")
	WebElement wpLogo;
	@FindBy(xpath="//*[@class='wptheader_menubtn']")
	WebElement wptHeaderMenu;
	@FindBy(xpath="//li/a/span[.='Test History']")
	WebElement testHistoryLink;

	Config testConfig=null;

	public HomePage(Config testConfig)
	{
		this.testConfig=testConfig;
		Browser.navigateToURL(testConfig, testConfig.getRunTimeProperty("HomePage"));
		PageFactory.initElements(this.testConfig.driver, this);
			Browser.waitForPageLoad(testConfig, wpLogo);
	}

	public HomePage clickMenu(Config testConfig) {
			Element.click(testConfig, wptHeaderMenu, "Clicking on Header menu dropdown");
			Element.waitForVisibility(testConfig, testHistoryLink, "Causing delay");
		return this;
	}
	
	public void clickTestHistory(Config testConfig) {
		Element.waitForVisibility(testConfig, testHistoryLink, 10000, "Wait for Test History to display");
		if(Element.IsElementDisplayed(testConfig, testHistoryLink))
		Element.click(testConfig, testHistoryLink, "Clicking on Test History");
   }

	public TestLogPage goToTestHistoryPage(Config testConfig) {
		clickMenu(testConfig).clickTestHistory(testConfig);
	    return new TestLogPage(testConfig);
   }
}
