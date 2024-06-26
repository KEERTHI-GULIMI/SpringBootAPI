package com.siemens.SpringBootAPI.models;

import com.siemens.SpringBootAPI.controller.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class myCustomException extends Exception {


        @Autowired
        private ProductController productController;

        public myCustomException(String s) {
            super(s);
        }

        public myCustomException() {
            super();
        }
    }

