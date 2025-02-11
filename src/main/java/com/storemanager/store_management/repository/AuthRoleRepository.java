package com.storemanager.store_management.repository;


import com.storemanager.store_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRoleRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role <> ?1")
    List<User> findByRole(String role);


}
