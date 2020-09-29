# ScanningView
安卓智能电视平台自定义控件，用于控件获得焦点时显示扫光效果。
# 效果展示
![效果展示](ImageForReadme\scanning1.gif)
# 使用步骤
1. 引入library

```implementation 'com.beauney.scanning:scanning-view:1.0.0'```

2. xml中使用控件

```
<FrameLayout
        android:layout_width="283dp"
        android:layout_height="159dp"
        android:layout_marginStart="20dp"
        android:layout_marginTop="20dp"
        android:focusable="true"
        android:focusableInTouchMode="true">

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/temp_demand_3" />

        <com.beauney.scanning.library.ScanningView
            android:tag="scanning_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#66000000"/>
    </FrameLayout>
```

3. 代码中调用start()、stop()方法

```
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
```
