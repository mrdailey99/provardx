package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="ConvertLead"                                
     , summary=""
     , relativeUrl=""
     , connection="Admin_Sandbox"
     )             
public class ConvertLead {

	@LinkType()
	@FindBy(linkText = "Contact")
	public WebElement contact;
	public WebDriver WebDriver;
	
	public ConvertLead(WebDriver driver) {
		WebDriver = driver;		
	}
	
	public void ClickAccountLink(String companyName) {
		WebElement accountLink = WebDriver.findElement(By.xpath("//div/div/div/a[normalize-space(.)='" + companyName + "']"));
		accountLink.click();
	}
}
