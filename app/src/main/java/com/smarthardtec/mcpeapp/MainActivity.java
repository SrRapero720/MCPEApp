package com.smarthardtec.mcpeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import android.widget.ProgressBar;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String version = "";
	private HashMap<String, Object> system32 = new HashMap<>();
	private boolean banned = false;
	
	private ArrayList<HashMap<String, Object>> System32 = new ArrayList<>();
	
	private LinearLayout actionbar;
	private LinearLayout base;
	private ImageView gradient;
	private ImageView logo;
	private TextView title;
	private LinearLayout extensor;
	private LinearLayout border;
	private AdView ad;
	private LinearLayout base_info;
	private LinearLayout progress;
	private TextView log;
	private ProgressBar pb;
	private TextView percentage;
	
	private Intent i = new Intent();
	private TimerTask delay;
	private DatabaseReference starterData = _firebase.getReference("system32/");
	private ChildEventListener _starterData_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		actionbar = (LinearLayout) findViewById(R.id.actionbar);
		base = (LinearLayout) findViewById(R.id.base);
		gradient = (ImageView) findViewById(R.id.gradient);
		logo = (ImageView) findViewById(R.id.logo);
		title = (TextView) findViewById(R.id.title);
		extensor = (LinearLayout) findViewById(R.id.extensor);
		border = (LinearLayout) findViewById(R.id.border);
		ad = (AdView) findViewById(R.id.ad);
		base_info = (LinearLayout) findViewById(R.id.base_info);
		progress = (LinearLayout) findViewById(R.id.progress);
		log = (TextView) findViewById(R.id.log);
		pb = (ProgressBar) findViewById(R.id.pb);
		percentage = (TextView) findViewById(R.id.percentage);
		
		_starterData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		starterData.addChildEventListener(_starterData_child_listener);
	}
	private void initializeLogic() {
		_ConsoleChanges("PERMISOS ACEPTADOS", 5);
		_LayoutLogicDesign();
		_ConsoleChanges("DESIGN APLICADO", 18);
		version = "null"; 
		try { 
			android.content.pm.PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			
			version = pInfo.versionName;
		} catch (Exception e){ 
			e.printStackTrace();
			version = "null";
			finish();
		}
		delay = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_ConsoleChanges("CARGANDO ARCHIVOS", 24);
						if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/MCPEApp".concat("/.system.json")))) {
							_ConsoleChanges("LEYENDO ARCHIVOS", 28);
							_LoadReadDataStorage(FileUtil.getExternalStorageDir().concat("/MCPEApp/"), ".system.json", true);
						}
						else {
							_ConsoleChanges("LEYENDO ARCHIVOS", 28);
							_LoadReadDataStorage(FileUtil.getExternalStorageDir().concat("/MCPEApp/"), ".system.json", false);
						}
					}
				});
			}
		};
		_timer.schedule(delay, (int)(1000));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	private void _LayoutLogicDesign () {
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/orbitron.ttf"), 0);
		percentage.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gilroylight.ttf"), 0);
		log.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gilroylight.ttf"), 0);
		ad.loadAd(new AdRequest.Builder().build());
	}
	
	
	private void _LoadReadDataStorage (final String _path, final String _filename, final boolean _readOnlyMode) {
		if (_readOnlyMode) {
			system32 = new Gson().fromJson(FileUtil.readFile(_path.concat(_filename)), new TypeToken<HashMap<String, Object>>(){}.getType());
			if (system32.get("status").toString().equals("ok")) {
				banned = false;
				_startFirebase();
			}
			else {
				_LayoutLogicDialog("INTERNAL BANNED", system32.get("reason").toString(), "block");
				banned = true;
			}
		}
		else {
			system32 = new HashMap<>();
			system32.put("status", "ok");
			system32.put("error", "null");
			system32.put("reason", "null");
			system32.put("local", "null");
			FileUtil.writeFile(_path.concat(_filename), new Gson().toJson(system32));
			SketchwareUtil.showMessage(getApplicationContext(), "archivo creado");
			_startFirebase();
		}
	}
	
	
	private void _ConsoleChanges (final String _state, final double _pt) {
		log.setText(_state);
		percentage.setText(String.valueOf((long)(_pt)).concat("%"));
		pb.setProgress((int)_pt);
	}
	
	
	private void _checkAdminMode (final String _packdata) {
		
	}
	
	
	private void _startFirebase () {
		starterData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				System32 = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						System32.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				SketchwareUtil.showMessage(getApplicationContext(), "conected");
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _LayoutLogicDialog (final String _title, final String _desc, final String _ico) {
		/*final AlertDialog dialog = new AlertDialog.Builder(this).create();
LayoutInflater inflater = getLayoutInflater();
View convertView = (View) inflater.inflate(R.layout.dialog, null);
dialog.setView(convertView);

dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));

LinearLayout base = (LinearLayout)
convertView.findViewById(R.id.base);
LinearLayout acbar = (LinearLayout) convertView.findViewById(R.id.bar);
TextView txt1 = (TextView)
convertView.findViewById(R.id.title);
TextView txt2 = (TextView)
convertView.findViewById(R.id.desc);
Button btn1 = (Button) convertView.findViewById(R.id.btn);
ImageView imco = (ImageView) convertView.findViewById(R.id.type);

txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/minetitle.ttf"), 0);
txt2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gilroy.ttf"), 0);
btn1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gilroy.ttf"), 0);

android.graphics.drawable.GradientDrawable dialbase = new android.graphics.drawable.GradientDrawable();
dialbase.setColor(Color.parseColor("#FFFFFF"));
dialbase.setCornerRadius(24);

android.graphics.drawable.GradientDrawable dialbar = new android.graphics.drawable.GradientDrawable();
dialbar.setColor(Color.parseColor("#689F38"));
dialbar.setCornerRadius(24);

android.graphics.drawable.GradientDrawable dialbtn = new android.graphics.drawable.GradientDrawable();
dialbtn.setColor(Color.parseColor("#33691E"));
dialbtn.setCornerRadius(14);

txt1.setText(_title);
txt2.setText(_desc);
base.setBackground(dialbase);
btn1.setBackground(dialbtn);
if (_ico.equals("cloud")) {
imco.setImageResource(R.drawable.ic_cloud_off_white);
}
else {
if (_ico.equals("error")) {
imco.setImageResource(R.drawable.ic_cancel_white);
}
else {
if (_ico.equals("warn")) {
imco.setImageResource(R.drawable.ic_warning_white);
}
else {
if (_ico.equals("403")) {
imco.setImageResource(R.drawable.ic_block_white);
}
else {
if (_ico.equals("lock")) {
imco.setImageResource(R.drawable.ic_https_white);
}
else {
imco.setImageResource(R.drawable.ic_warning_white);
}
}
}
}
}
btn1.setOnClickListener(new View.OnClickListener(){
    public void onClick(View v){
dialog.dismiss();
    }
});
dialog.setCancelable(false);
dialog.show();
*/
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
