package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USER")
@Entity
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "AVATAR")
    private String avatar;
}
