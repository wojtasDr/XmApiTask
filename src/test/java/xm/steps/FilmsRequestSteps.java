package xm.steps;

import io.cucumber.java8.En;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import xm.utils.restutils.ApiResponseStorage;
import xm.utils.restutils.GetRequestFactory;
import xm.utils.restutils.RequestsEndpoints;
import xm.utils.restutils.ResponseUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FilmsRequestSteps implements En {
    @Autowired
    GetRequestFactory getRequestFactory;

    @Autowired
    ResponseUtils responseUtils;

    @Autowired
    ApiResponseStorage apiResponseStorage;

    private Map<String, Object> newestFilm;
    private List<Map<String, Object>> allPagesFilmsList;

    private static final String CHARACTERS = "characters";
    private static final String HEIGHT = "height";
    private static final String NAME = "name";
    private static final String NEXT = "next";
    private static final String RESULTS = "results";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String UNKNOWN = "unknown";
    private static final String URL = "url";

    public FilmsRequestSteps() {
        When("^I get all films$", () -> {
            //Send GET /films request for all pages and collect all films into List
            allPagesFilmsList = responseUtils
                    .collectDataFromAllPages(RequestsEndpoints.FILMS.getEndPoint(), RESULTS);
        });

        Then("Film title with latest release date is {string}", (String expectedLatestFilmTitle) -> {
            //find film with latest release date
            newestFilm = allPagesFilmsList.stream()
                    .max(Comparator.comparing(film -> film.get(RELEASE_DATE).toString()))
                    .orElseThrow(() -> new RuntimeException("No films found"));

            String actualLatestFilmTitle = newestFilm.get(TITLE).toString();

            // Check if found film name is equal to expected film name
            Assertions.assertThat(expectedLatestFilmTitle).isEqualTo(actualLatestFilmTitle);
        });

        Then("The tallest character in that film is is {string}", (String expectedTallestCharacterName) -> {
            int maxHeight = 0;
            String actualTallestCharacter = "";

            //Send GET /films/{id} for newest film and get its characters urls list
            ValidatableResponse newestFilmResponse = getRequestFactory.sendGetRequest(newestFilm.get(URL).toString());
            newestFilmResponse.statusCode(HttpStatus.SC_OK);
            List<String> newestFilmsCharactersUrlsList = responseUtils.extractList(newestFilmResponse, CHARACTERS);

            for (String characterUrl : newestFilmsCharactersUrlsList) {
                //Send GET /people/{id}
                ValidatableResponse characterResponse = getRequestFactory.sendGetRequest(characterUrl);
                characterResponse.statusCode(HttpStatus.SC_OK);

                //find max height among characters of newest film
                String heightStr = responseUtils.extractString(characterResponse, HEIGHT);
                int height = heightStr.equals(UNKNOWN) ? 0 : Integer.parseInt(heightStr);
                if (height > maxHeight) {
                    maxHeight = height;
                    actualTallestCharacter = responseUtils.extractString(characterResponse, NAME);
                }
            }

            //check if found character is equal to expected tallest character of latest film
            Assertions.assertThat(expectedTallestCharacterName).isEqualTo(actualTallestCharacter);
        });

        Then("The tallest character in all Star Wars films is {string}", (String expectedTallestCharacterName) -> {
            //Send GET /people request for all pages and collect all characters into List
            List<Map<String, Object>> allPagesCharactersList = responseUtils
                    .collectDataFromAllPages(RequestsEndpoints.PEOPLE.getEndPoint(), RESULTS);

            //Find the tallest character among all characters
            String actualTallestCharacterName = allPagesCharactersList.stream()
                    .filter(character -> !character.get(HEIGHT).equals(UNKNOWN))
                    .max(Comparator.comparingInt(character -> Integer.parseInt((String) character.get(HEIGHT))))
                    .map(character -> character.get(NAME).toString())
                    .orElseThrow(() -> new RuntimeException("No characters found"));

            //check if found character is equal to expected tallest character of all films
            Assertions.assertThat(expectedTallestCharacterName).isEqualTo(actualTallestCharacterName);
        });

        When("^I send GET /people request$", () -> {
            getRequestFactory.sendGetRequest(RequestsEndpoints.PEOPLE.getEndPoint());
        });

        Then("^People response json schema is correct$", () -> {
            apiResponseStorage.getCurrentResponse().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("peopleResponseSchema.json"));

        });
    }
}
