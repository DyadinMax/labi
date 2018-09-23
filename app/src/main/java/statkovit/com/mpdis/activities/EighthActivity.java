package statkovit.com.mpdis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import statkovit.com.mpdis.R;

public class EighthActivity extends AppCompatActivity implements View.OnClickListener {
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
        setTitle(R.string.page8_title);
        initButtons();
        webView.setWebViewClient(new MyBrowser());
    }

    private void initButtons() {
        buttonPrev = findViewById(R.id.prevActivity);
        url = findViewById(R.id.url);
        buttonGo = findViewById(R.id.go);
        webView = findViewById(R.id.webView);
        clearButton = findViewById(R.id.clear);
        nextPageButton = findViewById(R.id.nextPage);
        prevPageButton = findViewById(R.id.prevPage);
        buttonPrev.setOnClickListener(this);
        buttonGo.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        nextPageButton.setOnClickListener(this);
        prevPageButton.setOnClickListener(this);
    }

    private void goToTheLink() {
        String webUrl = url.getText().toString();
        if (!webUrl.startsWith("https://") && !webUrl.startsWith("http://")) {
            webUrl = "https://" + webUrl;
        }
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(webUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevActivity: {
                Intent page = new Intent(EighthActivity.this, SeventhActivity.class);
                startActivity(page);
                break;
            }
            case R.id.go: {
                goToTheLink();
                break;
            }
            case R.id.clear: {
                webView.clearHistory();
                break;
            }
            case R.id.nextPage: {
                webView.goForward();
                break;
            }
            case R.id.prevPage: {
                webView.goBack();
                break;
            }

        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
