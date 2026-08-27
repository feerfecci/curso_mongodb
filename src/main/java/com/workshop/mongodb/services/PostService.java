package com.workshop.mongodb.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.mongodb.domains.Post;
import com.workshop.mongodb.repository.PostRepository;
import com.workshop.mongodb.services.exception.ObjectNotFoundException;

@Service
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text) {
        return postRepository.searchTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        // Ajusta a data final para incluir o dia inteiro
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000 - 1);
        return postRepository.fullSearch(text, minDate, maxDate);
    }

}
