package com.android.trend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.myhealthmate.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

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

	public RecordViewSection(Context context, String type, Date date, String content) {
		this.contentLayout = new LinearLayout(context);
		this.line = new TextView(context);
		this.layout = new LinearLayout(context);
		this.titleType = new TextView(context);
		this.titleType.setText(type);
		this.icon = new TextView(context);
		this.time = new TextView(context);
		setTime(date);
		this.content = new TextView(context);
		this.content.setText(content);
		this.titleLayout = new LinearLayout(context);
		this.timeLayout = new LinearLayout(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		setFormat();
	}

	private void setFormat() {
		int padding = 5;
		layout.setLayoutParams(setLayoutParams(0, 0, 0, 0));
		layout.setBackgroundResource(R.drawable.textlines);
		layout.setOrientation(LinearLayout.VERTICAL);

		addTitleLayout();
		setLine();
		setContentLayout();
	}

	private void addTitleLayout() {
		int padding = 5;
		titleLayout.setLayoutParams(setLayoutParams(0, 10, 0, 0));
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setPadding(padding, padding, padding, padding);

		icon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		icon.setPadding(padding, 0, padding, 0);
		icon.setBackgroundResource(R.drawable.ic_dialog_icon_about);

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
		final SimpleDateFormat nextTimeFormat = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
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

}
