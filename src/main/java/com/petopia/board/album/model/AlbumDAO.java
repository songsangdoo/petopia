package com.petopia.board.album.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.album.mapper.AlbumMapper;

@Repository
@MapperScan("com.petopia.board.album.mapper")
public class AlbumDAO {

	@Autowired
	private AlbumMapper mapper;
	
	public List<AlbumTO> recList(){
		List<AlbumTO> datas = mapper.recList();
		
		return datas;
	}
	
	public List<AlbumTO> cmtList(){
		List<AlbumTO> datas = mapper.cmtList();
		
		return datas;
	}
	
	public List<AlbumTO> hitList(){
		List<AlbumTO> datas = mapper.hitList();
		
		return datas;
	}
	
	public List<AlbumTO> list(){
		List<AlbumTO> datas = new ArrayList<>();
		
		datas = mapper.list();
		
		return datas;
	}
	
	public int writeOk(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public AlbumTO view(AlbumTO to) {
		AlbumTO data = new AlbumTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upCmt(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.upCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downCmt(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.downCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(AlbumTO to) {
		int flag = 1;
		
		AlbumTO data = mapper.delete(to);
		
		File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getAb_rep_img_path());
		
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
	
	public int adminDeleteOk(AlbumTO to) {
		int flag = 1;
		
		AlbumTO data = mapper.delete(to);
		
		File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getAb_rep_img_path());
		
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
	
	public AlbumTO modify(AlbumTO to) {
		AlbumTO data = new AlbumTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int modifyOkNoRep(AlbumTO to) {
		int flag = 1;
		
		int result = mapper.modifyOkNoRep(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public List<AlbumTO> mainList(){
		List<AlbumTO> datas = mapper.mainList();
		
		return datas;
	}
}
