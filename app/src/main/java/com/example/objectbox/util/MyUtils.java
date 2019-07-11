package com.example.objectbox.util;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyUtils {
    /**
     *  动画
     * type  1 显示订单表/学生表  2 显示客户表/老师表
     * @return
     */
    public static void startAnim(final int type, int measuredWidth, final int width, final LinearLayout ll_customer, final LinearLayout ll_order) {
        //如果显示的是所需要的页面 则不用再开启动画
        if (measuredWidth > 0 && type == 2) {
            return;
        } else if (measuredWidth == 0 && type == 1) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofInt(0, width);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int curValue = (int) animator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ll_customer.getLayoutParams();
                layoutParams.width = type == 1 ? width - curValue : curValue;
                ll_customer.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams orderParams = ll_order.getLayoutParams();
                orderParams.width = type == 1 ? curValue : width - curValue;
                ll_order.setLayoutParams(orderParams);
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 产生随机数
     * @return
     */
    public static int creatRandom() {
        int v = (int) (Math.random() * (1000 + 100 + 10));
        return v;
    }
}
