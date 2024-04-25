package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.entity.FootballClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFootballClubRepository extends JpaRepository<FootballClubEntity, Long> {
}
