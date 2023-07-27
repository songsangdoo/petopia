package com.petopia.board.free.model;

import java.io.File;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.free.mapper.FreeBoardFileMapper;

@Repository
@MapperScan("com.petopia.board.free.mapper")
public class FreeBoardFileDAO {

	@Autowired
	private FreeBoardFileMapper mapper;
	
	public List<FreeBoardFileTO> view(FreeBoardFileTO to){
		List<FreeBoardFileTO> datas = mapper.list(to);
		
		return datas;
	}
	
	public int writeOk(FreeBoardFileTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(FreeBoardFileTO to) {
		int flag = 1;
		
		List<FreeBoardFileTO> datas = mapper.delete(to);
		
		for(FreeBoardFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getFb_file_img_path());
			
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
