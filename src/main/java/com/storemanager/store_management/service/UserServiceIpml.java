package com.storemanager.store_management.service;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.repository.UserRepository;
import com.storemanager.store_management.service.IService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceIpml implements UserService {

    private UserRepository userRepository;
    @Autowired
    public UserServiceIpml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                rolesToAuthorities(user) // Thay đổi từ user.getRoles() sang user.getRole()
        );
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(User role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public String saveAvatar(MultipartFile avatar) throws IOException {
        String avatarUrl = "/assets/images/pushImg/" + avatar.getOriginalFilename();
        avatar.transferTo(new File(avatarUrl));
        return avatarUrl;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "guest";
    }
}
