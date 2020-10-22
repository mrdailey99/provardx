package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="FullProductTransitionModal"                                
     , summary=""
     , relativeUrl=""
     , connection="BusinessBOM"
     )             
public class FullProductTransitionModal {

	@TextType()
	@FindBy(xpath = "//label[normalize-space(.)='*To Product']/following-sibling::div//input")
	public WebElement toProduct;
	@BooleanType()
	@FindBy(xpath = "//fieldset//input[@value='Reason_Other' and @type='radio']")
	public WebElement OtherRadioButton;
	@TextType()
	@FindBy(xpath = "//input[@name='Notes_Required']")
	public WebElement Notes;
	@TextType()
	@FindBy(xpath = "//input[@name='Effective_Date']")
	public WebElement EffectiveDate;
	@ButtonType()
	@FindByLabel(label = "Next")
	public WebElement Next;
			
}
