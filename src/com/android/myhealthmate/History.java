package com.android.myhealthmate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity {

	private TextView oneWeekFilter;
	private TextView oneMonthFilter;
	private TextView threeMonthFilter;

	private LinearLayout barView;

	private TextView historyHrLeft;
	private TextView historyHrMid;
	private TextView historyHrRight;

	private TextView historyBpLeft;
	private TextView historyBpMid;
	private TextView historyBpRight;
	
	private TextView historyActLeft;
	private TextView historyActMid;
	private TextView historyActRight;

	private TextView historySleepLeft;
	private TextView historySleepMid;
	private TextView historySleepRight;
	
	int barLen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		oneWeekFilter = (TextView) findViewById(R.id.history_filter_1week);
		oneMonthFilter = (TextView) findViewById(R.id.history_filter_1month);
		threeMonthFilter = (TextView) findViewById(R.id.history_filter_3month);

		barView = (LinearLayout) findViewById(R.id.bar);
		
		
		// HR Bar
		historyHrLeft = (TextView) findViewById(R.id.history_hr_left);
		historyHrMid = (TextView) findViewById(R.id.history_hr_mid);
		historyHrRight = (TextView) findViewById(R.id.history_hr_right);

		// BP Bar
		historyBpLeft = (TextView) findViewById(R.id.history_bp_left);
		historyBpMid = (TextView) findViewById(R.id.history_bp_mid);
		historyBpRight = (TextView) findViewById(R.id.history_bp_right);
		
		// Act Bar
		historyActLeft = (TextView) findViewById(R.id.history_act_left);
		historyActMid = (TextView) findViewById(R.id.history_act_mid);
		historyActRight = (TextView) findViewById(R.id.history_act_right);
		
		// Sleep Bar
		historySleepLeft = (TextView) findViewById(R.id.history_sleep_left);
		historySleepMid = (TextView) findViewById(R.id.history_sleep_mid);
		historySleepRight = (TextView) findViewById(R.id.history_sleep_right);
		
		setOneWeekLayout();
		
		oneWeekFilter.setOnClickListener(getOneWeekFilterListener());
		oneMonthFilter.setOnClickListener(getOneMonthFilterListener());
		threeMonthFilter.setOnClickListener(getThreeMonthFilterListener());
	}
	
	
	public void setOneWeekLayout(){
		changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.45, 0.3, 0.15);
		changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.2, 0.5, 0.2);
		changeByPercent(historyActLeft,historyActMid,historyActRight,0.3, 0.4, 0.2);
		changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.13, 0.57, 0.2);
	}
	
	
	public OnClickListener getOneWeekFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "clickclcickccklcic", Toast.LENGTH_SHORT).show();
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.45, 0.3, 0.15);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.2, 0.5, 0.2);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.3, 0.4, 0.2);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.13, 0.57, 0.2);
			}
		};
	}
	
	public OnClickListener getThreeMonthFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "clickclcickccklcic", Toast.LENGTH_SHORT).show();
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.15, 0.35, 0.4);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.15, 0.6, 0.15);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.35, 0.4, 0.15);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.23, 0.47, 0.2);
			}
		};
	}


	public OnClickListener getOneMonthFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "clickclcickccklcic", Toast.LENGTH_SHORT).show();
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.35, 0.35, 0.2);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.3, 0.4, 0.2);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.35, 0.4, 0.15);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.33, 0.37, 0.2);
			}
		};
	}	

	public void changeByPercent(TextView leftView, TextView midView, TextView rightView ,double left, double mid, double right) {
		changeOneViewByPercent(barLen, left, leftView);
		changeOneViewByPercent(barLen, mid, midView);
		changeOneViewByPercent(barLen, right, rightView);
	}

	public void changeOneViewByPercent(int len, double percent, TextView view) {
		barLen = 680;
		LayoutParams params = view.getLayoutParams();
		params.width = (int) (barLen * percent);
		view.setLayoutParams(params);
	}
}
