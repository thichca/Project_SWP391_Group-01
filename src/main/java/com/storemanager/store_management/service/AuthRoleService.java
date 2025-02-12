package com.storemanager.store_management.service;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.repository.AuthRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthRoleService {

    @Autowired
    private AuthRoleRepository authRoleRepository;

    public List<User> getUsersByRole(String role) {
        return authRoleRepository.findByRole(role);
    }

    public List<User> getAllUsers() {
        return authRoleRepository.findAll();
    }

    public void updateUserRole(Long userId, String newRole) {
        Optional<User> userOptional = authRoleRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(newRole);
            authRoleRepository.save(user);
        }


    }
}
