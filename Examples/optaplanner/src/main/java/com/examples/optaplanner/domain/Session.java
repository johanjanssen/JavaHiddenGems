package com.examples.optaplanner.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
