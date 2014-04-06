package com.android.remoteProfile;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.myhealthmate.R;
import com.android.trend.RecordModel.recordType;

public class UserApprovalSection {

	TextView name;
	TextView email;
	Button approveButton;
	TextView nameContent;
	TextView emailContent;
	Button cancelButton;
	LinearLayout layout;
	LinearLayout nameLayout;
	LinearLayout emailLayout;
	TextView line;
	Context context;
	RemoteRequestModel requestUserModel;
	boolean approved;
	int topPadding = 5;
	int padding = 10;
	
	public UserApprovalSection(Context context, RemoteRequestModel requestModel){
		name = new TextView(context);
		email = new TextView(context);
		approveButton = new Button(context);
		nameContent = new TextView(context);
		emailContent = new TextView(context);
		cancelButton = new Button(context);
		layout = new LinearLayout(context);
		nameLayout = new LinearLayout(context);
		emailLayout = new LinearLayout(context);
		line = new TextView(context);
		this.context = context;
		this.requestUserModel = requestModel;
		this.approved = requestModel.isApproved();
		setFormat();
	}
	
	private void setFormat() {

		setLayoutFomat(layout);
		layout.setBackgroundResource(R.drawable.textlines);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setClickable(true);

		addNameLayout();
		setLine();
		addEmailLayout();
		addButton();
	}
	
	private void addNameLayout(){
		
		setLayoutFomat(nameLayout);
		
		setTextViewFomat(this.name,R.string.profile_name);
		name.setTypeface(null,Typeface.BOLD);
		
		setTextViewFomat(this.nameContent,requestUserModel.getOwnerName());
			
		nameLayout.addView(name);
		nameLayout.addView(nameContent);
		layout.addView(nameLayout);
	}
	
	private void setLine(){
		
		line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
		line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
		layout.addView(line);
	}
	
	private void addEmailLayout(){

		setLayoutFomat(emailLayout);
		
		setTextViewFomat(this.email,R.string.account);
		email.setTypeface(null,Typeface.BOLD);
		
		setTextViewFomat(this.emailContent,requestUserModel.getRequestorEmail());
			
		emailLayout.addView(email);
		emailLayout.addView(emailContent);
		layout.addView(emailLayout);
	}
	
	public View getLayout() {
		return layout;
	}

	public void setLayout(LinearLayout layout) {
		this.layout = layout;
	}

	private void addButton(){
		
	}
	
	private void  setTextViewFomat(TextView textView, int rRes){
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setTextColor(context.getResources().getColor(R.color.black));
		textView.setText(rRes);
	}
	
	private void  setTextViewFomat(TextView textView, String rRes){
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setTextColor(context.getResources().getColor(R.color.black));
		textView.setText(rRes);
	}
	
	private void  setLayoutFomat(LinearLayout linearLayout){
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(padding, topPadding, padding, padding);
	}

}
