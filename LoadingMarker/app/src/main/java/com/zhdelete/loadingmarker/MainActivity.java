package com.zhdelete.loadingmarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button controlBtn;
    private LoadingMarker loadingMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingMarker = (LoadingMarker) findViewById(R.id.loading_marker);
        controlBtn = (Button) findViewById(R.id.controlBtn);
        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMarker.setLoading(!loadingMarker.isLoading());

                controlBtn.setText(loadingMarker.isLoading() ? "暂停" : "播放");
            }
        });
    }
}
