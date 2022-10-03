package customapis;


import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Math Power"
        , summary="Return the mathematical exponential power of a number and its exponent."
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Integer Operations"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class MathPower {
    
    @TestApiParameter(seq=1, 
            summary="The base number.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public int base;
    
    @TestApiParameter(seq=2, 
            summary="The exponent of the exponential function.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public int exponent;

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

    	// Put our implementation logic here.
    	testLogger.info("Hello from " + this.getClass().getName());

    	int exponential = (int)(Math.pow(base, exponent)); 
        testExecutionContext.setValue(resultName, exponential, resultScope);
    }
    
}
