package com.cda.freely.dto.company;

import com.cda.freely.dto.address.AddressDTO;

import java.util.List;

public class CompanyDTO {
    private String name;
    private String siren;
    private boolean tva;
    private String number;

    private List<AddressDTO> addresses;


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

    public boolean isTva() {
        return tva;
    }

    public void setTva(boolean tva) {
        this.tva = tva;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
