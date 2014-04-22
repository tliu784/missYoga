package com.android.summary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.android.entity.AccountController;
import com.android.entity.AccountModel;
import com.android.service.FileOperation;
import com.android.trend.ChartDataController;
import com.android.trend.ChartPointModel;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.Boolean;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelExporter {

	private String filename = "Health_Record.xls";
	private WritableWorkbook workbook;
	private WritableSheet dataSheet;
	private WritableSheet infoSheet;
	private ChartDataController chartData;
	private File exlFile = null;

	public ExcelExporter(ChartDataController chartData) {
		this.chartData = chartData;
		init();
	}

	private boolean init() {
		exlFile = FileOperation.createExternalFile(filename);
		try {
			workbook = Workbook.createWorkbook(exlFile);
		} catch (IOException e) {
			return false;
		}
		infoSheet = workbook.createSheet("User Info", 0);
		dataSheet = workbook.createSheet("Data", 1);
		return true;
	}

	private boolean finish() {
		try {
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void createTitles(String[] titles, int col, int row, WritableSheet sheet) {
		for (int i = 0; i < titles.length; i++) {
			Label titleCell = new Label(col + i, 0, titles[i]);
			try {
				sheet.addCell(titleCell);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}

	}

	public File export() {
		SimpleDateFormat dobsdf = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.CANADA);

		try {
			// write info
			AccountModel acc = AccountController.getInstance().getAccount();
			int inforow = 0;
			Label NameTitle = new Label(inforow, 0, "Name");
			inforow++;
			Label GenderTitle = new Label(inforow, 0, "Gender");
			inforow++;
			Label DobTitle = new Label(inforow, 0, "Date of Birth");
			inforow++;
			Label HeightTitle = new Label(inforow, 0, "Height");
			inforow++;
			Label WeightTitle = new Label(inforow, 0, "Weight");
			inforow++;
			inforow = 0;
			Label Name = new Label(inforow, 1, acc.getName());
			inforow++;
			String gender = "Male";
			if (acc.isGender())
				gender = "Male";
			else
				gender = "Female";
			Label Gender = new Label(inforow, 1, gender);
			inforow++;
			Label Dob = new Label(inforow, 1, dobsdf.format(acc.getBirthDate()));
			inforow++;
			Number Height = new Number(inforow, 1, acc.getHeight());
			inforow++;
			Number Weight = new Number(inforow, 1, acc.getWeight());
			inforow++;

			infoSheet.addCell(NameTitle);
			infoSheet.addCell(Name);
			infoSheet.addCell(GenderTitle);
			infoSheet.addCell(Gender);
			infoSheet.addCell(DobTitle);
			infoSheet.addCell(Dob);
			infoSheet.addCell(HeightTitle);
			infoSheet.addCell(Height);
			infoSheet.addCell(WeightTitle);
			infoSheet.addCell(Weight);
			// write data
			String[] titles = { "Timestamp", "Heart Rate", "Systolic", "Diastolic", "Activity", "Sleep", "is Sleep" };
			createTitles(titles, 0, 0, dataSheet);
			ArrayList<ChartPointModel> chartpoints = chartData.getDataset();
			int row = 1;
			int col = 0;
			for (ChartPointModel point : chartpoints) {
				Label ts = new Label(col, row, sdf.format(point.getTimestamp()));
				col++;
				Number hr = new Number(col, row, (int) point.getHr());
				col++;
				Number bph = new Number(col, row, (int) point.getBph());
				col++;
				Number bpl = new Number(col, row, (int) point.getBpl());
				col++;
				Number act = new Number(col, row, (int) point.getAct());
				col++;
				Label sleep = new Label(col, row, ChartPointModel.getSleepText(point.getSleep()));
				col++;

				col = 0; // reset
				row++;
				dataSheet.addCell(ts);
				dataSheet.addCell(hr);
				dataSheet.addCell(bph);
				dataSheet.addCell(bpl);
				if (point.isSleep())
					dataSheet.addCell(sleep);
				else
					dataSheet.addCell(act);

			}

			// Write and close the workbook
			finish();
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return null;
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
		return exlFile;
	}

}
