package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="viewRecordWithParentRecordDataWrapper"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="viewRecordWithParentRecordDataWrapper"
               , namespacePrefix=""
     )             
public class viewRecordWithParentRecordDataWrapper {

	@TextType()
	@FindBy(xpath = ".//c-view-record-with-parent-record-data-wrapper//div[1]/lightning-output-field//lightning-formatted-text")
	public WebElement accountName;
	
}
