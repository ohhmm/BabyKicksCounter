package Utils;

import android.content.Context;

public class ApplicationData {
	private static Context applicationContext;
	public static void setApplicationContext(Context applicationContext) {
		ApplicationData.applicationContext = applicationContext;
		setApplicationName(applicationContext.getPackageName());
	}
	public static Context getApplicationContext() {
		return applicationContext;
	}
	
	private static String applicationName;
	public static void setApplicationName(String applicationName) {
		ApplicationData.applicationName = applicationName;
	}
	public static String getApplicationName() {
		return applicationName;
	}
	
	
}
