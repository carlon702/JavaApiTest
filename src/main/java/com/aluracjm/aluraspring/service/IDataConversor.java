package com.aluracjm.aluraspring.service;

public interface IDataConversor {
    <T> T getData(String json, Class<T> classType);
}
