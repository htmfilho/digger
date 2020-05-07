package digger.service;

import digger.model.Role;
import digger.repository.RoleRepository;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByUsername(String username) {
        return this.roleRepository.findByUsername(username);
    }
}