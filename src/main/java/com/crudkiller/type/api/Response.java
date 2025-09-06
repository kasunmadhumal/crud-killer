package com.crudkiller.type.api;

import lombok.Getter;

import java.util.List;

@Getter
public class Response<E> {
    private E data;
    private List<E> dataList;
    private String message;

    public Response(E data) {
        this.data = data;
    }

    public Response(E data, String message) {
        this.data = data;
        this.message = message;
    }

    public Response(List<E> dataList) {
        this.dataList = dataList;
    }

    public Response(List<E> dataList, String message) {
        this.dataList = dataList;
        this.message = message;
    }
}