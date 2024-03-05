package ru.center.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMsg {
    @JsonProperty
    private String errorCode;
    @JsonProperty
    private String errorMessage;

    public ErrorMsg(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
