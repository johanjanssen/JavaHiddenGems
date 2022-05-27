package com.examples.optaplanner.persistence;

import com.examples.optaplanner.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
