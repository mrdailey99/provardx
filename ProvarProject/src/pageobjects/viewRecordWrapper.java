package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="viewRecordWrapper"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="viewRecordWrapper"
               , namespacePrefix=""
     )             
public class viewRecordWrapper {

	@TextType()
	@FindBy(xpath = "//lightning-formatted-number")
	public WebElement employees;
	@TextType()
	@FindBy(xpath = "//c-view-record//form//a")
	public WebElement accountPhone;
	@TextType()
	@FindBy(xpath = ".//c-view-record-wrapper//div/div[1]/div[1]/lightning-output-field//lightning-formatted-text")
	public WebElement accountName;
	
}
