package com.boomshair.mainentrance.repository;

import com.boomshair.mainentrance.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getUserByUsername(String username);

}
