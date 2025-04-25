package com.shoppi.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shoppi.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
