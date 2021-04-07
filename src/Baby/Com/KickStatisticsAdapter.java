package Baby.Com;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

/**
 * 
 */

public class KickStatisticsAdapter extends BaseAdapter {

	private Context context;
	private HashMap<Long, ArrayList<Date>> dates = new HashMap<Long, ArrayList<Date>>();
	private Long[] keys = new Long[0];
	private ArrayList<Boolean> expanded = new ArrayList<Boolean>();

	long trimToDay(Date date) {
		long dateTime = date.getTime();
		// reset milliseconds
		dateTime = dateTime - (dateTime % 1000);
		// reset hours, minutes, seconds
		Date newDate = new Date(dateTime);
		newDate.setHours(0);
		newDate.setMinutes(0);
		newDate.setSeconds(0);
		return newDate.getTime();
	}

	public KickStatisticsAdapter(Context context) {
		this.context = context;
	}

	synchronized public void setList(ArrayList<Date> list) {
		dates.clear();
		for (Date date : list) {
			addDateInternal(date);
		}

		prepareKeysAndProvideSorting();

		if (list.size() > 0) {
			notifyDataSetChanged();
		} else {
			notifyDataSetInvalidated();
		}
	}

	private void prepareKeysAndProvideSorting() {
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(dates.keySet());
		Collections.sort(list);
		Collections.reverse(list);
		keys = list.toArray(keys);
		// sort values
		for (Long key : keys) {
			Collections.sort(dates.get(key));
		}
	}

	private void addDateInternal(Date date) {
		long day = trimToDay(date);
		ArrayList<Date> datesList;
		if (dates.containsKey(day)) {
			datesList = dates.get(day);
		} else {
			datesList = new ArrayList<Date>();
			dates.put(day, datesList);
			expanded.add(false);
		}
		datesList.add(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return keys.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return keys[position];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	class Listener implements OnClickListener {
		int position;
		KicksDayLayout view;
		public Listener(KicksDayLayout view, int position) {
			this.position = position;
			this.view = view;
		}

		@Override
		public void onClick(View v) {
			boolean isExpanded = expanded.get(position);
			isExpanded = !isExpanded;
			expanded.set(position, isExpanded);
			view.setExpanded(isExpanded);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	synchronized public View getView(int position, View convertView, ViewGroup parent) {
		KicksDayLayout view = (KicksDayLayout)convertView;
		if(position < getCount()) {
			long day = keys[position];
			if(view == null) {
				view = new KicksDayLayout(context);
			} else {
				view.unsubscribeAll();
			}
			
			view.subscribe(new Listener(view, position));

			view.init(day, dates.get(day), expanded.get(position));
		}		
		return view;
	}

	synchronized public void addDate(Date date) {
		addDateInternal(date);
		prepareKeysAndProvideSorting();
		notifyDataSetChanged();
	}

	synchronized public ArrayList<Date> getList() {
		ArrayList<Date> datesList = new ArrayList<Date>();
		for (ArrayList<Date> dayDatesList : dates.values()) {
			datesList.addAll(dayDatesList);
		}
		return datesList;
	}

}
