package statkovit.com.mpdis.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

import statkovit.com.mpdis.R;

import static android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class FourthActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView footballBall;
    private ImageView basketballBall;
    private int width;
    private int height;
    private Button animateButton;
    private EditText duration;
    private Button buttonPrev;
    private Button buttonNext;
    final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        setTitle(getResources().getString(R.string.page4_title));
        initButtons();
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);
        ViewTreeObserver observer = constraintLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {//размеры layout
            @Override
            public void onGlobalLayout() {
                width = constraintLayout.getWidth() - 200;
                height = constraintLayout.getHeight() - 400;
                constraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initButtons() {
        footballBall = findViewById(R.id.footballBall);
        basketballBall = findViewById(R.id.BasketballBall);
        animateButton = findViewById(R.id.animate);
        duration = findViewById(R.id.duration);
        buttonPrev = findViewById(R.id.prevActivity);
        buttonNext = findViewById(R.id.nextActivity);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        animateButton.setOnClickListener(this);
    }

    private void animate() {
        AnimatorSet as = new AnimatorSet();
        as.playTogether(
                ObjectAnimator.ofFloat(footballBall, View.X,
                        footballBall.getX(), random.nextInt(width)),
                ObjectAnimator.ofFloat(footballBall, View.Y,
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
        as.playSequentially(ObjectAnimator.ofFloat(basketballBall, View.X,
                basketballBall.getX(), random.nextInt(width)),
                ObjectAnimator.ofFloat(basketballBall, View.Y,
                        basketballBall.getY(), random.nextInt(height)));
        as.setDuration(durationInt);
        as.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevActivity: {
                Intent page = new Intent(FourthActivity.this, ThirdActivity.class);
                startActivity(page);
                break;
            }
            case R.id.nextActivity: {
                Intent page = new Intent(FourthActivity.this, FifthActivity.class);
                startActivity(page);
                break;
            }

            case R.id.animate: {
                animate();
                break;
            }
        }
    }
}
