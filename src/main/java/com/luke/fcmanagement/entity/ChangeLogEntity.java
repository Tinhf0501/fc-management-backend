package com.luke.fcmanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_CHANGE_LOG")
public class ChangeLogEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "LOG_ID", nullable = false)
    private Long logId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "ACTION", nullable = false)
    private String action;

    @Column(name = "TABLE_ID", nullable = false)
    private String tableId;

    @Column(name = "TABLE_NAME", nullable = false)
    private String tableName;

    @Column(name = "RECORD_OLD", nullable = false)
    private String recordOld;

    @Column(name = "NOTE", nullable = false)
    private String note;
}
