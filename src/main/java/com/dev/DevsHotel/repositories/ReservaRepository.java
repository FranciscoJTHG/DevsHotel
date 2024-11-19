package com.dev.DevsHotel.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.DevsHotel.domain.reserva.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	// @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
	// attributePaths = {"huesped"})
	// Page<Reserva>
	// findByHuesped_NombreCompletoContainingIgnoreCaseAndFechaCheckInEquals(
	// String nombreCompleto,
	// LocalDate fechaCheckIn,
	// Pageable pageable
	// );

	// La consulta solo muestra las reservas que estan activas
	@Query("""
			SELECT DISTINCT r FROM Reserva r
			JOIN FETCH r.huesped h
			WHERE r.activo = true
			AND (COALESCE(:nombreHuesped, '') = ''
			    OR UPPER(h.nombreCompleto) LIKE UPPER(CONCAT('%', :nombreHuesped, '%')))
			AND (COALESCE(:fechaCheckIn, r.fechaCheckIn) = r.fechaCheckIn)
			ORDER BY r.fechaCheckIn
			""")
	List<Reserva> buscarPorHuespedYFecha(
			@Param("nombreHuesped") String nombreHuesped,
			@Param("fechaCheckIn") LocalDate fechaCheckIn,
			Pageable pageable);

	@Query("""
			SELECT COUNT(DISTINCT r) FROM Reserva r
			JOIN r.huesped h
			WHERE r.activo = true
			AND (COALESCE(:nombreHuesped, '') = '' OR
					UPPER(h.nombreCompleto) LIKE UPPER(CONCAT('%', :nombreHuesped, '%')))
			AND (COALESCE(:fechaCheckIn, r.fechaCheckIn) = r.fechaCheckIn)
			""")
	long contarReservas(
			@Param("nombreHuesped") String nombreHuesped,
			@Param("fechaCheckIn") LocalDate fechaCheckIn);
	
	// Reserva findById(Long id)

	@Query("SELECT r FROM Reserva r JOIN FETCH r.huesped WHERE r.id = :id")
	Optional<Reserva> findByIdWithHuesped(@Param("id") Long id);

}
