package com.beauney.scanning;

import android.os.Bundle;
import android.view.View;

import com.beauney.scanning.library.ScanningRoundView;

import androidx.annotation.Nullable;

/**
 * @author zengjiantao
 * @since 2020-09-28
 */
public class ScanningRoundDemoActivity extends BaseActivity {
    private ScanningRoundView mScanningRoundView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_round_demo);
        mScanningRoundView = findViewById(R.id.scanning_round_view);
    }

    public void startScanning(View view) {
        mScanningRoundView.start();
    }

    public void stopScanning(View view) {
        mScanningRoundView.stop();
    }
}
