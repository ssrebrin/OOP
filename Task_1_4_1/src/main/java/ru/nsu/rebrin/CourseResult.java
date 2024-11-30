package ru.nsu.rebrin;

/**
 * Represents the result of a course.
 */
public class CourseResult {
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
