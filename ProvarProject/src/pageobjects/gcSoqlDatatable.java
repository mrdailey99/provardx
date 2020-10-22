package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="gcSoqlDatatable"                                
               , summary=""
               , connection="BusinessBOM"
               , lightningWebComponent="gcSoqlDatatable"
               , namespacePrefix=""
     )             
     
public class gcSoqlDatatable {

	@ButtonType
	@FindBy(xpath = ".//c-gc-soql-datatable//a[normalize-space(.)='Full Product Transition']")
	public WebElement FullProductTransition;

	@FindBy(xpath = "//flexipage-tab2[contains(@class, 'slds-show')]//c-gc-soql-datatable//table/tbody/tr")
    @PageTable(firstRowContainsHeaders = false, row = ListItems.class)
    public List<ListItems> WorkspaceGSKUList;
    @PageRow()
    public static class ListItems {
       @LinkType
       @FindBy(xpath = ".//th[@data-label='GSKU']")
       public WebElement GSKU;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Product Desc']")
       public WebElement ProductDescription;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Active Product']")
       public WebElement Activity;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Card Status']")
       public WebElement Retailer;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Safety Stock']")
       public WebElement RetStatus;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Ship-to-Sales Ratio (SSR)']")
       public WebElement Distro;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Lead Time']")
       public WebElement Launch;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Order Cycle']")
       public WebElement Stores;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Upcoming Transition']")
       public WebElement Pegs;
       @LinkType
       @FindBy(xpath = ".td//button[normalize-space(.)='Show actions']")
       public WebElement ShowMoreActions;
   }	
}

