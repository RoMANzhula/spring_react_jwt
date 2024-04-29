package org.romanzhula.clear_sol_practical.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.romanzhula.clear_sol_practical.models.enums.EnumRole;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumRole name;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staffMember;

}
