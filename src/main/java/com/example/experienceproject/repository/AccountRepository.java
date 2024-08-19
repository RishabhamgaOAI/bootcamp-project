package com.example.experienceproject.repository;

import com.example.experienceproject.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}