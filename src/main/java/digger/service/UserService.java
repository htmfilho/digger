package digger.service;

import java.util.List;

public interface UserService {

    boolean thereIsNoUser();

    List<digger.model.User> findAll();

    digger.model.User findById(Long id);

    digger.model.User findByUsername(String username);

    void save(digger.model.User user);

    void saveAdmin(String username, String password);

    void saveReader(String username, String password);

    void enableOrDisableUser(digger.model.User user);

    void changePassword(digger.model.User user, String newPassword);
}