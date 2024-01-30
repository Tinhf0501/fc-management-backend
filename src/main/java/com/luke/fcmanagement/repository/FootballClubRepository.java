package com.luke.fcmanagement.repository;

import com.luke.fcmanagement.entity.FootballClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballClubRepository extends JpaRepository<FootballClubEntity, Long> {
}
