package ai.spoda.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import ai.spoda.pojo.SpodaBody;
import ai.spoda.pojo.SpodaResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestRunnerService {

	private static final String COOKIE_FILE_PATH = System.getProperty("user.dir") + "/Cookie/Cookie.txt";
	private static final String INPUT_EXCEL_PATH = System.getProperty("user.dir") + "/Excel/SpodaDataInput.xlsx";
	private static final String OUTPUT_EXCEL_PATH = System.getProperty("user.dir") + "/Excel/SpodaDataOutput.xlsx";

	public static String getCookie() throws IOException {
		return new String(Files.readAllBytes(Paths.get(COOKIE_FILE_PATH)));
	}

	public static Map<String, String> getRequestResponse(List<String> questions) throws IOException {
		Map<String, String> responses = new HashMap<>();
		int count = 1;
		for (String q : questions) {
			SpodaBody sp = new SpodaBody(q);
			Response response = null;
			SpodaResponse res = null;
			String answer = "";
			try {
				response = RestAssured.given().headers("content-type", "application/json").header("Cookie", getCookie())
						.body(sp).post("https://api.spoda.ai/messages");
				res = response.as(SpodaResponse.class);
				answer = res.getWidgets().getCredit_used() + " = " + response.getTimeIn(TimeUnit.SECONDS) + " = "
						+ res.getWidgets().getWidget_data().get(0).getData();

			} catch (Exception e) {
				answer = 0 + " = " + 0 + " = " + "Server Error in Response";

			}

			System.out.println("Question " + count + " -> " + q + " : answer -> " + answer);
			responses.put(q, answer);
			count++;
		}
		return responses;
	}

	public static void writeToExcelUpdate() throws IOException {
		File file = new File(OUTPUT_EXCEL_PATH);
		file.delete();
		file.createNewFile();
		try (FileInputStream fin = new FileInputStream(file);
				Workbook wb = new XSSFWorkbook();
				FileOutputStream fout = new FileOutputStream(file)) {

			Sheet sheetAt = wb.createSheet("Output");
			Map<String, String> requestResponse = getRequestResponse(getQuestionsFromExcel());
			Row headerRow = sheetAt.createRow(0);

			headerRow.createCell(0).setCellValue("Questions");
			headerRow.createCell(1).setCellValue("Tokens");
			headerRow.createCell(2).setCellValue("Time Taken in seconds");
			headerRow.createCell(3).setCellValue("Answers");

			int rowNum = 1;
			for (Entry<String, String> entry : requestResponse.entrySet()) {
				Row row = sheetAt.createRow(rowNum++);
				String[] parts = entry.getValue().split("=");
				row.createCell(0).setCellValue(entry.getKey());
				row.createCell(1).setCellValue(parts[0].trim());
				row.createCell(2).setCellValue(parts[1].trim());
				row.createCell(3).setCellValue(parts[2].trim());

			}
			wb.write(fout);
			System.out.println("Excel file has been created successfully.");
		}
	}

	public static List<String> getQuestionsFromExcel() throws IOException {
		List<String> questions = new ArrayList<>();
		try (FileInputStream fin = new FileInputStream(INPUT_EXCEL_PATH); Workbook wb = new XSSFWorkbook(fin)) {

			Sheet sheetAt = wb.getSheetAt(0);
			for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {
				Cell cell = sheetAt.getRow(i).getCell(0);
				if (cell.getCellType().equals(CellType.STRING)) {
					questions.add(cell.getStringCellValue());
				} else if (cell.getCellType().equals(CellType.NUMERIC)) {
					double numericCellValue = cell.getNumericCellValue();
					questions.add(String.valueOf((long) numericCellValue));
				}
			}
		}
		return questions;
	}

	@Test
	public void getAns() throws IOException {
		writeToExcelUpdate();
	}
}
