package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.abstracts.AbstractPerson;
import org.example.abstracts.AbstractPerson.Role;
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
import java.util.ArrayList;
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
                    registerNewPerson(scanner, teacherDAO, studentDAO, selector);
                    break;
                case "2":
                    createNewCourse(teacherDAO, scanner, courseDAO);
                    break;
                case "3":
                    createNewLecture(courseDAO, scanner, teacherDAO, lectureDAO, selector);
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
                                            findTeacherById(scanner, teacherDAO);
                                            break;
                                        case 2:
                                            findTeacherByName(scanner, teacherDAO);
                                            break;
                                        case 3:
                                            findTeacherByAll(scanner, teacherDAO);
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
                                            findStudentById(scanner, studentDAO);
                                            break;
                                        case 2:
                                            findStudentByName(scanner, studentDAO);
                                            break;
                                        case 3:
                                            findStudentByAll(scanner, studentDAO);
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
                                    handleSelectCommandCourse(result, scanner);
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

    private static void handleSelectCommandCourse(CourseImpl course, Scanner scanner) {
        boolean runHandler = true;
        scanner.nextLine();

        while (runHandler) {
            displayCurrentSelections();
            displaySelectHandlerCommandsCourse();
            System.out.println("Enter command (or q to return to main menu): ");
            String choice = scanner.nextLine();

            switch (choice) {
//                case "a":
//                    selector.addSelection(person);
//                    break;
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

    private static void findTeacherByAll(Scanner scanner, TeacherDAOSet teacherDAO) {
        Collection<TeacherImpl> results = teacherDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (TeacherImpl t : results)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        TeacherImpl result = teacherDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findTeacherByName(Scanner scanner, TeacherDAOSet teacherDAO) {
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

    private static void findTeacherById(Scanner scanner, TeacherDAOSet teacherDAO) {
        System.out.println("Enter teacher ID: ");
        int id = scanner.nextInt();
        TeacherImpl result = teacherDAO.findById(id);
        scanner.nextLine();
        handleSelectCommand(result, scanner);
    }

    private static void findStudentByAll(Scanner scanner, StudentDAOSet studentDAO) {
        Collection<StudentImpl> results = studentDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (StudentImpl s : results)
            System.out.println(s.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        StudentImpl result = studentDAO.findById(id);
        handleSelectCommand(result, scanner);
    }

    private static void findStudentByName(Scanner scanner, StudentDAOSet studentDAO) {
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

    private static void findStudentById(Scanner scanner, StudentDAOSet studentDAO) {
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

    private static void createNewLecture(CourseDAOSet courseDAO, Scanner scanner, TeacherDAOSet teacherDAO, LectureDAOSet lectureDAO, SelectionHandlerImpl selector) {
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

        if (askAssignTeacherToLecture(scanner, teacherDAO, lectureDAO, lecture, selector)) {
            System.out.println("Lecture created successfully.\n");
        } else {
            System.out.println("Failed to create lecture for unknown reason. Please try again.");
        }
    }

    private static boolean askAssignTeacherToLecture(Scanner scanner, TeacherDAOSet teacherDAO, LectureDAOSet lectureDAO, LectureImpl lecture, SelectionHandlerImpl selector) {
        TeacherImpl teacher = null;
        System.out.println("Assign teacher(s) to lecture? Y/N");

        if (scanner.nextLine().equalsIgnoreCase("n")) {
            return false;
        }

        if (!selector.getSelections().isEmpty() && selector.getSelections().getFirst().getRole() == Role.TEACHER) {
            askAssignSelectedTeachersToLecture(scanner, teacherDAO, lecture);
            if (selector.getSelections().size() > 1)
                System.out.printf("Teachers %s were assigned to the lecture successfully.\n", selector.toString());
            else
                System.out.printf("Teacher(s) %s assigned to the lecture successfully.\n", lecture.shortTeachersString());
            lectureDAO.saveLecture(lecture);
        } else {
            teacher = askTeacherToAssign(scanner, teacherDAO);
            lecture.registerTeacher(teacher);
            System.out.printf("Teacher %s %s was assigned to the lecture successfully.\n", teacher.getId(), teacher.getName());
        }

        return true;
    }

    private static void askAssignSelectedTeachersToLecture(Scanner scanner, TeacherDAOSet teacherDAO, LectureImpl lecture) {
        displayCurrentSelections();

        System.out.print("Assign all currently selected teachers? Y/N: ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("y")) {
            List<TeacherImpl> teacherList = new ArrayList<>();
            for (AbstractPerson person : selector.getSelections()) {
                if (person instanceof TeacherImpl) {
                    teacherList.add((TeacherImpl) person);
                }
            }
            lecture.setTeachers(teacherList);
            System.out.println("All selected teachers assigned successfully.");
            return;
        }

        System.out.println("Assign from selected teachers? Y/N: ");
        input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("y")) {
            askAssignFromSelectedToLecture(scanner, teacherDAO, lecture);
        }
    }

    private static void askAssignFromSelectedToLecture(Scanner scanner, TeacherDAOSet teacherDAO, LectureImpl lecture) {
        displayCurrentSelections();
        System.out.println("Enter teacher IDs separated by commas (or 's' to stop): ");
        String[] teacherIds = scanner.nextLine().split(",");

        for (String id : teacherIds) {
            TeacherImpl teacher = teacherDAO.findById(Integer.parseInt(id.trim()));
            if (teacher != null) {
                lecture.registerTeacher(teacher);
                System.out.printf("Teacher %s was assigned to %s successfully.\n", teacher.getName(), lecture.getLectureName());
            } else {
                System.out.println("Invalid teacher ID. Teacher not found.");
            }
        }

        selector.clearSelections();
    }

    private static TeacherImpl askTeacherToAssign(Scanner scanner, TeacherDAOSet teacherDAO) {
        while (true) {
            System.out.println("Enter teacher's ID: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please try again.\n");
                continue;
            }

            int teacherId = scanner.nextInt();
            TeacherImpl teacher = teacherDAO.findById(teacherId);
            scanner.nextLine();

            if (teacher == null) {
                System.out.println("Invalid teacher ID. Please try again.");
            } else {
                return teacher;
            }
        }
    }

    private static boolean askAssignTeacherToCourse(Scanner scanner, TeacherDAOSet teacherDAO, CourseImpl course) {
        TeacherImpl supervisor;

        System.out.println("Assign a teacher from selections to the course? Y/N: ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            displayCurrentSelections();
        }

        supervisor = askTeacherToAssign(scanner, teacherDAO);

        course.registerTeacher(supervisor);
        System.out.printf("Teacher %s was assigned to %s successfully.\n", supervisor.getName(), course.getCourseName());
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

    private static void createNewCourse(TeacherDAOSet teacherDAO, Scanner scanner, CourseDAOSet courseDAO) {
        String courseName;
        LocalDate startDate;
        int weekDuration;

        System.out.println("Enter the course name: \n");
        courseName = scanner.nextLine();
        System.out.println("Enter the start date of the course (YYYY-MM-DD): ");
        startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter the week duration: ");
        weekDuration = scanner.nextInt();

        CourseImpl course = new CourseImpl(courseName, startDate, weekDuration);

        if (askAssignTeacherToCourse(scanner, teacherDAO, course)) {
            courseDAO.saveCourse(course);
            System.out.println("Course created successfully.\n");
        } else {
            System.out.println("Failed to create course for unknown reason. Please try again.");
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

    private static void addToSelectionList(AbstractPerson person, SelectionHandlerImpl selector, Scanner scanner) {
        if (!selector.addSelection(person)) {
            if (confirmOverwrite(scanner)) {
                selector.clearSelections();
                selector.addSelection(person);
            }
        }
    }
}