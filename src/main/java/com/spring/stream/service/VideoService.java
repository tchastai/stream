package com.spring.stream.service;

import com.spring.stream.domain.Video;
import com.spring.stream.domain.dto.VideoResponseDto;

import java.io.File;
import java.util.List;

public interface VideoService {

    List<VideoResponseDto> getAllVideos();

    VideoResponseDto getVideoById(Long id) throws Exception;

    File getLocalVideoFile(Long id);
}
