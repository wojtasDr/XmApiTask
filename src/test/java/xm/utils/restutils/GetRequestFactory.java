package xm.utils.restutils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetRequestFactory {

    @Autowired
    ApiResponseStorage apiResponseStorage;

    public ValidatableResponse sendGetRequest(String endPoint) {
        apiResponseStorage.setCurrentResponse(
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(endPoint)
                        .then()
                        .assertThat());

        return apiResponseStorage.getCurrentResponse();
    }
}
