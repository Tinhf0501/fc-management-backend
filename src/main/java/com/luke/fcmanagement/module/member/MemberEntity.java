package com.luke.fcmanagement.module.member;


import com.luke.fcmanagement.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_FOOTBALL_CLUB_MEMBER")
@Builder
@Entity
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FC_ID", nullable = false)
    private Long fcId;

    @Column(name = "NUMBER_SHIRT", nullable = false)
    private Integer numberShirt;

    @Column(name = "NAME_SHIRT", nullable = false)
    private String nameShirt;

    // * có thể nhiều vị trí, sẽ lưu chuỗi position
    @Column(name = "POSITION", nullable = false)
    private String position;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "PHONE_NUMBER", length = 10)
    private String phoneNumber;

    @Column(name = "ADDRESS", length = 2000)
    private String address;

    @Column(name = "DATE_OF_BIRTHDAY")
    private LocalDate dateOfBirthday;
}
