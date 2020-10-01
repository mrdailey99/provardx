package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="createRecordWithPrepopulatedValuesWrapper"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="createRecordWithPrepopulatedValuesWrapper"
               , namespacePrefix=""
     )             
public class createRecordWithPrepopulatedValuesWrapper {

	@TextType()
	@JavascriptBy(jspath = "return document.querySelector('lightning-input-field:nth-child(2) > lightning-input > div > input')")
	public WebElement accountName;
	@TextType()
	@JavascriptBy(jspath = "return document.querySelector('lightning-input-field:nth-child(5) > lightning-input > div > input')")
	public WebElement employees;
	@ButtonType()
	@FindBy(xpath = "//button[@name='create']")
	public WebElement save;
	
}
