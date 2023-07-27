package com.petopia.board.missing.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.missing.mapper.MissingMapper;

@Repository
@MapperScan("com.petopia.board.missing.mapper")
public class MissingDAO {

	@Autowired
	private MissingMapper mapper;
	
	public List<MissingTO> list(){
		List<MissingTO> datas = new ArrayList<>();
		
		datas = mapper.list();
		
		return datas;
	}
	
	public int writeOk(MissingTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public MissingTO view(MissingTO to) {
		MissingTO data = new MissingTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(MissingTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upCmt(MissingTO to) {
		int flag = 1;
		
		int result = mapper.upCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downCmt(MissingTO to) {
		int flag = 1;
		
		int result = mapper.downCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(MissingTO to) {
		int flag = 1;
		
		MissingTO data = mapper.delete(to);
		
		File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getMs_rep_img_path());
		
		if(!file.delete()) {
			System.out.println("파일 삭제 실패");
			return flag;
		}else {
			System.out.println("파일 삭제 성공");
		}
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(MissingTO to) {
		int flag = 1;
		
		MissingTO data = mapper.delete(to);
		
		File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getMs_rep_img_path());
		
		if(!file.delete()) {
			System.out.println("파일 삭제 실패");
			return flag;
		}else {
			System.out.println("파일 삭제 성공");
		}
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public MissingTO modify(MissingTO to) {
		MissingTO data = new MissingTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(MissingTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int modifyOkNoRep(MissingTO to) {
		int flag = 1;
		
		int result = mapper.modifyOkNoRep(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
}
