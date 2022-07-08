package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="OpenedWindow"                                
     , summary=""
     , relativeUrl=""
     , connection="Admin"
     )             
public class OpenedWindow {
	WebDriver driver;
	
	public OpenedWindow (WebDriver _driver) {
		this.driver = _driver;
	}
	
	public String GetURLInOpenedWindow() {
		// 	Switch to new window opened
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}

		// Perform the actions on new window
		String currentURL = driver.getCurrentUrl();
		// Close the new window
		driver.close();

		return currentURL;
	}
			
}
