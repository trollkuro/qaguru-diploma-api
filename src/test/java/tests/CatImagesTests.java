package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.image.CatImageInfoResponseModel;
import models.image.UploadCatImageResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.image.CatImageInfoSpec.*;
import static specs.image.UploadCatImageSpec.uploadCatImageResponseSpec;

@DisplayName("Cat images")
public class CatImagesTests extends BaseTest {

    private static final String CAT_IMAGE_ID_EXISTS = "brv";
    private static final File FILE_UPLOAD_PATH = new File("src/test/resources/cat.png");
    private static final String FILE_UPLOAD_NAME = "cat.png";

    @Test
    @Tag("Image")
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

    @Test
    @Tag("Image")
    @Feature("Cat images")
    @Owner("kegorova")
    @DisplayName("Add cat image")
    void addCatImageToFavorite(){
        UploadCatImageResponseModel uploadCatImageResponse = step("Make POST request to upload cat image", () ->
                given(catImageInfoRequestSpec).header("x-api-key", authConfig.authKey())
                        .multiPart("file", FILE_UPLOAD_PATH, "image/png")
                        .when()
                            .post("/images/upload")
                        .then()
                            .spec(uploadCatImageResponseSpec)
                            .extract().as(UploadCatImageResponseModel.class));
        step("Verify response", () -> {
            assertEquals(FILE_UPLOAD_NAME, uploadCatImageResponse.getOriginalFilename());
        });
    }

}
