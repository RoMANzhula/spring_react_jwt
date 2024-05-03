package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);

    Optional<User> findUserByEmail(String email);
}
