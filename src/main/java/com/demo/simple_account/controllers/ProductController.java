package com.demo.simple_account.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @GetMapping("")
    public ResponseEntity<Object> getMethodName() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Test success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
