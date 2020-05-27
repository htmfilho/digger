package digger.service.impl;

import digger.model.Role;
import digger.repository.RoleRepository;
import digger.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByUsername(final String username) {
        return this.roleRepository.findByUsername(username);
    }

    public void save(Role role) {
        this.roleRepository.save(role);
        logger.info("Assigned role {} to user {}", role.getAuthority(), role.getUsername());
    }
}