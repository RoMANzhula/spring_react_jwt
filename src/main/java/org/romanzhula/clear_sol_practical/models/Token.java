package org.romanzhula.clear_sol_practical.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String token;

    @Column(name = "is_log_out")
    private Boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staffMember;

}
