package com.spring.stream.controller;

import com.spring.stream.domain.dto.VideoResponseDto;
import com.spring.stream.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/stream")
    public ResponseEntity<Void> streamVideo(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long videoId) throws Exception {
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }
    @GetMapping()
    public ResponseEntity<List<VideoResponseDto>> getAllVideos(){
        return ResponseEntity.ok(videoService.getAllVideos());
    }
    @GetMapping("/{videoId}/file")
    public ResponseEntity<File> getLocalVideoFile(@PathVariable Long videoId){
        return ResponseEntity.ok(videoService.getLocalVideoFile(videoId));
    }
}
