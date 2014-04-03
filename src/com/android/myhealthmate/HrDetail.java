package com.android.myhealthmate;

import java.util.ArrayList;
import java.util.Date;

import com.android.reminder.MedReminderModel;
import com.android.reminder.MedReminderModel.DurationUnit;
import com.android.trend.AddNotePopupDialog;
import com.android.trend.RecordList;
import com.android.trend.RecordModel;
import com.android.trend.RecordModel.recordType;
import com.android.trend.RecordViewSection;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HrDetail extends Activity {

	private static ArrayList<RecordModel> recordList;
	private static LinearLayout recordLayout;
	private ScrollView scrolView;
	private static RecordList recordListInstance;
	private ArrayList<RecordViewSection> RecordViewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hr_details);

		recordListInstance = RecordList.getInstance();
		recordListInstance.init(getApplicationContext());
		recordList = recordListInstance.getRecordList();

		recordLayout = (LinearLayout) findViewById(R.id.recordTest);
		scrolView = (ScrollView) findViewById(R.id.scrollRecordTest);
		
		
		RecordViewList = new ArrayList<RecordViewSection>();

		/*
		 * init series data
		 */
		// bp
		int num = 7;
		GraphViewData[] data = new GraphViewData[num];
		double v = 0;
		for (int i = 0; i < num; i++) {
			// 30-40
			data[i] = new GraphViewData(i, Math.floor((Math.random() * 10) + 30));
		}
		GraphViewSeries seriesBp = new GraphViewSeries("Blood pressure", new GraphViewSeriesStyle(Color.rgb(255, 165,
				00), 3), data);

		// hr
		num = 100;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			// 20-30
			data[i] = new GraphViewData(i, Math.floor((Math.random() * 10) + 20));
		}
		GraphViewSeries seriesHr = new GraphViewSeries("Heart rate",
				new GraphViewSeriesStyle(Color.rgb(204, 00, 00), 3), data);

		// sleep
		num = 150;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			// 10-20
			data[i] = new GraphViewData(i, Math.floor((Math.random() * 10) + 10));
		}
		GraphViewSeries seriesSleep = new GraphViewSeries("Sleep", new GraphViewSeriesStyle(Color.rgb(00, 00, 178), 3),
				data);

		// activity
		num = 100;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			// 0-10
			data[i] = new GraphViewData(i, Math.floor((Math.random() * 10) + 1));
		}
		GraphViewSeries seriesAct = new GraphViewSeries("Activity",
				new GraphViewSeriesStyle(Color.rgb(50, 153, 50), 3), data);

		// graph with dynamically generated horizontal and vertical labels
		LineGraphView graphView;
		graphView = new LineGraphView(this, "Mon 23-24, April");
		// add data
		graphView.addSeries(seriesBp);
		graphView.addSeries(seriesHr);
		graphView.addSeries(seriesSleep);
		graphView.addSeries(seriesAct);

		// set legend
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.getGraphViewStyle().setLegendBorder(20);
		graphView.getGraphViewStyle().setLegendSpacing(30);
		graphView.getGraphViewStyle().setLegendWidth(300);

		// set view port, start=2, size=10
		graphView.setHorizontalLabels(new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" });
		graphView.setVerticalLabels(new String[] { "high", "mid", "low" });
		graphView.setScalable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.hr_graph1);
		layout.addView(graphView);

		// -------------------------------ben scroll
		// section-------------------------------------
		// recordListGenerator(recordList);

		recordListInstance.sortByNext();
		for (RecordModel record : recordList) {
			
			RecordViewList.add(new RecordViewSection(getApplicationContext(), record.getType(), record
					.getTimeStamp(), record.getContent(),record.isMissed(), record.getTitle()));

		}
		
		for (RecordViewSection recordView : RecordViewList) {

			recordLayout.addView(recordView.getLayout());

		}

	}

	public static void addHistorySection(RecordViewSection recordView,int index) {
		recordLayout.addView(recordView.getLayout(),index);
	}

	public void scrollHistorySection(Date selectedTime) {
		// Date testDate = recordList.get(10).getTimeStamp();
		int[] startEndLong = recordListInstance.getOneHourRecord(selectedTime);
		// Toast.makeText(HrDetail.this, Integer.toString(startEndLong[0]),
		// Toast.LENGTH_SHORT).show();
		scrolView.scrollTo(0, recordLayout.getChildAt(startEndLong[0]).getTop());
		for (int i = startEndLong[0]; i < startEndLong[0] + startEndLong[2]; i++) {
			recordLayout.getChildAt(i).setBackgroundResource(R.drawable.highlight_select_title_section);
		}
	}

	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here


	}

	private void recordListGenerator(ArrayList<RecordModel> recordList) {
		Date date = new Date();

		for (int i = 0; i < 100; i++) {
			RecordModel record = new RecordModel(recordType.Recommendation, date, "this is history record"
					+ Integer.toString(i), "Record",true);

			date = MedReminderModel.addDuration(date, 20, DurationUnit.Min);
			if (Math.random() > 0.7)
				recordList.add(record);
		}
	}

	// --------------------action bar test below------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reminder_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_reminder: {
		//	Dialog newNoteDialog = new AddNotePopupDialog().onCreateDialog(HrDetail.this);
		//	newNoteDialog.show();
			return true;
		}
		case R.id.delete_reminder: {

			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// --------------------action bar test above------------------------------

	public class GraphViewData implements GraphViewDataInterface {
		private double x, y;

		public GraphViewData(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public double getX() {
			return this.x;
		}

		@Override
		public double getY() {
			return this.y;
		}
	}
}
