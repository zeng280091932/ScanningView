# ScanningView
Android TV开发与手机开发最大的不同点就是焦点，TV常用的操作方式是遥控器，所以给用户一个醒目美观的焦点是至关重要的。很多产品经理在设计焦点的时候都会选择扫光的效果，比如小牧电视的控件选中效果。今天就给大家介绍一款自定义的扫光效果控件。
# 效果展示

![海报扫光效果](https://img-blog.csdnimg.cn/20200930102401482.gif)  
![带圆角的控件扫光效果，光效不会超过圆角的位置](https://img-blog.csdnimg.cn/20200930102141458.gif)  
![扫光容器，可以在扫光容器中放子控件](https://img-blog.csdnimg.cn/20200930102706939.gif)  
![图片扫光效果，光效与图片中不透明内容交融的一种效果](https://img-blog.csdnimg.cn/20200930102706939.gif)

# 使用步骤
1. 引入library

```implementation 'com.beauney.scanning:scanning-view:1.0.1'```

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

更多使用方法，请参考文章：[Android TV控件获得焦点的扫光效果](https://blog.csdn.net/zeng280091932/article/details/108880904)

# Android架构师全套视频获取：

1、百度网盘：链接：https://pan.baidu.com/s/1GBHi4SgodTzuBa5haeD2_Q

提取码：eh4b


2、直接私信或+V:zengxiaotao1987 +Q:280091932索取
