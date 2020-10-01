package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="propertySummary"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="propertySummary"
               , namespacePrefix=""
     )             
public class propertySummary {

	@ButtonType()
	@FindBy(xpath = ".//c-property-summary//slot[@name='actions']//button")
	public WebElement expandPropertyDetails;
	
}
