package com.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parking.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	public List<Ticket> findByStatus(String status);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_ticket set status = 'NotParked' where slot=:slot AND status = 'Parked'", nativeQuery = true)
	public void emptyTheSlot( @Param("slot") int slot);

	@Query(value = "select car_number from tbl_ticket where car_color=:color and status='Parked'", nativeQuery = true)
	public List<String> getCarNumberByColor(@Param("color") String color);
	
	@Query(value = "select slot from tbl_ticket where car_color=:color and status='Parked'", nativeQuery = true)
	public List<Integer> getSlotByColor(@Param("color") String color);
	
	@Query(value = "select slot from tbl_ticket where car_number=:number and status='Parked'", nativeQuery = true)
	public List<Integer> getSlotByCarNumber(@Param("number") String number);
}
