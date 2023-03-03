package com.examples.optaplanner.rest;

import com.examples.optaplanner.domain.TimeTable;
import com.examples.optaplanner.persistence.TimeTableRepository;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timeTable")
public class TimeTableController {

    private TimeTableRepository timeTableRepository;
    private SolverManager<TimeTable, Long> solverManager;
    private SolutionManager<TimeTable, HardSoftScore> solutionManager;

    public TimeTableController(TimeTableRepository timeTableRepository, SolverManager<TimeTable, Long> solverManager, SolutionManager<TimeTable, HardSoftScore> solutionManager) {
        this.timeTableRepository = timeTableRepository;
        this.solverManager = solverManager;
        this.solutionManager = solutionManager;
    }

    @GetMapping()
    public TimeTable getTimeTable() {
        SolverStatus solverStatus = getSolverStatus();
        TimeTable solution = timeTableRepository.findById(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
        solutionManager.update(solution);
        solution.setSolverStatus(solverStatus);
        return solution;
    }

    @PostMapping("/solve")
    public void solve() {
        solverManager.solveAndListen(TimeTableRepository.SINGLETON_TIME_TABLE_ID,
                timeTableRepository::findById,
                timeTableRepository::save);
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }

    @PostMapping("/stopSolving")
    public void stopSolving() {
        solverManager.terminateEarly(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }

}
