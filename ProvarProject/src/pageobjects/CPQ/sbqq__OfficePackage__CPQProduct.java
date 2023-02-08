package pageobjects.CPQ;

import java.util.List;

import org.openqa.selenium.WebElement;
import com.provar.core.testapi.annotations.*;

@SalesforcePage( title="Sbqq__ Office Package__ CPQ Product"                                
               , summary=""
               , page="sb"
               , namespacePrefix="SBQQ"
               , object=""
               , connection="CPQ"
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
