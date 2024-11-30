package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RecordBookTest {
    private Student student;

    @Test
    void testCalculateAverageGrade() {
        student = new Student("John Doe", false); // Student on a paid basis
        RecordBook recordBook = student.getRecordBook();

        recordBook.addCourseResult(new CourseResult("Math", CourseType.EXAM, Grade.EXCELLENT));
        CourseResult course = recordBook.getResults().get(0);
        assertEquals("Math", course.getCourseName());
        assertEquals(CourseType.EXAM, course.getCourseType());

        recordBook.addCourseResult(new CourseResult("Physics", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("History", CourseType.EXAM, Grade.SATISFACTORY));
        recordBook.addCourseResult(new CourseResult("English", CourseType.DIFFERENTIATED_CREDIT, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Chemistry", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Programming", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.setGrade(Grade.EXCELLENT);

        assertEquals(Grade.EXCELLENT, recordBook.getGrade());

        double expectedAverage = (5 + 4 + 3 + 5 + 5 + 5) / 6.0; // (Excellent, Good, Satisfactory, Excellent, Excellent, Excellent)
        assertEquals(expectedAverage, recordBook.calculateAverageGrade(), 0.01);
    }

    @Test
    void testCanGetIncreasedScholarship() {
        student = new Student("John Doe", false); // Student on a paid basis
        RecordBook recordBook = student.getRecordBook();

        recordBook.addCourseResult(new CourseResult("Algorithms", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Data Structures", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Operating Systems", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Databases", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Machine Learning", CourseType.EXAM, Grade.EXCELLENT));

        assertTrue(recordBook.canGetIncreasedScholarship(), "Student should be eligible for an increased scholarship.");
    }

    @Test
    void testCheckAndTransferToBudget() {
        student = new Student("John Doe", false); // Student on a paid basis
        RecordBook recordBook = student.getRecordBook();

        recordBook.addCourseResult(new CourseResult("Math", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Physics", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("History", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("English", CourseType.DIFFERENTIATED_CREDIT, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("Chemistry", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Programming", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Biology", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("Geography", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Art", CourseType.DIFFERENTIATED_CREDIT, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("Sports", CourseType.EXAM, Grade.EXCELLENT));

        student.checkAndTransferToBudget();
        assertTrue(student.isBudget(), "Student should have been transferred to budget.");
    }

    @Test
    void testRedDiploma() {
        student = new Student("John Doe", false); // Student on a paid basis
        RecordBook recordBook = student.getRecordBook();

        recordBook.addCourseResult(new CourseResult("Math", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Physics", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("History", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("English", CourseType.DIFFERENTIATED_CREDIT, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Chemistry", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Programming", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.setGrade(Grade.EXCELLENT);

        assertFalse(recordBook.canGetRed());

        recordBook.addCourseResult(new CourseResult("Art", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("PE", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("ML", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Culture", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Web", CourseType.EXAM, Grade.EXCELLENT));

        assertTrue(recordBook.canGetRed());
    }

    @Test
    void testStudentMethods() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));
        student = new Student("John Doe", false);

        RecordBook recordBook = student.getRecordBook();
        recordBook.addCourseResult(new CourseResult("Math", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("Physics", CourseType.EXAM, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("History", CourseType.EXAM, Grade.EXCELLENT));
        recordBook.addCourseResult(new CourseResult("English", CourseType.DIFFERENTIATED_CREDIT, Grade.GOOD));
        recordBook.addCourseResult(new CourseResult("Programming", CourseType.EXAM, Grade.EXCELLENT));

        // Test printAverageGrade
        student.printAverageGrade();
        assertEquals("Average grade: 4,60\n", outContent.toString().replace("\r\n", "\n"));
        outContent.reset();

        // Test checkRedDiploma
        recordBook.setGrade(Grade.EXCELLENT);
        student.checkRedDiploma();
        assertEquals("John Doe is not eligible for a red diploma.\n", outContent.toString().replace("\r\n", "\n"));
        outContent.reset();

        // Test checkIncreasedScholarship
        student.checkIncreasedScholarship();
        assertEquals("John Doe is not eligible for an increased scholarship.\n", outContent.toString().replace("\r\n", "\n"));
        System.setOut(originalOut);
    }
}
