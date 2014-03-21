package com.android.myhealthmate;

import com.android.trend.ChartHelper;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.ValueDependentColor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleDetail extends Activity {

	private TextView titleTextView;
	private LinearLayout titleSection;
	private LinearLayout hrSection;
	private LinearLayout actSection;
	private int pointCount = 24;

	// styling
	private int gridColor;
	private int hrLineColor;
	private int bpLowColor;
	private int bpHighColor;
	private int actBarColor;
	private int sleepBarColor;
	private int sleepBarColorLow;
	private int sleepBarColorMedium;
	private int sleepBarColorHigh;
	private int vlineColor;
	private int vlineThickness;
	private int lineChartThickness;
	private int barChartThickness;
	private int lineChartPointRadius;
	private int hrFloor;
	private int hrCeiling;
	private int bplFloor;
	private int bphFloor;
	private int bplCeiling;
	private int bphCeiling;
	private int actFloor;
	private int actCeiling;
	private int sleepFloor;
	private int sleepCeiling;
	private int maxY;
	private boolean transparentBack;

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
		transparentBack=getResources().getBoolean(R.bool.chart_transparent_background);
		maxY = getResources().getInteger(R.integer.maxY);
		hrFloor = getResources().getInteger(R.integer.hr_floor);
		hrCeiling = getResources().getInteger(R.integer.hr_ceiling);
		bplFloor = getResources().getInteger(R.integer.bpl_floor);
		bplCeiling = getResources().getInteger(R.integer.bpl_ceiling);
		bphFloor = getResources().getInteger(R.integer.bph_floor);
		bphCeiling = getResources().getInteger(R.integer.bph_ceiling);
		actFloor = getResources().getInteger(R.integer.act_floor);
		actCeiling = getResources().getInteger(R.integer.act_ceiling);
		sleepFloor = getResources().getInteger(R.integer.sleep_floor);
		sleepCeiling = getResources().getInteger(R.integer.sleep_ceiling);

		gridColor = getResources().getColor(R.color.chart_grid);
		hrLineColor = getResources().getColor(R.color.chart_hr_line);
		bpLowColor = getResources().getColor(R.color.chart_bp_low_line);
		bpHighColor = getResources().getColor(R.color.chart_bp_high_line);
		actBarColor = getResources().getColor(R.color.chart_act_bar);
		sleepBarColorLow = getResources().getColor(R.color.chart_sleep_bar_low);
		sleepBarColorMedium = getResources().getColor(R.color.chart_sleep_bar_medium);
		sleepBarColorHigh = getResources().getColor(R.color.chart_sleep_bar_high);

		vlineThickness = getResources().getColor(R.integer.v_line_thickness);
		vlineColor = getResources().getColor(R.color.chart_v_line);
		lineChartThickness = getResources().getColor(R.integer.line_thickness);
		lineChartPointRadius = getResources().getColor(R.integer.line_point_radius);
		barChartThickness = getResources().getColor(R.integer.bar_thickness);

	}

	private void drawTitle() {
		titleTextView.setText("testing chart");
	}

	private void creaHeartChart(LinearLayout container) {
		GraphViewSeriesStyle hrStyple = new GraphViewSeriesStyle(hrLineColor, lineChartThickness);
		GraphViewSeriesStyle bplStyple = new GraphViewSeriesStyle(bpLowColor, lineChartThickness);
		GraphViewSeriesStyle bphStyle = new GraphViewSeriesStyle(bpHighColor, lineChartThickness);

		GraphViewSeries exampleSeries = new GraphViewSeries("hr", hrStyple, ChartHelper.generateRandomData(pointCount,
				hrFloor, hrCeiling));
		GraphViewSeries exampleSeries2 = new GraphViewSeries("bpl", bplStyple, ChartHelper.generateRandomData(
				pointCount, bplFloor, bplCeiling));
		GraphViewSeries exampleSeries3 = new GraphViewSeries("bph", bphStyle, ChartHelper.generateRandomData(
				pointCount, bphFloor, bphCeiling));

		int xValue = 5;

		ChartHelper.GraphViewData[] vData = new ChartHelper.GraphViewData[2];
		vData[0] = new ChartHelper.GraphViewData(xValue, 0);
		vData[1] = new ChartHelper.GraphViewData(xValue, maxY);
		GraphViewSeries vSeries = new GraphViewSeries("hi", new GraphViewSeriesStyle(vlineColor, vlineThickness), vData);

		LineGraphView graphView = new LineGraphView(this, "can't remove stupid title");

		if (transparentBack)
			graphView.setBackground(hrSection.getBackground());
		else
			graphView.setBackgroundResource(R.color.chart_background);
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
		GraphViewSeriesStyle sleepStyle = new GraphViewSeriesStyle(sleepBarColor, barChartThickness);
		sleepStyle.setValueDependentColor((new ValueDependentColor() {
			@Override
			public int get(GraphViewDataInterface data) {
				int sleepRange = sleepCeiling - sleepFloor;
				int color = 0;
				if (data.getY() <= 0.33 * sleepRange) {
					color = sleepBarColorLow;
				} else {
					if (data.getY() > 0.66 * sleepRange) {
						color = sleepBarColorHigh;
					} else {
						color = sleepBarColorMedium;
					}
				}
				return color;
			}
		}));
		GraphViewSeries exampleSeries = new GraphViewSeries("act", actStyle, ChartHelper.generateRandomDataWithZero(
				pointCount, 12, 12, actFloor, actCeiling));
		GraphViewSeries exampleSeries2 = new GraphViewSeries("sleep", sleepStyle, ChartHelper.generateSleepData(
				pointCount, 0, 12, sleepFloor, sleepCeiling));
		graphView.addSeries(exampleSeries); // data
		graphView.addSeries(exampleSeries2); // data
		graphView.setShowHorizontalLabels(false);
		graphView.setShowVerticalLabels(false);
		graphView.setManualMaxY(true);
		graphView.setManualYMaxBound(maxY);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		container.addView(graphView);
	}

}
