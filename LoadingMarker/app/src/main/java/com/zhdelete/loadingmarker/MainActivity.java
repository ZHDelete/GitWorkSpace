package com.zhdelete.loadingmarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button controlBtn;

//    private LoadingMarker loadingMarker;

//    private LoadingBubble loadingBubble;
    private LoadingBubble_Padding loadingBubble;

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

        loadingBubble = (LoadingBubble_Padding) findViewById(R.id.loading_bubble);
        controlBtn = (Button) findViewById(R.id.control_btn);
        controlBtn.setOnClickListener((v) -> {

            if (loadingBubble.isLoading()) {
                loadingBubble.setLoading(false);
            } else {
                loadingBubble.setLoading(true);
            }


        });
    }

    List<String> strs = new ArrayList<>();
    private void listAdd() {
        strs.add(0, "tmp");
        Log.d("MainActivity", strs.toString());
    }

}
