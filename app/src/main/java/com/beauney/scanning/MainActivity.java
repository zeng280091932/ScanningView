package com.beauney.scanning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanningViewDemo(View view) {
        startActivity(ScanningViewDemoActivity.class);
    }

    public void RoundScanningViewDemo(View view) {
        startActivity(ScanningRoundDemoActivity.class);
    }

    public void imageScanningDemo(View view) {
        startActivity(ScanningImageDemoActivity.class);
    }

    public void scanningLayoutDemo(View view) {
        startActivity(ScanningLayoutDemoActivity.class);
    }
}
