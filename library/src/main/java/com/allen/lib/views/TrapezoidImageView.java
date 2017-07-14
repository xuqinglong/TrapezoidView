package com.allen.lib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.lib.R;

/**
 * 梯形图片控件
 * <p>说明：0.1.0版本，还有好多问题，以后有时间会优化！</p>
 * @author allen
 * @version 0.1.0
 * <p>联系QQ：1059709131</p>
 */

public class TrapezoidImageView extends ImageView {
    private Paint mPaint;//画梯形的画笔
    private Drawable mDrawable=null;//通过src设置的图片
    private int mWidth,mHeight;//TrapezoidImageView控件的宽度和高度
    private BitmapShader mShader;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mPosition;
    private int incline;



    private static final int TYPE_TOP = 0;//顶部
    private static final int TYPE_MIDDLE = 1;//中部
    private static final int TYPE_BOTTOM = 2;//底部

    public TrapezoidImageView(Context context) {
        this(context,null);
    }

    public TrapezoidImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TrapezoidImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrapezoidImageView,defStyleAttr,0);
        mPosition = array.getInt(R.styleable.TrapezoidImageView_position,0);
        incline = array.getDimensionPixelSize(R.styleable.TrapezoidImageView_incline,0);
        array.recycle();
    }

    /**
     * 初始化操作，初始化画笔、mDrawable等
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mDrawable = getDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable==null) return;
        //初始化BitmapShader
        initBitmapShader();
        //画梯形图片
        if (mPosition==TYPE_TOP) {
            canvasTop(canvas);
        }else if (mPosition==TYPE_MIDDLE){
            canvasMiddle(canvas);
            setMargin();
        }else if (mPosition==TYPE_BOTTOM){
            canvasBottom(canvas);
            setMargin();
        }

    }

    /**
     * 当type为middle和bottom时设置margin
     */
    private void setMargin() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getMeasuredWidth(),getMeasuredHeight());
        params.setMargins(0,-incline/5*4,0,0);
        setLayoutParams(params);
        requestLayout();
    }

    /**
     * 初始化BitmapShader
     */
    private void initBitmapShader() {
        mShader = new BitmapShader(getSrcBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale =  Math.max(getWidth() * 1.0f / getSrcBitmap().getWidth(), getHeight() * 1.0f / getSrcBitmap().getHeight());
        mMatrix = new Matrix();
        mMatrix.setScale(scale,scale);
        mShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mShader);
    }

    /**
     * 画顶部梯形图片
     * @param canvas
     */
    private void canvasTop(Canvas canvas) {
        canvas.drawPath(getTopPath(),mPaint);
    }
    /**
     * 画中部梯形图片
     * @param canvas
     */
    private void canvasMiddle(Canvas canvas) {
        canvas.drawPath(getMiddlePath(),mPaint);
    }

    /**
     * 画底部梯形图片
     * @param canvas
     */
    private void canvasBottom(Canvas canvas){
        canvas.drawPath(getBottomPath(),mPaint);
    }
    /**
     *获取顶部梯形路径
     * @return
     */
    private Path getTopPath() {
        Path topPath = new Path();
        topPath.moveTo(0,0);
        topPath.lineTo(mWidth,0);
        topPath.lineTo(mWidth,mHeight);
        topPath.lineTo(0,mHeight-incline);
        topPath.close();
        return topPath;
    }

    /**
     *获取顶部梯形路径
     * @return
     */
    private Path getMiddlePath() {
        Path topPath = new Path();
        topPath.moveTo(0,0);
        topPath.lineTo(mWidth,incline);
        topPath.lineTo(mWidth,mHeight-incline);
        topPath.lineTo(0,mHeight);
        topPath.close();
        return topPath;
    }
    /**
     *获取顶部梯形路径
     * @return
     */
    private Path getBottomPath() {
        Path topPath = new Path();
        topPath.moveTo(0,incline);
        topPath.lineTo(mWidth,0);
        topPath.lineTo(mWidth,mHeight);
        topPath.lineTo(0,mHeight);
        topPath.close();
        return topPath;
    }

    /**
     * 将mDrawable转换成Bitmap对象
     * @return
     */
    private Bitmap getSrcBitmap() {
        if (mDrawable instanceof BitmapDrawable){
            return ((BitmapDrawable) mDrawable).getBitmap();
        }
        int bitmapWidth = mDrawable.getIntrinsicWidth();
        int bitmapHeight = mDrawable.getIntrinsicHeight();

        mBitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvasBitmap = new Canvas(mBitmap);
        mDrawable.setBounds(0,0,bitmapWidth,bitmapHeight);
        mDrawable.draw(canvasBitmap);
        return mBitmap;
    }



}
