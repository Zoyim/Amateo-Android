package com.location.amateolocationengin.entities;

import java.util.List;
import java.util.Map;

public class ApiError {
    Map<String, List<String>> errors;
    String message;

    public String getMessage() {
        return this.message;
    }

    public Map<String, List<String>> getErrors() {
        return this.errors;
    }
}
