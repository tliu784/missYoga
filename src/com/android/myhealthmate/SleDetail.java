package com.android.myhealthmate;

import java.text.SimpleDateFormat;

import com.android.trend.ChartDataController;
import com.android.trend.ChartDataController.SeriesType;
import com.android.trend.ChartHelper;
import com.android.trend.ChartHelper.GraphViewData;
import com.android.trend.ChartPointModel;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleDetail extends Activity {
	// date helper
	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM-dd-yyyy HH:mm ");

	// views & buttons
	private TextView titleTextView;
	private LinearLayout titleSection;
	private LinearLayout hrSection;
	private LinearLayout actSection;
	private int pointCount = 24;
	private FrameLayout chartArea;
	private Button single_prev;
	private Button single_next;
	private Button double_prev;
	private Button double_next;

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

	// data
	private int currentX = 0;
	private GraphViewSeries vSeries;
	private GraphView heartGraph;
	private GraphView actGraph;
	private ChartDataController chartData;
	private GraphViewData[] hrGraphData;
	private GraphViewData[] bplGraphData;
	private GraphViewData[] bphGraphData;
	private GraphViewData[] actGraphData;
	private GraphViewData[] sleepGraphData;

	// on touch event
	private float lastTouchEventX;
	private boolean scrollingStarted;
	private int chartWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sle_details);
		setUpChartParams(); // must run before setupData
		titleSection = (LinearLayout) findViewById(R.id.detail_title_section);
		hrSection = (LinearLayout) findViewById(R.id.detail_hr_graph);
		actSection = (LinearLayout) findViewById(R.id.detail_act_graph);
		titleTextView = (TextView) findViewById(R.id.detail_title_text);
		setupChartListeners();
		setupChartData();
		heartGraph = createHeartChart(hrSection);
		actGraph = createActChart(actSection);
		moveVto(pointCount-1);
	}

	private void setupChartListeners() {
		chartArea = (FrameLayout) findViewById(R.id.chart_area);
		single_prev = (Button) findViewById(R.id.chart_prev);
		single_next = (Button) findViewById(R.id.chart_next);
		double_prev = (Button) findViewById(R.id.chart_double_prev);
		double_next = (Button) findViewById(R.id.chart_double_next);
		single_prev.setOnClickListener(getSinglePrevClickListener());
		single_next.setOnClickListener(getSingleNextClickListener());
		double_prev.setOnClickListener(getDoublePrevClickListener());
		double_next.setOnClickListener(getDoubleNextClickListener());
		chartArea.setOnTouchListener(getTouchChartListener());
	}

	private OnClickListener getSinglePrevClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				chartData.shiftDisplayData(-1);
				refreshChart();
				moveVto(currentX);
			}
		};
	}

	private OnClickListener getSingleNextClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				chartData.shiftDisplayData(1);
				refreshChart();
				moveVto(currentX);
			}
		};
	}

	private OnClickListener getDoublePrevClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		};
	}

	private OnClickListener getDoubleNextClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		};
	}

	private void setupChartData() {
		chartData = new ChartDataController(hrFloor, hrCeiling, bplFloor, bphFloor, bplCeiling, bphCeiling, actFloor,
				actCeiling, sleepFloor, sleepCeiling);

		// random data for now
		chartData.createRandomData(36);
		hrGraphData = chartData.generateGraphData(SeriesType.HR);
		bplGraphData = chartData.generateGraphData(SeriesType.BPL);
		bphGraphData = chartData.generateGraphData(SeriesType.BPH);
		actGraphData = chartData.generateGraphData(SeriesType.ACT);
		sleepGraphData = chartData.generateGraphData(SeriesType.SLEEP);
		pointCount=chartData.getDisplaySetLen();
		if (pointCount>chartData.getDisplayDataSet().size())
			pointCount=chartData.getDisplayDataSet().size();
	}

	private void refreshChart() {
		hrGraphData = chartData.generateGraphData(SeriesType.HR);
		bplGraphData = chartData.generateGraphData(SeriesType.BPL);
		bphGraphData = chartData.generateGraphData(SeriesType.BPH);
		actGraphData = chartData.generateGraphData(SeriesType.ACT);
		sleepGraphData = chartData.generateGraphData(SeriesType.SLEEP);
		hrSection.removeAllViews();
		actSection.removeAllViews();
		createHeartChart(hrSection);
		createActChart(actSection);
	}

	private OnTouchListener getTouchChartListener() {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean handled = false;
				if (!handled) {
					if ((event.getAction() & MotionEvent.ACTION_DOWN) == MotionEvent.ACTION_DOWN
							&& (event.getAction() & MotionEvent.ACTION_MOVE) == 0) {
						scrollingStarted = true;
						handled = true;
					}
					if ((event.getAction() & MotionEvent.ACTION_UP) == MotionEvent.ACTION_UP) {
						scrollingStarted = false;
						lastTouchEventX = 0;
						handled = true;
					}
					if ((event.getAction() & MotionEvent.ACTION_MOVE) == MotionEvent.ACTION_MOVE) {
						if (scrollingStarted) {
							if (lastTouchEventX != 0) {
								moveVbyTouch(event.getX());
							}
							lastTouchEventX = event.getX();
							handled = true;
						}
					}

				} else {
					// currently scaling
					scrollingStarted = false;
					lastTouchEventX = 0;
				}
				return handled;
			}

		};
	}

	private void displayValues() {
		ChartPointModel currentPoint = chartData.getDisplayDataSet().get(currentX);
		// to be changed
		TextView hrText = (TextView) findViewById(R.id.chart_values_hr);
		TextView bphText = (TextView) findViewById(R.id.chart_values_bp_systolic);
		TextView bplText = (TextView) findViewById(R.id.chart_values_bp_diastolic);
		TextView actText = (TextView) findViewById(R.id.chart_values_activity);
		TextView actTitleText = (TextView) findViewById(R.id.chart_textView_activity);
		TextView sleepText = (TextView) findViewById(R.id.chart_values_sleep);
		TextView sleepTitleText = (TextView) findViewById(R.id.chart_textView_sleep);
		hrText.setText(Integer.toString((int) currentPoint.getHr()));
		bplText.setText(Integer.toString((int) currentPoint.getBpl()));
		bphText.setText(Integer.toString((int) currentPoint.getBph()));
		titleTextView.setText(sdf.format(currentPoint.getTimestamp()));
		if (currentPoint.isSleep()) {
			actTitleText.setVisibility(View.GONE);
			actText.setVisibility(View.GONE);
			sleepTitleText.setVisibility(View.VISIBLE);
			sleepText.setVisibility(View.VISIBLE);
			int sleepValue = (int) currentPoint.getSleep();
			String sleepT = "";
			switch (sleepValue) {
			case (int) ChartPointModel.SLEEP_HIGH:
				sleepT = "Deep";
				break;
			case (int) ChartPointModel.SLEEP_MED:
				sleepT = "Light";
				break;
			case (int) ChartPointModel.SLEEP_LOW:
				sleepT = "awake";
				break;
			}
			sleepText.setText(sleepT);

		} else {
			actTitleText.setVisibility(View.VISIBLE);
			actText.setVisibility(View.VISIBLE);
			sleepTitleText.setVisibility(View.GONE);
			sleepText.setVisibility(View.GONE);
			actText.setText(Integer.toString((int) currentPoint.getAct()));
		}
	}

	
	private void moveV(boolean left) {
		heartGraph.removeSeries(vSeries);

		if (currentX < (pointCount - 1) && !left)
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
		displayValues();
	}

	private void moveVbyTouch(float x) {
		chartWidth = actSection.getWidth();
		int newX = (int) (x / chartWidth * pointCount);
		if (newX >= 0 && newX < pointCount) {
			moveVto(newX);
			currentX = newX;
		}
	}

	private GraphView createHeartChart(LinearLayout container) {
		GraphViewSeriesStyle hrStyple = new GraphViewSeriesStyle(hrLineColor, lineChartThickness);
		GraphViewSeriesStyle bplStyple = new GraphViewSeriesStyle(bpLowColor, lineChartThickness);
		GraphViewSeriesStyle bphStyle = new GraphViewSeriesStyle(bpHighColor, lineChartThickness);

		GraphViewSeries hrSeries = new GraphViewSeries("hr", hrStyple, hrGraphData);
		GraphViewSeries bplSeries = new GraphViewSeries("bpl", bplStyple, bplGraphData);
		GraphViewSeries bphSeries = new GraphViewSeries("bph", bphStyle, bphGraphData);

		LineGraphView graphView = new LineGraphView(this, " ");

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
				, "" // heading
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
