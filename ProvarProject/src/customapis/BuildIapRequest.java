package customapis;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

import org.openqa.selenium.remote.http.HttpMethod;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi( title="Build IAP Request"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Google API"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class BuildIapRequest {
	
    
    @TestApiParameter(seq=1, 
            summary="Request to add authorization header",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String httpReq;
    
    @TestApiParameter(seq=2, 
            summary="OAuth 2.0 client ID for IAP protected resource",
            remarks="",
            mandatory=false,
            parameterGroup="inputs")
    public String iapClientId;

    @TestApiParameter(seq=10, 
            summary="The name that the result will be stored under.",
            remarks="",
            mandatory=true,
            parameterGroup="result")
    public String OIDCToken;

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
    
  private static final String IAM_SCOPE = "https://www.googleapis.com/auth/iam";

  private static final HttpTransport httpTransport = new NetHttpTransport();

  private BuildIapRequest() {}

  private static IdTokenProvider getIdTokenProvider() throws IOException {
    GoogleCredentials credentials =
        GoogleCredentials.getApplicationDefault().createScoped(Collections.singleton(IAM_SCOPE));

    Preconditions.checkNotNull(credentials, "Expected to load credentials");
    Preconditions.checkState(
        credentials instanceof IdTokenProvider,
        String.format(
            "Expected credentials that can provide id tokens, got %s instead",
            credentials.getClass().getName()));

    return (IdTokenProvider) credentials;
  }

  /**
   * Clone request and add an IAP Bearer Authorization header with signed JWT token.
   *
   * @param request Request to add authorization header
   * @param iapClientId OAuth 2.0 client ID for IAP protected resource
   * @return Clone of request with Bearer style authorization header with signed jwt token.
   * @throws IOException exception creating signed JWT
   */
  @TestApiExecutor
  public void execute()
      throws IOException {
    IdTokenProvider idTokenProvider = getIdTokenProvider();
    IdTokenCredentials credentials =
        IdTokenCredentials.newBuilder()
            .setIdTokenProvider(idTokenProvider)
            .setTargetAudience(iapClientId)
            .build();

    HttpRequest request = new HttpRequest(HttpMethod.POST, httpReq);
    HttpRequestInitializer httpRequestInitializer = new HttpCredentialsAdapter(credentials);
    
    HttpRequest returnRequest = httpTransport
            .createRequestFactory(httpRequestInitializer)
            .buildRequest(request.getRequestMethod(), request.getUrl(), request.getContent());
    testExecutionContext.setValue(OIDCToken, returnRequest, resultScope);
  }
}