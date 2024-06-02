package xm.steps;

import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import xm.utils.restutils.ApiResponseStorage;

public class ResponseValidationSteps implements En {
    @Autowired
    ApiResponseStorage apiResponseStorage;

    public ResponseValidationSteps() {
        Then("The response status code equals {int}", (Integer expectedStatus) ->
                apiResponseStorage.getCurrentResponse().statusCode(expectedStatus));
    }
}
