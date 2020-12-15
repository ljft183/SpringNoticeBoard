package com.kjm.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kjm.vo.BoardVO;

@Component
public class fileUtil {
	String filePath = "C:\\Temp\\file\\";
	
	public List<Map<String,Object>> parseInsertFileInfo(BoardVO vo,HttpServletRequest req) throws Exception{
		List<Map<String,Object>> fileList = new ArrayList<Map<String,Object>>();
		Map<String,Object> fileMap = null;
		
		MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest)req;
		
		MultipartFile mulFile = null;
        Iterator<String> iterator= mulReq.getFileNames();

        String original_Name=null;
        String original_Extension=null;
        String stored_Name=null;
        
        UUID uuid = UUID.randomUUID();
        
        int bno = vo.getBno();
        
        
		
        File file = new File(filePath);
        if(file.exists()==false) {
            file.mkdirs();
        }
        
        
        while(iterator.hasNext()) {
            mulFile=mulReq.getFile(iterator.next());
             
            if(mulFile.isEmpty()==false) {
                original_Name=mulFile.getOriginalFilename();
                original_Extension=mulFile.getOriginalFilename().substring(original_Name.lastIndexOf("."));
                stored_Name=uuid.toString()+original_Extension;
                 
                file = new File(filePath+stored_Name);
                mulFile.transferTo(file);
                 
                fileMap = new HashMap<String,Object>();
                 
                fileMap.put("bno", bno);
                fileMap.put("filename", original_Name);
                fileMap.put("uuid", stored_Name);
                fileMap.put("filesize", mulFile.getSize());
                fileList.add(fileMap);
            }
        }       
        
        
        
        return fileList;
	}
	
	public void deleteFile(Map<String,Object> map) throws Exception{
		String uuid = (String) map.get("uuid");
		String filename = (String) map.get("filenmae");
		String fileFullPath = filePath + uuid;
		File file = new File(fileFullPath);
		if(file.exists()) {
			if(file.delete()) {
				System.out.println(filename+"삭제 완료");
			}
			else {
				System.out.println(filename+"삭제 실패");
			}
		}
		else {
			System.out.println("파일이 존재하지 않습니다.");
		}
	}
}
