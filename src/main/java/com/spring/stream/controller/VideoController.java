package com.spring.stream.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    @GetMapping()
    public ResponseEntity<Void> streamVideo(){
        return ResponseEntity.ok().build();
    }
}
