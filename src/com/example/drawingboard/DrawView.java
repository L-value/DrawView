package com.example.drawingboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View{

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cacheBitMap = Bitmap.createBitmap(WIDTH, HEIGHT, Config.ARGB_8888);
		cacheCanvas = new Canvas();
		cacheCanvas.setBitmap(cacheBitMap);
		path = new Path();
		cachePaint = new Paint(Paint.DITHER_FLAG);
		cachePaint.setColor(Color.RED);
		cachePaint.setStyle(Paint.Style.STROKE);
		cachePaint.setStrokeWidth(1);
		//反锯齿
		cachePaint.setAntiAlias(true);
		cachePaint.setDither(true);
	}
	float preX,preY;
	Path path;
	Paint cachePaint;
	Bitmap cacheBitMap;
	Canvas cacheCanvas;
	final int WIDTH = 320;
	final int HEIGHT = 320;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//从前一个点绘制到当前点之后，把当前点定义为之后的前一个点
			path.moveTo(x, y);
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, x, y);
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_UP:
			cacheCanvas.drawPath(path, cachePaint);
			//path.reset();
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		canvas.drawBitmap(cacheBitMap, 0, 0, paint);
		canvas.drawPath(path, paint);
		super.onDraw(canvas);
	}
	
}
