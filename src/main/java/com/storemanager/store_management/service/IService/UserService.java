package com.storemanager.store_management.service.IService;

import com.storemanager.store_management.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
    public void save(User user);
}
