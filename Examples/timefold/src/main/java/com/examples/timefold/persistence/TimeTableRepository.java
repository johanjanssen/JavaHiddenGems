package com.examples.timefold.persistence;

import com.examples.timefold.domain.Session;
import com.examples.timefold.domain.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimeTableRepository {

    // There is only one time table, so there is only timeTableId (= problemId).
    public static final Long SINGLETON_TIME_TABLE_ID = 1L;

    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public TimeTable findById(Long id) {
        if (!SINGLETON_TIME_TABLE_ID.equals(id)) {
            throw new IllegalStateException("There is no timeTable with id (" + id + ").");
        }
        return new TimeTable(
                timeslotRepository.findAll(),
                roomRepository.findAll(),
                sessionRepository.findAll());
    }

    public void save(TimeTable timeTable) {
        for (Session session : timeTable.getLessonList()) {
            sessionRepository.save(session);
        }
    }

}
