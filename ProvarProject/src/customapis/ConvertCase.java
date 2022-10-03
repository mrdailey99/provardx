package customapis;


import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Convert Case"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"String Operations"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class ConvertCase {
    
    @TestApiParameter(seq=1, 
            summary="The text that will be converted to all lower/upper case.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String InputText;
    
    @TestApiParameter(seq=2, 
            summary="Convert to upper case.",
            remarks="",
            mandatory=false,
            parameterGroup="inputs")
    public boolean Upper;

    @TestApiParameter(seq=10, 
            summary="The name that the result will be stored under.",
            remarks="",
            mandatory=true,
            parameterGroup="result")
    public String resultName;

    @TestApiParameter(seq=11, 
            summary="The lifespan of the result.",
            remarks="",
            mandatory=true,
            parameterGroup="result",
            defaultValue="Test")
    public ValueScope resultScope;

    /** 
     * Used to write to the test execution log.
     */
    @TestLogger
    public Logger testLogger;
    
    /** 
     * Provides access to facilities, mainly to set and get variable values.
     */
    @TestExecutionContext
    public ITestExecutionContext testExecutionContext;
    
    @TestApiExecutor
    public void execute() {
    	InputText = InputText.toString();
        if (Upper) {
        	testExecutionContext.setValue(resultName, InputText.toUpperCase(), resultScope);
        } else {
        	testExecutionContext.setValue(resultName, InputText.toLowerCase(), resultScope);
        }                               
    }
    
}
