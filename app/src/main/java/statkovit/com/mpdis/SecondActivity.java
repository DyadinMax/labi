package statkovit.com.mpdis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import statkovit.com.mpdis.entities.Student;
import statkovit.com.mpdis.repositories.StudentRepository;
import statkovit.com.mpdis.repositories.dao.FileStudentDao;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Student> students = new ArrayList<>();
    private SimpleAdapter sAdapter;
    private static boolean currentDatabaseSQLite = true;
    private static StudentRepository studentRepository =
            App.getInstance().getDatabase().getSQLiteStudentDao();
    private static File file;
    private Button buttonDb;
    private static final String filePath = Environment
            .getExternalStorageDirectory().getPath() + "/students.txt";
    private TextView typeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(R.string.page2_title);
        if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            students = new allStudentsAsyncTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        initListView();
        initAddButton();
        initDeleteAllButton();
        Button buttonPrev = findViewById(R.id.prevActivity);
        Button buttonNext = findViewById(R.id.nextActivity);
        buttonDb = findViewById(R.id.changeDatabase);
        typeView = findViewById(R.id.dbType);
        if (currentDatabaseSQLite) {
            typeView.setText(R.string.sqlite);
        } else {
            typeView.setText(R.string.file);
        }
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonDb.setOnClickListener(this);
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
        }
    }

    private void changeDatabase() {
        if (currentDatabaseSQLite) {
            studentRepository = new FileStudentDao();
        } else {
            studentRepository = App.getInstance().getDatabase().getSQLiteStudentDao();
        }
        currentDatabaseSQLite = !currentDatabaseSQLite;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void initListView() {
        ListView listView = findViewById(R.id.peopleList);
        String[] columns = {"surname", "firstName", "patronymic", "groupNumber", "faculty"
        };
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
        sAdapter = new SimpleAdapter(this, data, R.layout.listview_people_row,
                columns, resourceIds);
        listView.setAdapter(sAdapter);
    }

    private void initAddButton() {
        Button button = findViewById(R.id.addPerson);
        final EditText firstName = findViewById(R.id.firstName);
        final EditText secondName = findViewById(R.id.surname);
        final EditText patrynomic = findViewById(R.id.patrynomic);
        final EditText faculty = findViewById(R.id.faculty);
        final EditText groupNumber = findViewById(R.id.groupNumber);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = null;
                if (firstName.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.validate_first_name),
                            Toast.LENGTH_SHORT);
                } else if (secondName.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.validate_surname),
                            Toast.LENGTH_SHORT);
                } else if (patrynomic.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.validate_patronymic),
                            Toast.LENGTH_SHORT);
                } else if (faculty.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.validate_faculty),
                            Toast.LENGTH_SHORT);
                } else if (groupNumber.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.validate_group_number),
                            Toast.LENGTH_SHORT);
                }
                if (toast != null) {
                    toast.show();
                } else {
                    Student student = new Student();
                    student.setFirstName(firstName.getText().toString());
                    student.setSurname(secondName.getText().toString());
                    student.setPatronymic(patrynomic.getText().toString());
                    student.setFaculty(faculty.getText().toString());
                    student.setGroupNumber(groupNumber.getText().toString());
                    new insertStudentAsyncTask(student).execute();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }


    private void initDeleteAllButton() {
        Button button = findViewById(R.id.deleteAll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteAllStudentsAsyncTask().execute();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
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
