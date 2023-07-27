package com.petopia.board.freq.model;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.freq.mapper.QnABoardFreqMapper;


@Repository
@MapperScan("com.petopia.board.freq.mapper")
public class QnABoardFreqDAO {
	
	@Autowired
	QnABoardFreqMapper mapper;
	
	public List<QnABoardFreqTO> list(){
		List<QnABoardFreqTO> datas = mapper.list();
		
		return datas;
	}
	
	public List<QnABoardFreqTO> subjectList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.subjectList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> contentList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.contentList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> memberList(){
		List<QnABoardFreqTO> datas = mapper.memberList();
		
		return datas;
	}
	
	public List<QnABoardFreqTO> memberSubjectList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.memberSubjectList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> memberContentList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.memberContentList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> communityList(){
		List<QnABoardFreqTO> datas = mapper.communityList();
		
		return datas;
	}
	
	public List<QnABoardFreqTO> communitySubjectList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.communitySubjectList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> communityContentList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.communityContentList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> websiteList(){
		List<QnABoardFreqTO> datas = mapper.websiteList();
		
		return datas;
	}
	
	public List<QnABoardFreqTO> websiteSubjectList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.websiteSubjectList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> websiteContentList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.websiteContentList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> eventAndPointList(){
		List<QnABoardFreqTO> datas = mapper.eventAndPointList();
		
		return datas;
	}
	
	public List<QnABoardFreqTO> eventAndPointSubjectList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.eventAndPointSubjectList(to);
		
		return datas;
	}
	
	public List<QnABoardFreqTO> eventAndPointContentList(QnABoardFreqTO to){
		List<QnABoardFreqTO> datas = mapper.eventAndPointContentList(to);
		
		return datas;
	}
	
	public int writeOk(QnABoardFreqTO to) {
		int flag = 1;
		
		int result = mapper.writeOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(QnABoardFreqTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public QnABoardFreqTO modify(QnABoardFreqTO to) {
		QnABoardFreqTO data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(QnABoardFreqTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
}
