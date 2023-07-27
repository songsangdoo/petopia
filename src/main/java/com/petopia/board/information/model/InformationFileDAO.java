package com.petopia.board.information.model;

import java.io.File;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.information.mapper.InformationFileMapper;

@Repository
@MapperScan("com.petopia.board.information.mapper")
public class InformationFileDAO {
	
	@Autowired
	private InformationFileMapper mapper;
	
	public List<InformationFileTO> view(InformationFileTO to){
		List<InformationFileTO> datas = mapper.list(to);
		
		return datas;
	}
	
	public int writeOk(InformationFileTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(InformationFileTO to) {
		int flag = 1;
		
		List<InformationFileTO> datas = mapper.delete(to);
		
		for(InformationFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getInfo_file_img_path());
			
			if(!file.delete()) {
				System.out.println("파일 삭제 실패");
				return flag;
			}else {
				System.out.println("파일 삭제 성공");
			}
		}
		
		int result = mapper.deleteOk(to);
		System.out.println(result + "확인");
		
		if(result != 0) {
			flag = 0;
		}
		
		return flag;
	}
	
}
