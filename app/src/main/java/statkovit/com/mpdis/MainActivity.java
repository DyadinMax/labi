package statkovit.com.mpdis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private ImageButton catButton;
    private TextView hidenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catButton = findViewById(R.id.Button01);
        hidenText = findViewById(R.id.textView3);
        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hidenText.getVisibility() == INVISIBLE){
                    hidenText.setVisibility(VISIBLE);
                } else {
                    hidenText.setVisibility(INVISIBLE);
                }

            }
        });
    }


}
