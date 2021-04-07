package Utils;


public class SettingsManager {
	
	static final Context context = ApplicationData.getApplicationContext();
	static final SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	
	private static SettingsManager settingsManager = new SettingsManager();
	public static SettingsManager getInstance() {
		return settingsManager;
	}
	private SettingsManager() {
	
	}
	
	public void write(String name, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(name, value);
		editor.commit();
	}
	
	public String read(String name) {
		return sharedPreferences.getString(name, null);
	}

}
