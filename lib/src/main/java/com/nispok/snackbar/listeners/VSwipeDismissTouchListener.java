package com.nispok.snackbar.listeners;
/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * A {@link View.OnTouchListener} that makes any {@link View} dismissible
 * when the user swipes (drags her finger) horizontally across the view.
 *
 * @author Roman Nurik
 */
public class VSwipeDismissTouchListener implements View.OnTouchListener {

    private float mDownY;
    private View.OnClickListener mOnClickListener;
    private int mViewHeight = 1; // 1 and not 0 to prevent dividing by zero
    private long mAnimationTime;
    private int mSlop;

    public static final int POSITION_TOP  = 1;
    public static final int POSITION_DOWN = 2;
    private int mPosition;
    private SwipeDismissTouchListener.DismissCallbacks mCallBack;


    public VSwipeDismissTouchListener(View view, int position, View.OnClickListener mOnClickListener,
                                      SwipeDismissTouchListener.DismissCallbacks callback) {
        this.mOnClickListener = mOnClickListener;
        this.mPosition = position;
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        mSlop = vc.getScaledTouchSlop();
        mAnimationTime = view.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mCallBack = callback;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        if (mViewHeight < 2) {
            mViewHeight = view.getHeight();
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                if (mCallBack.canDismiss(view)) {
                    mCallBack.pauseTimer(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float curY = event.getY();
                mCallBack.pauseTimer(false);
                if (Math.abs(curY - mDownY) > mSlop) {
                    if (mPosition == POSITION_DOWN  && curY - mDownY > 0) {
                        //向下滑動
                        mCallBack.onDismiss(view ,view);
                    } else if (mPosition == POSITION_TOP && curY - mDownY < 0) {
                        //向上滑动
                        mCallBack.onDismiss(view ,view);
                    }
                } else {
                    if(mOnClickListener != null){
                        mOnClickListener.onClick(view);
                    }
                }

                break;
        }
        return true;
    }
}
