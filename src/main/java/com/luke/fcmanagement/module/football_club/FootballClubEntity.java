package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_FOOTBALL_CLUB")
@Builder
public class FootballClubEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "FC_ID", nullable = false)
    private Long fcId;

    @Column(name = "FC_NAME", nullable = false)
    private String fcName;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "IS_GUEST")
    private Boolean isGuest;
}
