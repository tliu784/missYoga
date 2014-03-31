package com.android.trend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.myhealthmate.R;
import com.android.trend.RecordModel.recordType;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecordViewSection {

	TextView titleType;
	TextView time;
	TextView content;
	LinearLayout layout;
	LinearLayout titleLayout;
	LinearLayout contentLayout;
	TextView line;
	Context context;
	TextView icon;
	LinearLayout timeLayout;
	recordType type;
	boolean miss;

	
	
	public RecordViewSection(Context context, recordType type, Date date, String content,boolean miss, String title) {
		this.contentLayout = new LinearLayout(context);
		this.line = new TextView(context);
		this.layout = new LinearLayout(context);
		this.type = type;
		this.titleType = new TextView(context);
		this.titleType.setText(type.toString()+" - "+ title);
		this.icon = new TextView(context);
		this.time = new TextView(context);
		setTime(date);
		this.content = new TextView(context);
		this.content.setText(content);
		this.titleLayout = new LinearLayout(context);
		this.timeLayout = new LinearLayout(context);
		this.context = context;
		this.miss = miss;
		// TODO Auto-generated constructor stub
		setFormat();
	}

	private void setFormat() {
		layout.setLayoutParams(setLayoutParams(0, 0, 0, 0));
		layout.setBackgroundResource(R.drawable.textlines);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setClickable(true);

		addTitleLayout();
		setLine();
		setContentLayout();
		recordSectionListener();
	}
	

	private void recordSectionListener() {
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "yeah click click", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addTitleLayout() {
		int padding = 5;
		titleLayout.setLayoutParams(setLayoutParams(0, 10, 0, 0));
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setPadding(padding, padding, padding, padding);

		icon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		icon.setPadding(padding, 0, padding, 0);
		setIcon();
		

		titleType.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		titleType.setPadding(padding, 0, padding, 0);
		titleType.setTextColor(Color.BLACK);
		titleType.setTypeface(null, Typeface.BOLD);

		timeLayout.setLayoutParams(setLayoutParams(0, padding, 0, 0));
		timeLayout.setPadding(padding, 0, padding, 0);
		timeLayout.setOrientation(LinearLayout.HORIZONTAL);

		time.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		time.setTextColor(context.getResources().getColor(R.color.grey));

		timeLayout.addView(time);
		timeLayout.setGravity(Gravity.RIGHT);
		time.setGravity(Gravity.RIGHT);

		titleLayout.addView(icon);
		titleLayout.addView(titleType);
		titleLayout.addView(timeLayout);
		layout.addView(titleLayout);
	}

	
	private void setIcon(){
		
		if(type.equals(recordType.Reminder) && miss)
			icon.setBackgroundResource(R.drawable.ic_action_alarms_small);
		else if(type.equals(recordType.Reminder)&& (!miss))
			icon.setBackgroundResource(R.drawable.ic_action_alarms_small);
		else if(type.equals(recordType.Note))
			icon.setBackgroundResource(R.drawable.ic_dialog_icon_about);
		else if(type.equals(recordType.Recommendation))
			icon.setBackgroundResource(R.drawable.ic_action_alarms_light);
	
	}
	
	
	
	private void setLine() {
		line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
		line.setBackgroundColor(context.getResources().getColor(R.color.grey));
		layout.addView(line);
	}

	private void setContentLayout() {
		int paddings = 10;
		contentLayout.setLayoutParams(setLayoutParams(0, 10, 0, 0));

		content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		content.setPadding(15, paddings, paddings, paddings);
		contentLayout.addView(content);

		layout.addView(contentLayout);
	}

	private LayoutParams setLayoutParams(int marginLeft, int marginTop, int marginRight, int marginButton) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(marginLeft, marginTop, marginRight, marginButton);
		return layoutParams;
	}

	public TextView getTitleType() {
		return titleType;
	}

	public TextView getTime() {
		return time;
	}

	public void setTime(Date date) {
		final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("MM/dd HH:mm a", Locale.ENGLISH);
		String dateStr = nextTimeFormat.format(date);
		this.time.setText(dateStr);
	}

	public TextView getContent() {
		return content;
	}

	public void setContent(TextView content) {
		this.content = content;
	}

	public LinearLayout getLayout() {
		return layout;
	}

	public void setLayout(LinearLayout layout) {
		this.layout = layout;
	}
	
	public void setHighLight(){
		layout.setBackgroundResource(R.drawable.highlight_select_title_section);
	}
	
	public void disableHighLight(){
		layout.setBackgroundResource(R.drawable.textlines);
	}
}
