package com.android.myhealthmate;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

public class ActDetail extends Activity {

//	private final Handler mHandler = new Handler();
//    private Runnable mTimer;
//    private GraphViewSeries exampleSeries2;
//
//    private double getRandom() {
//        double high = 3;
//        double low = 0.5;
//        return Math.random() * (high - low) + low;
//    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		/*
		 * Graph 1: individual label colors
		 * fix num labels
		 */
        // init example series data
//        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
//                new GraphViewData(1, 2.0d)
//                , new GraphViewData(2, 1.5d)
//                , new GraphViewData(2.5, 3.0d) // another frequency
//                , new GraphViewData(3, 2.5d)
//                , new GraphViewData(4, 1.0d)
//                , new GraphViewData(5, 3.0d)
//        });
//
//        // graph with dynamically generated horizontal and vertical labels
//        GraphView graphView;
//
//            graphView = new LineGraphView(
//                    this // context
//                    , "GraphViewDemo" // heading
//            );
//            ((LineGraphView) graphView).setDrawDataPoints(true);
//
//        // set styles
//        graphView.getGraphViewStyle().setGridColor(Color.GREEN);
//        graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.YELLOW);
//        graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
//        graphView.getGraphViewStyle().setNumHorizontalLabels(5);
//        graphView.getGraphViewStyle().setNumVerticalLabels(4);
//        graphView.getGraphViewStyle().setVerticalLabelsWidth(300);
//        graphView.getGraphViewStyle().setVerticalLabelsAlign(Align.CENTER);
//
//        graphView.addSeries(exampleSeries); // data
//
//        LinearLayout layout = (LinearLayout) findViewById(R.id.act_graph1);
//        layout.addView(graphView);
//	}
//
//	public class GraphViewData implements GraphViewDataInterface {
//		private double x, y;
//
//		public GraphViewData(double x, double y) {
//			this.x = x;
//			this.y = y;
//		}
//
//		@Override
//		public double getX() {
//			return this.x;
//		}
//
//		@Override
//		public double getY() {
//			return this.y;
//		}
//	}
//	
//	@Override
//    protected void onPause() {
//        mHandler.removeCallbacks(mTimer);
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mTimer = new Runnable() {
//            @Override
//            public void run() {
//                exampleSeries2.resetData(new GraphViewData[] {
//                        new GraphViewData(1, getRandom())
//                        , new GraphViewData(2, getRandom())
//                        , new GraphViewData(2.5, getRandom()) // another frequency
//                        , new GraphViewData(3, getRandom())
//                        , new GraphViewData(4, getRandom())
//                        , new GraphViewData(5, getRandom())
//                });
//                mHandler.postDelayed(this, 300);
//            }
//        };
//        mHandler.postDelayed(mTimer, 300);
    }
}
