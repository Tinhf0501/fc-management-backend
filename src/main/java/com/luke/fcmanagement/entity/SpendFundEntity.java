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
@Table(name = "TB_SPEND_FUND")
@Entity
public class SpendFundEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "SPEND_ID", nullable = false)
    private Long spendId;

    @Column(name = "AMOUNT_MONEY", nullable = false)
    private BigDecimal amountMoney;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "FC_ID", nullable = false)
    private Long fCId;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
