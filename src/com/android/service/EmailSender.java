package com.android.service;

import java.io.File;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class EmailSender {
	private Context context;
	private String toEmail;
	private String subject;
	private String bodyText;
	private File attachment;
	

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public EmailSender(Context context) {
		super();
		this.context = context;
	}

	public void send() {
		File file = attachment;
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, bodyText);
		if (!file.exists()) {
			Toast.makeText(context, "Attachment not exist", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!file.canRead()) {
			Toast.makeText(context, "Attachment can't be read", Toast.LENGTH_SHORT).show();
			return;
		}
		Uri uri = Uri.parse("file://" + file);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		context.startActivity(Intent.createChooser(intent, "Send email..."));
	}
}
