package com.beauney.scanning;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beauney.scanning.library.ScanningImageView;
import com.beauney.scanning.utils.ScreenCapture;

import androidx.annotation.Nullable;

/**
 * @author zengjiantao
 * @since 2020-09-28
 */
public class ScanningImageDemoActivity extends BaseActivity {

    private ScanningImageView mScanningImageView;
    private ImageView mIconImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_image_demo);
        mScanningImageView = findViewById(R.id.scanning_view);
        mIconImg = findViewById(R.id.icon_img);
    }

    public void startScanning(View view) {
        if (mScanningImageView.getSrcBitmap() == null) {
            mScanningImageView.setSrcBitmap(ScreenCapture.captureWidget(mIconImg));
        }
        mScanningImageView.start();
    }

    public void stopScanning(View view) {
        mScanningImageView.stop();
    }
}
