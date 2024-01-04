package specs.favorite;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class DeleteFromFavoriteSpec {

    public static RequestSpecification deleteFromFavoriteRequestSpec = with()
            .filter(withCustomTemplates())
            .log().headers()
            .log().uri()
            .log().method();

    public static ResponseSpecification deleteFromFavoriteResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();

}