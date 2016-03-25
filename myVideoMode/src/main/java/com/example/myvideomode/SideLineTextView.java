package com.example.myvideomode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class SideLineTextView extends TextView{  
		  
	    public SideLineTextView(Context context) {  
	        super(context);  
	    }  
	    public SideLineTextView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	    private int sroke_width = 1;  
	    @Override  
	    protected void onDraw(Canvas canvas) {  
	        Paint paint = new Paint();  
	        //  将边框设为白色色  
	        paint.setColor(android.graphics.Color.WHITE);  
	        //  画TextView的4个边  
	        canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);  
	        canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);  
	        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);  
	        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);  
	        super.onDraw(canvas);  
	    }  
	}  

