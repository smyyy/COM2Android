
package com.example.draganddrop;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//
//public class CustomButton extends View {
//
//        private Paint circlePaint;
//        private Paint circleStrokePaint;
//        private RectF circleArc;
//
//        // Attrs
//        private int circleRadius;
//        private int circleFillColor;
//        private int circleStrokeColor;
//        private int circleStartAngle;
//        private int circleEndAngle;
//
//    public CustomButton(Context context, AttributeSet attrs) {
//
//        super(context, attrs);
//        init(attrs); // Read all attributes
//
//        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circlePaint.setStyle(Paint.Style.FILL);
//        circleStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circleStrokePaint.setStyle(Paint.Style.STROKE);
//        circleStrokePaint.setStrokeWidth(2);
//        circleStrokePaint.setColor(circleStrokeColor);
//    }
//    
//
//    public void init(AttributeSet attrs)
//    {
//        // Go through all custom attrs.
//        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButton);
//        circleRadius = attrsArray.getInteger(R.styleable.circleview_cRadius, 0);
//        circleFillColor = attrsArray.getColor(R.styleable.circleview_cFillColor, 16777215);
//        circleStrokeColor = attrsArray.getColor(R.styleable.circleview_cStrokeColor, -1);
//        circleStartAngle = attrsArray.getInteger(R.styleable.circleview_cAngleStart, 0);
//        circleEndAngle = attrsArray.getInteger(R.styleable. circleview_cAngleEnd, 360);
//        // Google tells us to call recycle.
//        attrsArray.recycle();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        // Move canvas down and right 1 pixel.
//        // Otherwise the stroke gets cut off.
//        canvas.translate(1,1);
//        circlePaint.setColor(circleFillColor);
//        canvas.drawArc(circleArc, circleStartAngle, circleEndAngle, true, circlePaint);
//        canvas.drawArc(circleArc, circleStartAngle, circleEndAngle, true, circleStrokePaint);
//    }
//
//    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//
//        int measuredWidth = measureWidth(widthMeasureSpec);
//        if(circleRadius == 0) // No radius specified.
//        {                     // Lets see what we can make.
//            // Check width size. Make radius half of available.
//            circleRadius = measuredWidth / 2;
//            int tempRadiusHeight = measureHeight(heightMeasureSpec) / 2;
//            if(tempRadiusHeight < circleRadius)
//                // Check height, if height is smaller than
//                // width, then go half height as radius.
//                circleRadius = tempRadiusHeight;
//        }
//        // Remove 2 pixels for the stroke.
//        int circleDiameter = circleRadius * 2 - 2;
//        // RectF(float left, float top, float right, float bottom)
//        circleArc = new RectF(0, 0, circleDiameter, circleDiameter);
//        int measuredHeight = measureHeight(heightMeasureSpec);
//        setMeasuredDimension(measuredWidth, measuredHeight);
//        Log.d("onMeasure() ::", "measuredHeight =>" + String.valueOf(measuredHeight) + "px measuredWidth => " + String.valueOf(measuredWidth) + "px");
//    }
//
//    private int measureHeight(int measureSpec) {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        int result = 0;
//        if (specMode == MeasureSpec.AT_MOST) {
//            result = circleRadius * 2;
//        } else if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        }
//        return result;
//    }
//
//    private int measureWidth(int measureSpec) {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        int result = 0;
//        if (specMode == MeasureSpec.AT_MOST) {
//            result = specSize;
//        } else if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        }
//         return result;
//    }
//}
//

//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Toast;
//
//
///**
// * Example of how to write a custom subclass of View. LabelView
// * is used to draw simple text views. Note that it does not handle
// * styled text or right-to-left writing systems.
// *
// */
//public class CustomButton extends View {
//    private Paint mPaint ;
//    int startX,startY;
//    int height, width;
//    
//    /**
//     * Constructor.  This version is only needed if you will be instantiating
//     * the object manually (not from a layout XML file).
//     * @param context
//     */
//    public CustomButton(Context context) {
//        super(context);
//        init();
//    }
//
//    /**
//     * Construct object, initializing with any attributes we understand from a
//     * layout file. These attributes are defined in
//     * SDK/assets/res/any/classes.xml.
//     * 
//     * @see android.view.View#View(android.content.Context, android.util.AttributeSet)
//     */
//    public CustomButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    
//    public void paintComponent(int x, int y){
//    	startX = x;
//    	startY = y;
//    	invalidate();
//    }
//    
//    private void init(){
//    	startY = this.getWidth()/2;
//    	startX = this.getHeight()/2;
//    	paintComponent(startX, startY);
////      canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
////		canvas.drawColor(Color.CYAN);
//    	mPaint = new Paint();
//		// smooths
//    	mPaint.setAntiAlias(true);
//    	mPaint.setColor(Color.RED);
//    	mPaint.setStyle(Paint.Style.STROKE); 
//    	mPaint.setStrokeWidth(4.5f);
//		// opacity
//		//p.setAlpha(0x80); //
//    }
//    
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//        }
//        
//    	paintComponent(x, y);
//       
//    return true;
//    }
//    
//
//    /**
//     * @see android.view.View#measure(int, int)
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(widthMeasureSpec,
//        		heightMeasureSpec);
//       
//        
//    }
//    
//    /**
//     * Render the text
//     * 
//     * @see android.view.View#onDraw(android.graphics.Canvas)
//     */
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//		canvas.drawCircle(startX, startY, 30, mPaint);
//    }
//}
