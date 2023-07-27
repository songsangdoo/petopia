package com.petopia.board.tip.controller;

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
import com.petopia.board.recommend.model.RecCheckDAO;
import com.petopia.board.recommend.model.RecCheckTO;
import com.petopia.board.recommend.model.RecCmtCheckDAO;
import com.petopia.board.recommend.model.RecCmtCheckTO;
import com.petopia.board.tip.model.TipBoardCommentDAO;
import com.petopia.board.tip.model.TipBoardCommentTO;
import com.petopia.board.tip.model.TipBoardDAO;
import com.petopia.board.tip.model.TipBoardFileDAO;
import com.petopia.board.tip.model.TipBoardFileTO;
import com.petopia.board.tip.model.TipBoardTO;
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
@ComponentScan({"com.petopia.board.tip.model", "com.petopia.board.recommend.model", "com.petopia.model", "com.petopia.pointshop.model"})
public class TipBoardController {

	@Autowired
	private TipBoardDAO tipboardDAO;
	@Autowired
	private TipBoardFileDAO tipboardFileDAO;
	@Autowired
	private TipBoardCommentDAO tipboardCommentDAO;
	@Autowired
	private RecCheckDAO recCheckDAO;
	@Autowired
	private RecCmtCheckDAO recCmtCheckDAO;
	
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
	
	private int board_no = 5;

	@RequestMapping("/tipboard_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("tipboard_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("tb_list_flag")) {
					listCookie = cookies[i];
					
					order_flag = listCookie.getValue();
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("latest")) {
				Cookie newCookie = new Cookie("tb_list_flag", "latest");
				
				listCookie = newCookie;
				
				order_flag = "latest";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("rec")) {
				Cookie newCookie = new Cookie("tb_list_flag", "rec");
				
				listCookie = newCookie;
				
				order_flag = "rec";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("cmt")) {
				Cookie newCookie = new Cookie("tb_list_flag", "cmt");
				
				listCookie = newCookie;
				
				order_flag = "cmt";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("hit")) {
				Cookie newCookie = new Cookie("tb_list_flag", "hit");
				
				listCookie = newCookie;
				
				order_flag = "hit";
				
				response.addCookie(listCookie);
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("tb_list_flag", "latest");
			
			listCookie = newCookie;
			
			response.addCookie(listCookie);
		}
		
		String option = null;
		String searchStr = null;
		
		if(request.getParameter("option") != null) {
			option = request.getParameter("option");
		}
		
		if(request.getParameter("searchStr") != null) {
			searchStr = request.getParameter("searchStr");
		}
		
		TipBoardTO to = new TipBoardTO();
		
		List<TipBoardTO> datas = null;
		
		if(option == null && searchStr == null) {
			if(listCookie.getValue().equals("latest")) {
				datas = tipboardDAO.latestList();
			}else if(listCookie.getValue().equals("rec")) {
				datas = tipboardDAO.recList();
			}else if(listCookie.getValue().equals("cmt")) {
				datas = tipboardDAO.cmtList();
			}else if(listCookie.getValue().equals("hit")) {
				datas = tipboardDAO.hitList();
			}
		}else if(option.equals("subject")){
			to.setTb_subject("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = tipboardDAO.subjectLatestList(to);
			}else if(listCookie.getValue().equals("rec")) {
				datas = tipboardDAO.subjectRecList(to);
			}else if(listCookie.getValue().equals("cmt")) {
				datas = tipboardDAO.subjectCmtList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = tipboardDAO.subjectHitList(to);
			}
		}else if(option.equals("writer")){
			to.setM_nickname(searchStr);
			if(listCookie.getValue().equals("latest")) {
				datas = tipboardDAO.writerLatestList(to);
			}else if(listCookie.getValue().equals("rec")) {
				datas = tipboardDAO.writerRecList(to);
			}else if(listCookie.getValue().equals("cmt")) {
				datas = tipboardDAO.writerCmtList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = tipboardDAO.writerHitList(to);
			}
		}else if(option.equals("content")){
			to.setTb_content("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = tipboardDAO.contentLatestList(to);
			}else if(listCookie.getValue().equals("rec")) {
				datas = tipboardDAO.contentRecList(to);
			}else if(listCookie.getValue().equals("cmt")) {
				datas = tipboardDAO.contentCmtList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = tipboardDAO.contentHitList(to);
			}
		}
		
		request.setAttribute("datas", datas);
		request.setAttribute("order_flag", order_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_write1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		
		TipBoardTO to = new TipBoardTO();
		to.setM_seq(m_seq);
		to.setTb_subject(subject);
		to.setTb_content(content);
		
		int flag = 1;
		int file_flag = 1;
		int rec_insert = 1;
		int upPointCheck = 1;
		int upGradeCheck = 0;
		
		flag = tipboardDAO.writeOk(to);
		
		if(flag == 0) {
			int tb_seq = tipboardDAO.latestList().get(0).getTb_seq();
			
			MemberTO mto = new MemberTO();
			mto.setM_seq(String.valueOf(m_seq));
			
			RecCheckTO wrcto = new RecCheckTO();
			wrcto.setM_seq(m_seq);
			
			RecCheckTO wCheck = recCheckDAO.wCount(wrcto);
			
			if(wCheck.getW_count() < 3) {
				upPointCheck = memberDAO.upPoint20(mto);
				upGradeCheck = memberDAO.upGrade(mto);
			}
			
			RecCheckTO rcto = new RecCheckTO();
			rcto.setBoard_no(board_no);
			rcto.setBoard_seq(tb_seq);
			rcto.setM_seq(m_seq);
			
			rec_insert = recCheckDAO.insert(rcto);
			
			if(!uploads[0].isEmpty()) {
				for(MultipartFile upload : uploads) {

					String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
					String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
					UUID uuidFileImg = UUID.randomUUID(); 
					String tb_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
					
					try {
						upload.transferTo(new File(tb_file_img_path));
						
						TipBoardFileTO fto = new TipBoardFileTO();
						fto.setTb_seq(tb_seq);
						fto.setTb_file_img_path(tb_file_img_path);
						
						file_flag = tipboardFileDAO.writeOk(fto);
						
						if(file_flag == 1) {
							tipboardFileDAO.deleteOk(fto);
							tipboardDAO.deleteOk(to);
							
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
		
		request.setAttribute("upPointCheck", upPointCheck);
		// 승급
		request.setAttribute("upGradeCheck", upGradeCheck);
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view1");
		
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));

		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		Cookie[] cookies = request.getCookies();
		
		Cookie viewCookie = null;
		Cookie listCookie = null;
		
		Cookie newCookie = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("tb_" + tb_seq + "_chk")) {
					viewCookie = cookies[i];
				}
				
				if(cookies[i].getName().equals("tb_cmt_list_flag")) {
					listCookie = cookies[i];
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("rec")) {
				newCookie = new Cookie("tb_cmt_list_flag", "rec");
				
				listCookie = newCookie;
				
				response.addCookie(listCookie);
			}else {
				newCookie = new Cookie("tb_cmt_list_flag", "latest");
				
				listCookie = newCookie;
				
				response.addCookie(listCookie);
			}
		}
		
		if(viewCookie == null) {
			newCookie = new Cookie("tb_" + tb_seq + "_chk", "visited_" + tb_seq);
			response.addCookie(newCookie);
			tipboardDAO.upHit(to);
		}
		
		if(listCookie == null) {
			newCookie = new Cookie("tb_cmt_list_flag", "latest");
			listCookie = newCookie;
				
			response.addCookie(listCookie);
		}

		TipBoardFileTO fto = new TipBoardFileTO();
		fto.setTb_seq(tb_seq);
		
		TipBoardCommentTO cto = new TipBoardCommentTO();
		cto.setTb_seq(tb_seq);
		
		TipBoardTO data = new TipBoardTO();
		data = tipboardDAO.view(to);
		
		List<TipBoardFileTO> file_list = tipboardFileDAO.view(fto);
		List<TipBoardCommentTO> cmt_list = new ArrayList<>();
		
		if(listCookie.getValue().equals("latest")) {
			cmt_list = tipboardCommentDAO.latestList(cto);
		}else {
			cmt_list = tipboardCommentDAO.recList(cto);
		}
		
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
	
	@RequestMapping("/tipboard_view_rec.do")
	public ModelAndView upRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		int rec_flag = 1;
		int rec_insert = 1; 
		
		RecCheckTO rcto = new RecCheckTO();
		rcto.setBoard_no(board_no);
		rcto.setBoard_seq(tb_seq);
		rcto.setM_seq(m_seq);
		
		if(recCheckDAO.check(rcto) == null) {
			rec_flag = tipboardDAO.upRec(to);
			
			rec_insert = recCheckDAO.insert(rcto);
			
		}
		
		request.setAttribute("rec_flag", rec_flag);
		request.setAttribute("rec_insert", rec_insert);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_view_cmt_write.do")
	public ModelAndView cmtWrite(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		String tb_cmt_content = request.getParameter("tb_cmt_content").replaceAll("/n", "<br>");
		
		TipBoardCommentTO cto = new TipBoardCommentTO();
		cto.setTb_seq(tb_seq);
		cto.setM_seq(m_seq);
		cto.setTb_cmt_content(tb_cmt_content);
		
		int cmt_write_flag = tipboardCommentDAO.writeOk(cto);
		
		int tb_cmt_flag = 1;
		int rec_check_flag = 1;
		
		if(cmt_write_flag == 0) {
			tb_cmt_flag = tipboardDAO.upCmt(to);
			
			if(tb_cmt_flag == 0) {
				int tb_cmt_seq = tipboardCommentDAO.latestList(cto).get(0).getTb_cmt_seq();
				
				RecCmtCheckTO rccto = new RecCmtCheckTO();
				rccto.setBoard_no(board_no);
				rccto.setBoard_seq(tb_seq);
				rccto.setCmt_seq(tb_cmt_seq);
				rccto.setM_seq(m_seq);
				
				rec_check_flag = recCmtCheckDAO.insert(rccto);
			}
		}
		
		request.setAttribute("rec_cmt_flag", rec_check_flag);
		request.setAttribute("cmt_write_flag", cmt_write_flag);
		request.setAttribute("tb_cmt_flag", tb_cmt_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_view_cmt_rec.do")
	public ModelAndView upCoRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view2");

		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		int tb_cmt_seq = Integer.parseInt(request.getParameter("tb_cmt_seq"));
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		TipBoardCommentTO cto = new TipBoardCommentTO();
		cto.setTb_seq(tb_seq);
		cto.setTb_cmt_seq(tb_cmt_seq);
		
		int cmt_rec_flag = 1;
		int rec_cmt_check_flag = 1;
		
		RecCmtCheckTO rccto = new RecCmtCheckTO();
		rccto.setBoard_no(board_no);
		rccto.setBoard_seq(tb_seq);
		rccto.setCmt_seq(tb_cmt_seq);
		rccto.setM_seq(m_seq);
		
		if(recCmtCheckDAO.check(rccto) == null) {
			cmt_rec_flag = tipboardCommentDAO.upRec(cto);
			
			rec_cmt_check_flag = recCmtCheckDAO.insert(rccto);
		}
		
		request.setAttribute("cmt_rec_flag", cmt_rec_flag);
		request.setAttribute("rec_cmt_check_flag", rec_cmt_check_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("tipboard_view_cmt_no_rec.do")
	public ModelAndView upCmtNoRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		int tb_cmt_seq = Integer.parseInt(request.getParameter("tb_cmt_seq"));
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		TipBoardCommentTO cto = new TipBoardCommentTO();
		cto.setTb_seq(tb_seq);
		cto.setTb_cmt_seq(tb_cmt_seq);
		
		int cmt_no_rec_flag = 1;
		int rec_cmt_check_flag = 1;
		
		RecCmtCheckTO rccto = new RecCmtCheckTO();
		rccto.setBoard_no(board_no);
		rccto.setBoard_seq(tb_seq);
		rccto.setCmt_seq(tb_cmt_seq);
		rccto.setM_seq(m_seq);
		
		if(recCmtCheckDAO.check(rccto) == null) {
			cmt_no_rec_flag = tipboardCommentDAO.upNoRec(cto);
			
			rec_cmt_check_flag = recCmtCheckDAO.insert(rccto);
		}
		
		request.setAttribute("cmt_no_rec_flag", cmt_no_rec_flag);
		request.setAttribute("rec_cmt_check_flag", rec_cmt_check_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_view_cmt_del.do")
	public ModelAndView deleteCo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int cmt_del_flag = 1;
		int rec_cmt_del_check_flag = 1;
		
		if(userData != null) {
			
			int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
			int tb_cmt_seq = Integer.parseInt(request.getParameter("tb_cmt_seq"));
			
			TipBoardTO to = new TipBoardTO();
			to.setTb_seq(tb_seq);
			
			TipBoardCommentTO cto = new TipBoardCommentTO();
			
			if(userData.getGrade_seq().equals("1")) {
				cto.setTb_seq(tb_seq);
				cto.setTb_cmt_seq(tb_cmt_seq);
				
				cmt_del_flag = tipboardCommentDAO.adminDeleteOk(cto);
				
			}else {
				
				int m_seq =  Integer.parseInt(userData.getM_seq());
				
				cto.setTb_seq(tb_seq);
				cto.setTb_cmt_seq(tb_cmt_seq);
				cto.setM_seq(m_seq);
				
				cmt_del_flag = tipboardCommentDAO.deleteOk(cto);
			}
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(board_no);
			rccto.setBoard_seq(tb_seq);
			rccto.setCmt_seq(tb_cmt_seq);
			
			if(cmt_del_flag == 0) {
				tipboardDAO.downCmt(to);
				
				rec_cmt_del_check_flag = recCmtCheckDAO.deleteAll(rccto);
			}
		
		}
		request.setAttribute("cmt_del_flag", cmt_del_flag);
		request.setAttribute("rec_cmt_del_check_flag", rec_cmt_del_check_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		int cmt_flag = 1;
		int rec_del_check = 1;
		int rec_cmt_del_check = 1;
		
		if(userData != null) {
			
			int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
			
			if(userData.getGrade_seq().equals("1")) {
				
				TipBoardTO to = new TipBoardTO();
				to.setTb_seq(tb_seq);
				
				flag = tipboardDAO.adminDeleteOk(to);
				
			}else {
				int m_seq =  Integer.parseInt(userData.getM_seq());
				
				TipBoardTO to = new TipBoardTO();
				to.setTb_seq(tb_seq);
				to.setM_seq(m_seq);
				
				flag = tipboardDAO.deleteOk(to);
			}
			
			TipBoardFileTO fto = new TipBoardFileTO();
			fto.setTb_seq(tb_seq);
			
			TipBoardCommentTO cto = new TipBoardCommentTO();
			cto.setTb_seq(tb_seq);
			
			RecCheckTO rcto = new RecCheckTO();
			rcto.setBoard_no(board_no);
			rcto.setBoard_seq(tb_seq);
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(board_no);
			rccto.setBoard_seq(tb_seq);
			
			if(flag == 0) {
				
				file_flag = tipboardFileDAO.deleteOk(fto);
				
				cmt_flag = tipboardCommentDAO.deleteAllOk(cto);
				
				rec_del_check = recCheckDAO.deleteAll(rcto);
				
				rec_cmt_del_check = recCmtCheckDAO.postDeleteAll(rccto);
			}
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		request.setAttribute("cmt_flag", cmt_flag);
		request.setAttribute("rec_del_check", rec_del_check);
		request.setAttribute("rec_cmt_del_check", rec_cmt_del_check);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_modify1");
		
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		
		TipBoardTO data = new TipBoardTO();
		data = tipboardDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/tipboard_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tipboard_views/board_modify1_ok");
		
		int tb_seq = Integer.parseInt(request.getParameter("tb_seq"));
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		
		TipBoardTO to = new TipBoardTO();
		to.setTb_seq(tb_seq);
		to.setTb_subject(subject);
		to.setTb_content(content);
		
		int flag = 1;
		int file_flag = 1;
		
		TipBoardFileTO fto = new TipBoardFileTO();
		fto.setTb_seq(tb_seq);
		
		List<TipBoardFileTO> old_file_img_path_list = tipboardFileDAO.view(fto);
		
		flag = tipboardDAO.modifyOk(to);
		
		if(uploads[0].getOriginalFilename().trim().equals("")) {
			file_flag = 0;
		}else {
			int old_file_del_flag = tipboardFileDAO.deleteOk(fto);
			
			if(old_file_del_flag == 0) {
				for(TipBoardFileTO old_file_img_path : old_file_img_path_list) {
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
				String tb_file_img_path = filename + "_" + uuidFileImg + ext;
						
				try {
					upload.transferTo(new File(tb_file_img_path));
					
					System.out.println("수정된 파일 업로드 성공");
				} catch (IllegalStateException e) {
					System.out.println("에러 :" + e.getMessage());
				} catch (IOException e) {
					System.out.println("에러 :" + e.getMessage());
				}
				
				fto.setTb_file_img_path(tb_file_img_path);
				
				file_flag = tipboardFileDAO.writeOk(fto);		
			}
			
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
}

