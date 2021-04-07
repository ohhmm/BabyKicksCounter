package Baby.Com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Utils.ApplicationData;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class BabyMain extends ListActivity {

	private static final String DATA = "BabyKicksData";
	OverlayedButton currentDiagram = null;
	LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	KickStatisticsAdapter kickStatisticsAdapter;
	
	@Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize application data
        ApplicationData.setApplicationContext(this.getApplicationContext());

        try{
        	setContentView(R.layout.main);
        	kickStatisticsAdapter = new KickStatisticsAdapter(this);
        	setListAdapter(kickStatisticsAdapter);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        Button bttn =  (Button) findViewById(R.id.buttonAdd);
        bttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMoment();
			}
		});
    }
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onStart() {
		super.onStart();		
    	load();
    	
    	// fill with testing data
    	/*
		ArrayList<Date> list = new ArrayList<Date>();
		long startTime = new Date().getTime();
		final long hour = 60*60*1000;
		final long day = 24*hour* 2;
		long endTime = startTime + day;
		for(long time = startTime; time < endTime; time += hour / 3)
			list.add(new Date(time));
		kickStatisticsAdapter.setList(list);*/
	}

	synchronized private void load() {
		FileInputStream fis=null;
		try {
			fis = openFileInput(DATA);
			
			if(fis != null) {
				try {
			        ObjectInputStream ois = new ObjectInputStream(fis);
			        try {
			        	ArrayList<Date> list = (ArrayList<Date>) ois.readObject();
			        	if(list!=null)
			        		kickStatisticsAdapter.setList(list);
					} catch (OptionalDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if(ois!= null) {
							ois.close();
						}
					}
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		save();
	}

	synchronized private void save() {
		try {
	    	FileOutputStream fos = openFileOutput(DATA, Context.MODE_PRIVATE);
			if(fos!=null) {
				try {
					ObjectOutputStream os = new ObjectOutputStream(fos);
					ArrayList<Date> list = kickStatisticsAdapter.getList();
					os.writeObject(list);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void addMoment() {
		kickStatisticsAdapter.addDate(
				Calendar.getInstance().getTime());
	}
}