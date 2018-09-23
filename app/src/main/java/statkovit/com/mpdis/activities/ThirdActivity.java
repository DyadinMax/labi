package statkovit.com.mpdis.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import statkovit.com.mpdis.R;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle(R.string.page3_title);
        initButtons();
    }

    @SuppressLint("FindViewByIdCast")
    private void initButtons() {
        List<Button> simpleButtons = new ArrayList<>();
        simpleButtons.add(findViewById(R.id.button1));
        simpleButtons.add(findViewById(R.id.button2));
        simpleButtons.add(findViewById(R.id.button3));
        simpleButtons.add(findViewById(R.id.button4));
        simpleButtons.add(findViewById(R.id.button5));
        simpleButtons.add(findViewById(R.id.button6));
        simpleButtons.add(findViewById(R.id.button7));
        simpleButtons.add(findViewById(R.id.button8));
        for (Button i : simpleButtons) {
            randomizeColor(i);
            i.setOnClickListener(this);
        }
        Button infoButton = findViewById(R.id.githubInfo);
        randomizeColor(infoButton);
        Button buttonPrev = findViewById(R.id.prevActivity);
        Button buttonNext = findViewById(R.id.nextActivity);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        infoButton.setOnClickListener(this);
    }

    private void showDeveloperInfo() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ThirdActivity.this);
        LayoutInflater inflater = ThirdActivity.this.getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_created_by, null);
        alert.setView(alertView);
        AlertDialog alertDialog = alert.show();
        Button okButton = alertView.findViewById(R.id.okButton);
        okButton.setOnClickListener(view -> alertDialog.dismiss());
        TextView link = alertView.findViewById(R.id.text);
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void randomizeColor(View view) {
        view.setBackgroundColor(Color.argb(255, random.nextInt(256),
                random.nextInt(256), random.nextInt(256)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8: {
                randomizeColor(v);
                break;
            }
            case R.id.prevActivity: {
                Intent page = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(page);
                break;
            }
            case R.id.nextActivity: {
                Intent page = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivity(page);
                break;
            }
            case R.id.githubInfo: {
                showDeveloperInfo();
                break;
            }
        }
    }
}
