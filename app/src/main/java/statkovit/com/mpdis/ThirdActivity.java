package statkovit.com.mpdis;

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

public class ThirdActivity extends AppCompatActivity {

    private List<Button> colorButtons = new ArrayList<>();
    private Button infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        final Random rnd = new Random();
        infoButton = findViewById(R.id.button14);
        infoButton.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        colorButtons.add((Button) findViewById(R.id.button9));
        colorButtons.add((Button) findViewById(R.id.button11));
        colorButtons.add((Button) findViewById(R.id.button12));
        colorButtons.add((Button) findViewById(R.id.button13));
        colorButtons.add((Button) findViewById(R.id.button15));
        colorButtons.add((Button) findViewById(R.id.button16));
        colorButtons.add((Button) findViewById(R.id.button17));
        colorButtons.add((Button) findViewById(R.id.button18));
        for (final Button i : colorButtons) {
            i.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                }
            });
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ThirdActivity.this);
                    LayoutInflater inflater = ThirdActivity.this.getLayoutInflater();
                    View alertView = inflater.inflate(R.layout.alert_created_by, null);
                    alert.setView(alertView);
                    final AlertDialog alertDialog = alert.show();

                    final Button button = alertView.findViewById(R.id.btn_dialog);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    TextView t2 = alertView.findViewById(R.id.text_dialog);
                    t2.setMovementMethod(LinkMovementMethod.getInstance());
                }
            });

            Button buttonPrev = findViewById(R.id.button4);
            Button buttonNext = findViewById(R.id.button2);
            buttonPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent page = new Intent(ThirdActivity.this, SecondActivity.class);
                    startActivity(page);
                }
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
