package customapis;

import java.util.logging.Logger;
import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Random Integer"
        , summary="Picks a random integer from list of values."
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Integer Operations"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class RandomInteger {
    
    @TestApiParameter(seq=1, 
            summary="The number of digits to pick a random value from.",
            remarks="1=0-9; 2=0-99",
            mandatory=true,
            parameterGroup="inputs")
    public int NumberOfDigits;
    
    @TestApiParameter(seq=2, 
            summary="Whether or not to include zero in the random list of integers.",
            remarks="True excludes '0' from possible value in the list.",
            mandatory=false,
            parameterGroup="inputs")
    public boolean ExcludeZero;

    @TestApiParameter(seq=3, 
            summary="The number of numbers to select from.",
            remarks="1=1, 10=1,2,3,4,5,6,7,8,9,10",
            mandatory=true,
            parameterGroup="inputs",
            defaultValue="-1")
    public int NumberOfNumbers;
    
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
    	
        // define the range 
        int max = (int)Math.pow(10, NumberOfDigits)-1;
        int min = 1;
        if (!ExcludeZero) {
        	min = 0;
        }
        
        int range = max - min + 1; 

        // generate random numbers within range
        int randomValue = 0;
        if (NumberOfNumbers > 0) {
        	randomValue = (int)(Math.random() * NumberOfNumbers) + min;	
        } else {
        	randomValue = (int)(Math.random() * range) + min;	
        }        
         
		testExecutionContext.setValue(resultName, randomValue, resultScope); 
    }
    
}
