package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="AddRowModal"                                
     , connection="BusinessBOM"
     )             
public class AddRowModal {
	@TextType()
	@FindBy(xpath = "//input[@name='gskus']")
	public WebElement GSKU;
	@TextType()
	@FindBy(xpath = "//input[@name='Stores__c']")
	public WebElement stores;
	@TextType()
	@FindBy(xpath = "//input[@name='Pegs__c']")
	public WebElement pegs;
	@TextType()
	@FindBy(xpath = "//input[@name='Cards_per_Peg__c']")
	public WebElement cardsPeg;
	@TextType()
	@FindBy(xpath = "//input[@name='Activity_Text__c']")
	public WebElement activity;
	@TextType()
	@FindBy(xpath = "//input[@name='Distribution_Date__c']")
	public WebElement distributionDate;
	@TextType()
	@FindBy(xpath = "//input[@name='Launch_Date__c']")
	public WebElement launchDate;
	@TextType()
	@FindBy(xpath = "//input[@name='Reviewed_Date__c']")
	public WebElement reviewedDate;
	@TextType()
	@FindBy(xpath = "//input[@name='Status__c']")
	public WebElement status;
	@ButtonType()
	@FindBy(xpath = "//div[contains(@class, 'active') and contains(@class, 'open') and (contains(@class, 'forceModal') or contains(@class, 'uiModal'))][last()]//button[normalize-space(.)='Save']")
	public WebElement save;
	@TextType()
	@FindBy(xpath = "//input[@name='Primary_Reason__c']")
	public WebElement primaryReason;
	@TextType()
	@FindBy(xpath = "//input[@name='Notes__c']")
	public WebElement notes;
	@TextType()
	@FindBy(xpath = "//input[@name='Date__c']")
	public WebElement date;
	@TextType()
	@FindBy(xpath = "//input[@name='Total_Units__c']")
	public WebElement totalUnits;
	@BooleanType()
	@FindBy(xpath = "//input[@name='IsIncluded__c']")
	public WebElement included;
	@TextType()
	@FindBy(xpath = "//input[@name='Reason_Text__c']")
	public WebElement reason;
	@TextType()
	@FindBy(xpath = "//input[@name='SSR_Override__c']")
	public WebElement SSROverride;
			
}
