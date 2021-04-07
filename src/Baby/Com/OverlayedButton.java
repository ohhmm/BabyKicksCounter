package Baby.Com;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;


public class OverlayedButton extends Button {

	long dayStart, dayEnd, timeSpan;
	ArrayList<Date> timestamps = new ArrayList<Date>(); 
	static final Paint paint = new Paint();
	static final long MillisecondsPerDay = 24*60*60*1000;
	static {
		paint.setColor(Color.RED);
	}
	
	public OverlayedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OverlayedButton(Context context) {
		super(context);
	}
	
	public void reset() {
		timestamps.clear();
	}

	public void addTimeStamp(Date date) {
		
		if(timestamps.size() == 0) {
			Date time = (Date) date.clone();
			time.setHours(0);
			time.setMinutes(0);
			time.setSeconds(0);
			dayStart = time.getTime();
			time.setTime(dayStart+MillisecondsPerDay);
			time.setMinutes(30);
			dayEnd = time.getTime();
			timeSpan = dayEnd - dayStart;
		}
		
		timestamps.add(date);
	}
	
	public ArrayList<Date> getTimestamps() {
		return timestamps;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {

		int width = canvas.getWidth();
		int height = canvas.getHeight();
		for(Date date : timestamps) {
			long time = date.getTime();
			float x = width * (time - dayStart) / timeSpan;
			canvas.drawLine(x, 0, x, height, paint);
		}
		super.onDraw(canvas);
	}
}
