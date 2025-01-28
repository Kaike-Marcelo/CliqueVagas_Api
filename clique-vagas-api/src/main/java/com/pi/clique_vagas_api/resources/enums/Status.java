package com.pi.clique_vagas_api.resources.enums;

public enum Status {

    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
