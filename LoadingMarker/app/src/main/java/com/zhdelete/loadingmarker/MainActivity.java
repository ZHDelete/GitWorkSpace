package com.zhdelete.loadingmarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button controlBtn;

//    private LoadingMarker loadingMarker;

    private LoadingBubble loadingBubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loadingMarker = (LoadingMarker) findViewById(R.id.loading_marker);
//        controlBtn = (Button) findViewById(R.id.control_btn);
//        controlBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingMarker.setLoading(!loadingMarker.isLoading());
//
//                controlBtn.setText(loadingMarker.isLoading() ? "暂停" : "播放");
//            }
//        });

        loadingBubble = (LoadingBubble) findViewById(R.id.loading_bubble);
        controlBtn = (Button) findViewById(R.id.control_btn);
        controlBtn.setOnClickListener((v) -> {
            if (loadingBubble.isLoading()) {
                loadingBubble.setLoading(false);
            } else {
                loadingBubble.setLoading(true);
            }
        });


    }
}
