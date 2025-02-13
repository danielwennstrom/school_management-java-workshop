package org.example;

import org.example.abstracts.AbstractPerson;
import org.example.abstracts.AbstractPerson.Role;
import org.example.data_access.StudentDAO;
import org.example.data_access.impl.CourseDAOSet;
import org.example.data_access.impl.LectureDAOSet;
import org.example.data_access.impl.StudentDAOSet;
import org.example.data_access.impl.TeacherDAOSet;
import org.example.handlers.SelectionHandlerImpl;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;
import org.example.model.StudentImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseDAOSet courseDAO = CourseDAOSet.getInstance();
        LectureDAOSet lectureDAO = LectureDAOSet.getInstance();
        StudentDAOSet studentDAO = StudentDAOSet.getInstance();
        TeacherDAOSet teacherDAO = TeacherDAOSet.getInstance();
        SelectionHandlerImpl selector = new SelectionHandlerImpl();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String subChoice;
        int subChoiceInt;
        AbstractPerson person;

        while (running) {
            displaySelectHandler(selector);
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
                    registerNewPerson(scanner, teacherDAO, studentDAO, selector);
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

    private static boolean assignStudentToCourse(Scanner scanner, StudentDAO studentDAO, CourseImpl course) {
        int studentId;

        System.out.println("Enter student's ID: ");
        studentId = scanner.nextInt();
        StudentImpl student = studentDAO.findById(studentId);

        if (student == null)
            return false;
        else {
            course.registerStudent(student);
            System.out.printf("Teacher %s was assigned to %s successfully.\n", student.getName(), course.getCourseName());
            return true;
        }
    }

    private static boolean assignTeacherToCourse(Scanner scanner, TeacherDAOSet teacherDAO, CourseImpl course) {
        int teacherId;

        System.out.println("Enter teacher's ID: ");
        teacherId = scanner.nextInt();
        TeacherImpl teacher = teacherDAO.findById(teacherId);

        if (teacher == null)
            return false;
        else {
            course.registerTeacher(teacher);
            System.out.printf("Teacher %s was assigned to %s successfully.\n", teacher.getName(), course.getCourseName());
            return true;
        }
    }

    private static void displaySelectHandler(SelectionHandlerImpl selector) {
        System.out.println("Current selections: " + selector.toString());
        System.out.println("a) Add person to selection, c) Clear selections, d) Delete selection\n");
    }

    private static void createNewCourse(TeacherDAOSet teacherDAO, Scanner scanner, CourseDAOSet courseDAO) {
        String courseName;
        LocalDate startDate;

        System.out.println("\nEnter the course name: ");
        courseName = scanner.nextLine();
        System.out.println("Enter the start date of the course (YYYY-MM-DD): ");
        startDate = LocalDate.parse(scanner.nextLine());

        CourseImpl course = new CourseImpl(courseName, startDate);

        if (assignTeacherToCourse(scanner, teacherDAO, course)) {
            courseDAO.saveCourse(course);
            System.out.println("\nCourse created successfully.");
        } else {
            System.out.println("Failed to create course due to invalid teacher ID.");
        }
    }

    private static boolean confirmOverwrite(Scanner scanner) {
        System.out.print("Do you want to overwrite the existing selections? Y/N: ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private static void registerNewPerson(Scanner scanner, TeacherDAOSet teacherDAO, StudentDAOSet studentDAO, SelectionHandlerImpl selector) {
        int subChoiceInt;
        AbstractPerson person;

        System.out.println("Register as teacher or student?");
        System.out.println("1) Teacher");
        System.out.println("2) Student");
        subChoiceInt = scanner.nextInt();
        scanner.nextLine();

        person = switch (subChoiceInt) {
            case 1 -> createTeacher(scanner);
            case 2 -> createStudent(scanner);
            default -> throw new IllegalArgumentException("Invalid choice.");
        };

        savePerson(person, teacherDAO, studentDAO);

        if (shouldAddToSelectionList(scanner)) {
            addToSelectionList(person, selector, scanner);
        }

        System.out.printf("%s %s with the ID %s registered successfully!", person.getRole(), person.getName(), person.getId());
    }

    private static AbstractPerson createTeacher(Scanner scanner) {
        TeacherImpl teacher = new TeacherImpl();
        setPersonDetails(scanner, teacher);
        return teacher;
    }

    private static AbstractPerson createStudent(Scanner scanner) {
        StudentImpl student = new StudentImpl();
        setPersonDetails(scanner, student);
        return student;
    }

    private static void setPersonDetails(Scanner scanner, AbstractPerson person) {
        System.out.println("Enter the person's name: ");
        person.setName(scanner.nextLine());
        System.out.println("Enter the person's email address: ");
        person.setEmail(scanner.nextLine());
        System.out.println("Enter the person's address: ");
        person.setAddress(scanner.nextLine());
    }

    private static void savePerson(AbstractPerson person, TeacherDAOSet teacherDAO, StudentDAOSet studentDAO) {
        if (person.getRole() == Role.TEACHER) {
            teacherDAO.saveTeacher((TeacherImpl) person);
        } else {
            studentDAO.saveStudent((StudentImpl) person);
        }
    }

    private static boolean shouldAddToSelectionList(Scanner scanner) {
        System.out.println("Add person to selection list? Y/N: ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private static void addToSelectionList(AbstractPerson person, SelectionHandlerImpl selector, Scanner scanner) {
        if (!selector.addSelection(person)) {
            if (confirmOverwrite(scanner)) {
                selector.clearSelection();
                selector.addSelection(person);
            }
        }
    }
}