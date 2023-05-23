package com.cda.freely.service;


import com.cda.freely.entity.Image;
import com.cda.freely.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private ImageRepository imageRepository;
    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * find an image
     * @param id
     * @return Image
     */
    public Optional<Image> findImageById(Long id){
        return imageRepository.findById(id);
    }

    /**
     * save an image
     * @param image
     * @return Image
     */
    public Image saveImage(Image image){
        return imageRepository.save(image);
    }

    /**
     * delete an image
     * @param id
     */
    public void deleteImageById(Long id){
        imageRepository.deleteById(id);
    }
}
