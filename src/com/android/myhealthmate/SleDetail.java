package com.android.myhealthmate;

import com.android.trend.ChartHelper;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleDetail extends Activity {

	private TextView titleTextView;
	private LinearLayout titleSection;
	private LinearLayout hrSection;
	private LinearLayout actSection;
	private int pointCount = 24;
	private int gridColor;
	private int hrLineColor;
	private int bpLowColor;
	private int bpHighColor;
	private int actBarColor;
	private int sleepBarColor;
	private int lineChartThickness;
	private int barChartThickness;
	private int lineChartPointRadius;
	private int chartBackColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sle_details);
		titleSection = (LinearLayout) findViewById(R.id.detail_title_section);
		hrSection = (LinearLayout) findViewById(R.id.detail_hr_graph);
		actSection = (LinearLayout) findViewById(R.id.detail_act_graph);
		titleTextView = (TextView) findViewById(R.id.detail_title_text);
		drawTitle();
		setUpChartParams();
		creaHeartChart(hrSection);
		createActChart(actSection);
	}

	private void setUpChartParams() {
		gridColor = getResources().getColor(R.color.white);
		hrLineColor = Color.YELLOW;
		bpLowColor = Color.rgb(156, 195, 230);
		bpHighColor = Color.rgb(67, 151, 244);
		sleepBarColor = Color.rgb(250, 174, 42);
		actBarColor = Color.rgb(42, 215, 250);
		lineChartThickness = 3;
		lineChartPointRadius = 4;
		barChartThickness = 1;
		//chartBackColor = Color.CYAN;
	}

	private void drawTitle() {
		titleTextView.setText("testing chart");
	}

	private void creaHeartChart(LinearLayout container) {
		GraphViewSeriesStyle hrStyple = new GraphViewSeriesStyle(hrLineColor, lineChartThickness);
		GraphViewSeriesStyle bplStyple = new GraphViewSeriesStyle(bpLowColor, lineChartThickness);
		GraphViewSeriesStyle bphStyle = new GraphViewSeriesStyle(bpHighColor, lineChartThickness);

		GraphViewSeries exampleSeries = new GraphViewSeries("hr", hrStyple, ChartHelper.generateRandomData(pointCount,
				41, 50));
		GraphViewSeries exampleSeries2 = new GraphViewSeries("bpl", bplStyple, ChartHelper.generateRandomData(
				pointCount, 31, 40));
		GraphViewSeries exampleSeries3 = new GraphViewSeries("bph", bphStyle, ChartHelper.generateRandomData(
				pointCount, 21, 30));

		int xValue = 5;
		double maxY = 50;

		ChartHelper.GraphViewData[] vData = new ChartHelper.GraphViewData[2];
		vData[0] = new ChartHelper.GraphViewData(xValue, 0);
		vData[1] = new ChartHelper.GraphViewData(xValue, maxY);
		GraphViewSeries vSeries = new GraphViewSeries("hi", new GraphViewSeriesStyle(Color.rgb(255, 100, 100), 3),
				vData);

		LineGraphView graphView = new LineGraphView(this, "can't remove stupid title");

		// set background at here
		graphView.setBackground(hrSection.getBackground());
		graphView.addSeries(exampleSeries); // data
		graphView.addSeries(exampleSeries2);
		graphView.addSeries(exampleSeries3);
		graphView.addSeries(vSeries);
		graphView.setShowHorizontalLabels(false);
		graphView.setShowVerticalLabels(false);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		graphView.setDrawDataPoints(true);
		graphView.setDataPointsRadius(lineChartPointRadius);

		container.addView(graphView);
	}

	public void createActChart(LinearLayout container) {
		BarGraphView graphView = new BarGraphView(this // context
				, "can't remove stupid title" // heading
		);

		GraphViewSeriesStyle actStyle = new GraphViewSeriesStyle(actBarColor, barChartThickness);
		GraphViewSeriesStyle sleepStyple = new GraphViewSeriesStyle(sleepBarColor, barChartThickness);

		GraphViewSeries exampleSeries = new GraphViewSeries("act", actStyle, ChartHelper.generateRandomDataWithZero(
				pointCount, 0, 12, 0, 19));
		GraphViewSeries exampleSeries2 = new GraphViewSeries("sleep", sleepStyple,
				ChartHelper.generateRandomDataWithZero(pointCount, 12, 12, 0d, 19));
		graphView.addSeries(exampleSeries); // data
		graphView.addSeries(exampleSeries2); // data
		graphView.setShowHorizontalLabels(false);
		graphView.setShowVerticalLabels(false);
		graphView.setManualMaxY(true);
		graphView.setManualYMaxBound(50);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		container.addView(graphView);
	}

}
