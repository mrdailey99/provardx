package customapis;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;


@TestApi( title="Run Batch File"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Command Line"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class RunBatchFile {
    
    @TestApiParameter(seq=1, 
            summary="The first parameter's summary.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String BatchFilePath;

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
    public void execute() throws Exception {
    	testLogger.info("Starting " + this.getClass().getName());
    	Path x = Paths.get(BatchFilePath);
    	String FinalPath = testExecutionContext.getProjectPath()+File.separator+x;
    	String FinalResult = null;
    	
    	ProcessBuilder processBuilder = new ProcessBuilder(FinalPath);
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
            	System.out.println(output);
//                System.exit(0);
            	testLogger.info(output.toString());
            	FinalResult=output.toString();
            	
            } else {
                //abnormal...
            	testLogger.info(output.toString());
            	throw new Exception("batch command failed");
            }

        } catch (IOException e) {
        	testLogger.info(e.getMessage());
            
        } catch (InterruptedException e) {
        	testLogger.info(e.getMessage());
        }

    	String dummyResult = FinalResult+"";
        testExecutionContext.setValue(resultName, dummyResult, resultScope);
        
    }
    
}
