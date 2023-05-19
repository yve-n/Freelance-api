package com.cda.freely.controller.auth.externalApiData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniteLegale {
    @JsonProperty("siren")
    private String siren;

    @JsonProperty("periodesUniteLegale")
    private List<PeriodeUniteLegale> periodesUniteLegale;

    public UniteLegale() {
    }

    public UniteLegale(String siren, List<PeriodeUniteLegale> periodesUniteLegale) {
        this.siren = siren;
        this.periodesUniteLegale = periodesUniteLegale;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public List<PeriodeUniteLegale> getPeriodesUniteLegale() {
        return periodesUniteLegale;
    }

    public void setPeriodesUniteLegale(List<PeriodeUniteLegale> periodesUniteLegale) {
        this.periodesUniteLegale = periodesUniteLegale;
    }

    // getters et setters
}
