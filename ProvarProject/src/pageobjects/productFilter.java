package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="productFilter"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="productFilter"
               , namespacePrefix=""
     )             
public class productFilter {

	@TextType()
	@FindBy(xpath = ".//c-product-filter//lightning-input/div/input")
	public WebElement searchKey;
	
}
