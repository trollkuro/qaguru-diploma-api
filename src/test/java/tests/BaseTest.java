package tests;

import config.AuthConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static AuthConfig authConfig = ConfigFactory.create(AuthConfig.class, System.getProperties());

    @BeforeAll
    static void setUP(){
        RestAssured.baseURI = "https://api.thecatapi.com";
        RestAssured.basePath = "/v1";
    }
}
