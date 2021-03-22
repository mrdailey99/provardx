package pageobjects.CPQ;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.provar.core.testapi.annotations.*;
import com.provar.core.testapi.annotations.SteelBrickBy.Facet;

@SalesforcePage( title="Sbqq__sb"                                
               , page="sb"
               , namespacePrefix="SBQQ"
               , connection="CPQ_New"
     )             
public class sbqq__sb {

	@PageRow()
	public static class Group1Lines {

		@BooleanType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "checkboxcontainer")
		public WebElement checkboxcontainer;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__ProductCode__c")
		public WebElement sBQQProductCodeC;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__ListPrice__c")
		public WebElement sBQQListPriceC;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__NetPrice__c")
		public WebElement sBQQNetPriceC;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__NetTotal__c")
		public WebElement sBQQNetTotalC;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__ProductName__c")
		public WebElement sBQQProductNameC;
		@ButtonType()
		@SteelBrickBy(pageGroupName = "Group1", customAction = "Add To Favorites")
		public WebElement addToFavorites;
		@ButtonType()
		@SteelBrickBy(pageGroupName = "Group1", customAction = "Clone Line")
		public WebElement cloneLine;
		@ButtonType()
		@SteelBrickBy(pageGroupName = "Group1", customAction = "Delete Line")
		public WebElement deleteLine;
		@ButtonType()
		@SteelBrickBy(pageGroupName = "Group1", customAction = "Reconfigure Line")
		public WebElement reconfigureLine;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__AdditionalDiscount__c")
		public WebElement sBQQAdditionalDiscountC;
		@TextType()
		@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__Quantity__c")
		public WebElement sBQQQuantityC;
	}

	@SteelBrickBy(pageGroupName = "Group1", pageTableRow = "sf-standard-table#standardLines")
	@PageTable(firstRowContainsHeaders = false, row = Group1Lines.class)
	public List<Group1Lines> group1Lines;
	@TextType()
	@SteelBrickBy(fieldSetTableItem = "SBQQ__StartDate__c")
	public WebElement startDate;
	@ButtonType()
	@SteelBrickBy(customAction = "Cancel")
	public WebElement cancel;
	@ButtonType()
	@SteelBrickBy(customAction = "Save")
	public WebElement save;
	@ButtonType()
	@SteelBrickBy(customAction = "Add Products")
	public WebElement addProducts;
	
	@PageRow()
	public static class ProductSelectionLines {

		@BooleanType()
		@SteelBrickBy(fieldSetTableItem = "checkbox")
		public WebElement checkbox;
		@ButtonType()
		@SteelBrickBy(fieldSetTableItem = "Product2.ProductCode")
		public WebElement productCode;
		@BooleanType()
		@SteelBrickBy(fieldSetTableItem = "checkbox")
		public WebElement checkbox1;
	}
	@SteelBrickBy(pageTableRow = "sb-lookup-layout#lookupLayout")
	@PageTable(firstRowContainsHeaders = false, row = ProductSelectionLines.class)
	public List<ProductSelectionLines> productSelectionLines;
	@ButtonType()
	@SteelBrickBy(customAction = "plSelectMore")
	public WebElement selectAndAddMore;
	@TextType()
	@SteelBrickBy(fieldSetTableItem = "itemLabel")
	public WebElement searchProducts;
	@ButtonType()
	@SteelBrickBy(customAction = "plSelect")
	public WebElement select;
	@ButtonType()
	@SteelBrickBy(customAction = "fb")
	public WebElement filter;
	@TextType()
	@SteelBrickBy(customAction = "clearfilter")
	public WebElement clearFields;
	@TextType()
	@SteelBrickBy(filterItems = "ProductCode.value")
	public WebElement productCodeValue;
	@TextType()
	@SteelBrickBy(filterItems = "Name.value")
	public WebElement nameValue;
	@TextType()
	@SteelBrickBy(filterItems = "Family.value")
	public WebElement familyValue;
	@ButtonType()
	@SteelBrickBy(customAction = "submit")
	public WebElement submit;
	@TextType()
	@SteelBrickBy(fieldSetTableItem = "sb-le-footer")
	public WebElement sbLeFooter;
	@ButtonType()
	@SteelBrickBy(customAction = "Add Group")
	public WebElement addGroup;
	@ButtonType()
	@SteelBrickBy(customAction = "cloneGroupBtn")
	public WebElement cloneGroupBtn;
	@ButtonType()
	@SteelBrickBy(customAction = "deleteGroupBtn")
	public WebElement deleteGroupBtn;
	@ButtonType()
	@SteelBrickBy(customAction = "resetDiscBtn")
	public WebElement resetDiscBtn;
	@ButtonType()
	@SteelBrickBy(customAction = "Quick Save")
	public WebElement quickSave;
	@TextType()
	@SteelBrickBy(pageGroupName = "Group1", fieldSetTableItem = "SBQQ__AdditionalDiscountRate__c")
	public WebElement additionalDisc;
	@ButtonType()
	@SteelBrickBy(customAction = "Calculate")
	public WebElement calculate;
	@ButtonType()
	@SteelBrickBy(customAction = "Ungroup")
	public WebElement ungroup;
	@ButtonType()
	@SteelBrickBy(customAction = "addFavoritesBtn")
	public WebElement addFavoritesBtn;
}
