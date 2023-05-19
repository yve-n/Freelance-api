package com.cda.freely.controller.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String name;
    private String siren;

    private int statusCode;

    public ApiResponse() {
    }

    public ApiResponse(String name, String siren, int statusCode) {
        this.name = name;
        this.siren = siren;
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
