package com.ray.timerforsportcycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {
    private Spinner timeUnit, timeUnit2;
    private Button btnStart, btnStop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timeUnit = (Spinner) findViewById(R.id.unit_spinner1);
		timeUnit2 = (Spinner) findViewById(R.id.unit_spinner2);
		btnStart = (Button) findViewById(R.id.btn_Start);
		btnStop = (Button) findViewById(R.id.btn_Stop);
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
	}
	
	
	//Spinner 的 Listener
	private Spinner.OnItemSelectedListener timeUnitListener = new Spinner.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			//實作內容
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
}
