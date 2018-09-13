package statkovit.com.mpdis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Activity_3 extends AppCompatActivity {

    private List<Button> colorButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        colorButtons.add((Button)findViewById(R.id.button9));
        colorButtons.add((Button)findViewById(R.id.button11));
        colorButtons.add((Button)findViewById(R.id.button12));
        colorButtons.add((Button)findViewById(R.id.button13));
        colorButtons.add((Button)findViewById(R.id.button15));
        colorButtons.add((Button)findViewById(R.id.button16));
        colorButtons.add((Button)findViewById(R.id.button17));
        colorButtons.add((Button)findViewById(R.id.button18));
        colorButtons.forEach(new Consumer<Button>() {
            @Override
            public void accept(final Button button) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        button.setBackgroundColor(color);
                    }
                });
            }
        });
    }
}
