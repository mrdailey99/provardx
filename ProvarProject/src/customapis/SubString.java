package customapis;


import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Substring"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"String Operations"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class SubString {
    
    @TestApiParameter(seq=1, 
            summary="The string to be trimmed.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String InputText;
    
    @TestApiParameter(seq=2, 
            summary="The number of digits to trim.",
            remarks="",
            mandatory=false,
            parameterGroup="inputs")
    public Integer Digits;

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
    	String result = null;
    	if ((InputText != null) && (InputText.length() > 0)) {
      		result = InputText.substring(0, InputText.length() - Digits);
   		}
    	testExecutionContext.setValue(resultName, result, resultScope);       
    }
    
}
