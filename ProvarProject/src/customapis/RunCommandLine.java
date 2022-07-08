package customapis;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Run Command Line"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Custom"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class RunCommandLine {
    
    @TestApiParameter(seq=1, 
            summary="The command to execute from the command line.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String command;

    @TestApiParameter(seq=10, 
            summary="The name that the result will be stored under.",
            remarks="",
            mandatory=true,
            parameterGroup="result",
            defaultValue="false")
    public String status;

    @TestApiParameter(seq=11, 
            summary="The lifespan of the result.",
            remarks="",
            mandatory=true,
            parameterGroup="result",
            defaultValue="Test")
    public ValueScope statusScope;

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

    	testLogger.info("Starting " + this.getClass().getName());

    	try {
			Runtime.getRuntime().exec(command);
	        //testExecutionContext.setValue(result, true, resultScope);
	        testExecutionContext.setValue(status, "true", statusScope);
	        testLogger.log(Level.INFO, "Command: '" + command + "' is running.");
		} catch (IOException e) {
			testExecutionContext.setValue(status, "false", statusScope);
			e.printStackTrace();
			testLogger.log(Level.SEVERE, "Command: '" + command + "' failed to run. See stack trace.");
			testLogger.log(Level.SEVERE, e.getMessage());			
		}
    }    
}
