package pageobjects.CPQ;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.provar.core.testapi.annotations.*;
import com.provar.core.testapi.annotations.SteelBrickBy.Facet;

@SalesforcePage( title="Sbqq__ Office Package__ CPQ Product"                                
               , summary=""
               , page="sb"
               , namespacePrefix="SBQQ"
               , object=""
               , connection="CPQ_New"
     )             
public class sbqq__OfficePackage__CPQProduct {

	@ButtonType()
	@SteelBrickBy(customAction = "pcSave")
	public WebElement save;

	@PageBlock()
	public static class OtherOptions {

		@SteelBrickBy(pageFeatureName = "Other Options", pageTableRow = "sb-product-option-table#ot")
		@PageTable(firstRowContainsHeaders = false, row = OtherOptionsLines.class)
		public List<OtherOptionsLines> otherOptionsLines;
	}

	@SteelBrickBy(tabName = "Other Options")
	public OtherOptions otherOptions;

	@PageRow()
	public static class OtherOptionsLines {

		@BooleanType()
		@SteelBrickBy(pageFeatureName = "Other Options", fieldSetTableItem = "checkbox")
		public WebElement checkbox;
	}
	
}
