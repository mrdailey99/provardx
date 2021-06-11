package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="ProductExplorer"                                
     , summary=""
     , relativeUrl=""
     , connection="Admin"
     )             
public class ProductExplorer {

	@TextType()
	@FindBy(xpath = "//label[normalize-space(.)='Search Key']/following-sibling::div//input")
	public WebElement searchKey;
	@LinkType()
	@JavascriptBy(jspath = "return document.querySelector('c-product-tile-list').shadowRoot.querySelector('c-product-tile').shadowRoot.querySelector('a')")
	public WebElement tileOne;
	@TextType()
	@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneContent')]//slot[@name='title']/span")
	public WebElement productName;
	@LinkType()
	@FindBy(xpath = "//a[normalize-space(.)='EXPLORE MORE']")
	public WebElement exploreMore;
			
}
