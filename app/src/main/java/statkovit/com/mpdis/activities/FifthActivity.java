package statkovit.com.mpdis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import statkovit.com.mpdis.R;

public class FifthActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        initButtons();
    }

    private void initButtons() {
        Button buttonPrev = findViewById(R.id.prevActivity);
        Button buttonNext = findViewById(R.id.nextActivity);
        Button buttonSelect = findViewById(R.id.selectPhoto);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.image);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevActivity: {
                Intent page = new Intent(FifthActivity.this, FourthActivity.class);
                startActivity(page);
                break;
            }
            case R.id.nextActivity: {
                Intent page = new Intent(FifthActivity.this, SeventhActivity.class);
                startActivity(page);
                break;
            }
            case R.id.selectPhoto: {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, GALLERY_REQUEST);
                break;
            }
        }
    }
}
