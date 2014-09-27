package com.ray.timerforsportcycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	int spinPosition1,spinPosition2;
	boolean isSubbmited = false;
	boolean isPaused = false;
    private Spinner timeUnit, timeUnit2;
    private Button btnStart, btnStop;
    private EditText edtCycle, edtItem1, edtItem2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timeUnit = (Spinner) findViewById(R.id.unit_spinner1);
		timeUnit2 = (Spinner) findViewById(R.id.unit_spinner2);
		btnStart = (Button) findViewById(R.id.btn_Start);
		btnStop = (Button) findViewById(R.id.btn_Stop);
		edtCycle = (EditText) findViewById(R.id.edt_cycle);
		edtItem1 = (EditText) findViewById(R.id.edt_item1);
		edtItem2 = (EditText) findViewById(R.id.edt_item2);
		//建立ArrayAdapter
		ArrayAdapter<CharSequence> adapterTimeUnit = ArrayAdapter.createFromResource(this, R.array.sTimeUnit, android.R.layout.simple_spinner_item);
		//設定Spinner 顯示格式
		adapterTimeUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//設定Spinner 資料來源
		timeUnit.setAdapter(adapterTimeUnit);
		timeUnit2.setAdapter(adapterTimeUnit);
		//設定Spinner 元件的listener
		timeUnit.setOnItemSelectedListener(timeUnitListener);
		timeUnit2.setOnItemSelectedListener(timeUnitListener);
		btnStart.setOnClickListener(btnStartListener);
	}
	
	private Button.OnClickListener btnStartListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction(CountTimer.ACTION);
			if (isPaused) {
				startService(intent);
				isPaused = false;
				btnStart.setText(R.string.btPause);
				return;
			}
			if (!isSubbmited) {
				int item1Time = 0;
				String edtItemNum1String = edtItem1.getText().toString();
				if (edtItemNum1String.equals("")) {
					ToastMaker(R.string.edt_no_number);
					return;
				}
				try {
					int tempInt = Integer.parseInt(edtItemNum1String);
					item1Time = tempInt;
				}
				catch (NumberFormatException e) {
					Log.e("Number Parse Error", e.toString());
					ToastMaker(R.string.edt_have_word);
					return;
				}
				btnStop.setVisibility(View.VISIBLE);
				btnStart.setText(R.string.btPause);
				//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(edtItem1.getWindowToken(), 0);
				stopService(intent);
				ToastMaker("Hello");
				//intent.setAction(CountTimer.AC)
				switch (spinPosition1) {
				case 0:
					//秒
					intent.putExtra("time", item1Time);
					startService(intent);
					break;
				case 1:
					//分鐘
					intent.putExtra("time", item1Time * 60);
					startService(intent);
					break;
				case 2:
					//小時
					intent.putExtra("time", item1Time * 60 * 60);
					startService(intent);
					break;
				default:
					break;
				}
				isSubbmited = true;
				isPaused = false;
			}
			else {
				stopService(intent);
				btnStart.setText(R.string.btStartAgain);
				isPaused = true;
			}
		}
	};
	
	
	//Spinner 的 Listener
	private Spinner.OnItemSelectedListener timeUnitListener = new Spinner.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			spinPosition1 = position;
			spinPosition2 = position;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void ToastMaker(int stringId) {
		Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
	}
	
	public void ToastMaker(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
