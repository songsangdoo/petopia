package com.petopia.group.board.model;

import java.io.File;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.free.mapper.FreeBoardFileMapper;
import com.petopia.group.board.mapper.GroupBoardFileMapper;

@Repository
@MapperScan("com.petopia.group.board.mapper")
public class GroupBoardFileDAO {

	@Autowired
	private GroupBoardFileMapper mapper;
	
	public List<GroupBoardFileTO> view(GroupBoardFileTO to){
		List<GroupBoardFileTO> datas = mapper.list(to);
		
		return datas;
	}
	
	public int writeOk(GroupBoardFileTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(GroupBoardFileTO to) {
		int flag = 1;
		
		List<GroupBoardFileTO> datas = mapper.delete(to);
		
		for(GroupBoardFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getGRB_FILE_IMG_PATH());
			
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
