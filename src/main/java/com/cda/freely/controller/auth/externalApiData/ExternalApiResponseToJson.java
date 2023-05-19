package com.cda.freely.controller.auth.externalApiData;
import com.cda.freely.controller.auth.externalApiData.PeriodeUniteLegale;
import com.cda.freely.controller.auth.externalApiData.UniteLegale;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalApiResponseToJson {
    @JsonProperty("uniteLegale")
    private UniteLegale uniteLegale;

    @JsonProperty("periodesUniteLegale")
    private PeriodeUniteLegale periodeUniteLegale;

    public ExternalApiResponseToJson() {
    }

    public ExternalApiResponseToJson(UniteLegale uniteLegale, PeriodeUniteLegale periodeUniteLegale) {
        this.uniteLegale = uniteLegale;
        this.periodeUniteLegale = periodeUniteLegale;
    }

    public UniteLegale getUniteLegale() {
        return uniteLegale;
    }

    public void setUniteLegale(UniteLegale uniteLegale) {
        this.uniteLegale = uniteLegale;
    }

    public PeriodeUniteLegale getPeriodeUniteLegale() {
        return periodeUniteLegale;
    }

    public void setPeriodeUniteLegale(PeriodeUniteLegale periodeUniteLegale) {
        this.periodeUniteLegale = periodeUniteLegale;
    }
}
