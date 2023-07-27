package com.petopia.admin.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petopia.admin.mapper.AdminMapperInter;
import com.petopia.pointshop.model.PointShopTO;


@Repository
@MapperScan( basePackages = { "com.petopia.admin.mapper" } )
public class AdminDAO {
	@Autowired
	private AdminMapperInter mapper;
	
	public ArrayList<AdminTO> userData() {
		ArrayList<AdminTO> lists = mapper.getMemberProfile();
		return lists;
	}
	
	public ArrayList<AdminTO> userData(String filter, String keyword) {
		ArrayList<AdminTO> lists = null;
		
		if(filter.equals("이름")) {
			lists = mapper.getMemberFilteredName(keyword);
		} else if(filter.equals("닉네임")) {
			lists = mapper.getMemberFilteredNickName(keyword);
		} else if(filter.equals("회원번호")) {
			lists = mapper.getMemberFilteredSeq(keyword);
		}
		return lists;
	}
	
	public AdminTO userInfo(String seq) {
		AdminTO to = mapper.getInfo(seq);
		return to;
	}
	
	public AdminTO userDelete(String seq) {
		AdminTO to = mapper.initDelete(seq);
		
		return to;
	}
	
	public AdminTO loginVerified(String username, String password) {
		AdminTO to = mapper.loginVerified(username, password);
		
		return to;
	}
	
	public void deleteObjectsBySeqList(List<Long> seqList) {
		
		List<String> deleteFileSeqs = seqList.stream().map(String::valueOf).collect(Collectors.toList());
		String seqListAsString = String.join( ",", deleteFileSeqs );
		
		List<String> deleteFileImgPaths = mapper.deleteFileImgPaths( seqListAsString );
		
		for( String imgPaths : deleteFileImgPaths ) {
			String category = mapper.deleteCategoryCheck( imgPaths );
			System.out.println( "카테고리 결과 : " + category );
			
			File file = null;
			File fileCard = null;
			
			
			if( category.equals( "0" ) ) {
				file = new File( "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_badge/" + imgPaths );
					if( !file.delete() ) {
						System.out.println("파일 삭제 실패");
					}else {
						System.out.println("파일 삭제 성공");
					}
			} else {
				file = new File( "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + imgPaths );
				String cardFileName = imgPaths.replace( "_listSample", "" );
				fileCard = new File( "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + cardFileName );
				if( !file.delete() || !fileCard.delete() ) {
					System.out.println("파일 삭제 실패");
				}else {
					System.out.println("파일 삭제 성공");
				}
			}	
		}
        mapper.deleteObjectsBySeqList( seqListAsString );
    }

	public int adminModifyItem( PointShopTO pointShopTO ) {
		
		int flag = mapper.adminModifyItem( pointShopTO );
		return flag;
	}
	
	public int adminFileModifyItem( PointShopTO pointShopTO ) {
		
		int flag = mapper.adminFileModifyItem( pointShopTO );
		return flag;
	}
	
	public String adminFileSearch( String ps_seq ) {
		
		String baseFilePath = mapper.adminFileSearch( ps_seq );
		return baseFilePath;
	}
	
}
