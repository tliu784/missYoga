package com.android.myhealthmate;

import com.android.trend.ChartDataController;
import com.android.trend.ChartDataController.SeriesType;
import com.android.trend.ChartHelper;
import com.android.trend.ChartHelper.GraphViewData;
import com.google.gson.Gson;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.ValueDependentColor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleDetail extends Activity {

	private TextView titleTextView;
	private LinearLayout titleSection;
	private LinearLayout hrSection;
	private LinearLayout actSection;
	private int pointCount = 24;

	private Button prev;
	private Button next;

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

	//data
	private int currentX = 0;
	private GraphViewSeries vSeries;
	private GraphView heartGraph;
	private ChartDataController chartData; 
	private GraphViewData[] hrGraphData;
	private GraphViewData[] bplGraphData;
	private GraphViewData[] bphGraphData;
	private GraphViewData[] actGraphData;
	private GraphViewData[] sleepGraphData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sle_details);
		setUpChartParams(); //must run before setupData
		
		titleSection = (LinearLayout) findViewById(R.id.detail_title_section);
		hrSection = (LinearLayout) findViewById(R.id.detail_hr_graph);
		actSection = (LinearLayout) findViewById(R.id.detail_act_graph);
		titleTextView = (TextView) findViewById(R.id.detail_title_text);
		prev = (Button) findViewById(R.id.chart_prev);
		next = (Button) findViewById(R.id.chart_next);
		prev.setOnClickListener(getPrevClickListener());
		next.setOnClickListener(getNextClickListener());
				
		drawTitle();
		setupData();

		
		heartGraph=creaHeartChart(hrSection);
		createActChart(actSection);
		moveVto(0);
	}
	
	private void setupData(){
		chartData=new ChartDataController(hrFloor, hrCeiling, bplFloor, bphFloor, bplCeiling, bphCeiling, actFloor, actCeiling, sleepFloor, sleepCeiling);
		//random data for now
		chartData.createRandomDisplaySet();
		hrGraphData=chartData.generateGraphData(SeriesType.HR);
		bplGraphData=chartData.generateGraphData(SeriesType.BPL);
		bphGraphData=chartData.generateGraphData(SeriesType.BPH);
		actGraphData=chartData.generateGraphData(SeriesType.ACT);
		sleepGraphData=chartData.generateGraphData(SeriesType.SLEEP);
		Log.d("act",new Gson().toJson(actGraphData));
		Log.d("sleep", new Gson().toJson(sleepGraphData));
	}

	private OnClickListener getNextClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				moveV(false);
			}
		};
	}

	private OnClickListener getPrevClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				moveV(true);
			}
		};
	}
	
	private void displayValues(){

	}

	private void moveV(boolean left) {
		heartGraph.removeSeries(vSeries);
	
		if (currentX < (pointCount-1) && !left)
			currentX++;
		if (currentX > 0 && left)
			currentX--;
		moveVto(currentX);
	}

	private void moveVto(int xValue) {
		if (vSeries != null) {
			heartGraph.removeSeries(vSeries);
		}
		ChartHelper.GraphViewData[] vData = new ChartHelper.GraphViewData[2];
		vData[0] = new ChartHelper.GraphViewData(xValue, 0);
		vData[1] = new ChartHelper.GraphViewData(xValue, maxY);
		vSeries = new GraphViewSeries("hi", new GraphViewSeriesStyle(vlineColor, vlineThickness), vData);
		heartGraph.addSeries(vSeries);
	}

	private void drawTitle() {
		titleTextView.setText("testing chart");
	}

	private GraphView creaHeartChart(LinearLayout container) {
		GraphViewSeriesStyle hrStyple = new GraphViewSeriesStyle(hrLineColor, lineChartThickness);
		GraphViewSeriesStyle bplStyple = new GraphViewSeriesStyle(bpLowColor, lineChartThickness);
		GraphViewSeriesStyle bphStyle = new GraphViewSeriesStyle(bpHighColor, lineChartThickness);

		GraphViewSeries hrSeries = new GraphViewSeries("hr", hrStyple, hrGraphData);
		GraphViewSeries bplSeries = new GraphViewSeries("bpl", bplStyple, bplGraphData);
		GraphViewSeries bphSeries = new GraphViewSeries("bph", bphStyle,bphGraphData); 
				

		LineGraphView graphView = new LineGraphView(this, "can't remove stupid title");

		if (transparentBack)
			graphView.setBackground(hrSection.getBackground());
		else
			graphView.setBackgroundResource(R.color.chart_background);
		graphView.addSeries(hrSeries); // data
		graphView.addSeries(bplSeries);
		graphView.addSeries(bphSeries);
		graphView.setShowHorizontalLabels(false);
		graphView.setShowVerticalLabels(false);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		graphView.setDrawDataPoints(true);
		graphView.setDataPointsRadius(lineChartPointRadius);

		container.addView(graphView);
		return graphView;
	}

	public GraphView createActChart(LinearLayout container) {
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
//		GraphViewSeries actSeries = new GraphViewSeries("act", actStyle, ChartHelper.generateRandomDataWithZero(
//				pointCount, 12, 12, actFloor, actCeiling));
//		GraphViewSeries sleepSeries = new GraphViewSeries("sleep", sleepStyle, ChartHelper.generateSleepData(
//				pointCount, 0, 12, sleepFloor, sleepCeiling));
		
		GraphViewSeries actSeries = new GraphViewSeries("act", actStyle, actGraphData);
		GraphViewSeries sleepSeries = new GraphViewSeries("sleep", sleepStyle, sleepGraphData);
		graphView.addSeries(actSeries); // data
		graphView.addSeries(sleepSeries); // data
		graphView.setShowHorizontalLabels(false);
		graphView.setShowVerticalLabels(false);
		graphView.setManualMaxY(true);
		graphView.setManualYMaxBound(maxY);
		graphView.getGraphViewStyle().setGridColor(gridColor);
		container.addView(graphView);
		return graphView;
	}

	private void setUpChartParams() {
		transparentBack = getResources().getBoolean(R.bool.chart_transparent_background);
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

}
