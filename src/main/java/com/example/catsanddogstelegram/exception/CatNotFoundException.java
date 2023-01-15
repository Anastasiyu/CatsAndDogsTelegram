package com.example.catsanddogstelegram.exception;

public class CatNotFoundException extends RuntimeException {

    private long id;

    public CatNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

