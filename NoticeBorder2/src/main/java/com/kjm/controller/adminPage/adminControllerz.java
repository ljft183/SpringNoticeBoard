package com.kjm.controller.adminPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kjm.service.BoardService;
import com.kjm.vo.BoardVO;
import com.kjm.vo.UserVO;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Controller
public class adminControllerz {
	
	
	@Inject
	private BoardService service;

	//notice_board, user_board 리스트 출력
	@RequestMapping(value="/ap/getList.do")	
	public ModelAndView getList()throws Exception{
		ModelAndView mav = new ModelAndView();
		List<BoardVO> listA = service.listAll();
		List<UserVO> listB = service.userList();
		mav.addObject("lists", listA);
		mav.addObject("userList", listB);
		
		mav.setViewName("/sboard/ap/adminPage");
		return mav;		
	}
	
	//게시글 추가	
	@RequestMapping(value="/ap/getListAdd.do")	
	public String getListAdd(BoardVO vo)throws Exception{
		service.registAdmin(vo);
		return "redirect:/ap/getList.do";
	}

	//게시글 수정
	@RequestMapping(value="/ap/getListModify.do")	
	public String getListModify(BoardVO vo)throws Exception{
		service.modifyAdmin(vo);
		return "redirect:/ap/getList.do";
	}

	//게시글 삭제
	@RequestMapping(value="/ap/getListDelete.do")	
	public String getListDelete(String delBnoArray)throws Exception{
		String[] strArray = delBnoArray.split(",");
		
		for(int i =0; i<strArray.length; i++) {
			
			service.remove(Integer.parseInt(strArray[i]));
		}
		
		return "redirect:/ap/getList.do";
	}
	
	//notice_board 게시글 엑셀 형식으로 다운로드
	@RequestMapping(value = "/ap/getListExcel.do" )	
	public String downloadExcelFile(HttpServletResponse response) throws Exception{
		List<BoardVO> excelList = service.listAll();
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy.MM.dd.HH.mm.ss");
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		//SimpleDateFormat format2 = new SimpleDateFormat ( "ddHHmmss");
		Date time = new Date();
		String time1 = format.format(time);
		
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Notice_Board");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
	    row = sheet.createRow(rowNo++);

	    cell = row.createCell(0);

	    cell.setCellValue("BNO");

	    cell = row.createCell(1);

	    cell.setCellValue("Title");

	    cell = row.createCell(2);

	    cell.setCellValue("Content");

	    cell = row.createCell(3);

	    cell.setCellValue("Writer");
	    
	    cell = row.createCell(4);

	    cell.setCellValue("Viewcnt");
	    
	    cell = row.createCell(5);

	    cell.setCellValue("Regdate");

		
		for(BoardVO vo : excelList) {
			row = sheet.createRow(rowNo++);
			
			cell=row.createCell(0);
			cell.setCellValue(vo.getBno());
			
			cell=row.createCell(1);
			cell.setCellValue(vo.getTitle());

			cell=row.createCell(2);
			cell.setCellValue(vo.getContent());
			
			cell=row.createCell(3);
			cell.setCellValue(vo.getWriter());
			
			cell=row.createCell(4);
			cell.setCellValue(vo.getViewcnt());
			
			cell=row.createCell(5);
			cell.setCellValue(format1.format(vo.getRegdate()));
		}
		
    	String path = "C:/excelTest/"; //경로
    	String fileName = time1 + ".xls"; //파일명
    	//String time2= format.format(time);
		
        try {

            File xlsFile = new File(path+fileName); //저장경로 설정
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		wb.close();
		
		String compressedFile = path + time1 + ".zip";
		try {
			ZipFile zipFile = new ZipFile(compressedFile);
			File inputFileH = new File(path+fileName);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);

            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword("1234");
				
			// file compressed
			zipFile.addFile(inputFileH, parameters);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/ap/getList.do";
	}
	
	//다운받은 notice_board의 엑셀 파일 업로드로 게시글 추가
	@RequestMapping(value="/ap/getListAddExcel.do")	
	public String getListAddExcel(MultipartHttpServletRequest request) throws Exception{
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
  
        try {
        	Workbook workbook = WorkbookFactory.create(file.getInputStream());

            // 첫번째 시트 불러오기
            Sheet sheet = workbook.getSheetAt(0);
            
            for(int i=sheet.getLastRowNum(); i>=1; i--) {
                BoardVO vo = new BoardVO();
                Row row = sheet.getRow(i);
                
                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }
                
                Cell cell = row.getCell(1);
                vo.setTitle(cell.getStringCellValue());
                cell = row.getCell(2);
                vo.setContent(cell.getStringCellValue());
                cell = row.getCell(3);
                vo.setWriter(cell.getStringCellValue());
                
                service.regist(vo, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/ap/getList.do";
	
	}

	//유저 정보 수정(권한,유저 사용가능 여부 등)
	@RequestMapping(value="/ap/userModify.do")	
	public String userModify(UserVO vo)throws Exception{
		service.modifyUser2(vo);
		return "redirect:/ap/getList.do";
	}
	
	//유저 삭제
	@RequestMapping(value="/ap/userDelete.do")	
	public String userDelete(@RequestParam String delBnoArray2)throws Exception{
		if(delBnoArray2==null||delBnoArray2.equals("")) {
			
			return "redirect:/ap/getList.do";
		}
		else {
			String[] strArray = delBnoArray2.split(",");
			
			for(int i =0; i<strArray.length; i++) {
				service.userDelete(strArray[i]);
			}
			
			return "redirect:/ap/getList.do";
		}

	}
	
	

	
}
