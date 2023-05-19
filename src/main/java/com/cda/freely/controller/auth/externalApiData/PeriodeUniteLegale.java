package com.cda.freely.controller.auth.externalApiData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodeUniteLegale {
    @JsonProperty("dateDebut")
    private String dateDebut;
    @JsonProperty("denominationUniteLegale")
    private String denominationUniteLegale;

    public PeriodeUniteLegale() {
    }

    public PeriodeUniteLegale(String dateDebut, String denominationUniteLegale) {
        this.dateDebut = dateDebut;
        this.denominationUniteLegale = denominationUniteLegale;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDenominationUniteLegale() {
        return denominationUniteLegale;
    }

    public void setDenominationUniteLegale(String denominationUniteLegale) {
        this.denominationUniteLegale = denominationUniteLegale;
    }
}
