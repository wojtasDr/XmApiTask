package xm.utils.restutils;

import lombok.Getter;

public enum RequestsEndpoints {
    FILMS("/films"),
    PEOPLE("/people");

    @Getter
    private final String endPoint;

    RequestsEndpoints(String endPoint) {
        this.endPoint = endPoint;
    }
}
