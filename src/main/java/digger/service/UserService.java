package digger.service;

import java.util.List;

public interface UserService {

    boolean thereIsNoUser();

    digger.model.User findById(Long id);
    digger.model.User findByUsername(String username);

    List<digger.model.User> findAll();

    void save(digger.model.User user);
    void delete(Long id);
    void saveAdmin(String username, String password);
    void saveReader(String username, String password);
    void enableOrDisableUser(digger.model.User user);
    void changePassword(digger.model.User user, String newPassword);
}