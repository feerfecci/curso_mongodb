package com.workshop.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.workshop.mongodb.domains.User;

public interface UserRepository extends MongoRepository<User, String> {

}
