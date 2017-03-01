package by.bsuir.aipos.oauth;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsuir.aipos.servlet.StudentLogger;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@WebServlet(urlPatterns = {"/vk/oauth" }, initParams = {
        @WebInitParam(name = "clientId", value = "5900444"),
        @WebInitParam(name = "clientSecret", value = "wGxkQqI6MTgmVTQxpkYQ"),
        @WebInitParam(name = "redirectUri", value = "http://localhost:8080/vk/oauth"),
        @WebInitParam(name = "environment", value = "https://oauth.vk.com/"), })
public class VkontakteServlet extends HttpServlet implements OAuthServlet{

    private String clientId = null;
    private String clientSecret = null;
    private String redirectUri = null;
    private String authUrl = null;
    private String tokenUrl = null;

    public void init() throws ServletException {
        clientId = this.getInitParameter("clientId");
        clientSecret = this.getInitParameter("clientSecret");
        redirectUri = this.getInitParameter("redirectUri");
        String environment = this.getInitParameter("environment");
        try {
            authUrl = environment
                    + "/authorize?" +
                    "client_id=" + clientId + "&" +
                    "redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                    "&response_type=code";
        } catch (UnsupportedEncodingException e) {
            throw new ServletException(e);
        }
        tokenUrl = environment + "/access_token";
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
            JSONObject authResponse = new JSONObject(
                    new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream())));
            StudentLogger.getLogger().info("Auth response: " + authResponse.toString(2));
            accessToken = authResponse.getString("access_token");
        } catch (JSONException e) {
            StudentLogger.getLogger().error("JSONException in VK");
            StudentLogger.getLogger().trace(e);
        } catch (IOException e) {
            StudentLogger.getLogger().error("IOException in VK");
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

