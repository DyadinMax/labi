package statkovit.com.mpdis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class EighthActivity extends AppCompatActivity {
    private Button buttonPrev;
    private EditText url;
    private Button buttonGo;
    private WebView webView;
    private Button clearButton;
    private Button nextPageButton;
    private Button prevPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);
        buttonPrev = findViewById(R.id.button8);
        url = findViewById(R.id.editText10);
        buttonGo = findViewById(R.id.button22);
        webView = findViewById(R.id.webView);
        clearButton = findViewById(R.id.button23);
        nextPageButton = findViewById(R.id.button24);
        prevPageButton = findViewById(R.id.button26);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(EighthActivity.this, SeventhActivity.class);
                startActivity(page);
            }
        });
        webView.setWebViewClient(new MyBrowser());
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urll = url.getText().toString();

                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(urll);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.clearHistory();
            }
        });

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goForward();
            }
        });

        prevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
