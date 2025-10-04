package br.com.apirest.leadersofts.leadcapture.infrastructure.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FilterType {

    ID("id"),
    NAME("name"),
    BIRTHDAY("birthday"),
    TELEPHONE("phone"),
    MOBILE("mobile");

    private String type;

    public String getType() {
        return type;
    }
}
