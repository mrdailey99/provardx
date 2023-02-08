package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="Edit Record"                                
               , summary=""
               , page="editRecord"
               , namespacePrefix=""
               , object="Account"
               , connection="Admin"
     )             
public class editRecord {

	@VisualforceBy(componentXPath = "apex:inputField[@value = \"{!account.Name}\"]")
	@SalesforceField(name = "Name", object = "Account")
	public WebElement accountName;
	@VisualforceBy(componentXPath = "apex:inputField[@value = \"{!account.NumberOfEmployees}\"]")
	@SalesforceField(name = "NumberOfEmployees", object = "Account")
	public WebElement employees;
	@ButtonType()
	@VisualforceBy(componentXPath = "apex:commandButton[@action='{!quicksave}']")
	public WebElement quickSave;
	@ButtonType()
	@VisualforceBy(componentXPath = "apex:commandButton[@action='{!save}']")
	public WebElement save;
	@VisualforceBy(componentXPath = "apex:inputField[@value = \"{!account.Type}\"]")
	@SalesforceField(name = "Type", object = "Account")
	public WebElement type;
	
}
