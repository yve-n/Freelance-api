package com.cda.freely.service;

import com.cda.freely.entity.Tag;
import com.cda.freely.entity.User;
import com.cda.freely.exception.ResourceNotFoundException;
import com.cda.freely.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private TagRepository tagRepository;
    private UserService userService;
    @Autowired
    public TagService(TagRepository tagRepository, UserService userService) {
        this.tagRepository = tagRepository;
        this.userService = userService;
    }

    /**
     * find a tag in database
     * @param id
     * @return Tag
     */
    public Optional<Tag> findById(Long id) {return tagRepository.findById(id);}

    public void addUserToTag(Long tagId, User user) {
        Tag tag = this.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        tag.getUsers().add(user);
        user.getTags().add(tag);
        tagRepository.save(tag);
        userService.saveUser(user);
    }

    public List<Tag> findTags(List<Long> tagIds){
        return tagRepository.findAllById(tagIds);
    }

}
