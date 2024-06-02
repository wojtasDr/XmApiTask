package xm.utils.restutils;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResponseUtils {

    @Autowired
    GetRequestFactory getRequestFactory;

    public String extractString(ValidatableResponse response, String jsonPath) {
        return response.extract().jsonPath().getString(jsonPath);
    }

    public List<String> extractList(ValidatableResponse response, String jsonPath) {
        return response.extract().jsonPath().getList(jsonPath);
    }

    public List<List<String>> extractListOfLists(ValidatableResponse response, String jsonPath) {
        return response.extract().jsonPath().getList(jsonPath);
    }

    public List<Map<String, Object>> extractListOfMaps(ValidatableResponse response, String jsonPath) {
        return response.extract().jsonPath().getList(jsonPath);
    }

    public List<Map<String, Object>> collectDataFromAllPages(String nextUrl, String jsonPath) {
        List<Map<String, Object>> allPagesData = new ArrayList<>();
        while (nextUrl != null) {
            ValidatableResponse peopleResponse = getRequestFactory.sendGetRequest(nextUrl);
            peopleResponse.statusCode(HttpStatus.SC_OK);

            List<Map<String, Object>> singlePageCharacters = extractListOfMaps(peopleResponse, jsonPath);
            allPagesData.addAll(singlePageCharacters);
            nextUrl = extractString(peopleResponse, "next");
        }
        return allPagesData;
    }
}
