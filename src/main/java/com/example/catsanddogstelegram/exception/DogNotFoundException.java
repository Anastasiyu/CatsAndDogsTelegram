package com.example.catsanddogstelegram.exception;

public class DogNotFoundException extends RuntimeException {
    private long id;

    public DogNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
