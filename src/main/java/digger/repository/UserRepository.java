package digger.repository;

import digger.model.User;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    Long countAllByEnabled(Boolean enabled);
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    void save(User user);
}