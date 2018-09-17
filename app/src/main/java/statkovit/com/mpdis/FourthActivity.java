package statkovit.com.mpdis;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

public class FourthActivity extends AppCompatActivity {
    private ImageView footballBall;
    private ImageView basketballBall;
    private int width;
    private int height;
    private Button animateButton;
    private EditText duration;
    private Button buttonPrev;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        setTitle(getResources().getString(R.string.page4_title));
        footballBall = findViewById(R.id.imageView2);
        basketballBall = findViewById(R.id.imageView3);
        animateButton = findViewById(R.id.button);
        duration = findViewById(R.id.editText7);
        buttonPrev = findViewById(R.id.button3);
        buttonNext = findViewById(R.id.button7);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels / 2;
        height = displaymetrics.heightPixels / 2; //????
        final Random random = new Random();
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(FourthActivity.this, ThirdActivity.class);
                startActivity(page);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(FourthActivity.this, FifthActivity.class);
                startActivity(page);
            }
        });
        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorSet as = new AnimatorSet();
                as.playTogether(
                        ObjectAnimator.ofFloat(footballBall, "x",
                                footballBall.getX(), random.nextInt(width)),
                        ObjectAnimator.ofFloat(footballBall, "y",
                                footballBall.getY(), random.nextInt(height))
                );
                int durationInt;
                if (duration.getText().toString().isEmpty()) {
                    durationInt = 600;
                } else {
                    durationInt = Integer.parseInt(duration.getText().toString());
                }
                as.setDuration(durationInt);
                as.start();
                as.playSequentially(ObjectAnimator.ofFloat(basketballBall, "x",
                        basketballBall.getX(), random.nextInt(width)),
                        ObjectAnimator.ofFloat(basketballBall, "y",
                                basketballBall.getY(), random.nextInt(height)));
                as.setDuration(durationInt);
                as.start();
            }
        });

    }


}
