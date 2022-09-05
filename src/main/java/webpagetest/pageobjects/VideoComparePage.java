package webpagetest.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webtestpage.utils.Browser;
import webtestpage.utils.Config;


public class VideoComparePage {
	
	
	@FindBy(className = "test_results-content")
	private WebElement resultContent;
	
	Config testConfig=null;
	
	public VideoComparePage(Config testConfig)
	{
		this.testConfig=testConfig;
		PageFactory.initElements(this.testConfig.driver, this);
			Browser.waitForPageLoad(testConfig, resultContent);
	}
	
	public void validateTestResultPage()
	{
		System.out.println(resultContent.getText());
		System.out.println(resultContent.getLocation());
		System.out.println(resultContent.getSize());
	}

}
