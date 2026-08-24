package com.workshop.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workshop.mongodb.domains.Post;
import com.workshop.mongodb.domains.User;

public interface PostRepository extends MongoRepository<Post, String> {

}
