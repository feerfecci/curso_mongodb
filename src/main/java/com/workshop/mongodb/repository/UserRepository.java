package com.workshop.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.workshop.mongodb.domains.User;

public interface UserRepository extends MongoRepository<User, String> {

}
