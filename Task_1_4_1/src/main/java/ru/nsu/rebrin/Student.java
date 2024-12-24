package ru.nsu.rebrin;

/**
 * Represents a student.
 */
public class Student {
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
        if (!isBudget
                && recordBook.isEligibleForBudget()) {
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

