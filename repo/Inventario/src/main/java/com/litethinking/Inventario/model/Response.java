package com.litethinking.Inventario.model;

import java.util.List;

public class Response<T> {
    private List<T> data;
    private String message;
    private Boolean error = false;
    private String errorDescription;

    public Response(List<T> data, String message, Boolean error, String errorDescription) {
        this.data = data;
        this.message = message;
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public Response(List<T> data, String message, Boolean error) {
        this.data = data;
        this.message = message;
        this.error = error;
    }

    public Response(List<T> data, Boolean error) {
        this.data = data;
        this.error = error;
    }

    public Response(List<T> data) {
        this.data = data;
    }

    public Response(String message){
        this.message = message;
    }

    public Response(Boolean error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public Response(List<T> data, Boolean error, String errorDescription) {
        this.data = data;
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
