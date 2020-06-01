package digger.service.impl;

import digger.model.Role;
import digger.model.User;
import digger.model.enums.RoleKind;
import digger.repository.UserRepository;
import digger.service.RoleService;
import digger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserDetailsManager userDetailsManager, UserRepository userRepository, RoleService roleService) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public boolean thereIsNoUser() {
        return userRepository.countAllByEnabled(true) == 0;
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    public digger.model.User findById(final Long id) {
        return userRepository.findById(id);
    }

    public digger.model.User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public void save(digger.model.User user) {
        userRepository.save(user);
        logger.info("Saved user {}", user.getUsername());
    }

    @Transactional
    public void save(digger.model.User user, RoleKind role) {
        // Abort if the current user is the only administrator and the role has changed.
        Role existingRole = roleService.findByUsername(user.getUsername());
        if(RoleKind.ROLE_ADMIN.toString().equals(existingRole.getAuthority())) {
            long numAdmins = roleService.countAllByAuthority(RoleKind.ROLE_ADMIN.toString());
            if (numAdmins > 1) {
                existingRole.setAuthority(role.toString());
                roleService.save(existingRole);
            } else {
                throw new RuntimeException("The only administrator cannot change its own role. Digger requires at least 2 administrators to allow this operation.");
            }
        }
        
        userRepository.save(user);
        logger.info("Saved user {}", user.getUsername());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        logger.info("Deleted user with id {}", id);
    }

    public void saveAdmin(final String username, final String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("ADMIN")
                .build();
        this.userDetailsManager.createUser(user);
        logger.info("Created admin user {}", username);
    }

    public void saveReader(final String username, final String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("READER")
                .disabled(true)
                .build();
        this.userDetailsManager.createUser(user);
        logger.info("Created reader user {}", username);
    }

    public void enableOrDisableUser(digger.model.User user) {
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
        logger.info("User {} was {}", user.getUsername(), user.getEnabled() ? "Enabled": "Disabled");
    }

    public digger.model.User changePassword(digger.model.User user, String newPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }
}
