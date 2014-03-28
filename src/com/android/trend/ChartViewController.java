package com.android.trend;

import android.content.Context;
import android.widget.LinearLayout;

import com.android.myhealthmate.R;
import com.android.trend.ChartDataController.SeriesType;
import com.android.trend.ChartHelper.GraphViewData;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

public class ChartViewController {
	private final Context context;

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

	// series style
	GraphViewSeriesStyle hrStyple;
	GraphViewSeriesStyle bplStyple;
	GraphViewSeriesStyle bphStyle;
	GraphViewSeriesStyle actStyle;
	GraphViewSeriesStyle sleepStyle;

	// series
	GraphViewSeries hrSeries;
	GraphViewSeries bplSeries;
	GraphViewSeries bphSeries;
	GraphViewSeries actSeries;
	GraphViewSeries sleepSeries;

	// data
	private int pointCount = 0;
	private GraphViewSeries vSeries;
	private GraphView heartGraph;
	private ChartDataController chartData;
	private GraphViewData[] hrGraphData;
	private GraphViewData[] bplGraphData;
	private GraphViewData[] bphGraphData;
	private GraphViewData[] actGraphData;
	private GraphViewData[] sleepGraphData;

	// layout
	private final LinearLayout hrSection;
	private final LinearLayout actSection;

	public ChartViewController(Context context, LinearLayout hrSection, LinearLayout actSection) {
		this.context = context;
		this.hrSection = hrSection;
		this.actSection = actSection;
		init();

	}

	private void init() {
		setUpChartParams(); // must run before setupData
		setupChartStyles();
		setupChartData();
		pointCount = chartData.getDisplayDataSet().size();
		heartGraph = createHeartChart(hrSection);
		createActChart(actSection);
		moveVto(pointCount - 1);
	}

	private void setupChartData() {
		chartData = new ChartDataController(hrFloor, hrCeiling, bplFloor, bphFloor, bplCeiling, bphCeiling, actFloor,
				actCeiling, sleepFloor, sleepCeiling);

		// random data for now
		chartData.createRandomData(100);
		hrGraphData = chartData.generateGraphData(SeriesType.HR);
		bplGraphData = chartData.generateGraphData(SeriesType.BPL);
		bphGraphData = chartData.generateGraphData(SeriesType.BPH);
		actGraphData = chartData.generateGraphData(SeriesType.ACT);
		sleepGraphData = chartData.generateGraphData(SeriesType.SLEEP);
		pointCount = chartData.getDisplaySetLen();
		if (pointCount > chartData.getDisplayDataSet().size())
			pointCount = chartData.getDisplayDataSet().size();
	}

	public void refreshChart() {
		hrGraphData = chartData.generateGraphData(SeriesType.HR);
		bplGraphData = chartData.generateGraphData(SeriesType.BPL);
		bphGraphData = chartData.generateGraphData(SeriesType.BPH);
		actGraphData = chartData.generateGraphData(SeriesType.ACT);
		sleepGraphData = chartData.generateGraphData(SeriesType.SLEEP);
		hrSeries.resetData(hrGraphData);
		bplSeries.resetData(bplGraphData);
		bphSeries.resetData(bphGraphData);
		actSeries.resetData(actGraphData);
		sleepSeries.resetData(sleepGraphData);
	}

	public void moveVto(int xValue) {
		ChartHelper.GraphViewData[] vData = new ChartHelper.GraphViewData[2];
		vData[0] = new ChartHelper.GraphViewData(xValue, 0);
		vData[1] = new ChartHelper.GraphViewData(xValue, maxY);

		if (vSeries != null) {
			vSeries.resetData(vData);
		} else {
			vSeries = new GraphViewSeries("hi", new GraphViewSeriesStyle(vlineColor, vlineThickness), vData);
			heartGraph.addSeries(vSeries);
		}

	}

	private GraphView createHeartChart(LinearLayout container) {

		hrSeries = new GraphViewSeries("hr", hrStyple, hrGraphData);
		bplSeries = new GraphViewSeries("bpl", bplStyple, bplGraphData);
		bphSeries = new GraphViewSeries("bph", bphStyle, bphGraphData);
		LineGraphView heartGraphView = new LineGraphView(context, " ");
		if (transparentBack)
			heartGraphView.setBackground(hrSection.getBackground());
		else
			heartGraphView.setBackgroundResource(R.color.chart_background);
		heartGraphView.addSeries(hrSeries); // data
		heartGraphView.addSeries(bplSeries);
		heartGraphView.addSeries(bphSeries);
		heartGraphView.setShowHorizontalLabels(false);
		heartGraphView.setShowVerticalLabels(false);
		heartGraphView.getGraphViewStyle().setGridColor(gridColor);
		heartGraphView.setDrawDataPoints(true);
		heartGraphView.setDataPointsRadius(lineChartPointRadius);

		container.addView(heartGraphView);
		return heartGraphView;
	}

	public GraphView createActChart(LinearLayout container) {
		BarGraphView graphView = new BarGraphView(context // context
				, "" // heading
		);
		actSeries = new GraphViewSeries("act", actStyle, actGraphData);
		sleepSeries = new GraphViewSeries("sleep", sleepStyle, sleepGraphData);
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
		transparentBack = context.getResources().getBoolean(R.bool.chart_transparent_background);
		maxY = context.getResources().getInteger(R.integer.maxY);
		hrFloor = context.getResources().getInteger(R.integer.hr_floor);
		hrCeiling = context.getResources().getInteger(R.integer.hr_ceiling);
		bplFloor = context.getResources().getInteger(R.integer.bpl_floor);
		bplCeiling = context.getResources().getInteger(R.integer.bpl_ceiling);
		bphFloor = context.getResources().getInteger(R.integer.bph_floor);
		bphCeiling = context.getResources().getInteger(R.integer.bph_ceiling);
		actFloor = context.getResources().getInteger(R.integer.act_floor);
		actCeiling = context.getResources().getInteger(R.integer.act_ceiling);
		sleepFloor = context.getResources().getInteger(R.integer.sleep_floor);
		sleepCeiling = context.getResources().getInteger(R.integer.sleep_ceiling);

		gridColor = context.getResources().getColor(R.color.chart_grid);
		hrLineColor = context.getResources().getColor(R.color.chart_hr_line);
		bpLowColor = context.getResources().getColor(R.color.chart_bp_low_line);
		bpHighColor = context.getResources().getColor(R.color.chart_bp_high_line);
		actBarColor = context.getResources().getColor(R.color.chart_act_bar);
		sleepBarColorLow = context.getResources().getColor(R.color.chart_sleep_bar_low);
		sleepBarColorMedium = context.getResources().getColor(R.color.chart_sleep_bar_medium);
		sleepBarColorHigh = context.getResources().getColor(R.color.chart_sleep_bar_high);

		vlineThickness = context.getResources().getColor(R.integer.v_line_thickness);
		vlineColor = context.getResources().getColor(R.color.chart_v_line);
		lineChartThickness = context.getResources().getColor(R.integer.line_thickness);
		lineChartPointRadius = context.getResources().getColor(R.integer.line_point_radius);
		barChartThickness = context.getResources().getColor(R.integer.bar_thickness);
	}

	private void setupChartStyles() {
		hrStyple = new GraphViewSeriesStyle(hrLineColor, lineChartThickness);
		bplStyple = new GraphViewSeriesStyle(bpLowColor, lineChartThickness);
		bphStyle = new GraphViewSeriesStyle(bpHighColor, lineChartThickness);
		actStyle = new GraphViewSeriesStyle(actBarColor, barChartThickness);
		sleepStyle = new GraphViewSeriesStyle(sleepBarColor, barChartThickness);
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
	}

	public ChartDataController getChartDataController() {
		return chartData;
	}

	public int getPointCount() {
		return pointCount;
	}

}
