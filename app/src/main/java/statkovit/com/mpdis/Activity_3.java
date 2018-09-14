package statkovit.com.mpdis;

import android.app.AlertDialog;
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
import java.util.function.Consumer;

public class Activity_3 extends AppCompatActivity {

    private List<Button> colorButtons = new ArrayList<>();
    private Button infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
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
        colorButtons.forEach(new Consumer<Button>() {
            @Override
            public void accept(final Button button) {
                button.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                    }
                });

            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_3.this);
                LayoutInflater inflater = Activity_3.this.getLayoutInflater();
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


    }
}
