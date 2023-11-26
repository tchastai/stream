package com.spring.stream.controller;

import com.spring.stream.domain.dto.VideoRequestDto;
import com.spring.stream.domain.dto.VideoResponseDto;
import com.spring.stream.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @GetMapping(value = "{videoId}/stream", produces = "video/mp4")
    public Mono<Resource> streamVideo(@PathVariable Long videoId) {
        return videoService.streamVideo(videoId);
    }

    @PostMapping()
    public ResponseEntity<VideoResponseDto> createVideo(@RequestBody VideoRequestDto videoRequestDto) throws Exception {
        return ResponseEntity.ok(videoService.createVideo(videoRequestDto));
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
