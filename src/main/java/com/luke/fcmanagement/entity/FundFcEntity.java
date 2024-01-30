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
@Entity(name = "TB_FUND_FC")
public class FundFcEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "FUND_ID", nullable = false)
    private Long fundId;

    @Column(name = "FC_ID", nullable = false)
    private Long fCId;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "DATE_FUND", nullable = false)
    private Date dateFund;
}
