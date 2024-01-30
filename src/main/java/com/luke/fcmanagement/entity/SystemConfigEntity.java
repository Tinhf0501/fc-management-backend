package com.luke.fcmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_SYSTEM_CONFIG")
public class SystemConfigEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "CONFIG_ID", nullable = false)
    private Long configId;

    @Column(name = "CONFIG_KEY", nullable = false)
    private String configKey;

    @Column(name = "CONFIG_CODE", nullable = false)
    private String configCode;

    @Column(name = "CONFIG_VALUE", nullable = false)
    private String configValue;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;
}
