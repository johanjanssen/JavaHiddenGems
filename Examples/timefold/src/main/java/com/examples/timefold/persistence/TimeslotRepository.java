package com.examples.timefold.persistence;

import com.examples.timefold.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
}
