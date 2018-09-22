package statkovit.com.mpdis.repositories;

import java.util.List;

import statkovit.com.mpdis.entities.Student;

public interface StudentRepository {

    List<Student> getAll();

    void insert(Student student);

    void deleteAll();
}
