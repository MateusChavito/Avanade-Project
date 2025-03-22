package com.mateuschaves.avanade_project_2025.service.exception;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {

    public NotFoundException(String message){
        super(message);
    }

}
