package com.siemens.SpringBootAPI.models;

import org.springframework.stereotype.Component;

@Component
public class myException extends Exception {

    public myException(String s) {
        super(s);
    }

    public myException() {
        super();
    }
}

