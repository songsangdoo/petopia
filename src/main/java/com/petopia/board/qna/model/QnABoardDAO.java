package com.petopia.board.qna.model;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.qna.mapper.QnABoardMapper;

@Repository
@MapperScan("com.petopia.board.qna.mapper")
public class QnABoardDAO {

	@Autowired
	private QnABoardMapper mapper;
	
	public List<QnABoardTO> list(){
		List<QnABoardTO> datas = mapper.list();
		
		return datas;
	}
	
	public List<QnABoardTO> noSecretList(){
		List<QnABoardTO> datas = mapper.noSecretList();
		
		return datas;
	}
	
	public List<QnABoardTO> noAnswerList(){
		List<QnABoardTO> datas = mapper.noAnswerList();
		
		return datas;
	}
	
	public List<QnABoardTO> myList(QnABoardTO to){
		List<QnABoardTO> datas = mapper.myList(to);
		
		return datas;
	}
	
	public int writeOk(QnABoardTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QnABoardTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
	
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(QnABoardTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);

		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QnABoardTO modify(QnABoardTO to) {
		QnABoardTO data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(QnABoardTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QnABoardTO answer(QnABoardTO to) {
		QnABoardTO data = mapper.answer(to);
		
		return data;
	}
	
	public int answerOk(QnABoardTO to) {
		int flag = 1;
		
		int result = mapper.answerOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
}
