package com.spring.stream.service.impl;

import com.spring.stream.common.ModelMapperCommon;
import com.spring.stream.constant.VideoConstant;
import com.spring.stream.domain.Video;
import com.spring.stream.domain.dto.VideoResponseDto;
import com.spring.stream.exception.ResourceNotFoundException;
import com.spring.stream.repository.VideoRepository;
import com.spring.stream.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
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
    public VideoResponseDto getVideoById(Long id) {
        logger.info("[{}] getVideoById have been call with id={}", VideoConstant.VIDEO_SERVICE_ENTITY, id);
        Optional<Video> videoFinded = this.videoRepository.findById(id);
        if (videoFinded.isPresent()) {
            return ModelMapperCommon.convertClass(videoFinded.get(), VideoResponseDto.class);
        } else {
            throw new ResourceNotFoundException(VideoConstant.VIDEO_NOT_FOUND_MESSAGE, VideoConstant.VIDEO_ENTITY, VideoConstant.VIDEO_ERROR_KEY);
        }
    }

    @Override
    public File getLocalVideoFile(Long id) {
        logger.info("[{}] getLocalVideoFile have been call with id={}", VideoConstant.VIDEO_SERVICE_ENTITY, id);
        VideoResponseDto video = getVideoById(id);
        return new File(video.getVideoPath());
    }
}
