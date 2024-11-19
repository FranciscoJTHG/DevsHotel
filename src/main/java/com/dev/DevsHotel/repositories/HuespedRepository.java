package com.dev.DevsHotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.DevsHotel.domain.huesped.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped>  findFirstByEmail(String email);

}
