package com.api.recette.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recette.entity.User;

public interface UserRepository extends JpaRepository<User,Long>  {
    @Query(value = "SELECT * FROM users where email = :email and mdp = :mdp",nativeQuery = true)
    User findByEmailAndMdp(String email, String mdp);    
}
