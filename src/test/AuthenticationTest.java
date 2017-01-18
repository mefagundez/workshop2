import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private static String CLIENT_ID = "normal-app";
    private static String CLIENT_SECRET = "secret";
    private static String GRANT_TYPE = "password";

    private HttpEntity getAuthorizationRequest(String username, String password, String clientId, String clientSecret, String grantType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("username", username);
        map.add("password", password);

        return new HttpEntity(map, headers);
    }
    private String authorize(String username, String password, String clientId, String clientSecret, String grantType) {
        HttpEntity entity = getAuthorizationRequest(username, password, clientId, clientSecret, grantType);
        ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + port + "/oauth/token", HttpMethod.POST, entity, JsonNode.class);
        return response.getBody().get("access_token").toString();
    }

    @Test
    public void greetingShouldNotBeAllowedToUnauthorizedUsers() throws Exception {
        ResponseEntity<JsonNode> greetingResponse = restTemplate.exchange("http://localhost:" + port + "/greeting", HttpMethod.GET, null, JsonNode.class);
        assertThat(greetingResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void greetingShouldBeAllowedToAuthorizedUsers() throws Exception {
        String accessToken = authorize(USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<JsonNode> greetingResponse = restTemplate.exchange("http://localhost:" + port + "/greeting", HttpMethod.GET, new HttpEntity(null, headers), JsonNode.class);
        assertThat(greetingResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
    }
}