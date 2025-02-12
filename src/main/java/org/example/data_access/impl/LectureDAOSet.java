package org.example.data_access.impl;

import org.example.data_access.LectureDAO;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LectureDAOSet implements LectureDAO {
    private Set<LectureImpl> lectures = new HashSet<>();
    private static volatile LectureDAOSet instance;

    public static LectureDAOSet getInstance() {
        if (instance == null) {
            synchronized (LectureDAOSet.class) {
                if (instance == null)
                    instance = new LectureDAOSet();
            }
        }
        return instance;
    }

    @Override
    public LectureImpl saveLecture(LectureImpl lecture) {
        if (lecture == null)
            throw new IllegalArgumentException("Course can't be null");

        LectureImpl existingLecture = findById(lecture.getId());
        if (existingLecture == null) {
            lectures.add(lecture);
            return lecture;
        }

        lectures.remove(existingLecture);

        existingLecture.setLectureName(lecture.getLectureName());
        existingLecture.setDate(lecture.getDate());
        existingLecture.setTeachers(lecture.getTeachers());

        lectures.add(existingLecture);

        return existingLecture;
    }

    @Override
    public LectureImpl findById(int id) {
        return lectures.stream().filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<LectureImpl> findByTeacher(TeacherImpl teacher) {
        return lectures.stream()
                .filter(l -> l.getTeachers().contains(teacher))
                .toList();
    }

    @Override
    public Collection<LectureImpl> findByCourse(CourseImpl course) {
        Collection<CourseImpl> courses = CourseDAOSet.getInstance().findAll();

        return courses.stream().filter(c -> c.getId() == course.getId())
                .findFirst()
                .map(CourseImpl::getLectures).orElse(null);
    }

    @Override
    public Collection<LectureImpl> findAll() {
        return Set.copyOf(lectures);
    }

    @Override
    public boolean deleteLecture(LectureImpl lecture) {
        if (lecture == null) {
            throw new IllegalArgumentException("Lecture can't be null");
        }
        if (!lectures.contains(lecture)) {
            throw new IllegalStateException("Lecture not found in the list");
        }

        lectures.remove(lecture);
        return true;
    }
}
