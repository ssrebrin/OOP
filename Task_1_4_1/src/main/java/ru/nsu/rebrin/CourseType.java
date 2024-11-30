package ru.nsu.rebrin;

/**
 * Represents the type of a course.
 */
public enum CourseType {
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