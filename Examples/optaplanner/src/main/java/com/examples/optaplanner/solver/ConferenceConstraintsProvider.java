package com.examples.optaplanner.solver;

import com.examples.optaplanner.domain.Session;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.Duration;

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
                // ... and penalize each pair with a hard weight.
                .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }

    Constraint speakerConflict(ConstraintFactory constraintFactory) {
        // A speaker can teach at most one session at the same time.
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getTimeslot),
                        Joiners.equal(Session::getSpeaker))
                .penalize("Speaker conflict", HardSoftScore.ONE_HARD);
    }

    Constraint speakerRoomStability(ConstraintFactory constraintFactory) {
        // A speaker prefers to teach in a single room.
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getSpeaker))
                .filter((lesson1, lesson2) -> lesson1.getRoom() != lesson2.getRoom())
                .penalize("Speaker room stability", HardSoftScore.ONE_SOFT);
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
                .reward("Speaker time efficiency", HardSoftScore.ONE_SOFT);
    }

    Constraint languageStability(ConstraintFactory constraintFactory) {
        // A room prefers to host sessions of the same language
        return constraintFactory
                .forEachUniquePair(Session.class,
                        Joiners.equal(Session::getLanguage))
                .filter((lesson1, lesson2) -> lesson1.getRoom() != lesson2.getRoom())
                .penalize("Language room stability", HardSoftScore.ONE_SOFT);
    }
}
