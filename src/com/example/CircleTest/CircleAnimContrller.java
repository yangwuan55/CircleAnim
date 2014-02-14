package com.example.CircleTest;

import android.animation.*;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.animation.OvershootInterpolator;


public class CircleAnimContrller {
    public static final String TAG = "CircleAnimContrller";
    View view1;
    View view2;
    private RotateListener listener;

    public CircleAnimContrller(View view1 , View view2) {
        this.view1 = view1;
        this.view2 = view2;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startCircleAnim() {
        Rect r1 = new Rect();
        view1.getGlobalVisibleRect(r1);
        Rect r2 = new Rect();
        view2.getGlobalVisibleRect(r2);
        final int radius = (int) (r2.centerX() - r1.centerX()) / 2;
        float centerX = r2.centerX() - (r2.centerX() - r1.centerX()) / 2;
        float centerY = view1.getBottom() - view1.getHeight() / 2;
        final Circle circle = new Circle(radius, (int) centerX, (int) centerY);

        final ObjectAnimator animCircle = AnimUtils.ofInt(circle, "angle", -180);

        float scale1 = (float)r1.width()/r2.width();
        float scale2 = (float)r2.width()/r1.width();
        PropertyValuesHolder pScaleX1 = PropertyValuesHolder.ofFloat("ScaleX", scale2);
        PropertyValuesHolder pScaleY1 = PropertyValuesHolder.ofFloat("ScaleY", scale2);

        PropertyValuesHolder pScaleX2 = PropertyValuesHolder.ofFloat("ScaleX", scale1);
        PropertyValuesHolder pScaleY2 = PropertyValuesHolder.ofFloat("ScaleY", scale1);

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(view1, pScaleX1, pScaleY1);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(view2, pScaleX2, pScaleY2);
        animCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateViews(circle);
            }
        });
        animCircle.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                updateViews(circle);
                listener.onRotateEnd();
/*                GalleryAppImpl app = (GalleryAppImpl) view1.getContext().getApplicationContext();
                app.onDoSwitcherEnd();*/
            }
        });
        AnimatorSet animatorSet = AnimUtils.createAnimatorSet();
        animatorSet.playTogether(animator1 ,animator2, animCircle);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.start();
        listener.onRotateStart();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void updateViews(Circle circle) {
        view2.setX(circle.getX() - view2.getWidth() / 2);
        view2.setY(circle.getY() - view2.getHeight() / 2);
        view1.setX(circle.getX(circle.getAngle() + 180) - view1.getWidth() / 2);
        view1.setY(circle.getY(circle.getAngle() + 180) - view1.getHeight() / 2);
    }

    public void setRotateListener(RotateListener listener) {
        this.listener = listener;
    }

    public interface RotateListener{
        void onRotateStart();
        void onRotateEnd();
    }

}