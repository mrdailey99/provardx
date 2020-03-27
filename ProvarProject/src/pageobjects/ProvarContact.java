package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;

@Page( title="ProvarContact"                                
     , summary=""
     , relativeUrl=""
     , connection="ProvarWebsite"
     )             
public class ProvarContact {

	@TextType()
	@FindByLabel(label = "First name:")
	public WebElement firstName;
	@TextType()
	@FindByLabel(label = "Last name:")
	public WebElement lastName;
	@TextType()
	@FindByLabel(label = "Company:")
	public WebElement company;
	@TextType()
	@FindByLabel(label = "Email:")
	public WebElement email;
	@ChoiceListType()
	@FindByLabel(label = "How did you hear about us?:")
	public WebElement howDidYouHearAboutUs;
	@TextType()
	@FindByLabel(label = "Your Message:")
	public WebElement yourMessage;
	@PageFrame()
	public static class Frame {

		@TextType()
		@FindBy(xpath = "//span[@id='recaptcha-anchor']/div[contains(@class,'recaptcha-checkbox-checkmark')]")
		public WebElement CaptchaBox;
	}
	@FindBy(css = "div.g-recaptcha iframe[src='https://www.google.com/recaptcha/api2/anchor?ar=1&k=6LcWltMUAAAAAF1yYfEVCQ5CR3BzEKLWxBrc0Jb8&co=aHR0cHM6Ly93d3cucHJvdmFydGVzdGluZy5jb206NDQz&hl=en&v=vJuUWXolyYJx1oqUVmpPuryQ&size=normal&cb=q19iiq4ieb63']")
	public Frame frame;
}
