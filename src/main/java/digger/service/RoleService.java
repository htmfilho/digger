package digger.service;

import digger.model.Role;

public interface RoleService {

    Role findByUsername(String username);

    void save(Role role);
}