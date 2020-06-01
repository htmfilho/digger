package digger.service;

import java.util.List;

import digger.model.enums.RoleKind;

public interface UserService {

    boolean thereIsNoUser();

    digger.model.User findById(Long id);
    digger.model.User findByUsername(String username);
    digger.model.User changePassword(digger.model.User user, String newPassword);

    List<digger.model.User> findAll();

    void save(digger.model.User user);
    void save(digger.model.User user, RoleKind roleKind);
    void saveAdmin(String username, String password);
    void saveReader(String username, String password);
    void enableOrDisableUser(digger.model.User user);
    void delete(Long id);
}