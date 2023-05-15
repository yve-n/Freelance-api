package com.cda.freely.request;

import lombok.Data;

@Data
public class SirenRequest {
    private String siren;

    public SirenRequest() {
    }

    public SirenRequest(String siren) {
        this.siren = siren;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }
}
