package com.tree.treeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.journeyapps.barcodescanner.CaptureActivity;

public class ZxingActivity extends CaptureActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView title_view = new TextView(this);
        title_view.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        title_view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        title_view.setPadding(150, 100, 100, 100);
        title_view.setTextColor(Color.parseColor("#ffc000"));
        title_view.setTextSize(30);
        title_view.setText("삑! 안전");

        this.addContentView(title_view, layoutParams);
    }
}