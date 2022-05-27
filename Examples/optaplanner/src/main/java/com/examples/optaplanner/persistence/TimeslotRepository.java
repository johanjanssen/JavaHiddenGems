package com.examples.optaplanner.persistence;

import com.examples.optaplanner.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
}
