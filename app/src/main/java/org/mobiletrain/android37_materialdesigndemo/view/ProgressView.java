package org.mobiletrain.android37_materialdesigndemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.mobiletrain.android37_materialdesigndemo.R;

public class ProgressView extends View {

	// 定义自定义View的全局属性(参见attrs.xml)
	private int startAngle = -90;
	private int sweepStep = 5;
	private int padding = 5;
	private int circleColor = Color.GRAY;
	private int sweepColor = Color.YELLOW;

	private float sweepAngle = 0; //扇形每次划过的累加数

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取在布局文件中用户传进来的属性参数，如果没有就使用默认值
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.ProgressView);
		if (array != null) {
			sweepStep = array.getInteger(R.styleable.ProgressView_sweepStep,
					sweepStep);
			padding = array.getInteger(R.styleable.ProgressView_padding,
					padding);
			circleColor = array.getColor(R.styleable.ProgressView_circleColor,
					circleColor);
			sweepColor = array.getColor(R.styleable.ProgressView_sweepColor,
					sweepColor);
			startAngle = array.getInteger(R.styleable.ProgressView_startAngle,
					startAngle);

			// 缓存机制，提高数据获取性能
			array.recycle();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true); // 抗锯齿
		paint.setColor(circleColor); // 先设置外围的圆的颜色

		// 画圆
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);

		// 扇形区的颜色
		paint.setColor(sweepColor);

		// 画弧形
		canvas.drawArc(new RectF(padding, padding, getWidth() - padding,
				getWidth() - padding), startAngle, sweepAngle, true, paint);

		sweepAngle += sweepStep;

		sweepAngle = (sweepAngle >= 361?0:sweepAngle);

		invalidate(); // 保证扇形可以持续的划动
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// MeasureSpec 对象，封装了ui控件规格说明。
		//MeasureSpec.EXACTLY  最大   match_parent
		//MeasureSpec.AT_MOST  wrap_content
		//MeasureSpec.UNSPECIFIED; 不常用

		//以下是宽高的规格
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		// 根据宽高的规格获取相应的大小
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		switch (widthMode) {
			// width="wrap_content"
			case MeasureSpec.AT_MOST:
				widthSize = 50;
				heightSize = 50;
				break;
			case MeasureSpec.EXACTLY: // Match_Parent
				widthSize = heightSize = Math.min(widthSize, heightSize);
				break;
		}
		setMeasuredDimension(widthSize, heightSize);
	}
}
