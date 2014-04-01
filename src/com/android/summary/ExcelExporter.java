package com.android.summary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

	private String filename = "terry_excel.xls";
	private WritableWorkbook workbook;
	private WritableSheet dataSheet;
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

		dataSheet = workbook.createSheet("First", 0);
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
		try {

			String[] titles = { "Timestamp", "Heart Rate", "Systolic", "Diastolic", "Activity", "Sleep", "is Sleep" };
			createTitles(titles, 0, 0, dataSheet);
			ArrayList<ChartPointModel> chartpoints = chartData.getDataset();
			int row = 1;
			int col =0;
			for (ChartPointModel point : chartpoints) {
				DateTime ts = new DateTime(col, row, point.getTimestamp());
				col++;
				Number hr = new Number(col, row, point.getHr());
				col++;
				Number bph = new Number(col, row, point.getBph());
				col++;
				Number bpl = new Number(col, row, point.getBpl());
				col++;
				Number act = new Number(col, row, point.getAct());
				col++;
				Number sleep = new Number(col, row, point.getSleep());
				col++;
				Boolean isSleep = new Boolean(col, row, point.isSleep());
				col=0; //reset
				row++;
				dataSheet.addCell(ts);
				dataSheet.addCell(hr);
				dataSheet.addCell(bph);
				dataSheet.addCell(bpl);
				dataSheet.addCell(act);
				dataSheet.addCell(sleep);
				dataSheet.addCell(isSleep);
				
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
