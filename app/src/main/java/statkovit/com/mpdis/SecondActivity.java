package statkovit.com.mpdis;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import statkovit.com.mpdis.entities.Student;

public class SecondActivity extends AppCompatActivity {

    private List<Student> students = new ArrayList<>();
    private SimpleAdapter sAdapter;
    private boolean db = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(getResources().getString(R.string.page2_title));
        try {
            if (db) {
                students = new allStudentsAsyncTask().execute().get();
            } else {

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        initListView();
        initAddButton();
        initDeleteAllButton();
        Button buttonPrev = findViewById(R.id.button5);
        Button buttonNext = findViewById(R.id.button6);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(page);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(page);
            }
        });
    }

    private void initListView() {
        ListView listView = findViewById(R.id.listView_people);
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
        Button button = findViewById(R.id.button_addPerson);
        final EditText firstName = findViewById(R.id.editText);
        final EditText secondName = findViewById(R.id.editText2);
        final EditText patrynomic = findViewById(R.id.editText3);
        final EditText faculty = findViewById(R.id.editText4);
        final EditText groupNumber = findViewById(R.id.editText5);
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
        Button button = findViewById(R.id.button_deleteAll);
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
            return App.getInstance().getDatabase().getStudentDao().getAll();
        }
    }

    private static class insertStudentAsyncTask extends AsyncTask<Void, Void, Student> {
        private Student student;

        public insertStudentAsyncTask(Student student) {
            this.student = student;
        }

        @Override
        protected Student doInBackground(Void... voids) {
            App.getInstance().getDatabase().getStudentDao().insert(student);
            return student;
        }
    }

    private static class deleteAllStudentsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            App.getInstance().getDatabase().getStudentDao().deleteAll();
            return null;
        }
    }
}
