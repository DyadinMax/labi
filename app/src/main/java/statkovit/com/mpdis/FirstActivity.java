package statkovit.com.mpdis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
    private TextView hiddenText;
    private ImageView clickMe;
    private ImageButton catButton;
    private Button buttonNext;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.page1_title));

        clickMe = findViewById(R.id.clickMe);
        hiddenText = findViewById(R.id.hiddenText);
        catButton = findViewById(R.id.catButton);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homepage = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(homepage);
            }
        });
        addAnimation();
    }

    private void addAnimation() {
        hiddenText.setAlpha(0);
        catButton.setOnClickListener(new OnClickListener() {
            boolean visible = false;

            @Override
            public void onClick(View v) {
                if (visible) {
                    hiddenText.animate().alpha(0f).setDuration(500).start();
                    clickMe.animate().alpha(1f).setDuration(500).start();
                } else {
                    hiddenText.animate().alpha(1f).setDuration(500).start();
                    clickMe.animate().alpha(0f).setDuration(500).start();
                }
                visible = !visible;
            }
        });
    }


}
