package pageobjects.CPQ;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;
import com.provar.core.testapi.annotations.SteelBrickBy.Facet;

@SalesforcePage( title="Sbqq__ Applei Phone X Package__ CPQ Product 1"                                
               , summary=""
               , page="sb"
               , namespacePrefix="SBQQ"
               , object=""
               , connection="CPQ_New"
     )             
public class sbqq__AppleiPhoneXPackage__CPQProduct1 {

	@PageRow()
	public static class MemoryCardsLines {

		@BooleanType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "checkbox")
		public WebElement checkbox;
		
		@TextType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "record.SBQQ__Quantity__c")
		public WebElement quantity;
	}

	@SteelBrickBy(pageFeatureName = "Memory Cards", pageTableRow = "sb-product-option-table#ot")
	@PageTable(firstRowContainsHeaders = false, row = MemoryCardsLines.class)
	public List<MemoryCardsLines> memoryCardsLines;
	
	@PageRow()
	public static class HeadPhonesLines {

		@BooleanType()
		@SteelBrickBy(pageFeatureName = "HeadPhones", fieldSetTableItem = "checkbox")
		public WebElement checkbox;
		
		@TextType()
		@SteelBrickBy(pageFeatureName = "HeadPhones", fieldSetTableItem = "record.SBQQ__Quantity__c")
		public WebElement quantity;
	}

	@SteelBrickBy(pageFeatureName = "HeadPhones", pageTableRow = "sb-product-option-table#ot")
	@PageTable(firstRowContainsHeaders = false, row = MemoryCardsLines.class)
	public List<HeadPhonesLines> headPhonesLines;
	
}
