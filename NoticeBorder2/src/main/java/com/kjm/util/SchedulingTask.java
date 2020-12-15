package com.kjm.util;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTask {
	@Scheduled(cron = "0 0 12 * * ?")
	public void DeleteImage (){
		System.out.println("scehdule start");
		String path = "C:\\Temp\\images";
		File folder = new File(path);

		File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

		for (int j = 0; j < folder_list.length; j++) {
			folder_list[j].delete(); // 파일 삭제

		}

		System.out.println("Images Folder Clean");
	}

}
