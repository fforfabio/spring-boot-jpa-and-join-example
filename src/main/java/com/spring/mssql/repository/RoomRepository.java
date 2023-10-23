package com.spring.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.mssql.models.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

}
