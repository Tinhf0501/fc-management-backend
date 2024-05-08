package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_FOOTBALL_CLUB")
@Entity
@Builder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class FootballClubEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "FC_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long fcId;

    @Column(name = "FC_NAME", nullable = false)
    private String fcName;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "SLUG", length = 2000)
    private String slug;
}
