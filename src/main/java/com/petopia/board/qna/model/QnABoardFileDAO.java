package com.petopia.board.qna.model;

import java.io.File;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.qna.mapper.QnABoardFileMapper;


@Repository
@MapperScan("com.petopia.board.qna.mapper")
public class QnABoardFileDAO {

	@Autowired
	private QnABoardFileMapper mapper;
	
	public List<QnABoardFileTO> allList(){
		List<QnABoardFileTO> file_datas = mapper.allList();
		
		return file_datas;
	}
	
	public List<QnABoardFileTO> view(QnABoardFileTO to){
		List<QnABoardFileTO> datas = mapper.list(to);
		
		return datas;
	}
	
	public int writeOk(QnABoardFileTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QnABoardFileTO to) {
		int flag = 1;
		
		List<QnABoardFileTO> datas = mapper.delete(to);
		
		for(QnABoardFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getQa_file_img_path());
			
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
