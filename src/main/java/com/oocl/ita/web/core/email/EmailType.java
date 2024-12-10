package com.oocl.ita.web.core.email;

import lombok.Getter;

@Getter
public enum EmailType {
    EMAIL_FLOW("https://prod-13.southeastasia.logic.azure.com:443/workflows/e3a08f741f304a468295d853e4914542/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=xm9I048rE2kU1mhxD8KeiIt7LplWS7AU_W6xWIB_1QE");

    private final String url;

    EmailType(String url) {
        this.url = url;
    }

}