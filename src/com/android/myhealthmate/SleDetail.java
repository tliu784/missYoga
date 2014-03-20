package com.android.myhealthmate;

import com.android.trend.ChartHelper;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.ValueDependentColor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
	private int sleepBarColor1;
	private int sleepBarColor2;
	private int sleepBarColor3;
	private int vlineColor;
	private int vlineThickness;
	private int lineChartThickness;
	private int barChartThickness;
	private int lineChartPointRadius;
	private int chartBackColor;
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
		hrFloor = 41;
		hrCeiling = 50;
		bplFloor = 21;
		bplCeiling = 30;
		bphFloor = 31;
		bphCeiling = 40;
		actFloor = 0;
		actCeiling = 19;
		sleepFloor = 0;
		sleepCeiling = 17;

		gridColor = getResources().getColor(R.color.white);
		hrLineColor = Color.YELLOW;
		bpLowColor = Color.rgb(156, 195, 230);
		bpHighColor = Color.rgb(67, 151, 244);
		actBarColor = Color.rgb(255, 153, 0);
		sleepBarColor = Color.rgb(42, 215, 250);
		sleepBarColor1 = Color.rgb(153, 204, 255);
		sleepBarColor2 = Color.rgb(0, 102, 204);
		sleepBarColor3 = Color.rgb(0, 0, 204);
		
		
		vlineThickness=2;
		vlineColor=Color.RED;
		lineChartThickness = 4;
		lineChartPointRadius = 5;
		barChartThickness = 1;
		// chartBackColor = Color.CYAN;
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
		double maxY = 50;

		ChartHelper.GraphViewData[] vData = new ChartHelper.GraphViewData[2];
		vData[0] = new ChartHelper.GraphViewData(xValue, 0);
		vData[1] = new ChartHelper.GraphViewData(xValue, maxY);
		GraphViewSeries vSeries = new GraphViewSeries("hi", new GraphViewSeriesStyle(vlineColor, vlineThickness),
				vData);

		LineGraphView graphView = new LineGraphView(this, "can't remove stupid title");

		// set background at here
		graphView.setBackground(hrSection.getBackground());
		//or use
//		graphView.setBackgroundResource(R.color.highlight_titleSec_blue);
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
					color = sleepBarColor1;
				} else {
					if (data.getY() > 0.66 * sleepRange) {
						color = sleepBarColor3;
					} else {
						color = sleepBarColor2;
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
		graphView.setManualYMaxBound(50);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		container.addView(graphView);
	}

}
