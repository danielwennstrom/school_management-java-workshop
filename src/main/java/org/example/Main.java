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

        seedInitialDatabase(studentDAO, teacherDAO, courseDAO, lectureDAO);

        while (running) {
            displayCurrentSelections();
            displayDatabaseActions();
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerNewPerson(teacherDAO, studentDAO, scanner);
                    break;
                case 2:
                    createNewCourse(teacherDAO, courseDAO, scanner);
                    break;
                case 3:
                    createNewLecture(courseDAO, lectureDAO, teacherDAO, scanner);
                    break;
                case 4:
                    askFindByType(scanner, teacherDAO, studentDAO, courseDAO, lectureDAO);
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again. (Main)");
                    break;
            }
        }
    }

    private static void askFindByType(Scanner scanner, TeacherDAOSet teacherDAO, StudentDAOSet studentDAO, CourseDAOSet courseDAO, LectureDAOSet lectureDAO) {
        int subChoiceInt;
        System.out.println("Find person, course, or a lecture?: ");
        System.out.println("1) Person");
        System.out.println("2) Course");
        System.out.println("3) Lecture");
        subChoiceInt = scanner.nextInt();
        scanner.nextLine();
        switch (subChoiceInt) {
            case 1:
                askFindPersonByMethod(scanner, teacherDAO, studentDAO);
                break;
            case 2:
                CourseImpl resultCourse = askFindCourseByMethod(scanner, courseDAO);
                if (resultCourse == null)
                    break;
                handleSelectCommandCourse(courseDAO, lectureDAO, studentDAO, teacherDAO, resultCourse, scanner);
                break;
            case 3:
                LectureImpl resultLecture = askFindLectureByMethod(scanner, lectureDAO, teacherDAO, courseDAO);
                if (resultLecture == null)
                    break;
                handleSelectCommandLecture(lectureDAO, teacherDAO, resultLecture, scanner);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                break;
        }
    }

    private static void askFindPersonByMethod(Scanner scanner, TeacherDAOSet teacherDAO, StudentDAOSet studentDAO) {
        System.out.println("Find a student or teacher?: ");
        System.out.println("1) Teacher");
        System.out.println("2) Student");
        int subChoicePerson = scanner.nextInt();
        scanner.nextLine();

        switch (subChoicePerson) {
            case 1:
                TeacherImpl resultTeacher = askFindTeacherByMethod(scanner, teacherDAO);
                if (resultTeacher == null)
                    break;
                handleSelectCommand(studentDAO, teacherDAO, resultTeacher, scanner);
                break;
            case 2:
                StudentImpl resultStudent = askFindStudentByMethod(scanner, studentDAO);
                if (resultStudent == null)
                    break;
                handleSelectCommand(studentDAO, teacherDAO, resultStudent, scanner);
                break;
            default:
                System.out.println("Invalid choice. Try again. (Person)");
                break;
        }
    }

    private static LectureImpl askFindLectureByMethod(Scanner scanner, LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, CourseDAOSet courseDAO) {
        int subChoiceMethod;
        System.out.println("Find lecture by: ");
        System.out.println("1) ID");
        System.out.println("2) Teacher");
        System.out.println("3) Course");
        System.out.println("4) All");
        subChoiceMethod = scanner.nextInt();
        scanner.nextLine();
        LectureImpl resultLecture = null;
        switch (subChoiceMethod) {
            case 1:
                resultLecture = findLectureById(lectureDAO, scanner);
                break;
            case 2:
                resultLecture = findLectureByTeacher(lectureDAO, teacherDAO, scanner);
                break;
            case 3:
                resultLecture = findLectureByCourse(courseDAO, lectureDAO, scanner);
                break;
            case 4:
                resultLecture = findLectureByAll(lectureDAO, scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again. (Lecture)");
                break;
        }
        return resultLecture;
    }

    private static CourseImpl askFindCourseByMethod(Scanner scanner, CourseDAOSet courseDAO) {
        int subChoiceMethod;
        System.out.println("Find course by: ");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) All");
        subChoiceMethod = scanner.nextInt();
        scanner.nextLine();

        CourseImpl resultCourse = null;
        switch (subChoiceMethod) {
            case 1:
                resultCourse = findCourseById(courseDAO, scanner);
                break;
            case 2:
                resultCourse = findCourseByName(courseDAO, scanner);
                break;
            case 3:
                resultCourse = findCourseByAll(courseDAO, scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again. (Course)");
                break;
        }
        return resultCourse;
    }

    private static StudentImpl askFindStudentByMethod(Scanner scanner, StudentDAOSet studentDAO) {
        int subChoiceMethod;
        System.out.println("Find student by: ");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) All");
        subChoiceMethod = scanner.nextInt();
        scanner.nextLine();
        StudentImpl resultStudent = null;
        switch (subChoiceMethod) {
            case 1:
                resultStudent = findStudentById(studentDAO, scanner);
                break;
            case 2:
                resultStudent = findStudentByName(studentDAO, scanner);
                break;
            case 3:
                resultStudent = findStudentByAll(studentDAO, scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again. (Student)");
                break;
        }
        return resultStudent;
    }

    private static TeacherImpl askFindTeacherByMethod(Scanner scanner, TeacherDAOSet teacherDAO) {
        int subChoiceMethod;
        System.out.println("Find teacher by: ");
        System.out.println("1) ID");
        System.out.println("2) Name");
        System.out.println("3) All");
        subChoiceMethod = scanner.nextInt();
        scanner.nextLine();
        TeacherImpl resultTeacher = null;
        switch (subChoiceMethod) {
            case 1:
                resultTeacher = findTeacherById(teacherDAO, scanner);
                break;
            case 2:
                resultTeacher = findTeacherByName(teacherDAO, scanner);
                break;
            case 3:
                resultTeacher = findTeacherByAll(teacherDAO, scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again. (Teacher)");
                break;
        }
        return resultTeacher;
    }

    private static CourseImpl findCourseById(CourseDAOSet courseDAO, Scanner scanner) {
        Collection<CourseImpl> results = courseDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (CourseImpl c : results)
            System.out.println(c.toString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return courseDAO.findById(id);
    }

    private static CourseImpl findCourseByName(CourseDAOSet courseDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<CourseImpl> results = courseDAO.findByName(name);
        System.out.println(results.size() + " results found: ");
        for (CourseImpl c : results)
            System.out.println(c.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return courseDAO.findById(id);
    }

    private static CourseImpl findCourseByAll(CourseDAOSet courseDAO, Scanner scanner) {
        Collection<CourseImpl> results = courseDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (CourseImpl c : results)
            System.out.println(c.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return courseDAO.findById(id);
    }

    private static void handleSelectCommandCourse(CourseDAOSet courseDAO, LectureDAOSet lectureDAO, StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, CourseImpl course, Scanner scanner) {
        boolean running = true;
        int subChoice;

        while (running) {
            System.out.println(course.toString());
            displayCurrentSelections();
            displaySelectHandlerCommandsCourse();
            System.out.println("Enter command (or q to return to main menu): ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "a":
                    System.out.println("1) Person");
                    System.out.println("2) Lecture");
                    subChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (subChoice) {
                        case 1:
                            askAssignPersonToCourse(courseDAO, studentDAO, teacherDAO, course, scanner);
                            break;
                        case 2:
                            promptAssignLectureToCourse(courseDAO, lectureDAO, course, scanner);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case "e":
                    editCourseInformation(courseDAO, course, scanner);
                    break;
                case "u":
                    System.out.println("1) Person");
                    System.out.println("2) Lecture");
                    subChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (subChoice) {
                        case 1:
                            askUnassignPersonFromCourse(courseDAO, studentDAO, course, scanner);
                            break;
                        case 2:
                            promptUnassignLectureFromCourse(courseDAO, lectureDAO, course, scanner);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case "s":
                    System.out.println(selector.toString());
                    break;
                case "v":
                    System.out.println("Information about this course: ");
                    System.out.println(course);
                    break;
                case "q":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }

    private static LectureImpl findLectureById(LectureDAOSet lectureDAO, Scanner scanner) {
        Collection<LectureImpl> results = lectureDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (LectureImpl l : results)
            System.out.println(l.toString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return lectureDAO.findById(id);
    }

    private static LectureImpl findLectureByTeacher(LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<TeacherImpl> resultsTeachers = teacherDAO.findByName(name);
        System.out.println(resultsTeachers.size() + " results found: ");
        for (TeacherImpl t : resultsTeachers)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        TeacherImpl resultTeacher = teacherDAO.findById(id);

        Collection<LectureImpl> results = lectureDAO.findByTeacher(resultTeacher);
        System.out.println(results.size() + " results found: ");
        for (LectureImpl l : results)
            System.out.println(l.shortToString());
        System.out.println("Enter ID: ");
        id = scanner.nextInt();
        scanner.nextLine();

        return lectureDAO.findById(id);
    }

    private static LectureImpl findLectureByCourse(CourseDAOSet courseDAO, LectureDAOSet lectureDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<CourseImpl> resultsCourses = courseDAO.findByName(name);
        System.out.println(resultsCourses.size() + " results found: ");
        for (CourseImpl c : resultsCourses)
            System.out.println(c.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        CourseImpl resultCourse = courseDAO.findById(id);

        Collection<LectureImpl> results = lectureDAO.findByCourse(resultCourse);
        System.out.println(results.size() + " results found: ");
        for (LectureImpl l : results)
            System.out.println(l.shortToString());
        System.out.println("Enter ID: ");
        id = scanner.nextInt();
        scanner.nextLine();

        return lectureDAO.findById(id);
    }

    private static LectureImpl findLectureByAll(LectureDAOSet lectureDAO, Scanner scanner) {
        Collection<LectureImpl> results = lectureDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (LectureImpl l : results)
            System.out.println(l.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return lectureDAO.findById(id);
    }

    private static void handleSelectCommandLecture(LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, LectureImpl lecture, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println(lecture.toString());
            displayCurrentSelections();
            displaySelectHandlerCommandsLecture();
            System.out.println("Enter command (or q to return to main menu): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "a":
                    askAssignTeacherToLecture(lectureDAO, teacherDAO, lecture, scanner);
                    break;
                case "e":
                    editLectureInformation(lectureDAO, lecture, scanner);
                    break;
                case "u":
                    askUnassignTeacherFromLecture(lectureDAO, teacherDAO, lecture, scanner);
                    break;
                case "s":
                    System.out.println(selector.toString());
                    break;
                case "v":
                    System.out.println("Information about this lecture: ");
                    System.out.println(lecture);
                    break;
                case "q":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }

    private static void editPersonalInformation(StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, AbstractPerson person, Scanner scanner) {
        while (true) {
            System.out.println("Edit... (or 0 to save and exit)");
            System.out.printf("1) Name - %s\n", person.getName());
            System.out.printf("2) E-mail address - %s\n", person.getEmail());
            System.out.printf("3) Address - %s\n", person.getAddress());
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    if (newName.isEmpty()) {
                        System.out.println("Value can't be empty.");
                        break;
                    }
                    person.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new e-mail address: ");
                    String newEmail = scanner.nextLine();
                    if (newEmail.isEmpty()) {
                        System.out.println("Value can't be empty.");
                        break;
                    }
                    person.setEmail(newEmail);
                    break;
                case 3:
                    System.out.println("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    if (newAddress.isEmpty()) {
                        System.out.println("Value can't be empty.");
                        break;
                    }
                    person.setAddress(newAddress);
                    break;
                case 0:
                    if (person.getRole() == Role.TEACHER)
                        teacherDAO.saveTeacher((TeacherImpl) person);
                    else
                        studentDAO.saveStudent((StudentImpl) person);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void editCourseInformation(CourseDAOSet courseDAO, CourseImpl course, Scanner scanner) {
        while (true) {
            System.out.println("Edit... (or 0 to save and exit)");
            System.out.printf("1) Course name - %s\n", course.getCourseName());
            System.out.printf("2) Starting date - %s\n", course.getStartDate());
            System.out.printf("3) Week duration - %s weeks\n", course.getWeekDuration());
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new course name: ");
                    String newName = scanner.nextLine();
                    if (newName.isEmpty()) {
                        System.out.println("Value can't be empty.");
                        break;
                    }
                    course.setCourseName(newName);
                    break;
                case 2:
                    System.out.print("Enter new course starting date: ");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    course.setStartDate(newDate);
                    break;
                case 3:
                    System.out.println("Enter new week duration: ");
                    int newDuration = scanner.nextInt();
                    scanner.nextLine();
                    if (newDuration < 0) {
                        System.out.println("Value can't be negative.");
                        break;
                    }
                    course.setWeekDuration(newDuration);
                    break;
                case 0:
                    courseDAO.saveCourse(course);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void editLectureInformation(LectureDAO lectureDAO, LectureImpl lecture, Scanner scanner) {
        while (true) {
            System.out.println("Edit... (or 0 to save and exit)");
            System.out.printf("1) Lecture name - %s\n", lecture.getLectureName());
            System.out.printf("2) Lecture date - %s\n", lecture.getDate());
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new lecture name: ");
                    String newName = scanner.nextLine();
                    if (newName.isEmpty()) {
                        System.out.println("Value can't be empty.");
                        break;
                    }
                    lecture.setLectureName(newName);
                    break;
                case 2:
                    System.out.print("Enter new lecture date: ");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    lecture.setDate(newDate);
                    break;
                case 0:
                    lectureDAO.saveLecture(lecture);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void promptAssignLectureToCourse(CourseDAOSet courseDAO, LectureDAOSet lectureDAO, CourseImpl course, Scanner scanner) {
        System.out.println("Enter lecture IDs separated by commas: ");
        String[] lectureIds = scanner.nextLine().split(",");

        for (String id : lectureIds) {
            LectureImpl lecture = lectureDAO.findById(Integer.parseInt(id.trim()));

            if (lecture != null) {
                lecture.registerCourse(course);
                lectureDAO.saveLecture(lecture);
                course.registerLecture(lecture);
            }
        }


        courseDAO.saveCourse(course);
    }

    private static void promptUnassignLectureFromCourse(CourseDAOSet courseDAO, LectureDAOSet lectureDAO, CourseImpl course, Scanner scanner) {
        System.out.println("Enter lecture IDs separated by commas: ");
        String[] lectureIds = scanner.nextLine().split(",");

        for (String id : lectureIds) {
            LectureImpl lecture = lectureDAO.findById(Integer.parseInt(id.trim()));

            if (lecture != null) {
                lecture.unregisterCourse();
                lectureDAO.saveLecture(lecture);
                course.unregisterLecture(lecture);
            }
        }

        courseDAO.saveCourse(course);
    }

    private static void askUnassignPersonFromCourse(CourseDAOSet courseDAO, StudentDAOSet studentDAO, CourseImpl course, Scanner scanner) {
        int subChoicePerson;
        System.out.println("Unassign teacher or student from course?: ");
        System.out.println("1) Teacher");
        System.out.println("2) Student");
        subChoicePerson = scanner.nextInt();
        scanner.nextLine();

        switch (subChoicePerson) {
            case 1:
                askUnassignTeacherFromCourse(courseDAO, course, scanner);
                break;
            case 2:
                promptUnassignStudentsFromCourse(courseDAO, studentDAO, course, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void promptUnassignStudentsFromCourse(CourseDAOSet courseDAO, StudentDAOSet studentDAO, CourseImpl course, Scanner scanner) {
        System.out.println("Enter student IDs separated by commas: ");
        String[] studentIds = scanner.nextLine().split(",");
        scanner.nextLine();
        Collection<StudentImpl> studentList = new ArrayList<>();
        for (String studentId : studentIds) {
            StudentImpl student = studentDAO.findById(Integer.parseInt(studentId.trim()));
            if (student != null) {
                studentList.add(student);
            }
        }
        unassignStudentsFromCourse(courseDAO, studentDAO, course, studentList);
    }

    private static void askAssignPersonToCourse(CourseDAOSet courseDAO, StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, CourseImpl course, Scanner scanner) {
        int subChoicePerson;
        System.out.println("Assign teacher or student to course?: ");
        System.out.println("1) Teacher");
        System.out.println("2) Student");
        subChoicePerson = scanner.nextInt();
        scanner.nextLine();

        switch (subChoicePerson) {
            case 1:
                askAssignTeacherToCourse(courseDAO, teacherDAO, course, scanner);
                break;
            case 2:
                askAssignStudentToCourse(courseDAO, studentDAO, course, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void askUnassignTeacherFromCourse(CourseDAOSet courseDAO, CourseImpl course, Scanner scanner) {
        if (course.getSupervisor() != null) {
            System.out.printf("Unassign %s as supervisor? Y/N: \n", course.getSupervisor().shortToString());
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                course.unregisterTeacher();

                courseDAO.saveCourse(course);
            }
        } else
            System.out.println("This course has no assigned supervisor.");
    }

    private static void unassignStudentsFromCourse(CourseDAOSet courseDAO, StudentDAOSet studentDAO, CourseImpl course, Collection<StudentImpl> students) {
        for (StudentImpl student : students) {
            if (course.getStudents().contains(student) && student.getCourses().contains(course)) {
                course.unregisterStudent(student);
                student.unregisterCourse(course);

                courseDAO.saveCourse(course);
                studentDAO.saveStudent(student);
            }
        }
    }

    private static TeacherImpl findTeacherByAll(TeacherDAOSet teacherDAO, Scanner scanner) {
        Collection<TeacherImpl> results = teacherDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (TeacherImpl t : results)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return teacherDAO.findById(id);
    }

    private static TeacherImpl findTeacherByName(TeacherDAOSet teacherDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<TeacherImpl> results = teacherDAO.findByName(name);
        System.out.println(results.size() + " results found: ");
        for (TeacherImpl t : results)
            System.out.println(t.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return teacherDAO.findById(id);
    }

    private static TeacherImpl findTeacherById(TeacherDAOSet teacherDAO, Scanner scanner) {
        System.out.println("Enter teacher ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return teacherDAO.findById(id);
    }

    private static StudentImpl findStudentByAll(StudentDAOSet studentDAO, Scanner scanner) {
        Collection<StudentImpl> results = studentDAO.findAll();
        System.out.println(results.size() + " results found: ");
        for (StudentImpl s : results)
            System.out.println(s.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return studentDAO.findById(id);
    }

    private static StudentImpl findStudentByName(StudentDAOSet studentDAO, Scanner scanner) {
        System.out.println("Enter a name (or part of a name): ");
        String name = scanner.nextLine();
        Collection<StudentImpl> results = studentDAO.findByName(name);
        System.out.println(results.size() + " results found: ");
        for (StudentImpl s : results)
            System.out.println(s.shortToString());
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return studentDAO.findById(id);
    }

    private static StudentImpl findStudentById(StudentDAOSet studentDAO, Scanner scanner) {
        System.out.println("Enter teacher ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        return studentDAO.findById(id);
    }

    private static void seedInitialDatabase(StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, CourseDAOSet courseDAO, LectureDAOSet lectureDAO) throws IOException {
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

        List<LectureImpl> lectureData = objectMapper.readValue(new File("src/main/java/org/example/db_seeds/lectures.json"),
                new TypeReference<>() {
                });

        for (StudentImpl s : studentData)
            studentDAO.saveStudent(s);

        for (TeacherImpl t : teacherData)
            teacherDAO.saveTeacher(t);

        for (CourseImpl c : courseData)
            courseDAO.saveCourse(c);

        for (LectureImpl l : lectureData)
            lectureDAO.saveLecture(l);
    }

    private static void displayDatabaseActions() {
        System.out.println("1) Register new person");
        System.out.println("2) Create course");
        System.out.println("3) Create lecture");
        System.out.println("4) Find person, course or lecture by...");
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
        System.out.println("Enter the date of the lecture (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        lecture = new LectureImpl(lectureName, date);

        if (askAssignTeacherToLecture(lectureDAO, teacherDAO, lecture, scanner)) {
            System.out.printf("Lecture with %s as lecturers created successfully.\n", lecture.shortTeachersString());
        } else {
            System.out.println("Lecture created successfully.");
            lectureDAO.saveLecture(lecture);
        }
    }

    private static boolean askAssignTeacherToLecture(LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, LectureImpl lecture, Scanner scanner) {
        TeacherImpl teacher;
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

    private static void askUnassignTeacherFromLecture(LectureDAOSet lectureDAO, TeacherDAOSet teacherDAO, LectureImpl lecture, Scanner scanner) {
        TeacherImpl teacher;
        System.out.println("Unassign teacher(s) from lecture? Y/N");

        if (scanner.nextLine().equalsIgnoreCase("n")) {
            return;
        }

        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.TEACHER)
            displayCurrentSelections();

        System.out.println("Enter teacher IDs separated by commas: ");
        String[] teacherIds = scanner.nextLine().split(",");

        for (String id : teacherIds) {
            teacher = teacherDAO.findById(Integer.parseInt(id.trim()));
            if (teacher != null && !lecture.getTeachers().contains(teacher)) {
                lecture.unregisterTeacher(teacher);
                lectureDAO.saveLecture(lecture);
                System.out.printf("Teacher %s was unassigned from %s successfully.\n", teacher.shortToString(), lecture.getLectureName());
            } else {
                System.out.println("Invalid teacher ID. Teacher not found.");
            }
        }

    }

    private static boolean askAssignTeacherToCourse(CourseDAOSet courseDAO, TeacherDAOSet teacherDAO, CourseImpl course, Scanner scanner) {
        TeacherImpl teacher;
        System.out.println("Assign teacher to course? Y/N");

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

    private static void askAssignStudentToCourse(CourseDAOSet courseDAO, StudentDAOSet studentDAO, CourseImpl course, Scanner scanner) {
        StudentImpl student;
        System.out.println("Assign student(s) to course? Y/N");

        if (scanner.nextLine().equalsIgnoreCase("n")) {
            return;
        }

        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.STUDENT) {
            displayCurrentSelections();
            System.out.println("Assign all currently selected students to course? Y/N");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                for (AbstractPerson s : selector.getSelections()) {
                    course.registerStudent((StudentImpl) s);
                    ((StudentImpl) s).registerCourse(course);
                    studentDAO.saveStudent((StudentImpl) s);
                }

                courseDAO.saveCourse(course);
                return;
            }
        }

        if (!selector.getSelections().isEmpty() && selector.getSelectorRole() == Role.STUDENT)
            displayCurrentSelections();

        System.out.println("Enter student IDs separated by commas: ");
        String[] studentIds = scanner.nextLine().split(",");

        for (String id : studentIds) {
            student = studentDAO.findById(Integer.parseInt(id.trim()));
            if (student != null && !course.getStudents().contains(student)) {
                course.registerStudent(student);
                student.registerCourse(course);
                studentDAO.saveStudent(student);
                System.out.printf("Student %s was assigned to %s successfully.\n", student.shortToString(), course.getCourseName());
            } else {
                System.out.println("Invalid student ID. Student not found.");
            }
        }

        courseDAO.saveCourse(course);
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

        System.out.println("e) Edit personal information        s) Show selections      v) View information\n");
    }

    private static void displaySelectHandlerCommandsCourse() {
        System.out.println("a) Assign ... to course      s) Show selections      u) Unassign ... from course");

        System.out.println("e) Edit course information      v) View information\n");
    }

    private static void displaySelectHandlerCommandsLecture() {
        System.out.println("a) Assign teachers to lecture      s) Show selections      u) Unassign teachers from lecture");

        System.out.println("e) Edit lecture information        v) View information\n");
    }

    private static void handleSelectCommand(StudentDAOSet studentDAO, TeacherDAOSet teacherDAO, AbstractPerson person, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println(person);
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
                case "e":
                    editPersonalInformation(studentDAO, teacherDAO, person, scanner);
                case "s":
                    System.out.println(selector.toString());
                    break;
                case "v":
                    System.out.println("Information about the selected person: \n");
                    System.out.println(person.toString());
                    break;
                case "q":
                    running = false;
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