package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the type of a course.
 */
enum CourseType {
    EXAM("Exam"),
    CREDIT("Credit"),
    DIFFERENTIATED_CREDIT("Differentiated Credit");

    private final String typeName;

    CourseType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the name of the course type.
     *
     * @return the name of the course type.
     */
    public String getTypeName() {
        return typeName;
    }
}

/**
 * Represents a grade.
 */
enum Grade {
    EXCELLENT("Excellent", 5),
    GOOD("Good", 4),
    SATISFACTORY("Satisfactory", 3),
    UNSATISFACTORY("Unsatisfactory", 2);

    private final String gradeName;
    private final int numericValue;

    Grade(String gradeName, int numericValue) {
        this.gradeName = gradeName;
        this.numericValue = numericValue;
    }

    /**
     * Gets the name of the grade.
     *
     * @return the name of the grade.
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * Gets the numeric value of the grade.
     *
     * @return the numeric value of the grade.
     */
    public int getNumericValue() {
        return numericValue;
    }
}

/**
 * Represents the result of a course.
 */
class CourseResult {
    private final String courseName;
    private final CourseType courseType;
    private final Grade grade;

    /**
     * Constructs a CourseResult.
     *
     * @param courseName the name of the course.
     * @param courseType the type of the course (Exam, Credit, Differentiated Credit).
     * @param grade      the grade (Excellent, Good, Satisfactory, Unsatisfactory).
     */
    public CourseResult(String courseName, CourseType courseType, Grade grade) {
        this.courseName = courseName;
        this.courseType = courseType;
        this.grade = grade;
    }

    /**
     * Gets the name of the course.
     *
     * @return the name of the course.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets the type of the course.
     *
     * @return the type of the course.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Gets the grade.
     *
     * @return the grade.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Gets the numeric value of the grade.
     *
     * @return the numeric value of the grade.
     */
    public int getNumericGrade() {
        return grade.getNumericValue();
    }
}

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

        return excellentCount >= total * 0.75 &&
                results.stream().noneMatch(result -> result.getGrade() == Grade.SATISFACTORY);
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

/**
 * Represents a student.
 */
class Student {
    private final String name;
    private boolean isBudget;
    private final RecordBook recordBook;

    /**
     * Constructs a Student.
     *
     * @param name     the name of the student.
     * @param isBudget true if the student is on a budget, false otherwise.
     */
    public Student(String name, boolean isBudget) {
        this.name = name;
        this.isBudget = isBudget;
        this.recordBook = new RecordBook();
    }

    /**
     * Gets the record book of the student.
     *
     * @return the record book.
     */
    public RecordBook getRecordBook() {
        return recordBook;
    }

    /**
     * Checks if the student is on a budget.
     *
     * @return true if the student is on a budget, false otherwise.
     */
    public boolean isBudget() {
        return isBudget;
    }

    /**
     * Checks and transfers the student to budget if eligible.
     */
    public void checkAndTransferToBudget() {
        if (!isBudget && recordBook.isEligibleForBudget()) {
            isBudget = true;
            System.out.println(name + " has been transferred to budget.");
        }
    }

    /**
     * Prints the average grade of the student.
     */
    public void printAverageGrade() {
        System.out.printf("Average grade: %.2f%n", recordBook.calculateAverageGrade());
    }

    /**
     * Checks if the student is eligible for a red diploma.
     */
    public void checkRedDiploma() {
        if (recordBook.canGetRed()) {
            System.out.println(name + " is eligible for a red diploma.");
        } else {
            System.out.println(name + " is not eligible for a red diploma.");
        }
    }

    /**
     * Checks if the student is eligible for an increased scholarship.
     */
    public void checkIncreasedScholarship() {
        if (recordBook.canGetIncreasedScholarship()) {
            System.out.println(name + " is eligible for an increased scholarship.");
        } else {
            System.out.println(name + " is not eligible for an increased scholarship.");
        }
    }
}
