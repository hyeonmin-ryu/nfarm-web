package kr.re.amc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.MediaType;

import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;

public class ExcelDownLoadUtil {
	public void XLSXWriter(ProductDemandCostPopDto productDemandCostPopDto,DemandSearchDto demandSearchDto, HttpServletResponse response) {

		//엑셀 파일명
		String dateTime = new SimpleDateFormat("HHmmssSSSS").format(new Date());
		String excelFile = demandSearchDto.getOrgName() +  "_" + demandSearchDto.getDemandMonth() + "_" + dateTime + "_" + ".xlsx";
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet("이용내역");

		try {

			for(int i = 0; i < 10; i++) {
				//셀 넓이 조정
				sheet.setColumnWidth(i, 8000);
			}
			
			sheet.setDisplayGridlines(false); //시트 눈금선 제거
			
			//1번 로우 상세 이용 내역
			Font headFont = wb.createFont();
			headFont.setFontHeightInPoints((short)20);
			headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headFont.setFontName("맑은 고딕");
			
			CellStyle row1style = wb.createCellStyle();
			row1style.setAlignment(CellStyle.ALIGN_CENTER);
			row1style.setFont(headFont);
			
			Row rowProvider = sheet.createRow(0);
			Cell cellProvider = null;
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("상세 이용 내역");
			cellProvider.setCellStyle(row1style);
			cellProvider = rowProvider.createCell(1);
			cellProvider = rowProvider.createCell(2);
			cellProvider = rowProvider.createCell(3);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3)); //첫행, 마지막행, 첫열, 마지막열
			
			//3번 로우 공급자(하드코딩)
			Font boldFont = wb.createFont();
			boldFont.setFontHeightInPoints((short)16);
			boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			boldFont.setFontName("맑은 고딕");
			
			Font boldFont11 = wb.createFont();
			boldFont11.setFontHeightInPoints((short)11);
			boldFont11.setBoldweight(Font.BOLDWEIGHT_BOLD);
			boldFont11.setFontName("맑은 고딕");
			
			CellStyle row31style = wb.createCellStyle();
			row31style.setBorderTop(CellStyle.BORDER_THICK);
			row31style.setBorderLeft(CellStyle.BORDER_THICK);
			row31style.setBorderRight(CellStyle.BORDER_THICK);
			row31style.setBorderBottom(CellStyle.BORDER_THIN);
			row31style.setAlignment(CellStyle.ALIGN_CENTER);
			row31style.setFont(boldFont);
			
			CellStyle row32style = wb.createCellStyle();
			row32style.setBorderTop(CellStyle.BORDER_THICK);
			row32style.setBorderLeft(CellStyle.BORDER_THIN);
			row32style.setBorderRight(CellStyle.BORDER_THIN);
			row32style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle row33style = wb.createCellStyle();
			row33style.setBorderTop(CellStyle.BORDER_THICK);
			row33style.setBorderLeft(CellStyle.BORDER_THIN);
			row33style.setBorderRight(CellStyle.BORDER_THIN);
			row33style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle row34style = wb.createCellStyle();
			row34style.setBorderTop(CellStyle.BORDER_THICK);
			row34style.setBorderRight(CellStyle.BORDER_THICK);
			row34style.setBorderBottom(CellStyle.BORDER_THIN);
			
			rowProvider = sheet.createRow(2);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("공급자");
			cellProvider.setCellStyle(row31style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellStyle(row32style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row33style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row34style);
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,3)); //첫행, 마지막행, 첫열, 마지막열
			
			//4번 로우 상호명, 대표자
			CellStyle row41style = wb.createCellStyle();
			row41style.setBorderLeft(CellStyle.BORDER_THICK);
			row41style.setAlignment(CellStyle.ALIGN_CENTER);
			row41style.setFont(boldFont11);
			
			CellStyle row42style = wb.createCellStyle();
			row42style.setBorderLeft(CellStyle.BORDER_THIN);
			row42style.setBorderRight(CellStyle.BORDER_THIN);
			
			CellStyle row43style = wb.createCellStyle();
			row43style.setAlignment(CellStyle.ALIGN_CENTER);
			row43style.setFont(boldFont11);
			
			CellStyle row44style = wb.createCellStyle();
			row44style.setBorderLeft(CellStyle.BORDER_THIN);
			row44style.setBorderRight(CellStyle.BORDER_THICK);
			
			rowProvider = sheet.createRow(3);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("상호명");
			cellProvider.setCellStyle(row41style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgName());
			cellProvider.setCellStyle(row42style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellValue("대표자");
			cellProvider.setCellStyle(row43style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgCeo());
			cellProvider.setCellStyle(row44style);
			
			//5번 로우 등록번호
			CellStyle row51style = wb.createCellStyle();
			row51style.setBorderTop(CellStyle.BORDER_THIN);
			row51style.setBorderLeft(CellStyle.BORDER_THICK);
			row51style.setBorderRight(CellStyle.BORDER_THIN);
			row51style.setBorderBottom(CellStyle.BORDER_THICK);
			row51style.setAlignment(CellStyle.ALIGN_CENTER);
			row51style.setFont(boldFont11);
			
			CellStyle row52style = wb.createCellStyle();
			row52style.setBorderTop(CellStyle.BORDER_THIN);
			row52style.setBorderLeft(CellStyle.BORDER_THIN);
			row52style.setBorderRight(CellStyle.BORDER_THIN);
			row52style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle row53style = wb.createCellStyle();
			row53style.setBorderTop(CellStyle.BORDER_THIN);
			row53style.setBorderLeft(CellStyle.BORDER_THIN);
			row53style.setBorderRight(CellStyle.BORDER_THIN);
			row53style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle row54style = wb.createCellStyle();
			row54style.setBorderTop(CellStyle.BORDER_THIN);
			row54style.setBorderLeft(CellStyle.BORDER_THIN);
			row54style.setBorderRight(CellStyle.BORDER_THICK);
			row54style.setBorderBottom(CellStyle.BORDER_THICK);
			
			rowProvider = sheet.createRow(4);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("등록번호");
			cellProvider.setCellStyle(row51style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgBlNumber().length() == 10 ? 
					productDemandCostPopDto.getSupOrgBlNumber().substring(0, 3) + "-" + productDemandCostPopDto.getSupOrgBlNumber().substring(3, 5) + "-" + productDemandCostPopDto.getSupOrgBlNumber().substring(5) :
						productDemandCostPopDto.getSupOrgBlNumber());
			cellProvider.setCellStyle(row52style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row53style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row54style);
			sheet.addMergedRegion(new CellRangeAddress(4,4,1,3)); //첫행, 마지막행, 첫열, 마지막열
			
			//6번 로우 >> 빈줄
			
			//7번 로우 수신자(하드코딩)
			rowProvider = sheet.createRow(6);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("수신자");
			cellProvider.setCellStyle(row31style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellStyle(row32style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row33style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row34style);
			sheet.addMergedRegion(new CellRangeAddress(6,6,0,3)); //첫행, 마지막행, 첫열, 마지막열
			
			//8번 로우 수신자 상호명, 대표자
			rowProvider = sheet.createRow(7);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("상호명");
			cellProvider.setCellStyle(row41style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getDemOrgName());
			cellProvider.setCellStyle(row42style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellValue("대표자");
			cellProvider.setCellStyle(row43style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellValue(productDemandCostPopDto.getDemOrgCeo());
			cellProvider.setCellStyle(row44style);
			
			//9번 로우 등록번호
			rowProvider = sheet.createRow(8);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("등록번호");
			cellProvider.setCellStyle(row51style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getDemOrgBlNumber().length() == 10 ? 
					productDemandCostPopDto.getDemOrgBlNumber().substring(0, 3) + "-" + productDemandCostPopDto.getDemOrgBlNumber().substring(3, 5) + "-" + productDemandCostPopDto.getDemOrgBlNumber().substring(5) :
						productDemandCostPopDto.getDemOrgBlNumber());
			cellProvider.setCellStyle(row52style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row53style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row54style);
			sheet.addMergedRegion(new CellRangeAddress(8,8,1,3)); //첫행, 마지막행, 첫열, 마지막열
			
			// 해더 정보 구성
			CellStyle style = wb.createCellStyle();
			style.setFillBackgroundColor(HSSFColor.AQUA.index);
			style.setFillForegroundColor(HSSFColor.AQUA.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			//body 헤더 스타일
			CellStyle row101style = wb.createCellStyle();
			row101style.setBorderTop(CellStyle.BORDER_THICK);
			row101style.setBorderLeft(CellStyle.BORDER_THICK);
			row101style.setBorderRight(CellStyle.BORDER_THIN);
			row101style.setBorderBottom(CellStyle.BORDER_THIN);
			row101style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row101style.setFillForegroundColor(HSSFColor.AQUA.index);
			row101style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row101style.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle row102style = wb.createCellStyle();
			row102style.setBorderTop(CellStyle.BORDER_THICK);
			row102style.setBorderLeft(CellStyle.BORDER_THIN);
			row102style.setBorderRight(CellStyle.BORDER_THIN);
			row102style.setBorderBottom(CellStyle.BORDER_THIN);
			row102style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row102style.setFillForegroundColor(HSSFColor.AQUA.index);
			row102style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle row103style = wb.createCellStyle();
			row103style.setBorderTop(CellStyle.BORDER_THICK);
			row103style.setBorderLeft(CellStyle.BORDER_THIN);
			row103style.setBorderRight(CellStyle.BORDER_THIN);
			row103style.setBorderBottom(CellStyle.BORDER_THIN);
			row103style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row103style.setFillForegroundColor(HSSFColor.AQUA.index);
			row103style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle row104style = wb.createCellStyle();
			row104style.setBorderTop(CellStyle.BORDER_THICK);
			row104style.setBorderLeft(CellStyle.BORDER_THIN);
			row104style.setBorderRight(CellStyle.BORDER_THICK);
			row104style.setBorderBottom(CellStyle.BORDER_THIN);
			row104style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row104style.setFillForegroundColor(HSSFColor.AQUA.index);
			row104style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row104style.setAlignment(CellStyle.ALIGN_CENTER);
			
			int bodyStartRow = 10;
			Row row = sheet.createRow(bodyStartRow);
			Cell cell = null;
			
			cell = row.createCell(0);
			cell.setCellValue("구분");
			cell.setCellStyle(row101style);
			cell = row.createCell(1);
			cell.setCellStyle(row102style);
			cell = row.createCell(2);
			cell.setCellStyle(row103style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow,bodyStartRow,0,2)); //첫행, 마지막행, 첫열, 마지막열

			cell = row.createCell(3);
			cell.setCellValue("이용금액");
			cell.setCellStyle(row104style);
			
			Long totUserAmount = 0L;
			
			CellStyle rowBody11style = wb.createCellStyle();
			rowBody11style.setBorderTop(CellStyle.BORDER_THIN);
			rowBody11style.setBorderLeft(CellStyle.BORDER_THICK);
			rowBody11style.setBorderRight(CellStyle.BORDER_THIN);
			rowBody11style.setBorderBottom(CellStyle.BORDER_THIN);
			rowBody11style.setAlignment(CellStyle.ALIGN_CENTER);
			rowBody11style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle rowBody12style = wb.createCellStyle();
			rowBody12style.setBorderTop(CellStyle.BORDER_THIN);
			rowBody12style.setBorderLeft(CellStyle.BORDER_THIN);
			rowBody12style.setBorderRight(CellStyle.BORDER_THIN);
			rowBody12style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle rowBody13style = wb.createCellStyle();
			rowBody13style.setBorderTop(CellStyle.BORDER_THIN);
			rowBody13style.setBorderLeft(CellStyle.BORDER_THIN);
			rowBody13style.setBorderRight(CellStyle.BORDER_THIN);
			rowBody13style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle rowBody14style = wb.createCellStyle();
			rowBody14style.setBorderTop(CellStyle.BORDER_THIN);
			rowBody14style.setBorderLeft(CellStyle.BORDER_THIN);
			rowBody14style.setBorderRight(CellStyle.BORDER_THICK);
			rowBody14style.setBorderBottom(CellStyle.BORDER_THIN);
			rowBody14style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			// 리스트 SIZE 만큼 ROW 생성
			for(int i=0; i< productDemandCostPopDto.getProductDemandCostList().size(); i++) {

				//행 생성
				row = sheet.createRow(i + bodyStartRow + 1);
				
				//1번 셀 하드코딩
				cell = row.createCell(0);
				cell.setCellValue("상품");
				cell.setCellStyle(rowBody11style);
				
				//2번 셀 상품명
				cell = row.createCell(1);
				cell.setCellValue(productDemandCostPopDto.getProductDemandCostList().get(i).getDemandAttributeCodeName());
				cell.setCellStyle(rowBody12style);
				
				//3번 셀
				cell = row.createCell(2);
				sheet.addMergedRegion(new CellRangeAddress(i + bodyStartRow + 1, i + bodyStartRow + 1, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
				cell.setCellStyle(rowBody13style);
				
				//4번 셀 이용요금
				cell = row.createCell(3);
				cell.setCellValue(productDemandCostPopDto.getProductDemandCostList().get(i).getUseAmount());
				cell.setCellStyle(rowBody14style);
				
				totUserAmount = Long.sum(totUserAmount, productDemandCostPopDto.getProductDemandCostList().get(i).getUseAmount());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow + 1, bodyStartRow + productDemandCostPopDto.getProductDemandCostList().size(), 0, 0)); //첫행, 마지막행, 첫열, 마지막열
			
			int cntRow = productDemandCostPopDto.getProductDemandCostList().size() + bodyStartRow + 1;
			
			//당월 소계
			CellStyle rowTot11style = wb.createCellStyle();
			rowTot11style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot11style.setBorderLeft(CellStyle.BORDER_THICK);
			rowTot11style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot11style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle rowTot12style = wb.createCellStyle();
			rowTot12style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot12style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot12style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot12style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle rowTot13style = wb.createCellStyle();
			rowTot13style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot13style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot13style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot13style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle rowTot14style = wb.createCellStyle();
			rowTot14style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot14style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot14style.setBorderRight(CellStyle.BORDER_THICK);
			rowTot14style.setBorderBottom(CellStyle.BORDER_THIN);
			rowTot14style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			row = sheet.createRow(cntRow);
			cell = row.createCell(0);
			cell.setCellValue("당월 소계");
			cell.setCellStyle(rowTot11style);
			cell = row.createCell(1);
			cell.setCellStyle(rowTot12style);
			cell = row.createCell(2);
			cell.setCellStyle(rowTot13style);
			cell = row.createCell(3);
			cell.setCellValue(totUserAmount);
			cell.setCellStyle(rowTot14style);
			sheet.addMergedRegion(new CellRangeAddress(cntRow, cntRow, 0, 2)); //첫행, 마지막행, 첫열, 마지막열
			
			//당월 청구금액
			CellStyle rowTot21style = wb.createCellStyle();
			rowTot21style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot21style.setBorderLeft(CellStyle.BORDER_THICK);
			rowTot21style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot21style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle rowTot22style = wb.createCellStyle();
			rowTot22style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot22style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot22style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot22style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle rowTot23style = wb.createCellStyle();
			rowTot23style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot23style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot23style.setBorderRight(CellStyle.BORDER_THIN);
			rowTot23style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle rowTot24style = wb.createCellStyle();
			rowTot24style.setBorderTop(CellStyle.BORDER_THIN);
			rowTot24style.setBorderLeft(CellStyle.BORDER_THIN);
			rowTot24style.setBorderRight(CellStyle.BORDER_THICK);
			rowTot24style.setBorderBottom(CellStyle.BORDER_THICK);
			rowTot24style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			row = sheet.createRow(cntRow + 1);
			cell = row.createCell(0);
			cell.setCellValue("당월 청구금액");
			cell.setCellStyle(rowTot21style);
			cell = row.createCell(1);
			cell.setCellValue("이용금액(합계)");
			cell.setCellStyle(rowTot22style);
			cell = row.createCell(2);
			cell.setCellStyle(rowTot23style);
			cell = row.createCell(3);
			cell.setCellValue(totUserAmount);
			cell.setCellStyle(rowTot24style);
			sheet.addMergedRegion(new CellRangeAddress(cntRow + 1, cntRow + 1, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
			
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			wb.write(fileOut);
			fileOut.close();
			
			File tempFile = new File(excelFile);

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment;");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        response.getOutputStream().write(FileUtils.readFileToByteArray(tempFile));
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        
	        tempFile.delete();
	        
		} catch(Exception e) {

			e.printStackTrace();

			response.setHeader("Set-Cookie","fileDownload=true; path=/");
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			response.setHeader("Content-Type","text/html; charset=utf-8");

			OutputStream out = null;
			try {

			}catch(Exception ignore) {
				ignore.printStackTrace();
			}finally {
				if(out != null) try {out.close();}catch(Exception ignore) {}
			}
		} finally {
			wb.dispose();
		}
	}
	
	public String adminXLSXWriter(ProductDemandCostPopDto productDemandCostPopDto,DemandSearchDto demandSearchDto,  HttpServletResponse response, String jobGubun) {

		//엑셀 파일명
		String dateTime = new SimpleDateFormat("HHmmssSSSS").format(new Date());
		String excelFile = demandSearchDto.getOrgName() +  "_" + demandSearchDto.getDemandMonth() + "_" + dateTime + "_" + ".xlsx";
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet("이용내역");
		try {

			for(int i = 0; i < 10; i++) {
				//셀 넓이 조정
				sheet.setColumnWidth(i, 5000);
			}
			
			sheet.setDisplayGridlines(false); //시트 눈금선 제거
			
			//1번 로우 상세 이용 내역
			Font headFont = wb.createFont();
			headFont.setFontHeightInPoints((short)20);
			headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headFont.setFontName("맑은 고딕");
			
			CellStyle row1style = wb.createCellStyle();
			row1style.setAlignment(CellStyle.ALIGN_CENTER);
			row1style.setFont(headFont);
			
			Row rowProvider = sheet.createRow(0);
			Cell cellProvider = null;
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("상세 이용 내역");
			cellProvider.setCellStyle(row1style);
			cellProvider = rowProvider.createCell(1);
			cellProvider = rowProvider.createCell(2);
			cellProvider = rowProvider.createCell(3);
			cellProvider = rowProvider.createCell(4);
			cellProvider = rowProvider.createCell(5);
			cellProvider = rowProvider.createCell(6);
			cellProvider = rowProvider.createCell(7);
			cellProvider = rowProvider.createCell(8);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,8)); //첫행, 마지막행, 첫열, 마지막열
			
			//3번 로우 공급자(하드코딩)
			Font boldFont = wb.createFont();
			boldFont.setFontHeightInPoints((short)16);
			boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			boldFont.setFontName("맑은 고딕");
			
			Font boldFont11 = wb.createFont();
			boldFont11.setFontHeightInPoints((short)11);
			boldFont11.setBoldweight(Font.BOLDWEIGHT_BOLD);
			boldFont11.setFontName("맑은 고딕");
			
			CellStyle row31style = wb.createCellStyle();
			row31style.setBorderTop(CellStyle.BORDER_THICK);
			row31style.setBorderLeft(CellStyle.BORDER_THICK);
			row31style.setBorderRight(CellStyle.BORDER_THICK);
			row31style.setBorderBottom(CellStyle.BORDER_THIN);
			row31style.setAlignment(CellStyle.ALIGN_CENTER);
			row31style.setFont(boldFont);
			
			CellStyle row32style = wb.createCellStyle();
			row32style.setBorderTop(CellStyle.BORDER_THICK);
			row32style.setBorderLeft(CellStyle.BORDER_THIN);
			row32style.setBorderRight(CellStyle.BORDER_THIN);
			row32style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle row33style = wb.createCellStyle();
			row33style.setBorderTop(CellStyle.BORDER_THICK);
			row33style.setBorderLeft(CellStyle.BORDER_THIN);
			row33style.setBorderRight(CellStyle.BORDER_THIN);
			row33style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle row34style = wb.createCellStyle();
			row34style.setBorderTop(CellStyle.BORDER_THICK);
			row34style.setBorderRight(CellStyle.BORDER_THICK);
			row34style.setBorderBottom(CellStyle.BORDER_THIN);
			
			rowProvider = sheet.createRow(2);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("공급자");
			cellProvider.setCellStyle(row31style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellStyle(row32style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row33style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row34style);
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,3)); //첫행, 마지막행, 첫열, 마지막열
			
			cellProvider = rowProvider.createCell(5);
			cellProvider.setCellValue("수신자");
			cellProvider.setCellStyle(row31style);
			cellProvider = rowProvider.createCell(6);
			cellProvider.setCellStyle(row32style);
			cellProvider = rowProvider.createCell(7);
			cellProvider.setCellStyle(row33style);
			cellProvider = rowProvider.createCell(8);
			cellProvider.setCellStyle(row34style);
			sheet.addMergedRegion(new CellRangeAddress(2,2,5,8)); //첫행, 마지막행, 첫열, 마지막열
			
			//4번 로우 상호명, 대표자
			CellStyle row41style = wb.createCellStyle();
			row41style.setBorderLeft(CellStyle.BORDER_THICK);
			row41style.setAlignment(CellStyle.ALIGN_CENTER);
			row41style.setFont(boldFont11);
			
			CellStyle row42style = wb.createCellStyle();
			row42style.setBorderLeft(CellStyle.BORDER_THIN);
			row42style.setBorderRight(CellStyle.BORDER_THIN);
			
			CellStyle row43style = wb.createCellStyle();
			row43style.setAlignment(CellStyle.ALIGN_CENTER);
			row43style.setFont(boldFont11);
			
			CellStyle row44style = wb.createCellStyle();
			row44style.setBorderLeft(CellStyle.BORDER_THIN);
			row44style.setBorderRight(CellStyle.BORDER_THICK);
			
			rowProvider = sheet.createRow(3);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("상호명");
			cellProvider.setCellStyle(row41style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgName());
			cellProvider.setCellStyle(row42style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellValue("대표자");
			cellProvider.setCellStyle(row43style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgCeo());
			cellProvider.setCellStyle(row44style);
			
			cellProvider = rowProvider.createCell(5);
			cellProvider.setCellValue("상호명");
			cellProvider.setCellStyle(row41style);
			cellProvider = rowProvider.createCell(6);
			cellProvider.setCellValue(productDemandCostPopDto.getDemOrgName());
			cellProvider.setCellStyle(row42style);
			cellProvider = rowProvider.createCell(7);
			cellProvider.setCellValue("대표자");
			cellProvider.setCellStyle(row43style);
			cellProvider = rowProvider.createCell(8);
			cellProvider.setCellValue(productDemandCostPopDto.getDemOrgCeo());
			cellProvider.setCellStyle(row44style);
			
			//5번 로우 등록번호
			CellStyle row51style = wb.createCellStyle();
			row51style.setBorderTop(CellStyle.BORDER_THIN);
			row51style.setBorderLeft(CellStyle.BORDER_THICK);
			row51style.setBorderRight(CellStyle.BORDER_THIN);
			row51style.setBorderBottom(CellStyle.BORDER_THICK);
			row51style.setAlignment(CellStyle.ALIGN_CENTER);
			row51style.setFont(boldFont11);
			
			CellStyle row52style = wb.createCellStyle();
			row52style.setBorderTop(CellStyle.BORDER_THIN);
			row52style.setBorderLeft(CellStyle.BORDER_THIN);
			row52style.setBorderRight(CellStyle.BORDER_THIN);
			row52style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle row53style = wb.createCellStyle();
			row53style.setBorderTop(CellStyle.BORDER_THIN);
			row53style.setBorderLeft(CellStyle.BORDER_THIN);
			row53style.setBorderRight(CellStyle.BORDER_THIN);
			row53style.setBorderBottom(CellStyle.BORDER_THICK);
			
			CellStyle row54style = wb.createCellStyle();
			row54style.setBorderTop(CellStyle.BORDER_THIN);
			row54style.setBorderLeft(CellStyle.BORDER_THIN);
			row54style.setBorderRight(CellStyle.BORDER_THICK);
			row54style.setBorderBottom(CellStyle.BORDER_THICK);
			
			rowProvider = sheet.createRow(4);
			cellProvider = rowProvider.createCell(0);
			cellProvider.setCellValue("등록번호");
			cellProvider.setCellStyle(row51style);
			cellProvider = rowProvider.createCell(1);
			cellProvider.setCellValue(productDemandCostPopDto.getSupOrgBlNumber().length() == 10 ? 
					productDemandCostPopDto.getSupOrgBlNumber().substring(0, 3) + "-" + productDemandCostPopDto.getSupOrgBlNumber().substring(3, 5) + "-" + productDemandCostPopDto.getSupOrgBlNumber().substring(5) :
						productDemandCostPopDto.getSupOrgBlNumber());
			cellProvider.setCellStyle(row52style);
			cellProvider = rowProvider.createCell(2);
			cellProvider.setCellStyle(row53style);
			cellProvider = rowProvider.createCell(3);
			cellProvider.setCellStyle(row54style);
			sheet.addMergedRegion(new CellRangeAddress(4,4,1,3)); //첫행, 마지막행, 첫열, 마지막열
			
			cellProvider = rowProvider.createCell(5);
			cellProvider.setCellValue("등록번호");
			cellProvider.setCellStyle(row51style);
			cellProvider = rowProvider.createCell(6);
			
			if(StringUtils.isNotEmpty(productDemandCostPopDto.getDemOrgBlNumber())) {
				cellProvider.setCellValue(productDemandCostPopDto.getDemOrgBlNumber().length() == 10 ? 
						productDemandCostPopDto.getDemOrgBlNumber().substring(0, 3) + "-" + productDemandCostPopDto.getDemOrgBlNumber().substring(3, 5) + "-" + productDemandCostPopDto.getDemOrgBlNumber().substring(5) :
							productDemandCostPopDto.getDemOrgBlNumber());
			}

			cellProvider.setCellStyle(row52style);
			cellProvider = rowProvider.createCell(7);
			cellProvider.setCellStyle(row53style);
			cellProvider = rowProvider.createCell(8);
			cellProvider.setCellStyle(row54style);
			sheet.addMergedRegion(new CellRangeAddress(4,4,6,8)); //첫행, 마지막행, 첫열, 마지막열
			
			//6번 로우 >> 빈줄
			
			// 해더 정보 구성
			CellStyle style = wb.createCellStyle();
			style.setFillBackgroundColor(HSSFColor.AQUA.index);
			style.setFillForegroundColor(HSSFColor.AQUA.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			//body 헤더 스타일
			CellStyle row101style = wb.createCellStyle();
			row101style.setBorderTop(CellStyle.BORDER_THICK);
			row101style.setBorderLeft(CellStyle.BORDER_THICK);
			row101style.setBorderRight(CellStyle.BORDER_THIN);
			row101style.setBorderBottom(CellStyle.BORDER_THIN);
			row101style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row101style.setFillForegroundColor(HSSFColor.AQUA.index);
			row101style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row101style.setAlignment(CellStyle.ALIGN_CENTER);
			row101style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle row102style = wb.createCellStyle();
			row102style.setBorderTop(CellStyle.BORDER_THICK);
			row102style.setBorderLeft(CellStyle.BORDER_THIN);
			row102style.setBorderRight(CellStyle.BORDER_THIN);
			row102style.setBorderBottom(CellStyle.BORDER_THIN);
			row102style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row102style.setFillForegroundColor(HSSFColor.AQUA.index);
			row102style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle row103style = wb.createCellStyle();
			row103style.setBorderTop(CellStyle.BORDER_THICK);
			row103style.setBorderLeft(CellStyle.BORDER_THIN);
			row103style.setBorderRight(CellStyle.BORDER_THIN);
			row103style.setBorderBottom(CellStyle.BORDER_THIN);
			row103style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row103style.setFillForegroundColor(HSSFColor.AQUA.index);
			row103style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle row104style = wb.createCellStyle();
			row104style.setBorderTop(CellStyle.BORDER_THICK);
			row104style.setBorderLeft(CellStyle.BORDER_THIN);
			row104style.setBorderRight(CellStyle.BORDER_THIN);
			row104style.setBorderBottom(CellStyle.BORDER_THIN);
			row104style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row104style.setFillForegroundColor(HSSFColor.AQUA.index);
			row104style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row104style.setAlignment(CellStyle.ALIGN_CENTER);
			row104style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle row8col5style = wb.createCellStyle(); //할인금액-프로모션, 약정, 기탕
			row8col5style.setBorderTop(CellStyle.BORDER_THIN);
			row8col5style.setBorderLeft(CellStyle.BORDER_THIN);
			row8col5style.setBorderRight(CellStyle.BORDER_THIN);
			row8col5style.setBorderBottom(CellStyle.BORDER_THIN);
			row8col5style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row8col5style.setFillForegroundColor(HSSFColor.AQUA.index);
			row8col5style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row8col5style.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle row7col9style = wb.createCellStyle(); //당월이용금액
			row7col9style.setBorderTop(CellStyle.BORDER_THICK);
			row7col9style.setBorderLeft(CellStyle.BORDER_THIN);
			row7col9style.setBorderRight(CellStyle.BORDER_THICK);
			row7col9style.setBorderBottom(CellStyle.BORDER_THIN);
			row7col9style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row7col9style.setFillForegroundColor(HSSFColor.AQUA.index);
			row7col9style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			row7col9style.setAlignment(CellStyle.ALIGN_CENTER);
			row7col9style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle row8col1style = wb.createCellStyle();
			row8col1style.setBorderTop(CellStyle.BORDER_THIN);
			row8col1style.setBorderLeft(CellStyle.BORDER_THICK);
			row8col1style.setBorderRight(CellStyle.BORDER_THIN);
			row8col1style.setBorderBottom(CellStyle.BORDER_THIN);
			row8col1style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row8col1style.setFillForegroundColor(HSSFColor.AQUA.index);
			row8col1style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle row8col9style = wb.createCellStyle();
			row8col9style.setBorderTop(CellStyle.BORDER_THIN);
			row8col9style.setBorderLeft(CellStyle.BORDER_THIN);
			row8col9style.setBorderRight(CellStyle.BORDER_THICK);
			row8col9style.setBorderBottom(CellStyle.BORDER_THIN);
			row8col9style.setFillBackgroundColor(HSSFColor.AQUA.index);
			row8col9style.setFillForegroundColor(HSSFColor.AQUA.index);
			row8col9style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			int bodyStartRow = 6;
			Row row = sheet.createRow(bodyStartRow);
			Cell cell = null;
			
			cell = row.createCell(0);
			cell.setCellValue("구분");
			cell.setCellStyle(row101style);
			cell = row.createCell(1);
			cell.setCellStyle(row102style);
			cell = row.createCell(2);
			cell.setCellStyle(row103style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow+1, 0, 2)); //첫행, 마지막행, 첫열, 마지막열

			cell = row.createCell(3);
			cell.setCellValue("이용금액");
			cell.setCellStyle(row104style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow+1, 3, 3)); //첫행, 마지막행, 첫열, 마지막열
			
			cell = row.createCell(4);
			cell.setCellValue("할인금액");
			cell.setCellStyle(row104style);
			cell = row.createCell(5);
			cell.setCellStyle(row104style);
			cell = row.createCell(6);
			cell.setCellStyle(row104style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow, 4, 6)); //첫행, 마지막행, 첫열, 마지막열
			
			cell = row.createCell(7);
			cell.setCellValue("상품할인");
			cell.setCellStyle(row104style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow+1, 7, 7)); //첫행, 마지막행, 첫열, 마지막열
			
			cell = row.createCell(8);
			cell.setCellValue("당월이용금액");
			cell.setCellStyle(row7col9style);
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow+1, 8, 8)); //첫행, 마지막행, 첫열, 마지막열
			
			row = sheet.createRow(bodyStartRow+1);
			cell = row.createCell(0);
			cell.setCellStyle(row8col1style);
			cell = row.createCell(3);
			cell.setCellStyle(row103style);
			cell = row.createCell(4);
			cell.setCellValue("프로모션");
			cell.setCellStyle(row8col5style);
			cell = row.createCell(5);
			cell.setCellValue("약정");
			cell.setCellStyle(row8col5style);
			cell = row.createCell(6);
			cell.setCellValue("기타");
			cell.setCellStyle(row8col5style);
			cell = row.createCell(7);
			cell.setCellStyle(row103style);
			cell = row.createCell(8);
			cell.setCellStyle(row8col9style);
			
			CellStyle bodyCol1Style = wb.createCellStyle();
			bodyCol1Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyCol1Style.setBorderLeft(CellStyle.BORDER_THICK);
			bodyCol1Style.setBorderRight(CellStyle.BORDER_THIN);
			bodyCol1Style.setBorderBottom(CellStyle.BORDER_THIN);
			bodyCol1Style.setAlignment(CellStyle.ALIGN_LEFT);
			bodyCol1Style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle bodyCol2Style = wb.createCellStyle();
			bodyCol2Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyCol2Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyCol2Style.setBorderRight(CellStyle.BORDER_THIN);
			bodyCol2Style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle bodyCol3Style = wb.createCellStyle();
			bodyCol3Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyCol3Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyCol3Style.setBorderRight(CellStyle.BORDER_THIN);
			bodyCol3Style.setBorderBottom(CellStyle.BORDER_THIN);
			
			CellStyle bodyCol4Style = wb.createCellStyle(); //이용금액~상품할인
			bodyCol4Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyCol4Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyCol4Style.setBorderRight(CellStyle.BORDER_THIN);
			bodyCol4Style.setBorderBottom(CellStyle.BORDER_THIN);
			bodyCol4Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			CellStyle bodyCol9Style = wb.createCellStyle(); //이용금액~상품할인
			bodyCol9Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyCol9Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyCol9Style.setBorderRight(CellStyle.BORDER_THICK);
			bodyCol9Style.setBorderBottom(CellStyle.BORDER_THIN);
			bodyCol9Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			CellStyle bodyLastStyle = wb.createCellStyle();
			bodyLastStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyLastStyle.setBorderLeft(CellStyle.BORDER_THICK);
			bodyLastStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyLastStyle.setBorderBottom(CellStyle.BORDER_THICK);
			bodyLastStyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			CellStyle bodyTotCol4Style = wb.createCellStyle();
			bodyTotCol4Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyTotCol4Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyTotCol4Style.setBorderBottom(CellStyle.BORDER_THIN);
			bodyTotCol4Style.setAlignment(CellStyle.ALIGN_RIGHT);
			bodyTotCol4Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			CellStyle bodyLastCol4Style = wb.createCellStyle(); //이용금액~상품할인
			bodyLastCol4Style.setBorderTop(CellStyle.BORDER_THIN);
			bodyLastCol4Style.setBorderLeft(CellStyle.BORDER_THIN);
			bodyLastCol4Style.setBorderBottom(CellStyle.BORDER_THICK);
			bodyLastCol4Style.setAlignment(CellStyle.ALIGN_RIGHT);
			bodyLastCol4Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			
			bodyStartRow += 2;
			
			int cntFor = 0;
			
			// 리스트 SIZE 만큼 ROW 생성
			for(ProductDemandCostDto productDemandCostDto : productDemandCostPopDto.getProductDemandCostList()) {

				//행 생성
				row = sheet.createRow(cntFor + bodyStartRow);
				
				if(productDemandCostDto.getLoginId().equals("TOTAL")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellValue("당월 소계");
					cell.setCellStyle(bodyCol1Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 0, 2)); //첫행, 마지막행, 첫열, 마지막열
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellValue(productDemandCostDto.getPromotionDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellValue(productDemandCostDto.getPromiseDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellValue(productDemandCostDto.getEtcDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellValue(productDemandCostDto.getProductDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellValue(productDemandCostDto.getThisMonthAmount());
					cell.setCellStyle(bodyCol9Style);
				} else if (productDemandCostDto.getLoginId().equals("TOT01")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellValue("당월 청구금액");
					cell.setCellStyle(bodyCol1Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow + 3, 0, 0)); //첫행, 마지막행, 첫열, 마지막열
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellValue(productDemandCostDto.getDemandAttributeCodeName());
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyTotCol4Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 4, 8)); //첫행, 마지막행, 첫열, 마지막열
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellStyle(bodyCol9Style);
					
				} else if (productDemandCostDto.getLoginId().equals("TOT02")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellStyle(bodyCol1Style);
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellValue(productDemandCostDto.getDemandAttributeCodeName());
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyTotCol4Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 4, 8)); //첫행, 마지막행, 첫열, 마지막열
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellStyle(bodyCol9Style);
					
				} else if (productDemandCostDto.getLoginId().equals("TOT03")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellStyle(bodyCol1Style);
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellValue(productDemandCostDto.getDemandAttributeCodeName());
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyTotCol4Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 4, 8)); //첫행, 마지막행, 첫열, 마지막열
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellStyle(bodyCol9Style);
				} else if (productDemandCostDto.getLoginId().equals("TOT04")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellStyle(bodyCol1Style);
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellValue(productDemandCostDto.getDemandAttributeCodeName());
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyTotCol4Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 4, 8)); //첫행, 마지막행, 첫열, 마지막열
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellStyle(bodyCol9Style);
				} else if (productDemandCostDto.getLoginId().equals("TOT05")) {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellValue("총 청구금액(부가세 포함)");
					cell.setCellStyle(bodyLastStyle);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 0, 2)); //첫행, 마지막행, 첫열, 마지막열
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellStyle(row52style);
					
					//3번 셀
					cell = row.createCell(2);
					cell.setCellStyle(row52style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyLastCol4Style);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 4, 8)); //첫행, 마지막행, 첫열, 마지막열
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellStyle(row52style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellStyle(row52style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellStyle(row52style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellStyle(row52style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellStyle(row54style);
				} else {
					//1번 셀 하드코딩
					cell = row.createCell(0);
					cell.setCellValue("상품");
					cell.setCellStyle(bodyCol1Style);
					
					//2번 셀 상품명
					cell = row.createCell(1);
					cell.setCellValue(productDemandCostDto.getDemandAttributeCodeName());
					cell.setCellStyle(bodyCol2Style);
					
					//3번 셀
					cell = row.createCell(2);
					sheet.addMergedRegion(new CellRangeAddress(cntFor + bodyStartRow, cntFor + bodyStartRow, 1, 2)); //첫행, 마지막행, 첫열, 마지막열
					cell.setCellStyle(bodyCol3Style);
					
					//4번 셀 이용요금
					cell = row.createCell(3);
					cell.setCellValue(productDemandCostDto.getUseAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//5번 셀 할인금액 프로모션
					cell = row.createCell(4);
					cell.setCellValue(productDemandCostDto.getPromotionDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//6번 셀 할인금액 약정
					cell = row.createCell(5);
					cell.setCellValue(productDemandCostDto.getPromiseDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//7번 셀 할인금액 기타
					cell = row.createCell(6);
					cell.setCellValue(productDemandCostDto.getEtcDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//8번 셀 상품할인
					cell = row.createCell(7);
					cell.setCellValue(productDemandCostDto.getProductDiscountAmount());
					cell.setCellStyle(bodyCol4Style);
					
					//9번 셀 상품할인
					cell = row.createCell(8);
					cell.setCellValue(productDemandCostDto.getThisMonthAmount());
					cell.setCellStyle(bodyCol9Style);
				}
				
				cntFor++;
			}
			
			sheet.addMergedRegion(new CellRangeAddress(bodyStartRow, bodyStartRow + productDemandCostPopDto.getProductDemandCostList().size() - 7, 0, 0)); //첫행, 마지막행, 첫열, 마지막열
			
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			wb.write(fileOut);
			fileOut.close();
			
			if(jobGubun.equals("sendmail")) {
			} else if(jobGubun.equals("download")) {
				
				File tempFile = new File(excelFile);
				
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
				response.setHeader("Content-Disposition", "attachment;");
		        response.setHeader("Content-Transfer-Encoding", "binary");
		        response.getOutputStream().write(FileUtils.readFileToByteArray(tempFile));
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
		        
		        tempFile.delete();
			}

		} catch(Exception e) {

			e.printStackTrace();

			response.setHeader("Set-Cookie","fileDownload=true; path=/");
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			response.setHeader("Content-Type","text/html; charset=utf-8");

			OutputStream out = null;
			try {

			}catch(Exception ignore) {
				ignore.printStackTrace();
			}finally {
				if(out != null) try {out.close();}catch(Exception ignore) {}
			}
		} finally {
			wb.dispose();
		}
		
		return excelFile;
	}
	
	
	public void gridToExcel(ArrayList<ProductDemandCostDto> ListExcel, String[] cellHeader, DemandSearchDto demandSearchDto, HttpServletResponse response) {

		//엑셀 파일명
		String dateTime = new SimpleDateFormat("HHmmssSSSS").format(new Date());
		String excelFile = "이용내역_" + demandSearchDto.getDemandMonth() + "_" + dateTime + "_" + ".xlsx";
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet("이용내역");
		
		


			// 행
			Row row = null;
			// 셀
			Cell cell = null;				
			// 샐 스타일
			CellStyle styleMoneyFormat = null;	
			// 셀 헤더 카운트
			int index = 0;								
			// 행 카운트
			

			try {

				CellStyle style = wb.createCellStyle();
				style.setFillBackgroundColor(HSSFColor.AQUA.index);
				style.setFillForegroundColor(HSSFColor.AQUA.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setAlignment(CellStyle.ALIGN_CENTER);
				
				//셀 칼럼 크기 설정
				// 행 생성
				row = sheet.createRow(0);
				
				// 셀 스타일 생성
				styleMoneyFormat = wb.createCellStyle();
				CreationHelper ch = wb.getCreationHelper();
				styleMoneyFormat.setDataFormat(ch.createDataFormat().getFormat("#,##0"));
				// 헤더 적용
				for(String head : cellHeader ) {
					
					cell = row.createCell(index++);
					cell.setCellValue(head);
					cell.setCellStyle(style);
				}

				int idxRow = 0;
				
				
		    	for (ProductDemandCostDto objArr : ListExcel) {
		    		
		    		row = sheet.createRow(1+idxRow);
		    		idxRow++;
		    		int idx = 0;
		    		
		    		
		    		int ln2 = 20*256;
	    	    	
	    	    	int ln = 0;
	    	    	
	    	    	if( objArr.getNcloudId() != null && !"0".equals(objArr.getNcloudId()) ) {
	    	    		ln  =	 (objArr.getNcloudId().toString().length()+10)*256 ;
	    	    	}
	    	    	sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getNcloudId());
		    		
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getInsttNm() != null && !"0".equals(objArr.getInsttNm()) ) {
	    	    		ln  =	 (objArr.getInsttNm().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getInsttNm());
		    		
		    		
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getChargerNm() != null && !"0".equals(objArr.getChargerNm()) ) {
	    	    		ln  =	 (objArr.getChargerNm().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getChargerNm());
		    		
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getDtbxNm() != null && !"0".equals(objArr.getDtbxNm()) ) {
	    	    		ln  =	 (objArr.getDtbxNm().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getDtbxNm());
		    		
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getMonbet() != null && !"0".equals(objArr.getMonbet()) ) {
	    	    		ln  =	 (objArr.getMonbet().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getMonbet());
		    	
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getUseAmount() != null && !"0".equals(objArr.getUseAmount()) ) {
	    	    		ln  =	 (objArr.getUseAmount().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getUseAmount());
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getNcloudDscntAmount() != null && !"0".equals(objArr.getNcloudDscntAmount()) ) {
	    	    		ln  =	 (objArr.getNcloudDscntAmount().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getNcloudDscntAmount());
		    		
		    		
		    		ln2 =  20*256;
		    		ln = 0;
		    		if( objArr.getTotRqestAdupAmount() != null && !"0".equals(objArr.getTotRqestAdupAmount()) ) {
	    	    		ln  =	 (objArr.getTotRqestAdupAmount().toString().length()+10)*256 ;
	    	    	}
		    		sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
		    		cell = row.createCell(idx++);
		    		cell.setCellValue(objArr.getTotRqestAdupAmount());
		    	   /* for (ProductDemandCostDto obj : objArr) {
						
		    	    	int ln2 = 20*256;
		    	    	
		    	    	int ln = 0;
		    	    	
		    	    	if( obj != null && !"0".equals(obj) ) {
		    	    		ln  =	 (obj.toString().length()+10)*256 ;
		    	    	}
		    	    	
		    	    	sheet.setColumnWidth(idx,   ln > ln2 ? ln : ln2);
						cell = row.createCell(idx++);
						cell.setCellValue(obj+"");
		    	    }*/
		    	}
				
			
			
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			wb.write(fileOut);
			fileOut.close();
			
			File tempFile = new File(excelFile);

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment;");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        response.getOutputStream().write(FileUtils.readFileToByteArray(tempFile));
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        
	        tempFile.delete();
	        
		} catch(Exception e) {

			e.printStackTrace();

			response.setHeader("Set-Cookie","fileDownload=true; path=/");
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			response.setHeader("Content-Type","text/html; charset=utf-8");

			OutputStream out = null;
			try {

			}catch(Exception ignore) {
				e.printStackTrace();
			}finally {
				if(out != null) try {out.close();}catch(Exception ignore) {}
			}
		} finally {
			wb.dispose();
		}
	}
}
