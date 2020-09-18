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
	@JavascriptBy(jspath = "return {provarContext}.querySelector('c-property-filter').shadowRoot.querySelector('lightning-slider').shadowRoot.querySelector('input.slds-slider__range')")
	public WebElement maxPrice;
	@TextType()
	@JavascriptBy(jspath = "return {provarContext}.querySelector('c-property-filter').shadowRoot.querySelectorAll('lightning-slider.slds-form-element')[1].shadowRoot.querySelector('input.slds-slider__range')")
	public WebElement bedrooms;
	@TextType()
	@JavascriptBy(jspath = "return {provarContext}.querySelector('c-property-filter').shadowRoot.querySelectorAll('lightning-slider.slds-form-element')[2].shadowRoot.querySelector('input.slds-slider__range')")
	public WebElement bathrooms;
	@ButtonType()
	@FindBy(xpath = ".//c-property-filter//button[normalize-space(.)='Reset']")
	public WebElement reset;
	
}
