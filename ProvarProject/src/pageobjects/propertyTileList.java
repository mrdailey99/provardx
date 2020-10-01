package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="propertyTileList"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="propertyTileList"
               , namespacePrefix=""
     )             
public class propertyTileList {

	@LinkType()
	@JavascriptBy(jspath = "return {provarContext}.querySelector('c-property-tile-list').shadowRoot.querySelector('c-property-tile').shadowRoot.querySelector('a')")
	public WebElement tileOne;
	
}
