package com.examples.optaplanner.persistence;

import com.examples.optaplanner.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
