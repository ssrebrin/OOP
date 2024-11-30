package ru.nsu.rebrin;


import java.util.ArrayList;
import java.util.List;

/**
 * Record book.
 */
class RecordBook {
    private final List<CourseResult> results = new ArrayList<>();
    private Grade qualificationWorkGrade;

    /**
     * Adds a course result.
     *
     * @param result the result to add.
     */
    public void addCourseResult(CourseResult result) {
        results.add(result);
    }

    /**
     * Sets the grade for the qualification work.
     *
     * @param grade the grade to set.
     */
    public void setGrade(Grade grade) {
        this.qualificationWorkGrade = grade;
    }

    /**
     * Gets the course results.
     *
     * @return the list of course results.
     */
    public List<CourseResult> getResults() {
        return results;
    }

    /**
     * Gets the grade for the qualification work.
     *
     * @return the grade for the qualification work.
     */
    public Grade getGrade() {
        return qualificationWorkGrade;
    }

    /**
     * Calculates the average grade.
     *
     * @return the average numeric grade.
     */
    public double calculateAverageGrade() {
        return results.stream()
                .mapToInt(CourseResult::getNumericGrade)
                .average()
                .orElse(0.0);
    }

    /**
     * Checks if the student is eligible for a budget transfer based on the last two sessions.
     *
     * @return true if eligible, false otherwise.
     */
    public boolean isEligibleForBudget() {
        List<CourseResult> lastTwoSessions = results
                .subList(Math.max(0, results.size() - 10), results.size());
        return lastTwoSessions.stream()
                .noneMatch(result -> result.getGrade() == Grade.SATISFACTORY);
    }

    /**
     * Checks if the student can receive a red diploma.
     *
     * @return true if eligible, false otherwise.
     */
    public boolean canGetRed() {
        if (qualificationWorkGrade != Grade.EXCELLENT) {
            return false;
        }

        long excellentCount = results.stream()
                .filter(result -> result.getGrade() == Grade.EXCELLENT)
                .count();

        long total = results.size();

        return excellentCount >= total * 0.75
                && results.stream().noneMatch(result -> result.getGrade() == Grade.SATISFACTORY);
    }

    /**
     * Checks if the student is eligible for an increased scholarship.
     *
     * @return true if eligible, false otherwise.
     */
    public boolean canGetIncreasedScholarship() {
        List<CourseResult> currentSession = results.subList(
                Math.max(0, results.size() - 5), results.size());

        return currentSession.stream()
                .allMatch(result -> result.getGrade() == Grade.EXCELLENT);
    }
}



