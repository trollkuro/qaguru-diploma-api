package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.CatImageInfoResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CatImageInfoSpec.*;

@DisplayName("Cats")
public class CatImagesTests extends BaseTest {

    private static final String CAT_IMAGE_ID_EXISTS = "brv";


    @Test
    @Feature("Cat images")
    @Owner("kegorova")
    @DisplayName("Get info about cat image by id, id exists in the system")
    void getCatInfoTest(){
        CatImageInfoResponseModel responseCatImage = step("Make GET request for specified cat image", () ->
                given(catImageInfoRequestSpec).header("x-api-key", authConfig.authKey())
                        .when()
                        .get("/images/" + CAT_IMAGE_ID_EXISTS)
                        .then()
                        .spec(catImageInfoResponseSpec)
                        .extract().as(CatImageInfoResponseModel.class)
        );
        step("Verify response", () -> {
            assertEquals(CAT_IMAGE_ID_EXISTS, responseCatImage.getId());
            assertThat(responseCatImage.getUrl(), containsString(CAT_IMAGE_ID_EXISTS));
        });
    }
}
