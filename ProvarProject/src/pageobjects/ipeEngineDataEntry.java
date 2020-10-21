package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="ipeEngineDataEntry"                                
               , summary=""
               , connection="BusinessBOM"
               , lightningWebComponent="ipeEngineDataEntry"
               , namespacePrefix=""
     )             
public class ipeEngineDataEntry {
	WebDriver driver;
	public ipeEngineDataEntry(WebDriver driver){ this.driver = driver;}

	@ButtonType()
	@FindBy(xpath = ".//c-ipe-engine-data-entry//button[normalize-space(.)='New']")
	public WebElement new_;
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-data-entry//tr[7]//span[normalize-space(.)='AT_15_1000327_1-step']//lightning-base-formatted-text")
	public WebElement ProductDesc;
	
	public int getRowCount(){
	List<WebElement> list = driver.findElements(By.xpath("//flexipage-tab2[contains(@class, 'slds-show')]//c-ipe-engine-data-entry//table/tbody/tr"));
		return list.size();
	}
	
	@FindBy(xpath = ".//c-ipe-engine-data-entry//table/tbody/tr")
    @PageTable(firstRowContainsHeaders = false, row = FillListItems.class)
    public List<FillListItems> FillList;
    @PageRow()
    public static class FillListItems {
       @LinkType
       @FindBy(xpath = ".//th[@data-label='IPE Fill']")
       public WebElement IPEFill;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='GSKU']")
       public WebElement GSKU;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Product Desc']")
       public WebElement ProductDescription;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Activity']")
       public WebElement Activity;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Retailer']")
       public WebElement Retailer;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Ret Status']")
       public WebElement RetStatus;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Distro']")
       public WebElement Distro;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Launch']")
       public WebElement Launch;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Stores']")
       public WebElement Stores;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Pegs']")
       public WebElement Pegs;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Cards/P']")
       public WebElement CardsPerPeg;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Units']")
       public WebElement Units;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Status']")
       public WebElement Status;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Incl?']")
       public WebElement Incl;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Reviewed']")
       public WebElement Reviewed;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Last Mod']")
       public WebElement LastMod;
   }
   
    @FindBy(xpath = ".//c-ipe-engine-data-entry//table/tbody/tr")
    @PageTable(firstRowContainsHeaders = false, row = TotalPUFListItems.class)
    public List<TotalPUFListItems> TotalPUFList;
    @PageRow()
    public static class TotalPUFListItems {
       @LinkType
       @FindBy(xpath = ".//th[@data-label='IPE Total PUF Name']")
       public WebElement IPETotalPUFName;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='GSKU']")
       public WebElement GSKU;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Product Desc']")
       public WebElement ProductDescription;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Reason']")
       public WebElement Reason;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Notes']")
       public WebElement Notes;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Date']")
       public WebElement Date;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Current PUF']")
       public WebElement CurrentPUF;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Total Units']")
       public WebElement TotalUnits;
       @LinkType
       @FindBy(xpath = ".//td[@data-label='Incl?']")
       public WebElement Included;
   }
}
