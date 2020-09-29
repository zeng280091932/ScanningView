package com.beauney.scanning;

import android.os.Bundle;
import android.view.View;

import com.beauney.scanning.library.ScanningLinearLayout;

import androidx.annotation.Nullable;

/**
 * @author zengjiantao
 * @since 2020-09-28
 */
public class ScanningLayoutDemoActivity extends BaseActivity {
    private ScanningLinearLayout mScanningLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_layout_demo);

        mScanningLayout = findViewById(R.id.scanning_container);
    }

    public void startScanning(View view) {
        mScanningLayout.start();
    }

    public void stopScanning(View view) {
        mScanningLayout.stop();
    }
}
