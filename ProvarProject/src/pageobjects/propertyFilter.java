package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="propertyFilter"                                
               , summary=""
               , connection="Admin"
               , lightningWebComponent="propertyFilter"
               , namespacePrefix=""
     )             
public class propertyFilter {

	@TextType()
	@FindBy(xpath = ".//c-property-filter//lightning-input//input[@type='text']")
	public WebElement searchKey;
	@TextType()
	@FindBy(xpath = ".//c-property-filter//label[normalize-space(.)='Max Price0-1200000']/following-sibling::div//input")
	public WebElement maxPrice;
	@TextType()
	@FindBy(xpath = ".//c-property-filter//label[normalize-space(.)='Bedrooms0-6']/following-sibling::div//input")
	public WebElement bedrooms;
	@TextType()
	@FindBy(xpath = ".//c-property-filter//label[normalize-space(.)='Bathrooms0-6']/following-sibling::div//input")
	public WebElement bathrooms;
	
}
