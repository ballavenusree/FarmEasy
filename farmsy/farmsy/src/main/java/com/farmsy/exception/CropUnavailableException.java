package com.farmsy.exception;

public class CropUnavailableException extends RuntimeException {
    public CropUnavailableException(Long id) {
        super("Crop with id " + id + " is not available");
    }
}

