package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_FUND_FC_MEMBER")
@Entity
public class FundFCMemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "FUND_ID", nullable = false)
    private Long fundFCMemberId;

    @Column(name = "FC_MEMBER_ID", nullable = false)
    private Long fcMemberId;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "FUND_FC_ID", nullable = false)
    private Long fundFCId;
}
