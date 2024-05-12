package com.luke.fcmanagement.module.history;


import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_HIS_LOG")
@Entity
@Builder
public class HisLogEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "LOG_ID", nullable = false)
    private Long logId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "ACTION", nullable = false)
    private String action;

    @Column(name = "TRACE_ID", nullable = false)
    private String traceID;

    @Column(name = "API_PATH", nullable = false)
    private String apiPath;
}
