package br.com.armando.efficienttraining.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    MEDIA_NOT_SUPPORTED("/media-not-supported", "Media Not Supported"),
    BAD_INPUT_MESSAGE("/bad-input-message", "Bad Input Message"),
    BUSINESS_RULE("/business-rule-error", "Business Rule Error"),
    ENTITY_IN_USE("/entity-in-use", "Entity in Use"),
    INVALID_PARAM("/invalid-param", "Invalid Param"),
    SYSTEM_ERROR("/system-error", "System Error"),
    INVALID_DATA("/invalid-data", "Invalid Data"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource Not Found");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://efficient-training.com.br" + path;
        this.title = title;
    }
}
