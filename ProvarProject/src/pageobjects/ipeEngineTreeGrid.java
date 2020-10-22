package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="ipeEngineTreeGrid"                                
               , summary=""
               , connection="BusinessBOM"
               , lightningWebComponent="ipeEngineTreeGrid"
               , namespacePrefix=""
     )             
public class ipeEngineTreeGrid {

	WebDriver driver;
	
	public ipeEngineTreeGrid(WebDriver driver) {this.driver = driver;}
	
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-tree-grid//tbody/tr/th//button[@title='Expand AT20003']")
	public WebElement AT20003Expander;
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-tree-grid//table/tbody/tr[contains(@data-row-key-value, 'Fill')]/td[@data-label='OCT 2021']//lightning-base-formatted-text")
	public WebElement October2021FillValue;
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-tree-grid//table/tbody/tr[contains(@data-row-key-value, 'Total PUF')]/td[@data-label='OCT 2021']//lightning-base-formatted-text")
	public WebElement October2021TotalPUFValue;
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-tree-grid//table/tbody/tr[contains(@data-row-key-value, 'SSR')]/td[@data-label='OCT 2021']//lightning-base-formatted-text")
	public WebElement October2021SSRValue;
	@TextType()
	@FindBy(xpath = ".//c-ipe-engine-tree-grid//table/tbody/tr[contains(@data-row-key-value, 'Replen')]/td[@data-label='OCT 2021']//lightning-base-formatted-text")
	public WebElement October2021ReplenValue;
	
	public String RetrieveDateFromTable(String Row, String Date) {
	// DATE = OCT 2021
		WebElement currentDate = driver.findElement(By.xpath(".//c-ipe-engine-tree-grid//table/tbody/tr[contains(@data-row-key-value, '" + Row + "')]/td[@data-label='" + Date + "']//lightning-base-formatted-text"));
		return currentDate.getText();
	}
}
