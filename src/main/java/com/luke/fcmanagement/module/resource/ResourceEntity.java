package com.luke.fcmanagement.module.resource;


import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_RESOURCE")
@Builder
public class ResourceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "RESOURCE_ID", nullable = false)
    private Long resourceId;

    @Column(name = "PATH", length = 1000, nullable = false)
    private String path;

    // * type : video, img
    @Column(name = "MEDIA_TYPE", nullable = false)
    private String mediaType;

    // * keyID: id FC, Member, ....
    @Column(name = "KEY_ID", nullable = false)
    private Long targetId;

    // * keyType: FC, Mem, ...
    @Column(name = "KEY_TYPE", nullable = false)
    private Integer targetType;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "PROVIDER", nullable = false)
    private String provider;
}
