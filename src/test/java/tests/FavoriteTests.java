package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.favorite.AddToFavoriteBodyModel;
import models.favorite.AddToFavoriteResponseModel;
import models.favorite.DeleteFromFavoriteResponseModel;
import models.favorite.FavoriteRecordByIdResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.favorite.AddToFavoriteSpec.addToFavoriteRequestSpec;
import static specs.favorite.AddToFavoriteSpec.addToFavoriteResponseSpec;
import static specs.favorite.DeleteFromFavoriteSpec.deleteFromFavoriteRequestSpec;
import static specs.favorite.DeleteFromFavoriteSpec.deleteFromFavoriteResponseSpec;
import static specs.favorite.FavoriteRecordByIdSpec.favoriteRecordRequestSpec;
import static specs.favorite.FavoriteRecordByIdSpec.favoriteRecordResponseSpec;

@DisplayName("Favorite image")
public class FavoriteTests extends BaseTest {

    private static final String ADD_TO_FAVORITE_IMAGE_DATA = "iyFN2mF8l";
    private static final int FAVORITE_ID = 232430012;


    @Test
    @Tag("Favorite")
    @Feature("Favorite")
    @Owner("kegorova")
    @DisplayName("Show favorite cat image by id")
    void getCatImageFavorites(){
        FavoriteRecordByIdResponseModel responseFavoriteRecord = step("Make GET request for list of favorite images", () ->
                        given(favoriteRecordRequestSpec).header("x-api-key", authConfig.authKey())
                        .when()
                            .get("/favourites/" + FAVORITE_ID)
                        .then()
                            .spec(favoriteRecordResponseSpec)
                            .extract().as(FavoriteRecordByIdResponseModel.class)
        );
        step("Verify id from request is the same as in the response", () -> {
            assertEquals(FAVORITE_ID, responseFavoriteRecord.getId());
        });
        step("Verify that image_id is the same as in the object [image]", () -> {
            assertEquals(responseFavoriteRecord.getImageId(), responseFavoriteRecord.getImage().getId());
        });
    }

    @Test
    @Tag("Favorite")
    @Feature("Favorite")
    @Owner("kegorova")
    @DisplayName("Add cat image to favorites")
    void addCatImageToFavorite(){
        AddToFavoriteBodyModel requestAddToFavorite = new AddToFavoriteBodyModel();
        requestAddToFavorite.setImageId(ADD_TO_FAVORITE_IMAGE_DATA);

        AddToFavoriteResponseModel responseAddToFavorite = step("Make POST request for adding image to favorite", () ->
                        given(addToFavoriteRequestSpec).header("x-api-key", authConfig.authKey())
                        .body(requestAddToFavorite)
                        .when()
                            .post("/favourites")
                        .then()
                            .spec(addToFavoriteResponseSpec)
                            .extract().as(AddToFavoriteResponseModel.class)
        );
        step("Verify response", () -> {
            assertEquals("SUCCESS", responseAddToFavorite.getMessage());
        });
    }

    @Test
    @Tag("Favorite")
    @Feature("Favorite")
    @Owner("kegorova")
    @DisplayName("Remove cat image from favorites")
    void removeCatImageFromFavorite(){
        AddToFavoriteBodyModel requestAddToFavorite = new AddToFavoriteBodyModel();
        requestAddToFavorite.setImageId(ADD_TO_FAVORITE_IMAGE_DATA);

        AddToFavoriteResponseModel responseAddToFavorite = step("Add image to favorite", () ->
                        given(addToFavoriteRequestSpec).header("x-api-key", authConfig.authKey())
                        .body(requestAddToFavorite)
                        .when()
                            .post("/favourites")
                        .then()
                            .spec(addToFavoriteResponseSpec)
                            .extract().as(AddToFavoriteResponseModel.class)
        );
        step("Verify response", () -> {
            assertEquals("SUCCESS", responseAddToFavorite.getMessage());
        });

        var savedId = responseAddToFavorite.getId(); //save id of created favorite instance from step 1

        DeleteFromFavoriteResponseModel responseDeleteFromFavorite = step("Delete added record by saved id", () ->
                                given(deleteFromFavoriteRequestSpec).header("x-api-key", authConfig.authKey())
                                .when()
                                    .delete("/favourites/" + savedId) //set saved id for deletion
                                .then()
                                    .spec(deleteFromFavoriteResponseSpec)
                                    .extract().as(DeleteFromFavoriteResponseModel.class)
        );
        step("Verify that record is deleted", () -> {
            assertEquals("SUCCESS", responseDeleteFromFavorite.getMessage());
        });
    }
}