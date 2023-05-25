package com.examples.timefold.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@PlanningEntity
@Entity
public class Session {
    @PlanningId
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String speaker;
    private String language;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    @ManyToOne
    private Timeslot timeslot;

    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    @ManyToOne
    private Room room;

    public Session() {
    }

    public Session(String title, String speaker, String language) {
        this.title = title;
        this.speaker = speaker;
        this.language = language;
    }

    public Session(long id, String title, String speaker, String language, Timeslot timeslot, Room room) {
        this(title, speaker, language);
        this.id = id;
        this.timeslot = timeslot;
        this.room = room;
    }

    @Override
    public String toString() {
        return title + "(" + id + ")";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getLanguage() {
        return language;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
