package com.codingdojo.courses.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.courses.models.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}