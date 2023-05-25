package com.examples.timefold.solver;

import com.examples.timefold.domain.Session;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;

import java.time.Duration;

// Timefold was previously known as OptaPlanner, but was forked and renamed by the creator
public class ConferenceConstraintsProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                roomConflict(constraintFactory),
                speakerConflict(constraintFactory),
                // Soft constraints
                speakerRoomStability(constraintFactory),
                speakerTimeEfficiency(constraintFactory),
                languageStability(constraintFactory)
        };
    }

    Constraint roomConflict(ConstraintFactory constraintFactory) {
        // A room can accommodate at most one session at the same time.
        return constraintFactory
                // Select each pair of 2 different sessions ...
                .forEachUniquePair(Session.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Session::getTimeslot),
                        // ... in the same room ...
                        Joiners.equal(Session::getRoom))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Room conflict");
    }

    Constraint speakerConflict(ConstraintFactory constraintFactory) {
        // A speaker can teach at most one session at the same time.
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getTimeslot),
                        Joiners.equal(Session::getSpeaker))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Speaker conflict");
    }

    Constraint speakerRoomStability(ConstraintFactory constraintFactory) {
        // A speaker prefers to teach in a single room.
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getSpeaker))
                .filter((lesson1, lesson2) -> lesson1.getRoom() != lesson2.getRoom())
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Speaker room stability");
    }

    Constraint speakerTimeEfficiency(ConstraintFactory constraintFactory) {
        // A speaker prefers to present sequential sessions and dislikes gaps between sessions.
        return constraintFactory
                .forEach(Session.class)
                .join(Session.class, Joiners.equal(Session::getSpeaker),
                        Joiners.equal((lesson) -> lesson.getTimeslot().getDayOfWeek()))
                .filter((lesson1, lesson2) -> {
                    Duration between = Duration.between(lesson1.getTimeslot().getEndTime(),
                            lesson2.getTimeslot().getStartTime());
                    return !between.isNegative() && between.compareTo(Duration.ofMinutes(30)) <= 0; // Because of the lunch in between a fourth session isn't always planned consecutive
                })
                .reward(HardSoftScore.ONE_SOFT)
                .asConstraint("Speaker time efficiency");
    }

    Constraint languageStability(ConstraintFactory constraintFactory) {
        // A room prefers to host sessions of the same language
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getLanguage))
                .filter((lesson1, lesson2) -> lesson1.getRoom() != lesson2.getRoom())
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Language room stability");
    }
}
