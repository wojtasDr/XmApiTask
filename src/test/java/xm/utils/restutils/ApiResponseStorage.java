package xm.utils.restutils;

import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseStorage {
    @Setter
    @Getter
    private ValidatableResponse currentResponse;
}
