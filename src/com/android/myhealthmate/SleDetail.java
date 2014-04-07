package com.android.myhealthmate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.android.remoteProfile.BenTestClass;
import com.android.remoteProfile.RemoteDataController;
import com.android.remoteProfile.RemoteDataModel;
import com.android.trend.AddNotePopupDialog;
import com.android.trend.ChartDataController;
import com.android.trend.ChartPointModel;
import com.android.trend.ChartViewController;
import com.android.trend.RecordList;
import com.android.trend.RecordModel;
import com.android.trend.RecordModel.recordType;
import com.android.trend.RecordViewSection;

import android.app.ActionBar.OnNavigationListener;
import android.app.Dialog;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SleDetail extends FragmentActivity {
	// date helper
	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM-dd-yyyy HH:mm ", Locale.CANADA);

	// views & buttons
	private TextView titleTextView;
	private LinearLayout hrSection;
	private LinearLayout actSection;
	private int pointCount = 0;
	private FrameLayout chartArea;
	private Button single_prev;
	private Button single_next;
	private Button double_prev;
	private Button double_next;

	// chart
	private int currentX = 0;
	private ChartDataController chartData;
	private ChartViewController chartView;

	// on touch event
	private float lastTouchEventX;
	private boolean scrollingStarted;
	private int chartWidth;

	// history section
	private ArrayList<RecordModel> recordList;
	private ArrayList<RecordViewSection> recordViewList = new ArrayList<RecordViewSection>();
	private static LinearLayout recordLayout;
	private ScrollView scrolView;
	private RecordList recordListInstance;
	int[] oldstartEndLong = { 0, 0, 0 };
	private TextView filterAll;
	private TextView filterReminder;
	private TextView filterRec;
	private TextView filterNote;
	private TextView filterBtn;
	private GridLayout filterArea;

	ArrayList<String> itemList;
	ArrayAdapter<String> userList;
	BenTestClass benTestClass;
	RemoteDataController remoteDataController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sle_details);

		benTestClass = new BenTestClass(SleDetail.this);

		recordLayout = (LinearLayout) findViewById(R.id.recordListSection);
		scrolView = (ScrollView) findViewById(R.id.scrollHistorySection);

		hrSection = (LinearLayout) findViewById(R.id.detail_hr_graph);
		actSection = (LinearLayout) findViewById(R.id.detail_act_graph);
		titleTextView = (TextView) findViewById(R.id.detail_title_text);

		filterAll = (TextView) findViewById(R.id.all_event_filter);
		filterReminder = (TextView) findViewById(R.id.reminder_event_filter);
		filterRec = (TextView) findViewById(R.id.rec_event_filter);
		filterNote = (TextView) findViewById(R.id.note_event_filter);
		filterBtn = (TextView) findViewById(R.id.category_filter_button);
		filterArea = (GridLayout) findViewById(R.id.filter_area);

		remoteDataController = RemoteDataController.getInstance();

		initNavigationBar();
		initChart();
		setupChartListeners();
		initHistorySection();
	}

	private void initNavigationBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		userList = loadUser();
		actionBar.setListNavigationCallbacks(userList, new OnNavigationListener() {
			// Get the same strings provided for the drop-down's ArrayAdapter
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				refreshPageByUserName(userList.getItem(itemPosition).toString());
				Toast.makeText(SleDetail.this, userList.getItem(itemPosition).toString(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private void refreshPageByUserName(String name) {
		RemoteDataModel selectedUserRemoteData = remoteDataController.getDataModelByName(name);
		chartData.setDataset(selectedUserRemoteData.getHealthdata());
		chartView.refreshChart();
		refreshHistorySection(selectedUserRemoteData.getEventdata());
		recordList = selectedUserRemoteData.getEventdata();
		recordListInstance.setRecordList(recordList);
		currentX = (int) chartData.getDisplaySetLen() / 2;
		chartView.moveVto(currentX);
		displayValues();
		scrollHistorySection(chartData.getDisplayDataSet().get(currentX).getTimestamp());
	}

	private ArrayAdapter<String> loadUser() {
		itemList = new ArrayList<String>();
		for (RemoteDataModel user : remoteDataController.getDataList())
			itemList.add(user.getOwnerName());
		userList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
		return userList;
	}

	// --------------------action bar test below------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.event_menu, menu);

		MenuItem searchViewItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchViewItem.getActionView();
		searchView.setIconifiedByDefault(true);

		MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				displayAllEvent();
				return true;
			}

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {

				return true;
			}

		});

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered

				searchEventHistory(newText);
				return true;
			}

			public boolean onQueryTextSubmit(String query) {
				displayAllEvent();
				searchEventHistory(query);
				return true;
			}
		};
		searchView.setOnQueryTextListener(queryTextListener);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items

		switch (item.getItemId()) {
		case R.id.add_note: {
			Dialog newNoteDialog = new AddNotePopupDialog().onCreateDialog(SleDetail.this, getCurrentPoint());
			newNoteDialog.show();
			return true;
		}
		case R.id.action_search: {
			// Toast.makeText(this, "search search search",
			// Toast.LENGTH_SHORT).show();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// --------------------action bar test above------------------------------

	private void initHistorySection() {
		recordListInstance = RecordList.getInstance();
		recordListInstance.init(getApplicationContext());
		recordList = recordListInstance.getRecordList();

		filterAll.setOnClickListener(getFilterAllClickListener());
		filterNote.setOnClickListener(getFilterNoteClickListener());
		filterReminder.setOnClickListener(getFilterReminderClickListener());
		filterRec.setOnClickListener(getFilterRecClickListener());
		filterBtn.setOnClickListener(getFilterBtnClickListener());
		filterBtn.setBackgroundResource(R.drawable.ic_action_next_item);

	}

	public void refreshHistorySection(ArrayList<RecordModel> remoteUserRecordList) {
		recordViewList.clear();
		recordLayout.removeAllViewsInLayout();
		recordListInstance.sortByNext();
		for (RecordModel record : remoteUserRecordList) {
			RecordViewSection rvsection = new RecordViewSection(SleDetail.this, record.getType(),
					record.getTimeStamp(), record.getContent(), record.isMissed(), record.getTitle());
			recordViewList.add(rvsection);
			rvsection.getLayout().setOnClickListener(getHistorySectionClickListener());
			recordLayout.addView(rvsection.getLayout());
		}

	}

	private void initChart() {
		if (chartView == null)
			chartView = new ChartViewController(SleDetail.this, hrSection, actSection);
		chartData = chartView.getChartDataController();
		pointCount = chartView.getPointCount();
		currentX = pointCount - 1;
		displayValues();
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
				shiftChart(-1);
			}
		};
	}

	private OnClickListener getSingleNextClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				shiftChart(1);
			}
		};
	}

	private OnClickListener getDoublePrevClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				shiftChart(-24);
			}
		};
	}

	private OnClickListener getDoubleNextClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				shiftChart(24);
			}
		};
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

	private OnClickListener getHistorySectionClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				int viewIndex = recordLayout.indexOfChild(v);
				Date timestamp = recordList.get(viewIndex).getTimeStamp();
				shiftChart(timestamp);
			}
		};
	}

	private void searchEventHistory(String keyword) {
		int i = 0;
		for (RecordModel record : recordList) {
			if (!record.contains(keyword))
				recordViewList.get(i).getLayout().setVisibility(View.GONE);
			i++;
		}
	}

	public ChartPointModel getCurrentPoint() {
		return chartData.getDisplayDataSet().get(currentX);
	}

	private void displayAllEvent() {
		int i = 0;
		for (RecordModel record : recordList) {
			recordViewList.get(i).getLayout().setVisibility(View.VISIBLE);
			i++;
		}
	}

	private OnClickListener getFilterBtnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (filterArea.getVisibility() == View.GONE) {
					filterArea.setVisibility(View.VISIBLE);
					filterBtn.setBackgroundResource(R.drawable.ic_action_expand);
				} else {
					filterArea.setVisibility(View.GONE);
					filterBtn.setBackgroundResource(R.drawable.ic_action_next_item);
				}

			}
		};
	}

	private OnClickListener getFilterAllClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayAllEvent();
			}
		};
	}

	private OnClickListener getFilterReminderClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayReminderEvent();
			}
		};
	}

	private OnClickListener getFilterRecClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayRecommendationEvent();
			}
		};
	}

	private OnClickListener getFilterNoteClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayNoteEvent();
			}
		};
	}

	private void displayReminderEvent() {
		int i = 0;
		for (RecordModel record : recordList) {
			if (record.getType().equals(recordType.Reminder))
				recordViewList.get(i).getLayout().setVisibility(View.VISIBLE);
			else
				recordViewList.get(i).getLayout().setVisibility(View.GONE);
			i++;
		}
	}

	private void displayRecommendationEvent() {
		int i = 0;
		for (RecordModel record : recordList) {
			if (record.getType().equals(recordType.Recommendation))
				recordViewList.get(i).getLayout().setVisibility(View.VISIBLE);
			else
				recordViewList.get(i).getLayout().setVisibility(View.GONE);
			i++;
		}
	}

	private void displayNoteEvent() {
		int i = 0;
		for (RecordModel record : recordList) {
			if (record.getType().equals(recordType.Note))
				recordViewList.get(i).getLayout().setVisibility(View.VISIBLE);
			else
				recordViewList.get(i).getLayout().setVisibility(View.GONE);
			i++;
		}
	}

	private void displayValues() {
		ChartPointModel currentPoint = chartData.getDisplayDataSet().get(currentX);
		TextView hrText = (TextView) findViewById(R.id.chart_values_hr);
		TextView bpHighText = (TextView) findViewById(R.id.chart_values_bp_high);
		TextView bpText = (TextView) findViewById(R.id.chart_values_bp);
		TextView actText = (TextView) findViewById(R.id.chart_values_activity);
		TextView actTitleText = (TextView) findViewById(R.id.chart_textView_activity);
		TextView sleepText = (TextView) findViewById(R.id.chart_values_sleep);
		TextView sleepTitleText = (TextView) findViewById(R.id.chart_textView_sleep);
		hrText.setText(Integer.toString((int) currentPoint.getHr()));
		bpHighText.setText(Integer.toString((int) currentPoint.getBph()));
		bpText.setText(Integer.toString((int) currentPoint.getBpl()));
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

	private void shiftChart(int points) {
		chartData.shiftDisplayData(points);
		chartView.refreshChart();
		chartView.moveVto(currentX);
		displayValues();
		scrollHistorySection(chartData.getDisplayDataSet().get(currentX).getTimestamp());
	}

	private void shiftChart(Date timestamp) {
		int newX = chartData.shiftDisplayData(timestamp, currentX);
		chartView.refreshChart();
		chartView.moveVto(newX);
		currentX = newX;
		displayValues();
	}

	private void moveVbyTouch(float x) {
		chartWidth = actSection.getWidth();
		int newX = (int) (x / chartWidth * pointCount);
		if (newX >= 0 && newX < pointCount) {
			chartView.moveVto(newX);
			currentX = newX;
		}
		displayValues();
		scrollHistorySection(chartData.getDisplayDataSet().get(currentX).getTimestamp());
	}

	public static void addHistorySection(RecordViewSection recordView, int index) {
		recordLayout.addView(recordView.getLayout(), index);
	}

	private void scrollHistorySection(Date selectedTime) {
		int[] startEndLong = recordListInstance.getOneHourRecord(selectedTime);
		scrolView.scrollTo(0, recordLayout.getChildAt(startEndLong[0]).getTop());

		if (oldstartEndLong[2] != 0) {
			for (int i = oldstartEndLong[0]; i < oldstartEndLong[0] + oldstartEndLong[2]; i++) {
				recordViewList.get(i).disableHighLight();
			}
		}

		for (int i = startEndLong[0]; i < startEndLong[0] + startEndLong[2]; i++) {
			recordViewList.get(i).setHighLight();
		}

		oldstartEndLong = startEndLong;
	}

}
