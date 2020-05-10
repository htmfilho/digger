package digger.service;

import digger.repository.UserRepository;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;

    public UserService(UserDetailsManager userDetailsManager, UserRepository userRepository) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
    }

    public boolean thereIsNoUser() {
        return userRepository.countAllByEnabled(true) == 0;
    }

    public List<digger.model.User> findAll() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    public digger.model.User findById(Long id) {
        return userRepository.findById(id);
    }

    public digger.model.User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(digger.model.User user) {
        userRepository.save(user);
    }

    public void saveAdmin(String username, String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("ADMIN")
                .build();
        this.userDetailsManager.createUser(user);
    }

    public void saveReader(String username, String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("READER")
                .disabled(true)
                .build();
        this.userDetailsManager.createUser(user);
    }

    public void enableOrDisableUser(digger.model.User user) {
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    public void changePassword(digger.model.User user, String newPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}