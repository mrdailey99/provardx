package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="brokerCard"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="brokerCard"
               , namespacePrefix=""
     )             
public class brokerCard {

	@TextType()
	@FindBy(xpath = ".//c-broker-card//lightning-formatted-text")
	public WebElement brokerName;
	
}
