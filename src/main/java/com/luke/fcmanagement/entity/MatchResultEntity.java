package com.luke.fcmanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_MATCH_RESULT")
public class MatchResultEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "RESULT_ID", nullable = false)
    private Long resultId;

    @Column(name = "TEAM_ONE", nullable = false)
    private Long teamOneId;

    @Column(name = "TEAM_TWO", nullable = false)
    private Long teamTwoId;

    @Column(name = "TEAM_ONE_POINTS", nullable = false)
    private Integer teamOnePoints;

    @Column(name = "TEAM_TWO_POINTS", nullable = false)
    private Integer teamTwoPoints;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "MATCH_ID", nullable = false)
    private Long matchId;

}
