package com.luke.fcmanagement.module.job;

import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_JOB")
@Entity
public class JobEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "JOB_ID", nullable = false)
    private Long jobId;

    @Column(name = "JOB_VALUE")
    @Lob
    private String jobValue;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "JOB_TYPE")
    private String jobType;
}
