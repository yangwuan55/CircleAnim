/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.CircleTest;

import android.animation.*;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

import java.util.HashSet;
import java.util.List;

public class AnimUtils {
    static HashSet<Animator> sAnimators = new HashSet<Animator>();
    static Animator.AnimatorListener sEndAnimListener = new Animator.AnimatorListener() {
        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            sAnimators.remove(animation);
        }

        public void onAnimationCancel(Animator animation) {
            sAnimators.remove(animation);
        }
    };

    public static void cancelOnDestroyActivity(Animator a) {
        sAnimators.add(a);
        a.addListener(sEndAnimListener);
    }

    public static void onDestroyActivity() {
        HashSet<Animator> animators = new HashSet<Animator>(sAnimators);
        for (Animator a : animators) {
            if (a.isRunning()) {
                /*a.cancel();*/
            } else {
                sAnimators.remove(a);
            }
        }
    }

    public static AnimatorSet createAnimatorSet() {
        AnimatorSet anim = new AnimatorSet();
        cancelOnDestroyActivity(anim);
        return anim;
    }

    public static ValueAnimator ofFloat(float... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setFloatValues(values);
        cancelOnDestroyActivity(anim);
        return anim;
    }

    public static ObjectAnimator ofFloat(Object target, String propertyName, float... values) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(target);
        anim.setPropertyName(propertyName);
        anim.setFloatValues(values);
        cancelOnDestroyActivity(anim);
        return anim;
    }

    public static ObjectAnimator ofInt(Object target, String propertyName, int... values) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(target);
        anim.setPropertyName(propertyName);
        anim.setIntValues(values);
        cancelOnDestroyActivity(anim);
        return anim;
    }

    public static ObjectAnimator ofPropertyValuesHolder(Object target,
            PropertyValuesHolder... values) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(target);
        anim.setValues(values);
        cancelOnDestroyActivity(anim);
        return anim;
    }
    
	public static AnimatorSet createSequentialAnimatorSet(long interval,
			List<Animator> os) {
		AnimatorSet result = createAnimatorSet();
		
		if (os != null) {
			for (Animator o : os) {
				o.setStartDelay(interval);
				interval += interval;
			}
			result.playTogether(os);
		}
		return result;
	}

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ObjectAnimator getAnimofBottem(int displayHeight, final View v) {
        v.setVisibility(View.VISIBLE);
        ObjectAnimator result = null;
        if (v.isShown()) {
            final float y = v.getTranslationY();
            Rect r = new Rect();
            v.getGlobalVisibleRect(r);
            int needTraslateY = displayHeight - r.top;
            v.setTranslationY(needTraslateY);
            PropertyValuesHolder indicatorAnimValuesHolder = PropertyValuesHolder.ofFloat("translationY", y);
            ObjectAnimator anim = AnimUtils.ofPropertyValuesHolder(v, indicatorAnimValuesHolder);
            anim.setDuration(500);
            result = anim;
            result.setInterpolator(new OvershootInterpolator());
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ObjectAnimator getAnimofLeft(int displayWidth , final View v) {
        ObjectAnimator result = null;
        if (v.isShown()) {
            final float x = v.getTranslationX();
            Rect r = new Rect();
            v.getGlobalVisibleRect(r);
            int needTraslateX = displayWidth - r.left;
            v.setTranslationX(needTraslateX);
            PropertyValuesHolder indicatorAnimValuesHolder = PropertyValuesHolder.ofFloat("translationX", x);
            ObjectAnimator anim = AnimUtils.ofPropertyValuesHolder(v, indicatorAnimValuesHolder);
            anim.setDuration(500);
            result = anim;
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            result.setInterpolator(new OvershootInterpolator(1));
        }
        return result;
    }

    public static Rect getDisplaySize(Context context) {
        Rect rect = new Rect();
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        rect.set(0,0,display.getWidth(),display.getHeight());
        return rect;
    }

}