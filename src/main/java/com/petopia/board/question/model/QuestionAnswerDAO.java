package com.petopia.board.question.model;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.question.mapper.QuestionAnswerMapper;

@Repository
@MapperScan("com.petopia.board.question.mapper")
public class QuestionAnswerDAO {

	@Autowired
	private QuestionAnswerMapper mapper;
	
	public List<QuestionAnswerTO> view(QuestionAnswerTO to){
		List<QuestionAnswerTO> datas = mapper.view(to);
		
		return datas;
	}
	
	public QuestionAnswerTO viewSelected(QuestionAnswerTO to) {
		QuestionAnswerTO data = mapper.viewSelected(to);
		
		return data;
	}
	
	public int selectAns(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.selectAns(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int writeOk(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;	
		}
		
		return flag;
	}
	
	public int adminDeleteOk(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		List<QuestionAnswerTO> datas = mapper.view(to);
		
		if(datas != null && datas.size() != 0) {
			if(result != 0) {
				flag = 0;
			}
		}else {
			flag = 0;
		}
		
		return flag;
	}
	
	public QuestionAnswerTO modify(QuestionAnswerTO to) {
		QuestionAnswerTO data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(QuestionAnswerTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QuestionAnswerTO selectAnsInfo(QuestionAnswerTO to) {
		QuestionAnswerTO data = mapper.modify(to);
		
		return data;
	}
}
