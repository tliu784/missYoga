package com.android.myhealthmate;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class HrDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hr_details);

		/*
		 * init series data
		 */
		//bp
		int num = 7;
		GraphViewData[] data = new GraphViewData[num];
		double v = 0;
		for (int i = 0; i < num; i++) {
			//30-40
			data[i] = new GraphViewData(i, Math.floor((Math.random()*10)+30));
		}
		GraphViewSeries seriesBp = new GraphViewSeries("Blood pressure",
				new GraphViewSeriesStyle(Color.rgb(255, 165, 00), 3), data);

		//hr
		num = 100;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			//20-30
			data[i] = new GraphViewData(i, Math.floor((Math.random()*10)+20));
		}
		GraphViewSeries seriesHr = new GraphViewSeries("Heart rate",
				new GraphViewSeriesStyle(Color.rgb(204, 00, 00), 3), data);

		//sleep
		num = 150;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			//10-20
			data[i] = new GraphViewData(i, Math.floor((Math.random()*10)+10));
		}
		GraphViewSeries seriesSleep = new GraphViewSeries("Sleep",
				new GraphViewSeriesStyle(Color.rgb(00, 00, 178), 3), data);

		//activity
		num = 100;
		data = new GraphViewData[num];
		v = 0;
		for (int i = 0; i < num; i++) {
			//0-10
			data[i] = new GraphViewData(i, Math.floor((Math.random()*10)+1));
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
		graphView.setHorizontalLabels(new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        graphView.setVerticalLabels(new String[] {"high", "mid", "low"});
		graphView.setScalable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.hr_graph1);
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
