package com.petopia.board.question.model;

import java.io.File;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.question.mapper.QuestionAnswerFileMapper;
import com.petopia.board.question.mapper.QuestionFileMapper;

@Repository
@MapperScan("com.petopia.board.question.mapper")
public class QuestionAnswerFileDAO {

	@Autowired
	private QuestionAnswerFileMapper mapper;
	
	public List<QuestionAnswerFileTO> view(QuestionAnswerFileTO to){
		List<QuestionAnswerFileTO> datas = mapper.list(to);
		
		return datas;
	}
	
	public int writeOk(QuestionAnswerFileTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QuestionAnswerFileTO to) {
		int flag = 1;
		
		List<QuestionAnswerFileTO> datas = mapper.delete(to);
		
		for(QuestionAnswerFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getQ_ans_file_img_path());
			
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
	
	public int deleteAllOk(QuestionAnswerFileTO to) {
		int flag = 1;
		
		List<QuestionAnswerFileTO> datas = mapper.deleteAll(to);
		
		for(QuestionAnswerFileTO data : datas) {
			File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getQ_ans_file_img_path());
			
			if(!file.delete()) {
				System.out.println("파일 삭제 실패");
				return flag;
			}else {
				System.out.println("파일 삭제 성공");
			}
		}
		
		int result = mapper.deleteAllOk(to);
		System.out.println(result + "확인");
		
		if(result != 0) {
			flag = 0;
		}
		
		return flag;
	}
	
	public List<QuestionAnswerFileTO> modify(QuestionAnswerFileTO to){
		List<QuestionAnswerFileTO> datas = mapper.modify(to);
		
		return datas;
	}
	
}
