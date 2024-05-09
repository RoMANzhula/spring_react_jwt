package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllTokensByStaffMember_Id(Long id);
}
