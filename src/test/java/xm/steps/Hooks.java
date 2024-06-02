package xm.steps;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;

public class Hooks {
    @Value("${xm.base.uri}")
    private String xmBaseUri;

    @Before
    public void setBaseUrl() {
        RestAssured.baseURI = xmBaseUri;
    }
}
