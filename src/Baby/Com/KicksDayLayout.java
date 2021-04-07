package Baby.Com;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class KicksDayLayout extends LinearLayout implements OnClickListener {

    private static final int FIVE_MINUTES = 5*60*1000;
	private static final int INITIAL_KICK_NUMBER = 1;
	private OverlayedButton diagram;
    private TextView datesText;
	
    public KicksDayLayout(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
        this.diagram = new OverlayedButton(context);
        addView(this.diagram, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        diagram.setOnClickListener(this);
        this.datesText = new TextView(context);
        addView(datesText, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
    
	public void init(long day, ArrayList<Date> dates, boolean expanded) {
		diagram.reset();
        for(Date date : dates)
        	diagram.addTimeStamp(date);
        this.diagram.setText(DateFormat.getDateInstance().format(new Date(day)));
        
        setExpanded(expanded);
        datesText.setText(datesToText(dates));
	}
	
	private static String datesToText(ArrayList<Date> dates) {
		StringBuilder sb = new StringBuilder();
		long prev = 0;
		int number = INITIAL_KICK_NUMBER;
		for(Date date : dates) {
			sb.append(date.toLocaleString());
			long time = date.getTime();
			if (time - prev > FIVE_MINUTES && ++number == 10) {
				sb.append(" - ").append(number);	
			};
			sb.append("\n");
			prev = time;
		}
		return sb.toString();
	}
  
    /**
     * Convenience method to expand or hide the dialogue
     */
    public void setExpanded(boolean expanded) {
        datesText.setVisibility(expanded ? VISIBLE : GONE);
    }
    
    ArrayList<OnClickListener> onClickEvent = new ArrayList<OnClickListener>();
    public void subscribe(OnClickListener listener) {
    	onClickEvent.add(listener);
    }
    public void unsubscribe(OnClickListener listener){
    	onClickEvent.remove(listener);
    }
    public void unsubscribeAll(){
    	onClickEvent = new ArrayList<OnClickListener>();
    }
    
	@Override
	public void onClick(View v) {
		for(OnClickListener listener : onClickEvent ) {
			listener.onClick(this);
		}
	}

}
