package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_FUND_DONATE")
@Entity
public class FundDonateEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "DONATE_ID", nullable = false)
    private Long donateId;

    @Column(name = "AMOUNT_MONEY", nullable = false)
    private BigDecimal amountMoney;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "DONOR", nullable = false)
    private String donor;

    @Column(name = "FC_ID", nullable = false)
    private Long fcId;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
