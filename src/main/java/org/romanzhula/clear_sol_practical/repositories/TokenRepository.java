package org.romanzhula.clear_sol_practical.repositories;

import org.romanzhula.clear_sol_practical.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
    select t from Token t inner join User u on t.staffMember.id = u.id
    where t.staffMember.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllTokensByUser(Long userId);

}
