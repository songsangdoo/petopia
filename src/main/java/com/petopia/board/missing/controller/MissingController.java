package com.petopia.board.missing.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.board.album.model.AlbumCommentTO;
import com.petopia.board.album.model.AlbumFileTO;
import com.petopia.board.album.model.AlbumTO;
import com.petopia.board.missing.model.MissingCommentDAO;
import com.petopia.board.missing.model.MissingCommentTO;
import com.petopia.board.missing.model.MissingDAO;
import com.petopia.board.missing.model.MissingFileDAO;
import com.petopia.board.missing.model.MissingFileTO;
import com.petopia.board.missing.model.MissingTO;
import com.petopia.board.recommend.model.RecCheckDAO;
import com.petopia.board.recommend.model.RecCheckTO;
import com.petopia.board.recommend.model.RecCmtCheckDAO;
import com.petopia.board.recommend.model.RecCmtCheckTO;
import com.petopia.model.MemberDAO;
import com.petopia.model.MemberTO;
import com.petopia.mypage.model.MyPagePetTO;
import com.petopia.mypage.model.MypagePetDAO;
import com.petopia.mypage.model.MypageProfileCardDAO;
import com.petopia.mypage.model.MypageProfileCardTO;
import com.petopia.pointshop.model.GradeDAO;
import com.petopia.pointshop.model.GradeTO;
import com.petopia.pointshop.model.InventoryDAO;
import com.petopia.pointshop.model.InventoryTO;
import com.petopia.pointshop.model.PointShopDAO;
import com.petopia.pointshop.model.PointShopTO;

@RestController
@ComponentScan({"com.petopia.board.missing.model", "com.petopia.model", "com.petopia.pointshop.model"})
public class MissingController {

	@Autowired
	private MissingDAO missingDAO;
	@Autowired
	private MissingFileDAO missingFileDAO;
	@Autowired
	private MissingCommentDAO missingCommentDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private GradeDAO gradeDAO;
	@Autowired
	private InventoryDAO inventoryDAO;
	@Autowired
	private PointShopDAO pointShopDAO;
	@Autowired
	private MypageProfileCardDAO mypageProfileCardDAO;
	@Autowired
	private MypagePetDAO mypetDAO;
	
	@RequestMapping("/missing_list.do")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("missing_views/board_list1");
		
		List<MissingTO> datas = missingDAO.list();
		
		request.setAttribute("datas", datas);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/missing_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_write1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		String pet_name = request.getParameter("pet_name");
		String pet_gender = "M";
		if(request.getParameter("pet_gender") != null && request.getParameter("pet_gender").equals("F")) {
			pet_gender = "F";
		}
		int pet_age = Integer.parseInt(request.getParameter("pet_age"));
		String open_phone = "N";
		if(request.getParameter("open_phone") != null && request.getParameter("open_phone").equals("Y")) {
			open_phone = "Y";
		}
		String missing_date = request.getParameter("missing_date");
		String missing_loc = request.getParameter("missingLoc") + "&&&" + (request.getParameter("missingLoc2") == null ? "&nbsp;": request.getParameter("missingLoc2"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		
		String rep_ext = rep_upload.getOriginalFilename().substring(rep_upload.getOriginalFilename().lastIndexOf("."));
		String rep_filename = rep_upload.getOriginalFilename().substring(0, rep_upload.getOriginalFilename().indexOf(rep_ext));
		UUID uuidRepImg = UUID.randomUUID(); // 파일 이름 중복방지
		String rep_img_path = rep_filename + "_" + uuidRepImg.toString() + rep_ext;
		
		MissingTO to = new MissingTO();
		to.setM_seq(m_seq);
		to.setMs_pet_name(pet_name);
		to.setMs_pet_gender(pet_gender);
		to.setMs_pet_age(pet_age);
		to.setMs_open_phone(open_phone);
		to.setMs_missing_date(missing_date);
		to.setMs_missing_loc(missing_loc);
		to.setMs_subject(subject);
		to.setMs_content(content);
		to.setMs_rep_img_path(rep_img_path);
		
		int flag = 1;
		int file_flag = 1;
		int rec_insert = 1;
		
		flag = missingDAO.writeOk(to);
		
		if(flag == 0) {
			int ms_seq = missingDAO.list().get(0).getMs_seq();

			try {
				rep_upload.transferTo(new File(rep_img_path));
			} catch (IllegalStateException e) {
				System.out.println("에러 : " + e.getMessage());
			} catch (IOException e) {
				System.out.println("에러 : " + e.getMessage());
			}
			
			
			if(!uploads[0].isEmpty()) {
				for(MultipartFile upload : uploads) {

					String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
					String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
					UUID uuidFileImg = UUID.randomUUID(); 
					String ms_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
					
					try {
						upload.transferTo(new File(ms_file_img_path));
						
						MissingFileTO fto = new MissingFileTO();
						fto.setMs_seq(ms_seq);
						fto.setMs_file_img_path(ms_file_img_path);
						
						file_flag = missingFileDAO.writeOk(fto);
						
						if(file_flag == 1) {
							missingFileDAO.deleteOk(fto);
							missingDAO.deleteOk(to);
							
							break;
						}
					} catch (IllegalStateException e) {
						System.out.println("에러 : " + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 : " + e.getMessage());
					}
				}
				
			}else {
				file_flag = 0;
			}

		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		request.setAttribute("rec_insert", rec_insert);
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_view1");
		
		int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));

		MissingTO to = new MissingTO();
		to.setMs_seq(ms_seq);
		
		Cookie[] cookies = request.getCookies();
		
		Cookie viewCookie = null;
		
		Cookie newCookie = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("ms_" + ms_seq + "_chk")) {
					viewCookie = cookies[i];
				}

			}
		}

		if(viewCookie == null) {
			newCookie = new Cookie("ms_" + ms_seq + "_chk", "visited_" + ms_seq);
			response.addCookie(newCookie);
			missingDAO.upHit(to);
		}

		MissingFileTO fto = new MissingFileTO();
		fto.setMs_seq(ms_seq);
		
		MissingCommentTO cto = new MissingCommentTO();
		cto.setMs_seq(ms_seq);
		
		MissingTO data = new MissingTO();
		data = missingDAO.view(to);
		
		List<MissingFileTO> file_list = missingFileDAO.view(fto);
		List<MissingCommentTO> cmt_list = missingCommentDAO.latestList(cto);
		
		// 프로필카드
		
		int w_seq = data.getM_seq();
		
		MemberTO w_info = memberDAO.userInfo(String.valueOf(w_seq));
		
		ArrayList<InventoryTO> userInvenSkin = inventoryDAO.skinCheck( String.valueOf(w_seq) );
		ArrayList<InventoryTO> userInvenBadge = inventoryDAO.badgeCheck( String.valueOf(w_seq) );
		
		MypageProfileCardTO profileCardInfo = mypageProfileCardDAO.profileCardInfoCheck( String.valueOf(w_seq) );
		
		ArrayList<PointShopTO> skinPointShopList = new ArrayList<>();
		ArrayList<PointShopTO> badgePointShopList = new ArrayList<>();
		
		if( userInvenSkin != null ) {
			for (int i = 0; i < userInvenSkin.size(); i++ ) {
				PointShopTO skinInfo = pointShopDAO.skinCheck( userInvenSkin.get(i).getPs_seq() );
				skinPointShopList.add( skinInfo );
			}
		}
		
		if( userInvenBadge != null ) {
			for (int i = 0; i < userInvenBadge.size(); i++ ) {
				PointShopTO badgeInfo = pointShopDAO.skinCheck( userInvenBadge.get(i).getPs_seq() );
				badgePointShopList.add( badgeInfo );
			}
		}
		
		if( profileCardInfo == null ) {
			profileCardInfo = new MypageProfileCardTO();
			profileCardInfo.setPro_comment( "한 마디 글을 작성해 주세요." );
			profileCardInfo.setPro_img( "no_image.png" );
		}
		
		ArrayList<String> myBadgeSeqList = inventoryDAO.myBadgeSeqList( String.valueOf(w_seq) );
		ArrayList<String> myBadgeImgList = new ArrayList<>();
				
		String mySkinSeq = inventoryDAO.mySkinSeq( String.valueOf(w_seq) );
		String mySkinImg = "";
		
		if( myBadgeSeqList != null ) {
			for (int i = 0; i < myBadgeSeqList.size(); i++ ) {
				String myBadgeImg = pointShopDAO.myBadgeImg( myBadgeSeqList.get(i).toString() );
				myBadgeImgList.add( myBadgeImg );
			}
		}
		
		if( mySkinSeq != null ) {
			mySkinImg = pointShopDAO.mySkinImg( mySkinSeq );
		}
		
		myBadgeSeqList = inventoryDAO.myBadgeSeqList( String.valueOf(w_seq) );
		
		GradeTO w_gradeChecks = gradeDAO.gradeCheck(w_info.getGrade_seq());
		
		
		List<GradeTO> grade_info_datas = gradeDAO.question_gradeCheck();
		List<MemberTO> m_info_datas = memberDAO.userData();
		
		ArrayList<MyPagePetTO> lists = new ArrayList<>();
		lists = mypetDAO.getPetList( String.valueOf(w_seq) );
		
		ArrayList<MyPagePetTO> petCheckLists = new ArrayList<>();
		if( lists.size() != 0 ) {
			for( int i = 0; i<lists.size(); i++ ) { 
				MyPagePetTO checkSearch = mypetDAO.equipCardPet( lists.get(i).getP_seq() );
				petCheckLists.add ( checkSearch );
			}
		}
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);
		request.setAttribute("cmt_list", cmt_list);
		
		request.setAttribute("w_info", w_info);
		request.setAttribute("w_gradeChecks", w_gradeChecks);
		request.setAttribute("grade_info_datas", grade_info_datas);
		request.setAttribute("m_info_datas", m_info_datas);
		
		modelAndView.addObject( "profileCardInfo", profileCardInfo );
		modelAndView.addObject( "skinPointShopList", skinPointShopList );
		modelAndView.addObject( "badgePointShopList", badgePointShopList );
		modelAndView.addObject( "myBadgeImgList", myBadgeImgList );
		modelAndView.addObject( "mySkinImg", mySkinImg );
		
		modelAndView.addObject( "petCheckLists", petCheckLists );
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_view_cmt_write.do")
	public ModelAndView cmtWrite(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));
		
		MissingTO to = new MissingTO();
		to.setMs_seq(ms_seq);
		
		String ms_cmt_content = request.getParameter("ms_cmt_content").replaceAll("/n", "<br>");
		
		MissingCommentTO cto = new MissingCommentTO();
		cto.setMs_seq(ms_seq);
		cto.setM_seq(m_seq);
		cto.setMs_cmt_content(ms_cmt_content);
		
		int cmt_write_flag = missingCommentDAO.writeOk(cto);
		
		int ms_cmt_flag = 1;
		
		if(cmt_write_flag == 0) {
			ms_cmt_flag = missingDAO.upCmt(to);
		}
		
		request.setAttribute("cmt_write_flag", cmt_write_flag);
		request.setAttribute("ms_cmt_flag", ms_cmt_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_view_cmt_del.do")
	public ModelAndView deleteCo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int cmt_del_flag = 1;
		
		if(userData != null) {
			
			int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));
			int ms_cmt_seq = Integer.parseInt(request.getParameter("ms_cmt_seq"));
			
			
			MissingTO to = new MissingTO();
			to.setMs_seq(ms_seq);
			
			MissingCommentTO cto = new MissingCommentTO();
			
			if(userData.getGrade_seq().equals("1")) {
				cto.setMs_seq(ms_seq);
				cto.setMs_cmt_seq(ms_cmt_seq);
				
				cmt_del_flag = missingCommentDAO.adminDeleteOk(cto);
				
			}else {
				
				int m_seq =  Integer.parseInt(userData.getM_seq());
				
				cto.setMs_seq(ms_seq);
				cto.setMs_cmt_seq(ms_cmt_seq);
				cto.setM_seq(m_seq);
				
				cmt_del_flag = missingCommentDAO.deleteOk(cto);
			}
			
			if(cmt_del_flag == 0) {
				missingDAO.downCmt(to);
				
			}
		
		}
		request.setAttribute("cmt_del_flag", cmt_del_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		int cmt_flag = 1;
		
		if(userData != null) {
			
			int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));
	
			if(userData.getGrade_seq().equals("1")) {
				
				MissingTO to = new MissingTO();
				to.setMs_seq(ms_seq);
				
				flag = missingDAO.adminDeleteOk(to);
				
			}else {
				int m_seq =  Integer.parseInt(userData.getM_seq());
				
				MissingTO to = new MissingTO();
				to.setMs_seq(ms_seq);
				to.setM_seq(m_seq);
				
				flag = missingDAO.deleteOk(to);
			}
			
			MissingFileTO fto = new MissingFileTO();
			fto.setMs_seq(ms_seq);
			
			MissingCommentTO cto = new MissingCommentTO();
			cto.setMs_seq(ms_seq);
			
			if(flag == 0) {
				
				file_flag = missingFileDAO.deleteOk(fto);
				
				cmt_flag = missingCommentDAO.deleteAllOk(cto);
				
			}
		
		}
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		request.setAttribute("cmt_flag", cmt_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_modify1");
		
		int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));
		
		MissingTO to = new MissingTO();
		to.setMs_seq(ms_seq);
		
		MissingTO data = new MissingTO();
		data = missingDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/missing_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("missing_views/board_modify1_ok");
		
		int ms_seq = Integer.parseInt(request.getParameter("ms_seq"));

		String pet_name = request.getParameter("pet_name");
		String pet_gender = "M";
		if(request.getParameter("pet_gender") != null && request.getParameter("pet_gender").equals("F")) {
			pet_gender = "F";
		}
		int pet_age = Integer.parseInt(request.getParameter("pet_age"));
		String open_phone = "N";
		if(request.getParameter("open_phone") != null && request.getParameter("open_phone").equals("Y")) {
			open_phone = "Y";
		}
		String missing_date = request.getParameter("missing_date");
		String missing_loc = request.getParameter("missingLoc") + "&&&" + request.getParameter("missingLoc2");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		
		MissingTO to = new MissingTO();
		to.setMs_seq(ms_seq);
		to.setMs_pet_name(pet_name);
		to.setMs_pet_gender(pet_gender);
		to.setMs_pet_age(pet_age);
		to.setMs_open_phone(open_phone);
		to.setMs_missing_date(missing_date);
		to.setMs_missing_loc(missing_loc);
		to.setMs_subject(subject);
		to.setMs_content(content);

		MissingTO old_data = missingDAO.view(to);
		String old_rep_img_path = old_data.getMs_rep_img_path();
		
		int flag = 1;
		int file_flag = 1;
		
		MissingFileTO fto = new MissingFileTO();
		fto.setMs_seq(ms_seq);
		
		List<MissingFileTO> old_file_img_path_list = missingFileDAO.view(fto);
		
		if(rep_upload.getOriginalFilename().trim().equals("")) {
			flag = missingDAO.modifyOkNoRep(to);
		}else {
			String rep_ext = rep_upload.getOriginalFilename().substring(rep_upload.getOriginalFilename().lastIndexOf("."));
			String rep_filename = rep_upload.getOriginalFilename().substring(0, rep_upload.getOriginalFilename().indexOf(rep_ext));
			UUID uuidRepImg = UUID.randomUUID();
			String ms_rep_img_path = rep_filename + "_" + uuidRepImg.toString() + rep_ext;
			
			to.setMs_rep_img_path(ms_rep_img_path);
			
			flag = missingDAO.modifyOk(to);
			
			if(flag == 0) {
				try {
					rep_upload.transferTo(new File(ms_rep_img_path));
					
					System.out.println("수정된 rep 업로드 성공");
				} catch (IllegalStateException e) {
					System.out.println("에러 : " + e.getMessage());
				} catch (IOException e) {
					System.out.println("에러 : " + e.getMessage());
				}
				
				File old_rep_file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + old_rep_img_path);
				if(old_rep_file.delete()) {
					System.out.println("old rep 삭제 성공");
				}
			}
		}
		
		if(uploads[0].getOriginalFilename().trim().equals("")) {
			file_flag = 0;
		}else {
			int old_file_del_flag = missingFileDAO.deleteOk(fto);
			
			if(old_file_del_flag == 0) {
				for(MissingFileTO old_file_img_path : old_file_img_path_list) {
					File old_file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + old_file_img_path);
					
					if(old_file.delete()) {
						System.out.println("old 파일 삭제 성공");
					}
				}
			}
			
			for(MultipartFile upload : uploads) {
				String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
				String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
				UUID uuidFileImg = UUID.randomUUID();
				String ms_file_img_path = filename + "_" + uuidFileImg + ext;
						
				try {
					upload.transferTo(new File(ms_file_img_path));
					
					System.out.println("수정된 파일 업로드 성공");
				} catch (IllegalStateException e) {
					System.out.println("에러 :" + e.getMessage());
				} catch (IOException e) {
					System.out.println("에러 :" + e.getMessage());
				}
				
				fto.setMs_file_img_path(ms_file_img_path);
				
				file_flag = missingFileDAO.writeOk(fto);		
			}
			
		}

		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
}
