package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.List;

enum CourseType {
    EXAM("Exam"),
    CREDIT("Credit"),
    DIFFERENTIATED_CREDIT("Differentiated Credit");

    private final String typeName;

    CourseType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}

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

    public String getGradeName() {
        return gradeName;
    }

    public int getNumericValue() {
        return numericValue;
    }
}

class CourseResult {
    private final String courseName;
    private final CourseType courseType;
    private final Grade grade;

    public CourseResult(String courseName, CourseType courseType, Grade grade) {
        this.courseName = courseName;
        this.courseType = courseType;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public Grade getGrade() {
        return grade;
    }

    public int getNumericGrade() {
        return grade.getNumericValue();
    }
}

class RecordBook {
    private final List<CourseResult> results = new ArrayList<>();
    private Grade qualificationWorkGrade;

    public void addCourseResult(CourseResult result) {
        results.add(result);
    }

    public void setGrade(Grade grade) {
        this.qualificationWorkGrade = grade;
    }

    public List<CourseResult> getResults() {
        return results;
    }

    public Grade getGrade() {
        return qualificationWorkGrade;
    }

    public double calculateAverageGrade() {
        return results.stream()
                .mapToInt(CourseResult::getNumericGrade)
                .average()
                .orElse(0.0);
    }

    public boolean isEligibleForBudget() {
        List<CourseResult> lastTwoSessions = results
                .subList(Math.max(0, results.size() - 10), results.size());
        return lastTwoSessions.stream()
                .noneMatch(result -> result.getGrade() == Grade.SATISFACTORY);
    }

    public boolean canGetRed() {
        if (qualificationWorkGrade != Grade.EXCELLENT) return false;

        long excellentCount = results.stream()
                .filter(result -> result.getGrade() == Grade.EXCELLENT)
                .count();

        long total = results.size();

        return excellentCount >= total * 0.75 &&
                results.stream().noneMatch(result -> result.getGrade() == Grade.SATISFACTORY);
    }

    public boolean canGetIncreasedScholarship() {
        List<CourseResult> currentSession = results.subList(
                Math.max(0, results.size() - 5), results.size());

        return currentSession.stream()
                .allMatch(result -> result.getGrade() == Grade.EXCELLENT);
    }
}

class Student {
    private final String name;
    private boolean isBudget;
    private final RecordBook recordBook;

    public Student(String name, boolean isBudget) {
        this.name = name;
        this.isBudget = isBudget;
        this.recordBook = new RecordBook();
    }

    public RecordBook getRecordBook() {
        return recordBook;
    }

    public boolean isBudget() {
        return isBudget;
    }

    public void checkAndTransferToBudget() {
        if (!isBudget && recordBook.isEligibleForBudget()) {
            isBudget = true;
            System.out.println(name + " has been transferred to budget.");
        }
    }

    public void printAverageGrade() {
        System.out.printf("Average grade: %.2f%n", recordBook.calculateAverageGrade());
    }

    public void checkRedDiploma() {
        if (recordBook.canGetRed()) {
            System.out.println(name + " is eligible for a red diploma.");
        } else {
            System.out.println(name + " is not eligible for a red diploma.");
        }
    }

    public void checkIncreasedScholarship() {
        if (recordBook.canGetIncreasedScholarship()) {
            System.out.println(name + " is eligible for an increased scholarship.");
        } else {
            System.out.println(name + " is not eligible for an increased scholarship.");
        }
    }
}
