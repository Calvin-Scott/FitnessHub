package uk.ac.lincoln.students.calvinscott13458203.fitnesshubv2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class VideoWebActivity extends AppCompatActivity {
    String title;
    String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = getIntent().getExtras().getString("Title");
        videoID= getIntent().getExtras().getString("videoID");

       /* TextView titleTV = (TextView) findViewById(R.id.Title_TextView);
        TextView videoIDTV = (TextView) findViewById(R.id.Video_TextView);

        titleTV.setText(title);
        videoIDTV.setText(videoID); */

        WebView webView = new WebView(this);
        setContentView(webView);

        webView.loadUrl("https://www.youtube.com/watch?v=" + videoID);

    }

}
