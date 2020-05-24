package com.tree.treeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DetectQRActivity extends AppCompatActivity {

    private final String TAG = DetectQRActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_q_r);
        Log.e(TAG, "onCreate");

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity( ZxingActivity.class ); //바코드인식 실행할 화면
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    } //onCreate

    //바코드인식결과 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // QR코드/ 바코드를 스캔한 결과
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        result.getFormatName(); //바코드 종류
        result.getContents(); //바코드 값
        Log.e(TAG,"바코드 : "+result.getContents());
        finish();
    }
}
