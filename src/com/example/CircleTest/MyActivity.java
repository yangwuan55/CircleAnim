package com.example.CircleTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener ,CircleAnimContrller.RotateListener{

    private CircleAnimContrller circleAnimContrller;
    private View doSomething;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        circleAnimContrller = new CircleAnimContrller(findViewById(R.id.view1),findViewById(R.id.view2));
        circleAnimContrller.setRotateListener(this);
        doSomething = findViewById(R.id.do_something);
        doSomething.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        circleAnimContrller.startCircleAnim();
    }

    @Override
    public void onRotateStart() {
        doSomething.setClickable(false);
    }

    @Override
    public void onRotateEnd() {
        doSomething.setOnClickListener(this);
    }
}
