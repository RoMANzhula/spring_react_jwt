package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
