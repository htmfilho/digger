package digger.service;

import digger.model.Role;

public interface RoleService {
    Long countAllByAuthority(String authority);
    
    Role findByUsername(String username);

    void save(Role role);
}