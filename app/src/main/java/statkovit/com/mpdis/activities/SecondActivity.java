package statkovit.com.mpdis.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import statkovit.com.mpdis.App;
import statkovit.com.mpdis.R;
import statkovit.com.mpdis.entities.Student;
import statkovit.com.mpdis.repositories.StudentRepository;
import statkovit.com.mpdis.repositories.dao.FileStudentDao;

import static statkovit.com.mpdis.repositories.dao.FileStudentDao.FILE_PATH;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_FILE_WRITE = 1;

    private Button buttonPrev;
    private Button buttonNext;
    private Button buttonAdd;
    private Button buttonDelete;
    private Button buttonChangeDB;
    private TextView databaseType;
    private ListView listViewStudents;
    private EditText firstName;
    private EditText secondName;
    private EditText patrynomic;
    private EditText faculty;
    private EditText groupNumber;
    private List<Student> students = new ArrayList<>();
    private static boolean currentDatabaseSQLite = true;
    private static StudentRepository studentRepository =
            App.getInstance().getDatabase().getSQLiteStudentDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(R.string.page2_title);
        initButtons();
        initFileDatabase();
        try {
            students = new allStudentsAsyncTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        initListView();
        if (currentDatabaseSQLite) {
            databaseType.setText(R.string.sqlite);
        } else {
            databaseType.setText(R.string.file);
        }
    }

    private void initButtons() {
        buttonPrev = findViewById(R.id.prevActivity);
        buttonNext = findViewById(R.id.nextActivity);
        buttonAdd = findViewById(R.id.addPerson);
        buttonChangeDB = findViewById(R.id.changeDatabase);
        databaseType = findViewById(R.id.dbType);
        listViewStudents = findViewById(R.id.peopleList);
        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.surname);
        patrynomic = findViewById(R.id.patrynomic);
        faculty = findViewById(R.id.faculty);
        groupNumber = findViewById(R.id.groupNumber);
        buttonDelete = findViewById(R.id.deleteAll);
        buttonAdd.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonChangeDB.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    private void initListView() {
        String[] columns = {"surname", "firstName", "patronymic", "groupNumber", "faculty"};
        int[] resourceIds = {
                R.id.textview_list_row_surname,
                R.id.textview_list_row_firstName,
                R.id.textview_list_row_patronymic,
                R.id.textview_list_row_groupNumber,
                R.id.textview_list_row_faculty
        };

        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;
        for (int i = 0; i < students.size(); i++) {
            m = new HashMap<>();
            m.put(columns[0], students.get(i).getSurname());
            m.put(columns[1], students.get(i).getFirstName());
            m.put(columns[2], students.get(i).getPatronymic());
            m.put(columns[3], students.get(i).getGroupNumber());
            m.put(columns[4], students.get(i).getFaculty());
            data.add(m);
        }
        SimpleAdapter sAdapter = new SimpleAdapter(SecondActivity.this, data, R.layout.listview_people_row,
                columns, resourceIds);
        listViewStudents.setAdapter(sAdapter);
    }

    private void initFileDatabase() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_FILE_WRITE);
        } else {
            createFileIfNotExists();
        }
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FILE_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createFileIfNotExists();
            } else {
                studentRepository = App.getInstance().getDatabase().getSQLiteStudentDao();
                currentDatabaseSQLite = true;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeDatabase: {
                changeDatabase();
                break;
            }
            case R.id.prevActivity: {
                Intent page = new Intent(SecondActivity.this, FirstActivity.class);
                startActivity(page);
                break;
            }
            case R.id.nextActivity: {
                Intent page = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(page);
                break;
            }
            case R.id.addPerson: {
                addPerson();
                break;
            }
            case R.id.deleteAll: {
                deleteAllStudents();
                break;
            }
        }
    }

    private void changeDatabase() {
        if (currentDatabaseSQLite) {
            studentRepository = new FileStudentDao();
        } else {
            studentRepository = App.getInstance().getDatabase().getSQLiteStudentDao();
        }
        currentDatabaseSQLite = !currentDatabaseSQLite;
        reload();
    }

    private void addPerson() {
        Toast toast;
        String text = null;
        if (firstName.getText().toString().isEmpty()) {
            text = getResources().getString(R.string.validate_first_name);
        } else if (secondName.getText().toString().isEmpty()) {
            text = getResources().getString(R.string.validate_surname);
        } else if (patrynomic.getText().toString().isEmpty()) {
            text = getResources().getString(R.string.validate_patronymic);
        } else if (faculty.getText().toString().isEmpty()) {
            text = getResources().getString(R.string.validate_faculty);
        } else if (groupNumber.getText().toString().isEmpty()) {
            text = getResources().getString(R.string.validate_group_number);
        }
        if (text != null) {
            toast = Toast.makeText(getApplicationContext(),
                    text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Student student = new Student();
            student.setFirstName(firstName.getText().toString());
            student.setSurname(secondName.getText().toString());
            student.setPatronymic(patrynomic.getText().toString());
            student.setFaculty(faculty.getText().toString());
            student.setGroupNumber(groupNumber.getText().toString());
            new insertStudentAsyncTask(student).execute();
            reload();
        }
    }

    private void deleteAllStudents() {
        new deleteAllStudentsAsyncTask().execute();
        reload();
    }

    private void reload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private static class allStudentsAsyncTask extends AsyncTask<Void, Void, List<Student>> {

        @Override
        protected List<Student> doInBackground(Void... voids) {
            return studentRepository.getAll();
        }
    }

    private static class insertStudentAsyncTask extends AsyncTask<Void, Void, Void> {
        private Student student;

        public insertStudentAsyncTask(Student student) {
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentRepository.insert(student);
            return null;
        }
    }

    private static class deleteAllStudentsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            studentRepository.deleteAll();
            return null;
        }
    }
}
