package ru.nsu.rebrin;

/**
 * Represents a grade.
 */
public enum Grade {
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