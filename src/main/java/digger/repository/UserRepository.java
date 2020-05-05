package digger.repository;

import digger.model.User;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {
    Long countAllByEnabled(Boolean enabled);
    List<User> findAll();
}