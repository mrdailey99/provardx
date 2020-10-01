package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="editRecordWrapper"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="editRecordWrapper"
               , namespacePrefix=""
     )             
public class editRecordWrapper {

	@TextType()
	@JavascriptBy(jspath = "return document.querySelector('div:nth-child(1) > div:nth-child(1) > lightning-input-field > lightning-input > div > input')")
	public WebElement accountName;
	@TextType()
	@JavascriptBy(jspath = "return document.querySelector('div:nth-child(2) > lightning-input-field > lightning-picklist > lightning-combobox > div > lightning-base-combobox > div > div.slds-combobox__form-element.slds-input-has-icon.slds-input-has-icon_right > input')")
	public WebElement accountType;
	@TextType()
	@FindBy(xpath = "//input[@name='NumberOfEmployees']")
	public WebElement employees;
	@ButtonType()
	@FindByLabel(label = "Save")
	public WebElement save;
	@TextType()
	@FindBy(xpath = "//input[@name='Phone']")
	public WebElement accountPhone;
	
}
