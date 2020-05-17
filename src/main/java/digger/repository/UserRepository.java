package digger.repository;

import digger.model.User;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    Long countAllByEnabled(Boolean enabled);
    
    User findById(long id);
    User findByUsername(String username);
    
    List<User> findAllByOrderByUsernameAsc();

    void save(User user);
    void deleteById(Long id);
}