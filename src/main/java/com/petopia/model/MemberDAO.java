package com.petopia.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.mapper.MemberMapper;
import com.petopia.mapper.SqlMemberMapperInter;

@Repository
@MapperScan( basePackages = { "com.petopia.mapper" } )
public class MemberDAO {
	
	@Autowired
	private SqlMemberMapperInter mapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	public ArrayList<MemberTO> userData() {
		ArrayList<MemberTO> lists = mapper.getMember();

		return lists;
	}
	
	public MemberTO userInfo(String seq) {
		MemberTO to = mapper.getInfo(seq);
		
		return to;
	}
	
	public MemberTO userDelete(String seq) {
		MemberTO to = mapper.initDelete(seq);
		
		return to;
	}
	
	public MemberTO loginVerified(String username, String password) {
		MemberTO to = mapper.loginVerified(username, password);
		
		return to;
	}
	
	public int MemberInsertOk(MemberTO to) {
		int flag = 1;
		
		int result = mapper.member_insert(to);
		System.out.println(result);
		if(result == 1) {
			flag =0;
		}
		return flag;
	}
	
	public MemberTO member(MemberTO to) {
		MemberTO data = new MemberTO();
		to.getM_seq();
		data = memberMapper.member(to);
		
		return data;
	}
	
	public int checkIdDup(MemberTO to) {
		
		System.out.println("getM_id : " +to.getM_id());
		
		return memberMapper.checkIdDup(to.getM_id());
	}
	
	
	public int checkEmailDup(MemberTO to) {
		
		System.out.println("getM_email: " +to.getM_email());
		
		return memberMapper.checkEmailDup(to.getM_email());
	}
	public int checkNicknameDup(MemberTO to) {
		
		System.out.println("getM_nickname: " +to.getM_nickname());
		
		return memberMapper.checkNicknameDup(to.getM_nickname());
	}

public int kakaoAddMember(MemberTO to2) {
		
		int flag = mapper.member_kakaoInsert(to2);
		memberMapper.kakaoLogin(to2.getM_email());
		
		return flag;
	}
	
	public String idCheck(String m_id) {
		
		String idCheck = memberMapper.idCheck(m_id);
		
		return idCheck;
		
	}
	
	public MemberTO userTransfer( String m_id ) {
		
		MemberTO userTransfer = memberMapper.userTransfer( m_id );
		
		return userTransfer;
		
	}
	
	public String selectSeq() {
		
		String selectSeq = memberMapper.selectSeq();
		
		return selectSeq;
	}
	
	public MemberTO memberInfo(MemberTO to) {
		
			MemberTO memberInfo = new MemberTO();
			to.setM_seq("0");
			to.setM_id("m_id");
			to.setM_nickname("m_nickname");
			to.setM_name("m_name");
			to.setM_password("m_password");
			to.setM_email("m_email");
			to.setM_addr("m_addr");
			to.setM_phone("m_phone");
			to.setM_age("m_age");
			to.setM_gender("m_gender");
			to.setM_date("m_date");
			to.setGrade_seq("grade_seq");
			to.setM_totalPoint("m_totalpoint");
			to.setM_point("m_point");
			memberInfo = memberMapper.member(to);
		return memberInfo;
	}
	
	
	// 게시판
		public int upPoint10(MemberTO to) {
			int flag = 1;
			
			int result = mapper.upPoint10(to);
			if(result == 1) {
				flag = 0;	
			}
			
			return flag;
		}
		
		public int upPoint20(MemberTO to) {
			int flag = 1;
			
			int result = mapper.upPoint20(to);
			if(result == 1) {
				flag = 0;
			}
			
			return flag;
		}
		
	// 프로필 카드 생성
		
		public int profileCardInsertOk( String m_seq ) {
			
			int profileCardInsertOk = memberMapper.profileCardInsertOk( m_seq );
			
			return profileCardInsertOk;
			
		}	
		
		// 승급 관련
		public int upGrade(MemberTO to) {
			int flag = 0;
			
			int result = mapper.becomeKnight(to);
			
			if(result == 1) {
				flag = 1;
			}else {
				result = mapper.becomeElder(to);
				
				if(result == 1) {
					flag = 2;
				}else {
					result = mapper.becomeHonor(to);
					
					if(result == 1) {
						flag = 3;
					}else {
						
						result = mapper.becomeRoyalStar(to);
						
						if(result == 1) {
							flag = 4;
						}
					}
				}
			}
			
			return flag;
		}	
	
}
