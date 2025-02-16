package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.abstracts.AbstractPerson;
import org.example.abstracts.AbstractPerson.Role;
import org.example.data_access.LectureDAO;
import org.example.data_access.impl.CourseDAOSet;
import org.example.data_access.impl.LectureDAOSet;
import org.example.data_access.impl.StudentDAOSet;
import org.example.data_access.impl.TeacherDAOSet;
import org.example.handlers.SelectionHandlerImpl;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.StudentImpl;
import org.example.model.TeacherImpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {
    static SelectionHandlerImpl selector = new SelectionHandlerImpl();

    public static void main(String[] args) throws IOException {
        CourseDAOSet courseDAO = CourseDAOSet.getInstance();
        LectureDAOSet lectureDAO = LectureDAOSet.getInstance();
        StudentDAOSet studentDAO = StudentDAOSet.getInstance();
        TeacherDAOSet teacherDAO = TeacherDAOSet.getInstance();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String subChoice;
        int subChoiceInt;
        AbstractPerson person;

        seedInitialDatabase(studentDAO, teacherDAO, courseDAO);

        while (running) {
            displayCurrentSelections();
            displaySelectHandlerCommands();
            displayDatabaseActions();
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    registerNewPerson(teacherDAO, studentDAO, scanner);
                    break;
                case "2":
                    createNewCourse(teacherDAO, courseDAO, scanner);
                    break;
                case "3":
                    createNewLecture(courseDAO, lectureDAO, teacherDAO, scanner);
                    break;
                case "4":
                    break;
                case "5":
                    System.out.println("Find person, course, or a lecture?: ");
                    System.out.println("1) Person");
                    System.out.println("2) Course");
                    System.out.println("3) Lecture");
                    subChoiceInt = scanner.nextInt();
                    scanner.nextLine();
                    switch (subChoiceInt) {
                        case 1:
                            System.out.println("Find a student or teacher?: ");
                            System.out.println("1) Teacher");
                            System.out.println("2) Student");
                            int subChoicePerson = scanner.nextInt();
                            scanner.nextLine();
                            int subChoiceMethod;
                            switch (subChoicePerson) {
                                case 1:
                                    System.out.println("Find teacher by: ");
                                    System.out.println("1) ID");
                                    System.out.println("2) Name");
                                    System.out.println("3) All");
                                    subChoiceMethod = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (subChoiceMethod) {
                                        case 1:
                                            findTeacherById(teacherDAO, scanner);
                                            break;
                                        case 2:
                                            findTeacherByName(teacherDAO, scanner);
                                            break;
                                        case 3:
                                            findTeacherByAll(teacherDAO, scanner);
                                            break;
                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                            break;
                                    }
                                    break;
                                case 2:
                                    System.out.println("Find student by: ");
                                    System.out.println("1) ID");
                                    System.out.println("2) Name");
                                    System.out.println("3) All");
                                    subChoiceMethod = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (subChoiceMethod) {
                                        case 1:
                                            findStudentById(studentDAO, scanner);
                                            break;
                                        case 2:
                                            findStudentByName(studentDAO, scanner);
                                            break;
                                        case 3:
                                            findStudentByAll(studentDAO, scanner);
                                            break;
                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                            break;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice. Try again.");
                                    break;
                            }
                            break;

                        case 2:
                            System.out.println("Find course by: ");
                            System.out.println("1) ID");
                            System.out.println("2) Name");
                            System.out.println("2) Starting date");
                            System.out.println("3) All");
                            subChoiceMethod = scanner.nextInt();
                            switch (subChoiceMethod) {
                                case 1:
                                    Collection<CourseImpl> results = courseDAO.findAll();
                                    System.out.println(results.size() + " results found: ");
                                    for (CourseImpl c : results)
                                        System.out.println(c.toString());
                                    System.out.println("Enter ID: ");
                                    int id = scanner.nextInt();
                                    CourseImpl result = courseDAO.findById(id);
                                    System.out.println(result.toString());
                                    handleSelectCommandCourse(courseDAO, teacherDAO, result, scanner);
                                case 2:
                                case 3:
                                default:
                                    System.out.println("Invalid option. Please try again.");
                            }
                            break;
                        case 3:
                            System.out.println("Find lecture by: ");
                            System.out.println("1) ID");
                            System.out.println("2) Teacher");
                            System.out.println("3) Course");
                            System.out.println("4) All");
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                            break;
                    }
                    break;
                case "0":
                    System.out.println("Exiting the program.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void handleSelectCommandCourse(CourseDAOSet courseDAO, TeacherDAOSet teacherDAO, CourseImpl course, Scanner scanner) {
        boolean runHandler = true;
        scanner.nextLine();

        while (runHandler) {
            displayCurrentSelections();
            displaySelectHandlerCommandsCourse();
            System.out.println("Enter command (or q to return to main menu): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "a":
                    askAssignTeacherToCourse(courseDAO, teacherDAO, course, scanner);
                    break;
//                case "c":
//                    selector.clearSelections();
//                    break;
//                case "d":
//                    selector.removeSelection(person);
//                    break;
//                case "s":
//                    System.out.println(selector.toString());
//                    break;
                case "v":
                    System.out.println("Information about this course: ");
                    System.out.println(course.toString());
                    break;
                case "q":
                    runHandler = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private static void findTeacherByAll(TeacherDAOSet teacherDAO, Scanner scanner) {
        Collection<TeacherImpl> results = teacherDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (TeacherImpl t : results)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        TeacherImpl result = teacherDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findTeacherByName(TeacherDAOSet teacherDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<TeacherImpl> results = teacherDAO.findByName(name);
        System.out.println(results.size() + " results found: ");
        for (TeacherImpl t : results)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        TeacherImpl result = teacherDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findTeacherById(TeacherDAOSet teacherDAO, Scanner scanner) {
        System.out.println("Enter teacher ID: ");
        int id = scanner.nextInt();
        TeacherImpl result = teacherDAO.findById(id);
        scanner.nextLine();
        handleSelectCommand(result, scanner);
    }

    private static void findStudentByAll(StudentDAOSet studentDAO, Scanner scanner) {
        Collection<StudentImpl> results = studentDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (StudentImpl s : results)
            System.out.println(s.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        StudentImpl result = studentDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findStudentByName(StudentDAOSet studentDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<StudentImpl> results = studentDAO.findByName(name);
        System.out.println(results.size() + " results found: ");
        for (StudentImpl s : results)
            System.out.println(s.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        StudentImpl result = studentDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findStudentById(StudentDAOSet studentDAO, Scanner scanner) {
        System.out.println("Enter teacher ID: ");
        int id = scanner.nextInt();
        StudentImpl result = studentDAO.findById(id);
        scanner.nextLine();
        handleSelectCommand(result, scanner);
    }

    private static void seedInitialDatabase(StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, CourseDAOSet courseDAO) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        List<StudentImpl> studentData = objectMapper.readValue(new File("src/main/java/org/example/db_seeds/students.json"),
                new TypeReference<>() {
                });

        List<TeacherImpl> teacherData = objectMapper.readValue(new File("src/main/java/org/example/db_seeds/teachers.json"),
                new TypeReference<>() {
                });
        List<CourseImpl> courseData = objectMapper.readValue(new File("src/main/java/org/example/db_seeds/courses.json"),
                new TypeReference<>() {
                });

        for (StudentImpl s : studentData)
            studentDAO.saveStudent(s);

        for (TeacherImpl t : teacherData)
            teacherDAO.saveTeacher(t);

        for (CourseImpl c : courseData)
            courseDAO.saveCourse(c);
    }

    private static void displayDatabaseActions() {
        System.out.println("1) Register new person");
        System.out.println("2) Create course");
        System.out.println("3) Create lecture");
        System.out.println("4) Assign person to...");
        System.out.println("5) Find person, course or lecture by...");
        System.out.println("0) Exit");
    }

    private static void createNewLecture(CourseDAOSet courseDAO, LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, Scanner scanner) {
        LectureImpl lecture;
        CourseImpl course;
        Collection<CourseImpl> allCourses = courseDAO.findAll();

        while (true) {
            for (CourseImpl c : allCourses)
                System.out.printf("%s) %s\n", c.getId(), c.getCourseName());

            System.out.println("Enter course ID: ");
            int courseId = scanner.nextInt();
            course = courseDAO.findById(courseId);
            scanner.nextLine();

            if (course == null)
                System.out.println("Invalid course ID. Please try again.");
            else
                break;
        }

        System.out.println("Enter lecture name: ");
        String lectureName = scanner.nextLine();
        lecture = new LectureImpl(lectureName);

        if (askAssignTeacherToLecture(lectureDAO, teacherDAO, lecture, scanner)) {
            System.out.printf("Lecture with %s as lecturers created successfully.\n", lecture.shortTeachersString());
        } else {
            System.out.println("Lecture created successfully.");
            lectureDAO.saveLecture(lecture);
        }
    }

    private static boolean askAssignTeacherToLecture(LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, LectureImpl lecture, Scanner scanner) {
        TeacherImpl teacher = null;
        System.out.println("Assign teacher(s) to lecture? Y/N");

        if (scanner.nextLine().equalsIgnoreCase("n")) {
            return false;
        }

        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.TEACHER)
            displayCurrentSelections();

        System.out.println("Enter teacher IDs separated by commas: ");
        String[] teacherIds = scanner.nextLine().split(",");

        for (String id : teacherIds) {
            teacher = teacherDAO.findById(Integer.parseInt(id.trim()));
            if (teacher != null && !lecture.getTeachers().contains(teacher)) {
                lecture.registerTeacher(teacher);
                lectureDAO.saveLecture(lecture);
                System.out.printf("Teacher %s was assigned to %s successfully.\n", teacher.shortToString(), lecture.getLectureName());
            } else {
                System.out.println("Invalid teacher ID. Teacher not found.");
            }
        }

        return true;
    }

    private static boolean askAssignTeacherToCourse(CourseDAOSet courseDAO, TeacherDAOSet teacherDAO, CourseImpl course, Scanner scanner) {
        TeacherImpl teacher = null;
        System.out.println("Assign teacher to course? Y/N");
        scanner.nextLine();

        if (scanner.nextLine().equalsIgnoreCase("n")) {
            return false;
        }

        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.TEACHER)
            displayCurrentSelections();

        System.out.println("Enter teacher ID: ");
        int teacherId = scanner.nextInt();
        scanner.nextLine();

        teacher = teacherDAO.findById(teacherId);
        if (teacher != null) {
            course.registerTeacher(teacher);
            courseDAO.saveCourse(course);
            System.out.printf("Teacher %s was assigned to %s successfully.\n", teacher.shortToString(), course.getCourseName());
        } else {
            System.out.println("Invalid teacher ID. Teacher not found.");
        }

        return true;
    }

    private static void displayCurrentSelections() {
        if (!selector.getSelections().isEmpty())
            System.out.printf("Current %s selections: %s\n", selector.getSelectorRole(), selector.toString());
        else
            System.out.printf("Current selections: %s\n", selector.toString());

    }

    private static void displaySelectHandlerCommands() {
        if (!selector.getSelections().isEmpty())
            System.out.printf("a) Add person to selection      c) Clear selections (%s %s)        d) Delete selection\n",
                    selector.getSelections().size(), selector.getSelectorRole());
        else
            System.out.println("a) Add person to selection      c) Clear selections        d) Delete selection");

        System.out.println("s) Show selections      v) View information\n");
    }

    private static void displaySelectHandlerCommandsCourse() {
        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.TEACHER)
            System.out.printf("a) Assign person to course      c) Clear selections (%s %s)        u) Unassign person from course\n",
                    selector.getSelections().size(), selector.getSelectorRole());

        System.out.println("s) Show selections      v) View information\n");
    }

    private static void handleSelectCommand(AbstractPerson person, Scanner scanner) {
        boolean runHandler = true;
        scanner.nextLine();

        while (runHandler) {
            displayCurrentSelections();
            displaySelectHandlerCommands();
            System.out.println("Enter command (or q to return to main menu): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "a":
                    selector.addSelection(person);
                    break;
                case "c":
                    selector.clearSelections();
                    break;
                case "d":
                    selector.removeSelection(person);
                    break;
                case "s":
                    System.out.println(selector.toString());
                    break;
                case "v":
                    System.out.println("Information about the selected person: \n");
                    System.out.println(person.toString());
                    break;
                case "q":
                    runHandler = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private static void createNewCourse(TeacherDAOSet teacherDAO, CourseDAOSet courseDAO, Scanner scanner) {
        String courseName;
        LocalDate startDate;
        int weekDuration;

        System.out.println("Enter the course name: ");
        courseName = scanner.nextLine();
        System.out.println("Enter the start date of the course (YYYY-MM-DD): ");
        startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter the week duration: ");
        weekDuration = scanner.nextInt();

        CourseImpl course = new CourseImpl(courseName, startDate, weekDuration);

        if (askAssignTeacherToCourse(courseDAO, teacherDAO, course, scanner)) {
            System.out.printf("Course with %s as supervisor created successfully.\n", course.getSupervisor().shortToString());
        } else {
            System.out.println("Course created successfully.");
            courseDAO.saveCourse(course);
        }
    }

    private static boolean confirmOverwrite(Scanner scanner) {
        System.out.print("Do you want to overwrite the existing selections? Y/N: ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private static void registerNewPerson(TeacherDAOSet teacherDAO, StudentDAOSet studentDAO, Scanner scanner) {
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
            addToSelectionList(person, scanner);
        }

        System.out.printf("%s %s with the ID %s registered successfully!\n", person.getRole(), person.getName(), person.getId());
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

    private static void addToSelectionList(AbstractPerson person, Scanner scanner) {
        if (!selector.addSelection(person)) {
            if (confirmOverwrite(scanner)) {
                selector.clearSelections();
                selector.addSelection(person);
            }
        }
    }
}