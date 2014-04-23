package com.android.myhealthmate;

import java.io.File;

import com.android.entity.AccountController;
import com.android.entity.AccountModel;
import com.android.service.EmailSender;
import com.android.summary.ExcelExporter;
import com.android.trend.ChartDataController;

import android.app.Activity;
import android.content.Intent;
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
	
	private Button exportToExcelbtn;
	private Button sharebtn;
	//number section
	private TextView hrAvg;
	private TextView hrMin;
	private TextView hrMax;
	
	private TextView bpAvg;
	private TextView bpMin;
	private TextView bpMax;
	
	private TextView actAvg;
	private TextView actMin;
	private TextView actMax;
	
	private TextView sleepAvg;
	private TextView sleepMin;
	private TextView sleepMax;
	
	int barLen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		setTitle(AccountController.getInstance().getAccount().getName());
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
		
		
		
		oneWeekFilter.setOnClickListener(getOneWeekFilterListener());
		oneMonthFilter.setOnClickListener(getOneMonthFilterListener());
		threeMonthFilter.setOnClickListener(getThreeMonthFilterListener());
		
		exportToExcelbtn = (Button) findViewById(R.id.history_export);
		sharebtn =(Button) findViewById(R.id.history_share);
		
		exportToExcelbtn.setOnClickListener(getExportListener());
		sharebtn.setOnClickListener(getShartListener());
		
		hrAvg = (TextView) findViewById(R.id.history_hr_average);
		hrMin = (TextView) findViewById(R.id.history_hr_min);
		hrMax = (TextView) findViewById(R.id.history_hr_max);
		
		bpAvg = (TextView) findViewById(R.id.history_bp_average);
		bpMin = (TextView) findViewById(R.id.history_bp_min);
		bpMax = (TextView) findViewById(R.id.history_bp_max);
		
		actAvg = (TextView) findViewById(R.id.history_act_average);
		actMin = (TextView) findViewById(R.id.history_act_min);
		actMax = (TextView) findViewById(R.id.history_act_max);
		
		sleepAvg = (TextView) findViewById(R.id.history_sleep_average);
		sleepMin = (TextView) findViewById(R.id.history_sleep_min);
		sleepMax = (TextView) findViewById(R.id.history_sleep_max);
		setOneWeekLayout();
	}
	
	
	private OnClickListener getShartListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(History.this, Settings.class));
			}
		};
	}

	private void setHistoryDataOneWeek(){
		hrAvg.setText("Avg: 87");
		hrMin.setText("Min: 67");
		hrMax.setText("Max: 122");
		
		bpAvg.setText("Avg: 100/119");
		bpMin.setText("Min: 67/90");
		bpMax.setText("Max: 122/156");
		
		actAvg.setText("Avg: 7011");
		actMin.setText("Min: 2310");
		actMax.setText("Max: 12210");
		
		sleepAvg.setText("Avg: 7.6");
		sleepMin.setText("Min: 3.5");
		sleepMax.setText("Max: 12.0");
		
	}
	
	private void setHistoryDataOneMonth(){
		hrAvg.setText("Avg: 85");
		hrMin.setText("Min: 70");
		hrMax.setText("Max: 92");
		
		bpAvg.setText("Avg: 98/104");
		bpMin.setText("Min: 80/92");
		bpMax.setText("Max: 102/132");
		
		actAvg.setText("Avg: 7217");
		actMin.setText("Min: 1310");
		actMax.setText("Max: 9754");
		
		sleepAvg.setText("Avg: 6.9");
		sleepMin.setText("Min: 4.2");
		sleepMax.setText("Max: 13.5");
		
	}
	
	private void setHistoryDataThreeMonth(){
		hrAvg.setText("Avg: 80");
		hrMin.setText("Min: 77");
		hrMax.setText("Max: 89");
		
		bpAvg.setText("Avg: 98/104");
		bpMin.setText("Min: 66/92");
		bpMax.setText("Max: 98/122");
		
		actAvg.setText("Avg: 5419");
		actMin.setText("Min: 2510");
		actMax.setText("Max: 10030");
		
		sleepAvg.setText("Avg: 7.4");
		sleepMin.setText("Min: 2.5");
		sleepMax.setText("Max: 14.0");
		
	}
	
	public OnClickListener getExportListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				
				
				File attachment = new ExcelExporter(ChartDataController.getInstance()).export();
				EmailSender email=new EmailSender(History.this);
				AccountModel acc =AccountController.getInstance().getAccount(); 
				email.setToEmail(acc.getEmail());
				email.setSubject(acc.getName()+"'s Health Record");
				email.setBodyText("Please find the heatlh status record in the attachment\n Sent from MyHealthMate \n");
				email.setAttachment(attachment);
				email.send();
			}
		};
	}
	
	
	public void setOneWeekLayout(){
		changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.15, 0.5, 0.25);
		changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.2, 0.5, 0.2);
		changeByPercent(historyActLeft,historyActMid,historyActRight,0.3, 0.4, 0.2);
		changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.13, 0.57, 0.2);
		setHistoryDataOneWeek();
	}
	
	
	public OnClickListener getOneWeekFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.15, 0.5, 0.25);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.2, 0.5, 0.2);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.3, 0.4, 0.2);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.13, 0.57, 0.2);
				
				setHistoryDataOneWeek();
			}
		};
	}
	
	public OnClickListener getOneMonthFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.15, 0.55, 0.20);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.2, 0.5, 0.2);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.15, 0.6, 0.15);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.13, 0.57, 0.2);
				
				setHistoryDataOneMonth();
			}
		};
	}
	
	public OnClickListener getThreeMonthFilterListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				changeByPercent(historyHrLeft,historyHrMid,historyHrRight,0.15, 0.60, 0.15);
				changeByPercent(historyBpLeft,historyBpMid,historyBpRight,0.15, 0.6, 0.15);
				changeByPercent(historyActLeft,historyActMid,historyActRight,0.25, 0.5, 0.15);
				changeByPercent(historySleepLeft,historySleepMid,historySleepRight,0.23, 0.47, 0.2);
				
				setHistoryDataThreeMonth();
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
