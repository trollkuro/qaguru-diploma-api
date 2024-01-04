package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.CatImageInfoResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CatImageInfoSpec.catImageInfoRequestSpec;
import static specs.CatImageInfoSpec.catImageInfoResponseSpec;

public class DraftTest extends BaseTest {
    private static final String CAT_WITHOUT_BREED_ID = "brv";
    private static final String CAT_WITH_BREED = "8B1WVLAWH";

    @Test
    void getCatInfoWithoutBreedTestFixed(){
        CatImageInfoResponseModel responseCatImage = step("Make GET request for specified cat image", () ->
                given(catImageInfoRequestSpec).header("x-api-key", authConfig.authKey())
                        .when()
                        .get("/images/" + CAT_WITHOUT_BREED_ID)
                        .then()
                        .spec(catImageInfoResponseSpec)
                        .extract().as(CatImageInfoResponseModel.class)
        );
        step("Verify response", () -> {
            assertEquals(CAT_WITHOUT_BREED_ID, responseCatImage.getId());
            assertThat(responseCatImage.getUrl(), containsString(CAT_WITHOUT_BREED_ID));
        });
    }



    @Test
    @Feature("Cat images")
    @Owner("kegorova")
    @DisplayName("Add cat image to favorites")
    void addCatImageToFavorite(){
        given().header("x-api-key", authConfig.authKey())
                .log().uri()
                .log().method()
                .log().body()
                .body("{ \"image_id\": \"brv\"}")
                .contentType(JSON)
                .when()
                    .post("/favourites")
                .then()
                .   log().status()
                    .log().body()
                    .statusCode(200)
                    .body("message", is("SUCCESS"));
    }
}