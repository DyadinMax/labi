package statkovit.com.mpdis.repositories.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import statkovit.com.mpdis.entities.Student;
import statkovit.com.mpdis.repositories.StudentRepository;

@Dao
public interface SQLiteStudentDao extends StudentRepository {

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Insert
    void insert(Student student);

    @Query("delete from student")
    void deleteAll();


}
