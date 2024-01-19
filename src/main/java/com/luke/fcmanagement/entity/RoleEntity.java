package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_ROLE")
public class RoleEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
