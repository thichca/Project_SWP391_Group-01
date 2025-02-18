package com.storemanager.store_management.repository;


import com.storemanager.store_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRoleRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role <> ?1")
    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE u.role <> ?1")
    Page<User> findByRole(String role, Pageable pageable);

}
