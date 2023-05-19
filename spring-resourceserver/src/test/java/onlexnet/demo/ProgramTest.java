package onlexnet.demo;

import java.net.URI;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

		URI authorizationURI = new URIBuilder(
				keycloak.getAuthServerUrl() + "/realms/baeldung/protocol/openid-connect/token").build();
		WebClient webclient = WebClient.builder().build();
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.put("grant_type", Collections.singletonList("password"));
		formData.put("client_id", Collections.singletonList("baeldung-api"));
		formData.put("username", Collections.singletonList("jane.doe@baeldung.com"));
		formData.put("password", Collections.singletonList("s3cr3t"));

		String result = webclient.post()
				.uri(authorizationURI)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.bodyToMono(String.class)
				.block();

	}

}
