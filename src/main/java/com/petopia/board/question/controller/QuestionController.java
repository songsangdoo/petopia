package com.petopia.board.question.controller;

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

import com.petopia.board.qna.model.QnABoardTO;
import com.petopia.board.question.model.QuestionAnswerDAO;
import com.petopia.board.question.model.QuestionAnswerFileDAO;
import com.petopia.board.question.model.QuestionAnswerFileTO;
import com.petopia.board.question.model.QuestionAnswerTO;
import com.petopia.board.question.model.QuestionDAO;
import com.petopia.board.question.model.QuestionFileDAO;
import com.petopia.board.question.model.QuestionFileTO;
import com.petopia.board.question.model.QuestionTO;
import com.petopia.board.recommend.model.RecCheckDAO;
import com.petopia.board.recommend.model.RecCheckTO;
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
@ComponentScan({"com.petopia.board.question.model", "com.petopia.board.question.model", "com.petopia.model", "com.petopia.pointshop.model"})
public class QuestionController {

	@Autowired
	private QuestionDAO questionDAO;
	
	@Autowired
	private QuestionFileDAO questionFileDAO;
	
	@Autowired
	private QuestionAnswerDAO questionAnswerDAO;
	
	@Autowired
	private QuestionAnswerFileDAO questionAnswerFileDAO;
	
	@Autowired
	private RecCheckDAO recCheckDAO;
	
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
	
	private int board_no = 9;
	
	@RequestMapping("/question_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("question_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("q_list_flag")) {
					if(cookies[i].getValue().equals("myList")) {
						HttpSession session = request.getSession();
						
						if(session.getAttribute("loginMember") == null) {
							listCookie = null;
							
						}else {
							listCookie = cookies[i];
							
							order_flag = cookies[i].getValue();
						}
					}
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("latest")) {
				Cookie newCookie = new Cookie("q_list_flag", "latest");
				
				listCookie = newCookie;
				
				order_flag = "latest";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("ans")) {
				Cookie newCookie = new Cookie("q_list_flag", "ans");
				
				listCookie = newCookie;
				
				order_flag = "ans";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("hit")) {
				Cookie newCookie = new Cookie("q_list_flag", "hit");
				
				listCookie = newCookie;
				
				order_flag = "hit";
				
				response.addCookie(listCookie);
			}else if(request.getParameter("order_flag").equals("myList")) {
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginMember") == null) {
					order_flag = null;
					
				}else {
					Cookie newCookie = new Cookie("q_list_flag", "myList");
					
					listCookie = newCookie;
					
					order_flag = "myList";
					
					response.addCookie(listCookie);
				}
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("q_list_flag", "latest");
			
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
		
		QuestionTO to = new QuestionTO();
		
		List<QuestionTO> datas = null;
		
		if(option == null && searchStr == null) {
			if(listCookie.getValue().equals("latest")) {
				datas = questionDAO.latestList();
			}else if(listCookie.getValue().equals("ans")) {
				datas = questionDAO.ansList();
			}else if(listCookie.getValue().equals("hit")) {
				datas = questionDAO.hitList();
			}else if(listCookie.getValue().equals("myList")) {
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginMember") != null) {

					MemberTO userData = (MemberTO)session.getAttribute("loginMember");
					
					int m_seq = Integer.parseInt(userData.getM_seq());
					
					to.setM_seq(m_seq);
					
					datas = questionDAO.myList(to);
				}else {
					datas= questionDAO.latestList();
				}
			}
		}else if(option.equals("subject")){
			to.setQ_subject("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = questionDAO.subjectLatestList(to);
			}else if(listCookie.getValue().equals("ans")) {
				datas = questionDAO.subjectAnsList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = questionDAO.subjectHitList(to);
			}else if(listCookie.getValue().equals("myList")) {
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginMember") != null) {

					MemberTO userData = (MemberTO)session.getAttribute("loginMember");
					
					int m_seq = Integer.parseInt(userData.getM_seq());
					
					to.setM_seq(m_seq);
					
					datas = questionDAO.subjectMyList(to);
				}else {
					datas= questionDAO.latestList();
				}
			}
		}else if(option.equals("content")){
			to.setQ_content("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = questionDAO.contentLatestList(to);
			}else if(listCookie.getValue().equals("ans")) {
				datas = questionDAO.contentAnsList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = questionDAO.contentHitList(to);
			}else if(listCookie.getValue().equals("myList")) {
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginMember") != null) {

					MemberTO userData = (MemberTO)session.getAttribute("loginMember");
					
					int m_seq = Integer.parseInt(userData.getM_seq());
					
					to.setM_seq(m_seq);
					
					datas = questionDAO.contentMyList(to);
				}else {
					datas= questionDAO.latestList();
				}
			}
		}
		
		request.setAttribute("datas", datas);
		request.setAttribute("order_flag", order_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/question_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/question_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_write1_ok");
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		if(userData != null) {
			int m_seq =  Integer.parseInt(userData.getM_seq());
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			
			QuestionTO to = new QuestionTO();
			to.setM_seq(m_seq);
			to.setQ_subject(subject);
			to.setQ_content(content);
			
			int flag = 1;
			int file_flag = 1;
			
			flag = questionDAO.writeOk(to);
			
			if(flag == 0) {
				int q_seq = questionDAO.latestList().get(0).getQ_seq();
				
				if(!uploads[0].isEmpty()) {
					for(MultipartFile upload : uploads) {
	
						String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
						String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
						UUID uuidFileImg = UUID.randomUUID(); 
						String q_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
						
						try {
							upload.transferTo(new File(q_file_img_path));
							
							QuestionFileTO fto = new QuestionFileTO();
							fto.setQ_seq(q_seq);
							fto.setQ_file_img_path(q_file_img_path);
							
							file_flag = questionFileDAO.writeOk(fto);
							
							if(file_flag == 1) {
								questionFileDAO.deleteOk(fto);
								questionDAO.deleteOk(to);
								
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
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/question_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_view1");
		
		int q_seq = Integer.parseInt(request.getParameter("q_seq"));

		QuestionTO to = new QuestionTO();
		to.setQ_seq(q_seq);
		
		Cookie[] cookies = request.getCookies();
		
		Cookie viewCookie = null;
		
		Cookie newCookie = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("q_" + q_seq + "_chk")) {
					viewCookie = cookies[i];
				}
				
			}
		}
		
		if(viewCookie == null) {
			newCookie = new Cookie("q_" + q_seq + "_chk", "visited_" + q_seq);
			response.addCookie(newCookie);
			questionDAO.upHit(to);
		}

		QuestionFileTO fto = new QuestionFileTO();
		fto.setQ_seq(q_seq);
		
		QuestionAnswerTO ato = new QuestionAnswerTO();
		ato.setQ_seq(q_seq);
		
		QuestionAnswerFileTO afto = new QuestionAnswerFileTO();
		afto.setQ_seq(q_seq);
		
		QuestionTO data = new QuestionTO();
		data = questionDAO.view(to);
		
		List<QuestionFileTO> file_list = questionFileDAO.view(fto);
		QuestionAnswerTO selected_ans = questionAnswerDAO.viewSelected(ato);
		List<QuestionAnswerTO> ans_list = questionAnswerDAO.view(ato);
		List<QuestionAnswerFileTO>ans_file_list = questionAnswerFileDAO.view(afto);
		
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
		request.setAttribute("selected_ans", selected_ans);
		request.setAttribute("ans_list", ans_list);
		request.setAttribute("ans_file_list", ans_file_list);
		
		request.setAttribute("w_info", w_info);
		request.setAttribute("w_gradeChecks", w_gradeChecks);
		
		request.setAttribute("m_info_datas", m_info_datas);
		request.setAttribute("grade_info_datas", grade_info_datas);
		
		modelAndView.addObject( "profileCardInfo", profileCardInfo );
		modelAndView.addObject( "skinPointShopList", skinPointShopList );
		modelAndView.addObject( "badgePointShopList", badgePointShopList );
		modelAndView.addObject( "myBadgeImgList", myBadgeImgList );
		modelAndView.addObject( "mySkinImg", mySkinImg );
		
		modelAndView.addObject( "petCheckLists", petCheckLists );
		
		return modelAndView;
	}
	
	@RequestMapping("/question_ans_write.do")
	public ModelAndView ansWrite(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_answer1");
		
		int q_seq = Integer.parseInt(request.getParameter("q_seq"));
		
		QuestionTO to = new QuestionTO();
		to.setQ_seq(q_seq);
		
		QuestionFileTO fto = new QuestionFileTO();
		fto.setQ_seq(q_seq);
		
		QuestionTO data = questionDAO.view(to);
		List<QuestionFileTO> file_list = questionFileDAO.view(fto);
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);
		
		return modelAndView;
	}
	
	@RequestMapping("/question_ans_write_ok.do")
	public ModelAndView ansWriteOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_answer1_ok");
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		if(userData != null) {
			int m_seq = Integer.parseInt(userData.getM_seq());
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			
			RecCheckTO rto = new RecCheckTO();
			rto.setBoard_no(board_no);
			rto.setBoard_seq(q_seq);
			rto.setM_seq(m_seq);
			
			RecCheckTO ansCheck = recCheckDAO.check(rto);
			
			int flag = 1;
			int file_flag = 1;
			
			if(ansCheck == null) {
				
				String content = request.getParameter("content").replace("\n", "<br>");
				
				QuestionAnswerTO ato = new QuestionAnswerTO();
				ato.setQ_seq(q_seq);
				ato.setM_seq(m_seq);
				ato.setQ_ans_content(content);
				
				
				flag = questionAnswerDAO.writeOk(ato);
				
				QuestionTO to = new QuestionTO(); 
				to.setQ_seq(q_seq);
				
				if(flag == 0) {
					QuestionAnswerTO qto = new QuestionAnswerTO();
					qto.setQ_seq(q_seq); 
					
					int q_ans_seq = questionAnswerDAO.view(qto).get(0).getQ_ans_seq();
					
					if(!uploads[0].isEmpty()) {
						for(MultipartFile upload : uploads) {
		
							String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
							String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
							UUID uuidFileImg = UUID.randomUUID(); 
							String q_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
							
							try {
								upload.transferTo(new File(q_file_img_path));
								
								QuestionAnswerFileTO afto = new QuestionAnswerFileTO();
								afto.setQ_seq(q_seq);
								afto.setQ_ans_seq(q_ans_seq);
								afto.setQ_ans_file_img_path(q_file_img_path);
								
								file_flag = questionAnswerFileDAO.writeOk(afto);
								
								if(file_flag == 1) {
									questionAnswerFileDAO.deleteOk(afto);
									questionAnswerDAO.deleteOk(ato);
									
									flag = 1;
									
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
				
				if(flag == 0 && file_flag == 0) {
					questionDAO.upAns(to);
					recCheckDAO.insert(rto);
				}
			}else {
				flag = 2;
				file_flag = 2;
			}
			
			request.setAttribute("flag", flag);
			request.setAttribute("file_flag", file_flag);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("question_ans_select.do")
	public ModelAndView selectAns(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		if(userData != null) {
			int m_seq =  Integer.parseInt(userData.getM_seq());
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			int q_ans_seq = Integer.parseInt(request.getParameter("q_ans_seq"));
			
			QuestionTO to = new QuestionTO();
			to.setQ_seq(q_seq);
			to.setM_seq(m_seq);
			
			int select_flag = 1;
			int	ans_select_flag = 1;
			
			select_flag = questionDAO.selectAns(to);
			
			if(select_flag == 0) {
				QuestionAnswerTO ato = new QuestionAnswerTO();
				ato.setQ_seq(q_seq);
				ato.setQ_ans_seq(q_ans_seq);
				
				ans_select_flag = questionAnswerDAO.selectAns(ato);
				
				QuestionAnswerTO selected_ans_info = questionAnswerDAO.selectAnsInfo(ato);
				
				MemberTO mto = new MemberTO();
				mto.setM_seq(String.valueOf(selected_ans_info.getM_seq()));
				
				memberDAO.upPoint20(mto);
				memberDAO.upGrade(mto);
			}
			
			request.setAttribute("ans_select_flag", ans_select_flag);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/question_ans_del.do")
	public ModelAndView deleteAns(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		if(userData != null) {
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			int q_ans_seq = Integer.parseInt(request.getParameter("q_ans_seq"));
			int m_seq =  Integer.parseInt(userData.getM_seq());
			
			int ans_del_flag = 1;
			
			QuestionTO to = new QuestionTO();
			to.setQ_seq(q_seq);
			
			QuestionAnswerTO ato = new QuestionAnswerTO();
			ato.setQ_seq(q_seq);
			ato.setQ_ans_seq(q_ans_seq);
			
			QuestionAnswerFileTO afto = new QuestionAnswerFileTO();
			
			if(userData.getGrade_seq().equals("1")) {
				
				ans_del_flag = questionAnswerDAO.adminDeleteOk(ato);
				
			}else {
				ato.setM_seq(m_seq);
				
				ans_del_flag = questionAnswerDAO.deleteOk(ato);
			}
			
			if(ans_del_flag == 0) {
				afto.setQ_seq(q_seq);
				afto.setQ_ans_seq(q_ans_seq);
				
				int ans_del_file_flag = questionAnswerFileDAO.deleteOk(afto);

				questionDAO.downAns(to);
				
				RecCheckTO rto = new RecCheckTO();
				rto.setBoard_no(board_no);
				rto.setBoard_seq(q_seq);
				rto.setM_seq(m_seq);
				
				recCheckDAO.deleteAns(rto);
			}
			
			request.setAttribute("ans_del_flag", ans_del_flag);
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping("/question_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		if(userData != null) {
			int flag = 1;
			int file_flag = 1;
			int ans_flag = 1;
			
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			
			if(userData.getGrade_seq().equals("1")) {
				
				QuestionTO to = new QuestionTO();
				to.setQ_seq(q_seq);
				
				flag = questionDAO.adminDeleteOk(to);
				
			}else {
				int m_seq =  Integer.parseInt(userData.getM_seq());
				
				QuestionTO to = new QuestionTO();
				to.setQ_seq(q_seq);
				to.setM_seq(m_seq);
				
				flag = questionDAO.deleteOk(to);
			}
			
			QuestionFileTO fto = new QuestionFileTO();
			fto.setQ_seq(q_seq);
			
			QuestionAnswerTO ato = new QuestionAnswerTO();
			ato.setQ_seq(q_seq);

			if(flag == 0) {
				
				file_flag = questionFileDAO.deleteOk(fto);
				
				ans_flag = questionAnswerDAO.deleteAllOk(ato);
				
				if(ans_flag == 0) {
					QuestionAnswerFileTO afto = new QuestionAnswerFileTO();
					afto.setQ_seq(q_seq);
					
					int ans_file_flag = questionAnswerFileDAO.deleteAllOk(afto);
					
					RecCheckTO rto = new RecCheckTO();
					rto.setBoard_no(board_no);
					rto.setBoard_seq(q_seq);
					
					recCheckDAO.deleteAll(rto);
				}
			}
			
			request.setAttribute("flag", flag);
			request.setAttribute("file_flag", file_flag);
			request.setAttribute("ans_flag", ans_flag);
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping("/question_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_modify1");
		
		int q_seq = Integer.parseInt(request.getParameter("q_seq"));
		
		QuestionTO to = new QuestionTO();
		to.setQ_seq(q_seq);
		
		QuestionTO data = new QuestionTO();
		data = questionDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/question_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null) {
			
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			
			QuestionTO to = new QuestionTO();
			to.setQ_seq(q_seq);
			to.setQ_subject(subject);
			to.setQ_content(content);
			
			QuestionFileTO fto = new QuestionFileTO();
			fto.setQ_seq(q_seq);
			
			List<QuestionFileTO> old_file_img_path_list = questionFileDAO.view(fto);
			
			flag = questionDAO.modifyOk(to);
			
			if(uploads[0].getOriginalFilename().trim().equals("")) {
				file_flag = 0;
			}else {
				int old_file_del_flag = questionFileDAO.deleteOk(fto);
				
				if(old_file_del_flag == 0) {
					for(QuestionFileTO old_file_img_path : old_file_img_path_list) {
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
					String q_file_img_path = filename + "_" + uuidFileImg + ext;
					
					try {
						upload.transferTo(new File(q_file_img_path));
						
						System.out.println("수정된 파일 업로드 성공");
					} catch (IllegalStateException e) {
						System.out.println("에러 :" + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 :" + e.getMessage());
					}
					
					fto.setQ_file_img_path(q_file_img_path);
					
					file_flag = questionFileDAO.writeOk(fto);		
				}
				
			}
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/question_ans_modify.do")
	public ModelAndView ansModify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_answer_modify1");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int q_seq = Integer.parseInt(request.getParameter("q_seq"));
		
		QuestionTO to = new QuestionTO();
		to.setQ_seq(q_seq);
		
		QuestionFileTO fto = new QuestionFileTO();
		fto.setQ_seq(q_seq);
		
		QuestionTO data = questionDAO.view(to);
		List<QuestionFileTO> file_list = questionFileDAO.view(fto);
		
		int q_ans_seq = Integer.parseInt(request.getParameter("q_ans_seq"));
		
		QuestionAnswerTO ato = new QuestionAnswerTO();
		ato.setQ_seq(q_seq);
		ato.setQ_ans_seq(q_ans_seq);
		
		QuestionAnswerTO ans_data = questionAnswerDAO.modify(ato);
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);
		request.setAttribute("ans_data", ans_data);

		return modelAndView;
	}
	
	@RequestMapping("/question_ans_modify_ok.do")
	public ModelAndView ansModifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("question_views/board_answer_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null) {
			
			int q_seq = Integer.parseInt(request.getParameter("q_seq"));
			int q_ans_seq = Integer.parseInt(request.getParameter("q_ans_seq"));
			
			String content = request.getParameter("content").replace("\n", "<br>");
			
			QuestionAnswerTO to = new QuestionAnswerTO();
			to.setQ_seq(q_seq);
			to.setQ_ans_seq(q_ans_seq);
			to.setQ_ans_content(content);
			
			QuestionAnswerFileTO fto = new QuestionAnswerFileTO();
			fto.setQ_seq(q_seq);
			fto.setQ_ans_seq(q_ans_seq);
			
			List<QuestionAnswerFileTO> old_file_img_path_list = questionAnswerFileDAO.modify(fto);
			
			flag = questionAnswerDAO.modifyOk(to);
			
			if(uploads[0].getOriginalFilename().trim().equals("")) {
				file_flag = 0;
			}else {
				int old_file_del_flag = questionAnswerFileDAO.deleteOk(fto);
				
				if(old_file_del_flag == 0) {
					for(QuestionAnswerFileTO old_file_img_path : old_file_img_path_list) {
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
					String q_file_img_path = filename + "_" + uuidFileImg + ext;
					
					try {
						upload.transferTo(new File(q_file_img_path));
						
						System.out.println("수정된 파일 업로드 성공");
					} catch (IllegalStateException e) {
						System.out.println("에러 :" + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 :" + e.getMessage());
					}
					
					fto.setQ_ans_file_img_path(q_file_img_path);
					
					file_flag = questionAnswerFileDAO.writeOk(fto);		
				}
				
			}
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
}
