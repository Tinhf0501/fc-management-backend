package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_MATCH_MEMBER_GOAL")
public class MatchMemberGoalEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "MEMBER_GOAL_ID", nullable = false)
    private Long memberGoalId;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "MATCH_ID", nullable = false)
    private Long matchId;

    @Column(name = "GOALS", nullable = false)
    private Integer goals;

    @Column(name = "GOALS_OG", nullable = false)
    private Integer goalsOG;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
