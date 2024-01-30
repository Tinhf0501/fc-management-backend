package com.luke.fcmanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_MATCH_FIXTURE")
public class MatchFixtureEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "MATCH_ID", nullable = false)
    private Long matchId;

    @Column(name = "TEAM_ONE", nullable = false)
    private Long teamOneId;

    @Column(name = "TEAM_TWO", nullable = false)
    private Long teamTwoId;

    // * ngày thi đấu
    @Column(name = "MATCH_DATE", nullable = false)
    private Date matchDate;

    // * địa điểm thi đấu
    @Column(name = "MATCH_VENUE", nullable = false)
    private String matchVenue;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "MATCH_NAME", length = 2000)
    private String matchName;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
