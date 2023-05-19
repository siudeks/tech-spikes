package onlexnet.demo;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import dasniko.testcontainers.keycloak.KeycloakContainer;

final public class KeycloakContainerExtension implements BeforeAllCallback, AfterAllCallback {

    KeycloakContainer keycloakContainer;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        keycloakContainer = new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");
        keycloakContainer.start();
        System.setProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri", keycloakContainer.getAuthServerUrl() + "/realms/onlexnet");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        keycloakContainer.start();
    }
    
}
