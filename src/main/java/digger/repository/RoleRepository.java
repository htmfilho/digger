package digger.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import digger.model.Role;

public interface RoleRepository extends Repository<Role, Long> {
    Role findByUsername(String username);
    List<Role> findAll();
}