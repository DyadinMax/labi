package statkovit.com.mpdis.repositories.dao;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

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
    private static final String filePath = Environment
            .getExternalStorageDirectory().getPath() + "/students.txt";

    @Override
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String tmp;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((tmp = bufferedReader.readLine()) != null) {
                Student student = new Student();
                student.setId(Long.parseLong(tmp));
                tmp = bufferedReader.readLine();
                student.setSurname(tmp);
                tmp = bufferedReader.readLine();
                student.setFirstName(tmp);
                tmp = bufferedReader.readLine();
                student.setPatronymic(tmp);
                tmp = bufferedReader.readLine();
                student.setGroupNumber(tmp);
                tmp = bufferedReader.readLine();
                student.setFaculty(tmp);
                list.add(student);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(Student student) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(String.valueOf(student.getId()));
                bufferedWriter.newLine();
                bufferedWriter.write(student.getSurname());
                bufferedWriter.newLine();
                bufferedWriter.write(student.getFirstName());
                bufferedWriter.newLine();
                bufferedWriter.write(student.getPatronymic());
                bufferedWriter.newLine();
                bufferedWriter.write(student.getGroupNumber());
                bufferedWriter.newLine();
                bufferedWriter.write(student.getFaculty());
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void deleteAll() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                fileWriter.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
