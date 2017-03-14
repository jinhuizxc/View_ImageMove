package com.example.jh.viewimagemove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：jinhui on 2017/3/14
 * 邮箱：1004260403@qq.com
 */

public class MyView extends View {

    private Paint paint = new Paint();
    private Bitmap bitmap;
    // 图片的宽度、高度
    private int bitmapWidth, bitmapHeight;
    // 图片的锚点
    int bitmapX, bitmapY;

//    public MyView(Context context) {
//        super(context);
//        bitmap = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.ic_launcher);
//        bitmapWidth = bitmap.getWidth();
//        bitmapHeight = bitmap.getHeight();
//    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
    }

    private boolean isMove = false;
    // 触摸点和图片锚点的差值
    private int subX, subY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // 如何判定图片不出界？
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if(x > bitmapX && x < bitmapX + bitmapWidth
                    && y > bitmapY && y < bitmapY + bitmapHeight){
                isMove = true;
                // 这句必须放在选中图片区域的后面才能
                // 保证点击点击图片才能移动！
                // 修正
                subX = x - bitmapX;
                subY = y - bitmapY;
            }
//            if(x > )
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isMove = false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isMove) {
                // 修正
                bitmapX = x - subX;
                bitmapY = y - subY;
                // 下面的逻辑保证移动的时候不出来屏幕
                if(bitmapX < 0){
                    bitmapX = 0;
                }
                if(bitmapY < 0){
                    bitmapY = 0;
                }
                if(bitmapX + bitmapWidth > this.getWidth()){
                    bitmapX = this.getWidth() - bitmapWidth;
                }
                if(bitmapY + bitmapHeight > this.getHeight()){
                    bitmapY = this.getHeight() - bitmapHeight;
                }
                this.invalidate();
            }
        }
        return true;
    }
}
