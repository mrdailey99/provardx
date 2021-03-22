package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="CaseDetails"                                
     , summary=""
     , relativeUrl=""
     , connection="Admin"
     )             
public class CaseDetails {

	@ButtonType()
	@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneContent')]//button[normalize-space(.)='Clear email and revert']")
	public WebElement publisherClearEmailAndRevert;
	@LinkType()
	@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneContent')]//a[normalize-space(.)='Reply']")
	public WebElement reply;
			
}
