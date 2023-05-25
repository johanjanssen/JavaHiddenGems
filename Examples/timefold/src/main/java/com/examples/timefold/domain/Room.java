package com.examples.timefold.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Room {
    @PlanningId
    @Id @GeneratedValue
    private Long id;

    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(long id, String name) {
        this(name);
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
