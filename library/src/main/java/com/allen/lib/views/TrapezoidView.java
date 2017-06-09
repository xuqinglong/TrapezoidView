package com.allen.lib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.allen.lib.R;

/**
 * 梯形图片控件
 * Created by allen on 2017/6/3.
 */

public class TrapezoidView extends AppCompatImageView {
    private static final int TYPE_TOP=1;//顶部
    private static final int TYPE_MIDDLE=2;//中部
    private static final int TYPE_BOTTOM=3;//底部
    private Paint paint;
    /**
     *画笔的宽度
     */
    private int paintStrokeWidth=10;
    /**
     * 获取xml设置的position
     */
    private static int mPosition = 0;

    public TrapezoidView(Context context) {
        this(context,null);
    }

    public TrapezoidView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TrapezoidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrapezoidView,defStyleAttr,0);
        mPosition = array.getInt(R.styleable.TrapezoidView_position,0);

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(paintStrokeWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        array.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(getPaddingLeft()+paintStrokeWidth/2,getPaddingTop()+paintStrokeWidth/2);
        path.lineTo(getWidth()-getPaddingRight()-paintStrokeWidth/2,getPaddingTop()+paintStrokeWidth/2);
        path.lineTo(getWidth()-getPaddingRight()-paintStrokeWidth/2,getHeight()-getPaddingBottom()-paintStrokeWidth/2);
        path.lineTo(getPaddingLeft()+paintStrokeWidth/2,getHeight()-getPaddingBottom()-paintStrokeWidth/2);
        path.close();
        canvas.drawPath(path,paint);
    }

    /**
     * 画顶部image
     * @param canvas
     */
    private void canvasTop(Canvas canvas){

    }
}
