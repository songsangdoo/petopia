package com.petopia.board.question.model;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.question.mapper.QuestionMapper;

@Repository
@MapperScan("com.petopia.board.question.mapper")
public class QuestionDAO {

	@Autowired
	private QuestionMapper mapper;
	
	public List<QuestionTO> latestList(){
		List<QuestionTO> datas = mapper.latestList();
		
		return datas;
	}
	
	public List<QuestionTO> subjectLatestList(QuestionTO to){
		List<QuestionTO> datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<QuestionTO> contentLatestList(QuestionTO to){
		List<QuestionTO> datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<QuestionTO> ansList(){
		List<QuestionTO> datas = mapper.ansList();
		
		return datas;
	}
	
	public List<QuestionTO> subjectAnsList(QuestionTO to){
		List<QuestionTO> datas = mapper.subjectAnsList(to);
		
		return datas;
	}
	
	public List<QuestionTO> contentAnsList(QuestionTO to){
		List<QuestionTO> datas = mapper.contentAnsList(to);
		
		return datas;
	}
	
	public List<QuestionTO> hitList(){
		List<QuestionTO> datas = mapper.hitList();
		
		return datas;
	}
	
	public List<QuestionTO> subjectHitList(QuestionTO to){
		List<QuestionTO> datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<QuestionTO> contentHitList(QuestionTO to){
		List<QuestionTO> datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public List<QuestionTO> myList(QuestionTO to){
		List<QuestionTO> datas = mapper.myList(to);
		
		return datas;
	}
	
	public List<QuestionTO> subjectMyList(QuestionTO to){
		List<QuestionTO> datas = mapper.subjectMyList(to);
		
		return datas;
	}
	
	public List<QuestionTO> contentMyList(QuestionTO to){
		List<QuestionTO> datas = mapper.contentMyList(to);
		
		return datas;
	}
	
	public int writeOk(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QuestionTO view(QuestionTO to) {
		QuestionTO data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upAns(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.upAns(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downAns(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.downAns(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int selectAns(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.selectAns(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QuestionTO modify(QuestionTO to) {
		QuestionTO data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(QuestionTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
}
