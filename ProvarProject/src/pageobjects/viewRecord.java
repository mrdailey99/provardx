package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="View Record"                                
               , summary=""
               , page="viewRecord"
               , namespacePrefix=""
               , object="Account"
               , connection="Admin"
     )             
public class viewRecord {

	@VisualforceBy(componentXPath = "apex:outputField[@value = \"{!Account.Phone}\"]")
	@SalesforceField(name = "Phone", object = "Account")
	public WebElement phone;
	
}
