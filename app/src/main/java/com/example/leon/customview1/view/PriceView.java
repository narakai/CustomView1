package com.example.leon.customview1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.leon.customview1.R;

/**
 * User: Leon Lai
 * Date: 2016-11-07
 */
public class PriceView extends View {
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Rect mBound;
    private Paint mPaint;
    private RectF rec;

    private int mBackground;
    private int mCornerSize;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PriceView);
        int n = a.getIndexCount();
        try {
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.PriceView_mTitleText:
                        mTitleText = a.getString(attr);
                        break;
                    case R.styleable.PriceView_mTitleTextColor:
                        mTitleTextColor = a.getColor(attr, Color.WHITE);
                        break;
                    case R.styleable.PriceView_mTitleTextSize:
                        mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                        break;
                    case R.styleable.PriceView_mBackground:
                        mBackground = a.getColor(attr, Color.RED);
                        break;
                    case R.styleable.PriceView_mCornerSize:
                        mCornerSize = a.getInteger(attr, 0);
                        break;
                }
            }
        } finally {
            a.recycle();
        }

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        rec = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = getPaddingLeft() + mBound.width() + getPaddingRight();
            width = desired <= widthSize ? desired : widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = getPaddingTop() + mBound.height() + getPaddingBottom();
            height = desired <= heightSize ? desired : heightSize;
        }
        rec.set(0, 0, width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBackground);
        canvas.drawRoundRect(rec, mCornerSize, mCornerSize, mPaint);

        mPaint.setColor(mTitleTextColor);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mTitleText, getPaddingLeft(), baseline, mPaint);
    }
}
