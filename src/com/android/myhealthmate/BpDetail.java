package com.android.myhealthmate;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class BpDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bp_detail);

		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(
				new GraphViewData[] {
						new GraphViewData(1, 2.0d),
						new GraphViewData(2, 1.5d),
						new GraphViewData(2.5, 3.0d) // another frequency
						, new GraphViewData(3, 2.5d),
						new GraphViewData(4, 1.0d), new GraphViewData(5, 3.0d) });

		// graph with dynamically genereated horizontal and vertical labels
		GraphView graphView;

		graphView = new LineGraphView(this // context
				, "GraphViewDemo" // heading
		);
		((LineGraphView) graphView).setDrawDataPoints(true);
		((LineGraphView) graphView).setDataPointsRadius(15f);

		graphView.addSeries(exampleSeries); // data

		LinearLayout layout = (LinearLayout) findViewById(R.id.bp_graph1);
		layout.addView(graphView);

		// graph with custom labels and drawBackground

		graphView = new LineGraphView(this, "GraphViewDemo");
		((LineGraphView) graphView).setDrawBackground(true);
		((LineGraphView) graphView).setBackgroundColor(Color.rgb(80, 30, 30));
		// custom static labels
		graphView.setHorizontalLabels(new String[] { "2 days ago", "yesterday",
				"today", "tomorrow" });
		graphView.setVerticalLabels(new String[] { "high", "middle", "low" });

		GraphViewSeries exampleSeries2 = new GraphViewSeries(
				new GraphViewData[] { new GraphViewData(1, 2.0d),
						new GraphViewData(2, 1.5d), new GraphViewData(3, 2.5d),
						new GraphViewData(4, 1.0d) });
		graphView.setManualYAxisBounds(2.5d, 0d);
		graphView.addSeries(exampleSeries2); // data

		layout = (LinearLayout) findViewById(R.id.bp_graph2);
		layout.addView(graphView);

	}

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
