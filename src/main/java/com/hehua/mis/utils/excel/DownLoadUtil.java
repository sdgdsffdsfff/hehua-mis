package com.hehua.mis.utils.excel;

import com.hehua.mis.utils.DateTimeUtil;
import com.hehua.mis.utils.MisConstants;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 
 * <p>
 * 项目名称：renren-profile
 * 
 * <p>
 * 类名称：DownLoadUtil
 * 
 * <p>
 * 类描述： 下载工具类
 * 
 * <p>
 * 创建人：wen.he1@renren-inc.com
 * 
 * <p>
 * 创建时间：2012-5-9 上午11:44:09
 * 
 * <p>
 * @version 1.0
 */
public class DownLoadUtil {

	@SuppressWarnings("unchecked")
	public static void downLoad(List downList, String fileName, HttpServletResponse httpResponse, String downLoadId) {
		// 获取列对象集合
		httpResponse.reset();
		List columnList = new ArrayList();
		try {
			// 初始化参数
			DownloadBean downloadBean = DownloadContentHelper.getDownloadBean(downLoadId);
			columnList = downloadBean.getAttributes();
		} catch (Exception e) {

		}
		OutputStream output = null;
		try {
			output = httpResponse.getOutputStream();
			httpResponse.setContentType("application/vnd.ms-excel; charset=GBK");
			httpResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
			WritableWorkbook w = Workbook.createWorkbook(output);
			output.flush();
			// 分sheet页操作start
			List<Object> newTotals = new ArrayList<Object>();
			Iterator itTotals = downList.iterator();
			while (itTotals.hasNext()) {
				newTotals.add(itTotals.next());
			}
			int sheetNum = 0;
			while (newTotals.size() > MisConstants.MAXNUM_FOR_PER_SHEET) {
				WritableSheet ws = w.createSheet("Page" + (sheetNum + 1), sheetNum);
				addLabelExcel(ws, newTotals.subList(0, MisConstants.MAXNUM_FOR_PER_SHEET), columnList);
				newTotals.subList(0, MisConstants.MAXNUM_FOR_PER_SHEET).clear();
				sheetNum = sheetNum + 1;
			}
			WritableSheet ws = w.createSheet("Page" + (sheetNum + 1), sheetNum);
			addLabelExcel(ws, newTotals, columnList);
			// 分sheet页操作end
			w.write();
			// 关闭Excel工作薄对象
			w.close();
			if(output!=null)
				output.close();
			
		} catch (Exception e) {
			if(output!=null)
				try {
					output.close();
				} catch (IOException e1) {
				}
			e.printStackTrace();
		}
	}

	/**
	 * 业务逻辑实现 - 点击统计下载excel
	 * 
	 * @param ws
	 *            工作表
	 * @param totals
	 *            待写入数据
	 * @throws WriteException
	 */
	@SuppressWarnings("unchecked")
	private static void addLabelExcel(WritableSheet ws, List totals, List columnList) throws WriteException {
		int row = 0, column = 0;
		// 设置Excel列中文标题
		Iterator itColumnList = columnList.iterator();
		while (itColumnList.hasNext()) {
			DownloadAttribute columnTitle = (DownloadAttribute) itColumnList.next();
			ws.addCell(new Label(column, row, columnTitle.getExcelName()));
			column++;
		}
		if (totals != null && totals.size() != 0) {
			Iterator itTotals = totals.iterator();
			WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			NumberFormat pnf = new NumberFormat("0.00"); // 用于Price Number的格式
			WritableCellFormat priceFormat = new WritableCellFormat(detFont, pnf);
			NumberFormat nf = new NumberFormat("0"); // 用于Number的格式
			WritableCellFormat numberFormat = new WritableCellFormat(detFont, nf);

			WritableFont stringFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat stringFormat = new WritableCellFormat(stringFont);
			WritableCellFormat stringWrapFormat = new WritableCellFormat(stringFont);
			stringWrapFormat.setWrap(true);

			row = 1;
			while (itTotals.hasNext()) {
				Object obj = itTotals.next();
				Map subTotals = obj instanceof Map ? (HashMap) obj : ObjectUtil.transform(obj);
				Iterator itColumnName = columnList.iterator();
				column = 0;
				while (itColumnName.hasNext()) {
					DownloadAttribute columnInfo = (DownloadAttribute) itColumnName.next();
					String columnType = columnInfo.getExcelType();
					String columnName = columnInfo.getRowName();
					Object columnValue = subTotals.get(columnName);
					if (columnValue == null) {
						column++;
						continue;
					}
					String strValue = null;
					if (columnValue instanceof Date) {
						strValue = DateTimeUtil.getFormatDateTime((Date) columnValue);
					} else {
						strValue = columnValue.toString();
					}
					// 设置内容
					if (columnType.equals("Label")) {
						if (columnValue.toString().indexOf("\r\n") > 0) {
							ws.addCell(new Label(column, row, strValue, stringWrapFormat));
						} else {
							ws.addCell(new Label(column, row, strValue, stringFormat));
						}
					}
					if (columnType.equals("Number"))
						ws.addCell(new jxl.write.Number(column, row, Double.parseDouble(strValue), numberFormat));
					if (columnType.equals("Price"))
						ws.addCell(new jxl.write.Number(column, row, Double.parseDouble(strValue), priceFormat));
					column++;
				}
				row++;
			}
		}
	}
	
}