package specs.image;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class UploadCatImageSpec {

    public static RequestSpecification uploadCatImageRequestSpec = with()
            .filter(withCustomTemplates())
            .log().headers()
            .log().uri()
            .log().method();

    public static ResponseSpecification uploadCatImageResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(201)
            .build();
}
