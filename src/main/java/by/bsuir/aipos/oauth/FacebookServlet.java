package by.bsuir.aipos.oauth;
import java.io.*;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsuir.aipos.servlet.StudentLogger;
import org.apache.commons.httpclient.methods.PostMethod;

@WebServlet(urlPatterns = {"/fb/oauth" }, initParams = {
        @WebInitParam(name = "clientId", value = "293015447783423"),
        @WebInitParam(name = "clientSecret", value = "1662772433c316cf0e9e24619a4cbaca"),
        @WebInitParam(name = "redirectUri", value = "http://localhost:8080/fb/oauth"),
        @WebInitParam(name = "authUrl", value = "https://www.facebook.com"),
        @WebInitParam(name = "tokenUrl", value = "https://graph.facebook.com"),})
public class FacebookServlet extends HttpServlet implements OAuthServlet{

    private String clientId = null;
    private String clientSecret = null;
    private String redirectUri = null;
    private String authUrl = null;
    private String tokenUrl = null;

    public void init() throws ServletException {
        clientId = this.getInitParameter("clientId");
        clientSecret = this.getInitParameter("clientSecret");
        redirectUri = this.getInitParameter("redirectUri");
        try {
            authUrl = this.getInitParameter("authUrl")
                    + "/v2.8/dialog/oauth?" +
                    "client_id=" + clientId +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                    "&response_type=code";
        } catch (UnsupportedEncodingException e) {
            throw new ServletException(e);
        }
        tokenUrl = this.getInitParameter("tokenUrl") + "/oauth/access_token";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OAuthUtils oAuthUtils = new OAuthUtils(this, request, response);
        oAuthUtils.doGet();
    }

    @Override
    public String getToken(PostMethod post){
        String accessToken = "";
        try {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
            String inputLine;
            StringBuffer b;
            b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine).append("\n");
            in.close();
            String response = b.toString();
            StudentLogger.getLogger().info("Auth response:\n  " + response);
            int startAccessToken = response.indexOf("=") + 1;
            int finishAccessToken = response.indexOf("&");
            accessToken = response.substring(startAccessToken, finishAccessToken);
        } catch (IOException e) {
            StudentLogger.getLogger().error("IOException in FB");
            StudentLogger.getLogger().trace(e);
        }
        return accessToken;
    }

    @Override
    public boolean getError(HttpServletRequest request) {
        return request.getParameter("error") != null &&
                request.getParameter("error").equals("invalid_request");
    }

    @Override
    public boolean notHaveCode(HttpServletRequest request) {
        return request.getParameter("code") == null;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getAuthUrl() {
        return authUrl;
    }

    @Override
    public String getTokenUrl() {
        return tokenUrl;
    }
}

