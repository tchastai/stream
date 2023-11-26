package com.spring.stream.service.impl;

import com.spring.stream.common.ModelMapperCommon;
import com.spring.stream.constant.VideoConstant;
import com.spring.stream.domain.Video;
import com.spring.stream.domain.dto.VideoRequestDto;
import com.spring.stream.domain.dto.VideoResponseDto;
import com.spring.stream.exception.ResourceNotFoundException;
import com.spring.stream.repository.VideoRepository;
import com.spring.stream.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.io.ResourceLoader;

@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final ResourceLoader resourceLoader;

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository, ResourceLoader resourceLoader) {
        this.videoRepository = videoRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<VideoResponseDto> getAllVideos() {
        logger.info("[{}] getAllVideos have been call", VideoConstant.VIDEO_SERVICE_ENTITY);
        List<Video> videos = this.videoRepository.findAll();

        return videos
                .stream()
                .map(video -> ModelMapperCommon.convertClass(video, VideoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public VideoResponseDto getVideoById(Long videoId) {
        logger.info("[{}] getVideoById have been call with id={}", VideoConstant.VIDEO_SERVICE_ENTITY, videoId);
        Optional<Video> videoFinded = this.videoRepository.findById(videoId);
        if (videoFinded.isPresent()) {
            return ModelMapperCommon.convertClass(videoFinded.get(), VideoResponseDto.class);
        } else {
            throw new ResourceNotFoundException(VideoConstant.VIDEO_NOT_FOUND_MESSAGE, VideoConstant.VIDEO_ENTITY, VideoConstant.VIDEO_ERROR_KEY);
        }
    }

    @Override
    public File getLocalVideoFile(Long videoId) {
        logger.info("[{}] getLocalVideoFile have been call with id={}", VideoConstant.VIDEO_SERVICE_ENTITY, videoId);
        VideoResponseDto video = getVideoById(videoId);
        return new File(video.getVideoPath());
    }

    @Override
    public Mono<Resource> streamVideo(Long videoId) {

        VideoResponseDto video = getVideoById(videoId);
        String path = String.format(VideoConstant.VIDEO_FORMAT,video.getTitle());
        return Mono.fromSupplier(()->resourceLoader.
                getResource(path))   ;
    }

    @Override
    public VideoResponseDto createVideo(VideoRequestDto videoRequestDto) {
        logger.info("[{}] createVideo have been call with title={}", VideoConstant.VIDEO_SERVICE_ENTITY, videoRequestDto.getTitle());
        Video video = ModelMapperCommon.convertClass(videoRequestDto, Video.class);
        Video videoSaved = videoRepository.save(video);
        return ModelMapperCommon.convertClass(videoSaved, VideoResponseDto.class);
    }
}
