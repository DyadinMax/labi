package statkovit.com.mpdis.repositories.dao;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import statkovit.com.mpdis.entities.Student;
import statkovit.com.mpdis.repositories.StudentRepository;

public class FileStudentDao implements StudentRepository {
    public static final String FILE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/students.txt";

    @Override
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String str;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((str = reader.readLine()) != null) {
                Student student = new Student();
                student.setId(Long.parseLong(str));
                str = reader.readLine();
                student.setSurname(str);
                str = reader.readLine();
                student.setFirstName(str);
                str = reader.readLine();
                student.setPatronymic(str);
                str = reader.readLine();
                student.setGroupNumber(str);
                str = reader.readLine();
                student.setFaculty(str);
                list.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.valueOf(student.getId()));
            writer.newLine();
            writer.write(student.getSurname());
            writer.newLine();
            writer.write(student.getFirstName());
            writer.newLine();
            writer.write(student.getPatronymic());
            writer.newLine();
            writer.write(student.getGroupNumber());
            writer.newLine();
            writer.write(student.getFaculty());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileWriter writer = new FileWriter(file.getAbsoluteFile())) {
                writer.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
