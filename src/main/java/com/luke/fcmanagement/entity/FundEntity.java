package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_FUND")
public class FundEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "FUND_ID", nullable = false)
    private Long fundId;

    @Column(name = "FC_ID", nullable = false)
    private Long fcId;

    @Column(name = "FC_MEMBER_ID", nullable = false)
    private Long fcMemberId;

    @Column(name = "AMOUNT_NUMBER", nullable = false)
    private BigDecimal amountNumber;

    // * tháng được đóng quỹ, vd đóng tháng 1/2023
    @Column(name = "FUND_DATE", nullable = false)
    private Date fundDate;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
