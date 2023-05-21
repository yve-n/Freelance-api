package com.cda.freely.service;

import com.cda.freely.entity.Tag;
import com.cda.freely.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    public Optional<Tag> findById(Long id) {return tagRepository.findById(id);}
}
