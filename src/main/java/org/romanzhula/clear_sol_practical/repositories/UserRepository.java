package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);
}
