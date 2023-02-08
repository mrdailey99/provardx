package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="SalesforceHome"                                
     , summary=""
     , relativeUrl=""
     , connection="Admin"
     )             
public class SalesforceHome {

	@ButtonType()
	@FindBy(xpath = "//button[normalize-space(.)='View profile']")
	public WebElement viewProfile;
	@LinkType()
	@FindBy(xpath = "//a[normalize-space(.)='Switch to Salesforce Classic']")
	public WebElement switchToSalesforceClassic;
	@LinkType()
	@FindBy(xpath = "//td//a[normalize-space(.)='Switch to Lightning Experience']")
	public WebElement switchToLightningExperience;
			
}
