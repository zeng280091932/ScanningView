package com.beauney.scanning;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.beauney.scanning.library.ScanningView;
import com.beauney.scanning.utils.AnimatorUtil;

import androidx.annotation.Nullable;

/**
 * @author zengjiantao
 * @since 2020-09-28
 */
public class ScanningViewDemoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_view_demo);
        RelativeLayout container = findViewById(R.id.container);

        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    ScanningView scanningView = view.findViewWithTag("scanning_view");
                    if (b) {
                        AnimatorUtil.scaleBigAnimator(view);
                        scanningView.start();
                    } else {
                        AnimatorUtil.scaleSmallAnimator(view);
                        scanningView.stop();
                    }
                }
            });
        }
    }
}
