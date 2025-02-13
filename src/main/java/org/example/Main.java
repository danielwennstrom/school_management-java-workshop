package org.example;

import org.example.abstracts.AbstractPerson;
import org.example.data_access.impl.CourseDAOSet;
import org.example.data_access.impl.LectureDAOSet;
import org.example.data_access.impl.StudentDAOSet;
import org.example.data_access.impl.TeacherDAOSet;
import org.example.interfaces.Course;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;
import org.example.model.StudentImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseDAOSet courseDAO = CourseDAOSet.getInstance();
        LectureDAOSet lectureDAO = LectureDAOSet.getInstance();
        StudentDAOSet studentDAO = StudentDAOSet.getInstance();
        TeacherDAOSet teacherDAO = TeacherDAOSet.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String subChoice;
        int subChoiceInt;

        while (running) {
            System.out.println("1) Register new person");
            System.out.println("2) Create course");
            System.out.println("3) Create lecture");
            System.out.println("4) Assign person to...");
            System.out.println("5) Find person, course or lecture by...");
            System.out.println("6) Create course");
            System.out.println("7) Create course");
            System.out.println("8) Create course");
            System.out.println("9) Create course");
            System.out.println("0) Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerNewPerson(scanner, teacherDAO, studentDAO);
                    break;
                case 2:
                    createNewCourse(teacherDAO, scanner, courseDAO);
                    break;
                case 3:
                    createNewLecture(courseDAO, scanner, teacherDAO, lectureDAO);
                    break;
                case 4:
                    System.out.println("Assign a student or teacher?");
                    System.out.println("1) Student");
                    System.out.println("2) Teacher");
                    subChoiceInt = scanner.nextInt();
                    switch (subChoiceInt) {
                        case 1:

                            break;
                        case 2:

                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                case 5:
                    System.out.println("Find person, course, or a lecture?: ");
                    System.out.println("1) Person");
                    System.out.println("2) Course");
                    System.out.println("3) Lecture");
                    subChoiceInt = scanner.nextInt();
                    switch (subChoiceInt) {
                        case 1:
                            System.out.println("Find a student or teacher?: ");
                            System.out.println("1) Student");
                            System.out.println("2) Teacher");
                            int subChoicePerson = scanner.nextInt();
                            switch (subChoicePerson) {
                                case 1:
                                    break;
                                case 2:
                                    break;
                                default:
                                    System.out.println("Invalid choice. Try again.");
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                case 0:
                    System.out.println("Exiting the program.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void createNewLecture(CourseDAOSet courseDAO, Scanner scanner, TeacherDAOSet teacherDAO, LectureDAOSet lectureDAO) {
        String subChoice;
        LectureImpl lecture;
        Collection<CourseImpl> allCourses = courseDAO.findAll();

        System.out.println("Enter course ID: ");
        for (CourseImpl course : allCourses)
            System.out.printf("%s) %s", course.getId(), course.getCourseName());

        int courseId = scanner.nextInt();
        CourseImpl course = courseDAO.findById(courseId);
        System.out.println("Enter lecture name: ");
        String lectureName = scanner.nextLine();
        lecture = new LectureImpl(lectureName);
        System.out.println("Assign teacher to lecture? Y/N");
        subChoice = scanner.nextLine();
        subChoice = subChoice.toLowerCase();

        switch (subChoice) {
            case "y":
                Collection<TeacherImpl> allTeachers = teacherDAO.findAll();

                System.out.println("Enter teacher ID: ");
                for (TeacherImpl teacher : allTeachers)
                    System.out.printf("%s) %s", teacher.getId(), teacher.getName());

                TeacherImpl supervisor = teacherDAO.findById(scanner.nextInt());

                if (supervisor == null)
                    System.out.println("Invalid teacher ID. Try again.");
                else {
                    lecture.registerTeacher(supervisor);
                }
                break;
            case "n":
                lectureDAO.saveLecture(lecture);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                break;
        }

        course.registerLecture(lecture);

        System.out.println("Lecture created successfully.");
    }

    private static void createNewCourse(TeacherDAOSet teacherDAO, Scanner scanner, CourseDAOSet courseDAO) {
        String subChoice;
        CourseImpl course;
        Collection<TeacherImpl> allTeachers = teacherDAO.findAll();

        System.out.println("Enter the course name: ");
        String courseName = scanner.nextLine();
        System.out.println("Enter the start date of the course: ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        course = new CourseImpl(courseName, startDate);

        System.out.println("Assign a teacher as supervisor? Y/N");
        subChoice = scanner.nextLine();
        subChoice = subChoice.toLowerCase();
        switch (subChoice) {
            case "y":
                System.out.println("Enter teacher ID: ");

                for (TeacherImpl teacher : allTeachers)
                    System.out.printf("%s) %s", teacher.getId(), teacher.getName());

                TeacherImpl supervisor = teacherDAO.findById(scanner.nextInt());

                if (supervisor == null)
                    System.out.println("Invalid teacher ID. Try again.");
                else {
                    course.registerTeacher(supervisor);
                }
            case "n":
                courseDAO.saveCourse(course);
        }
    }

    private static void registerNewPerson(Scanner scanner, TeacherDAOSet teacherDAO, StudentDAOSet studentDAO) {
        int subChoiceInt;
        AbstractPerson person;

        System.out.println("Register as teacher or student?");
        System.out.println("1) Teacher");
        System.out.println("2) Student");
        subChoiceInt = scanner.nextInt();
        scanner.nextLine();
        switch (subChoiceInt) {
            case 1:
                person = new TeacherImpl() {
                };
                break;
            case 2:
                person = new StudentImpl() {
                };
                break;
            default:
                throw new IllegalArgumentException("Invalid choice");
        }

        System.out.println("Enter the person's name: ");
        person.setName(scanner.nextLine());
        System.out.println("Enter the person's email address: ");
        person.setEmail(scanner.nextLine());
        System.out.println("Enter the person's address: ");
        person.setAddress(scanner.nextLine());

        if (subChoiceInt == 1)
            teacherDAO.saveTeacher((TeacherImpl) person);
        else
            studentDAO.saveStudent((StudentImpl) person);

        System.out.println("Person registered successfully!");
    }
}