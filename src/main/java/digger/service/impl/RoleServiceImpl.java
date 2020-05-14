package digger.service.impl;

import digger.model.Role;
import digger.repository.RoleRepository;
import digger.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByUsername(String username) {
        return this.roleRepository.findByUsername(username);
    }

    public void save(Role role) {
        this.roleRepository.save(role);
    }
}