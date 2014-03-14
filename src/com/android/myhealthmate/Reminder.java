package com.android.myhealthmate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myhealthmate.test;

public class Reminder extends Activity {

	private LinearLayout previous = null;
	private LinearLayout previousEditContent = null;
	private TextView previousEditButton = null;
	LinearLayout linearLayout;
	ArrayList<TextView> titleArray;
	ArrayList<TextView> contentArray;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		linearLayout = (LinearLayout) findViewById(R.id.reminder_item);

		titleArray = new ArrayList<TextView>();
		contentArray = new ArrayList<TextView>();

		for (int i = 0; i < 20; i++) {
			createNote();
		}

	}

	// private OnClickListener getBenPageClickListener(TextView content) {
	// return new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// content.setVisibility(reminderNotes.isShown() ? View.GONE
	// : View.VISIBLE);
	// }
	// };
	// }

	@SuppressLint("ResourceAsColor")
	private void createNote() {
		LinearLayout newLinearLayout = new LinearLayout(this);
		LinearLayout newNoteLinearLayout = new LinearLayout(this);
		LinearLayout editNoteLinearLayout = new LinearLayout(this);

		TextView editButton = new TextView(this);
		
		//title section
		
		newLinearLayout = CreateTitle(editButton);
		
		// display content section
		newNoteLinearLayout =  CreateNoteSection();

		// edit content section

		editNoteLinearLayout = CreateEditNoteSection();

		// add three sections into layout
		linearLayout.addView(newLinearLayout);
		linearLayout.addView(newNoteLinearLayout);
		linearLayout.addView(editNoteLinearLayout);

		newLinearLayout.setOnClickListener(new MyListener(newNoteLinearLayout,
				editButton, editNoteLinearLayout));
		editButton.setOnClickListener(new MyNoteListener(newNoteLinearLayout,
				editNoteLinearLayout));

	}

	
	private LinearLayout CreateTitle(TextView edit) {
		LinearLayout titleSection = new LinearLayout(this);
		EditText notes = new EditText(this);
		TextView reminderTime = new TextView(this);
		TextView title = new TextView(this);
		TextView editButton = new TextView(this);
		
		editButton = edit;

		// title section
		titleSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, 70));
		titleSection.setClickable(true);
		titleSection.setOrientation(LinearLayout.HORIZONTAL);

		reminderTime.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		reminderTime.setText("11:20 AM");
		reminderTime.setTextSize(20);

		title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		title.setText("ASPIRIN");
		title.setTextSize(20);
		title.setPadding(20, 0, 40, 0);

		editButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				R.drawable.ic_action_edit, 0);
		editButton.setGravity(Gravity.TOP);
		editButton.setClickable(true);
		editButton.setVisibility(View.GONE);

		titleSection.addView(reminderTime);
		titleSection.addView(title);
		titleSection.addView(editButton);
		return titleSection;
	}
	
	private LinearLayout CreateNoteSection() {

		LinearLayout newNoteLinearLayout = new LinearLayout(this);
		TextView content = new TextView(this);
		TextView rdTime = new TextView(this);

		content.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		content.setText("Take your three pills today");
		content.setBackgroundResource(R.drawable.textlines);
		content.setTextSize(20);

		// everyday section in display content section

		rdTime.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		rdTime.setText("everyday");
		rdTime.setBackgroundResource(R.drawable.textlines);
		rdTime.setTextSize(20);

		newNoteLinearLayout.addView(content);
		newNoteLinearLayout.addView(rdTime);

		newNoteLinearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		newNoteLinearLayout.setOrientation(LinearLayout.VERTICAL);
		newNoteLinearLayout.setVisibility(View.GONE);
		return newNoteLinearLayout;
	}

	private LinearLayout CreateEditNoteSection() {
		LinearLayout editNoteLinearLayout = new LinearLayout(this);
		CheckBox checkbox = new CheckBox(this);
		EditText editNotes = new EditText(this);
		TextView everyday = new TextView(this);

		editNoteLinearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		editNoteLinearLayout.setOrientation(LinearLayout.VERTICAL);

		// edit notes
		editNotes.setLayoutParams(new LayoutParams(
				R.dimen.login_edittext_width, R.dimen.login_edittext_height));
		editNotes.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		// edit everyday section in edit content section
		LinearLayout everydaySection = new LinearLayout(this);
		everydaySection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		everydaySection.setOrientation(LinearLayout.HORIZONTAL);

		checkbox.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		checkbox.setFocusable(false);

		everyday.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		everyday.setText("everyday");
		everyday.setBackgroundResource(R.drawable.textlines);
		everyday.setTextSize(20);

		everydaySection.addView(checkbox);
		everydaySection.addView(everyday);

		// add start date section in edit content section

		LinearLayout startDateSection = new LinearLayout(this);
		startDateSection.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		startDateSection.setOrientation(LinearLayout.HORIZONTAL);

		TextView startDate = new TextView(this);
		startDate.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		startDate.setText("Start Date");
		startDate.setBackgroundResource(R.drawable.textlines);
		startDate.setTextSize(20);

		EditText editDate = new EditText(this);
		editDate.setLayoutParams(new LayoutParams(R.dimen.login_edittext_width,
				R.dimen.login_edittext_height));
		editDate.setLayoutParams(new LayoutParams(150,
				LayoutParams.WRAP_CONTENT));

		TextView durition = new TextView(this);
		durition.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		durition.setText("Durition");
		durition.setBackgroundResource(R.drawable.textlines);
		durition.setTextSize(20);

		EditText editDurition = new EditText(this);
		editDurition.setLayoutParams(new LayoutParams(
				R.dimen.login_edittext_width, R.dimen.login_edittext_height));
		editDurition.setLayoutParams(new LayoutParams(150,
				LayoutParams.WRAP_CONTENT));

		startDateSection.addView(startDate);
		startDateSection.addView(editDate);
		startDateSection.addView(durition);
		startDateSection.addView(editDurition);

		// add all sections
		editNoteLinearLayout.addView(editNotes);
		editNoteLinearLayout.addView(everydaySection);
		editNoteLinearLayout.addView(startDateSection);
		editNoteLinearLayout.setVisibility(View.GONE);
		return editNoteLinearLayout;
	}

	private class MyNoteListener implements TextView.OnClickListener {
		LinearLayout display;
		LinearLayout edit;

		public MyNoteListener(LinearLayout displayNote, LinearLayout editNote) {
			display = displayNote;
			edit = editNote;
		}

		@Override
		public void onClick(View v) {
			display.setVisibility(View.GONE);
			edit.setVisibility(View.VISIBLE);

		}
	}

	private void closePrevious(LinearLayout content, TextView edit,
			LinearLayout editContent) {
		if (previous != null)
			if (!previous.equals(content)) {
				previous.setVisibility(View.GONE);
				previousEditButton.setVisibility(View.GONE);
				previousEditContent.setVisibility(View.GONE);
			}
		if (previous != null)
			if (!previous.equals(content)) {
				previous = content;
				previousEditButton = edit;
				previousEditContent = editContent;
			}
		if (previous == null) {
			previous = content;
			previousEditButton = edit;
			previousEditContent = editContent;
		}
	}

	private class MyListener implements TextView.OnClickListener {
		LinearLayout content;
		LinearLayout editContent;
		TextView editButton;

		public MyListener(LinearLayout current, TextView edit,
				LinearLayout currentEdit) {
			content = current;
			editContent = currentEdit;
			editButton = edit;
		}

		@Override
		public void onClick(View v) {
			if (content.isShown() || editContent.isShown()) {
				content.setVisibility(View.GONE);
				editButton.setVisibility(View.GONE);
				editContent.setVisibility(View.GONE);
			} else {
				content.setVisibility(content.isShown() ? View.GONE
						: View.VISIBLE);
				editButton.setVisibility(editButton.isShown() ? View.GONE
						: View.VISIBLE);
				closePrevious(content, editButton, editContent);
			}

		}
	}

}
