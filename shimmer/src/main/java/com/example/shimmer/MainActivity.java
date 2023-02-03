package com.example.shimmer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class MainActivity extends AppCompatActivity {

    private ShimmerTextView myShimmerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myShimmerTextView = findViewById(R.id.tv_shimmer);
        Shimmer shimmer = new Shimmer();
        shimmer.start(myShimmerTextView);
    }
}