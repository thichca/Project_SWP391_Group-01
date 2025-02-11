package com.storemanager.store_management.service;


import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.repository.PasswordResetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetRepository passwordResetRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean checkIdExists(String email) {
        return passwordResetRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public boolean updatePassword(String email, String newPassword) {
        Optional<User> userOpt = passwordResetRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            passwordResetRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }
}
