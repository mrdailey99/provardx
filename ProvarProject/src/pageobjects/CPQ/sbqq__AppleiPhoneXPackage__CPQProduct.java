package pageobjects.CPQ;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="Sbqq__ Applei Phone X Package__ CPQ Product"                                
               , summary=""
               , page="sb"
               , namespacePrefix="SBQQ"
               , object=""
               , connection="CPQ"
     )             
public class sbqq__AppleiPhoneXPackage__CPQProduct {

	@PageRow()
	public static class ConfigureProductsLines {


		@TextType()
		@SteelBrickBy(fieldSetTableItem = "record.SBQQ__Quantity__c")
		public WebElement quantity;
		@BooleanType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "checkbox")
		public WebElement checkbox;
		@TextType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "record.SBQQ__Quantity__c")
		public WebElement editquantity;
		@BooleanType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "checkbox")
		public WebElement checkbox1;
		@TextType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "record.SBQQ__Quantity__c")
		public WebElement quantity1;
		@BooleanType()
		@SteelBrickBy(pageFeatureName = "HeadPhones", fieldSetTableItem = "checkbox")
		public WebElement checkbox2;
	}

	@SteelBrickBy(pageTableRow = "sb-product-option-table#ot")
	@PageTable(firstRowContainsHeaders = false, row = ConfigureProductsLines.class)
	public List<ConfigureProductsLines> configureProductsLines;
	@ButtonType()
	@SteelBrickBy(customAction = "pcSave")
	public WebElement save;
	@ButtonType()
	@SteelBrickBy(customAction = "pcCancel")
	public WebElement cancel;
	@PageRow()
	public static class ConfigureProductsLinesMemC {

		@BooleanType()
		@SteelBrickBy(pageFeatureName = "Memory Cards", fieldSetTableItem = "checkbox")
		public WebElement checkbox;
	}

	@SteelBrickBy(pageFeatureName = "Memory Cards", pageTableRow = "sb-product-feature-list")
	@PageTable(firstRowContainsHeaders = false, row = ConfigureProductsLinesMemC.class)
	public List<ConfigureProductsLinesMemC> configureProductsLinesMemC;
	
}
