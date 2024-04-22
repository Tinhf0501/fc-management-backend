package com.luke.fcmanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_FC_RESOURCE")
@Builder
public class FCResourceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "RESOURCE_ID", nullable = false)
    private Long resourceId;

    @Column(name = "PATH", length = 1000, nullable = false)
    private String path;

    // * type : video, picture
    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "FC_ID", nullable = false)
    private Long fcId;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;
}
