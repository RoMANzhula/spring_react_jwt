package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.Role;
import org.romanzhula.clear_sol_practical.models.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRole enumRole);
}
