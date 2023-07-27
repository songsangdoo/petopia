package com.petopia.group.board.controller;

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

import com.petopia.board.free.model.FreeBoardCommentTO;
import com.petopia.board.recommend.model.RecCheckDAO;
import com.petopia.board.recommend.model.RecCheckTO;
import com.petopia.board.recommend.model.RecCmtCheckDAO;
import com.petopia.board.recommend.model.RecCmtCheckTO;
import com.petopia.group.board.model.GroupBoardCommentDAO;
import com.petopia.group.board.model.GroupBoardCommentTO;
import com.petopia.group.board.model.GroupBoardDAO;
import com.petopia.group.board.model.GroupBoardFileDAO;
import com.petopia.group.board.model.GroupBoardFileTO;
import com.petopia.group.board.model.GroupBoardTO;
import com.petopia.model.MemberTO;

@RestController
@ComponentScan({"com.petopia.group.board.model", "com.petopia.board.recommend.model"})
public class GroupBoardController {

	@Autowired
	private GroupBoardDAO groupBoardDAO;
	@Autowired
	private GroupBoardFileDAO groupBoardFileDAO;
	@Autowired
	private GroupBoardCommentDAO groupBoardCommentDAO;
	@Autowired
	private RecCheckDAO recCheckDAO;
	@Autowired
	private RecCmtCheckDAO recCmtCheckDAO;
	@Autowired
	private HttpSession session;

	@RequestMapping("/mygroup_board_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{		
			modelAndView.setViewName("group_views/mygroup_board_list");
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			Cookie[] cookies = request.getCookies();
			
			Cookie listCookie = null;
			
			if(cookies != null && cookies.length > 0) {
				for(int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("fb_list_flag")) {
						listCookie = cookies[i];
					}
				}
			}
			
			if(request.getParameter("order_flag") != null) {
				if(request.getParameter("order_flag").equals("latest")) {
					Cookie newCookie = new Cookie("fb_list_flag", "latest");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
					
				}else if(request.getParameter("order_flag").equals("rec")) {
					Cookie newCookie = new Cookie("fb_list_flag", "rec");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
					
				}else if(request.getParameter("order_flag").equals("cmt")) {
					Cookie newCookie = new Cookie("fb_list_flag", "cmt");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
					
				}else if(request.getParameter("order_flag").equals("hit")) {
					Cookie newCookie = new Cookie("fb_list_flag", "hit");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
				}
			}
			
			if(listCookie == null) {
				Cookie newCookie = new Cookie("fb_list_flag", "latest");
				
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
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGR_SEQ(gr_seq); 
			
			List<GroupBoardTO> datas = null;
			
			if(option == null && searchStr == null) {
				if(listCookie.getValue().equals("latest")) {
					datas = groupBoardDAO.latestList(to);
				}else if(listCookie.getValue().equals("rec")) {
					datas = groupBoardDAO.recList(to);
				}else if(listCookie.getValue().equals("cmt")) {
					datas = groupBoardDAO.cmtList(to);
				}else if(listCookie.getValue().equals("hit")) {
					datas = groupBoardDAO.hitList(to);
				}
			}else if(option.equals("subject")){
				to.setGR_SUBJECT("%" + searchStr + "%");
				if(listCookie.getValue().equals("latest")) {
					datas = groupBoardDAO.subjectLatestList(to);
				}else if(listCookie.getValue().equals("rec")) {
					datas = groupBoardDAO.subjectRecList(to);
				}else if(listCookie.getValue().equals("cmt")) {
					datas = groupBoardDAO.subjectCmtList(to);
				}else if(listCookie.getValue().equals("hit")) {
					datas = groupBoardDAO.subjectHitList(to);
				}
			}else if(option.equals("writer")){
				to.setM_NICKNAME(searchStr);
				if(listCookie.getValue().equals("latest")) {
					datas = groupBoardDAO.writerLatestList(to);
				}else if(listCookie.getValue().equals("rec")) {
					datas = groupBoardDAO.writerRecList(to);
				}else if(listCookie.getValue().equals("cmt")) {
					datas = groupBoardDAO.writerCmtList(to);
				}else if(listCookie.getValue().equals("hit")) {
					datas = groupBoardDAO.writerHitList(to);
				}
			}else if(option.equals("content")){
				to.setGR_CONTENT("%" + searchStr + "%");
				if(listCookie.getValue().equals("latest")) {
					datas = groupBoardDAO.contentLatestList(to);
				}else if(listCookie.getValue().equals("rec")) {
					datas = groupBoardDAO.contentRecList(to);
				}else if(listCookie.getValue().equals("cmt")) {
					datas = groupBoardDAO.contentCmtList(to);
				}else if(listCookie.getValue().equals("hit")) {
					datas = groupBoardDAO.contentHitList(to);
				}
			}
			
			request.setAttribute("datas", datas);
		 }
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		}else{
			modelAndView.setViewName("group_views/mygroup_board_write");
		}
			
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			modelAndView.setViewName("group_views/mygroup_board_write_ok");

			int m_seq =  Integer.parseInt(userData.getM_seq());
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
	
			GroupBoardTO to = new GroupBoardTO();
			to.setGR_SEQ(gr_seq);
			to.setM_SEQ(m_seq);
			to.setGR_SUBJECT(subject);
			to.setGR_CONTENT(content);
			
			int flag = 1;
			int file_flag = 1;
			int rec_insert = 1;
			
			flag = groupBoardDAO.writeOk(to);
			
			if(flag == 0) {
				int grb_seq = groupBoardDAO.latestList(to).get(0).getGRB_SEQ();
				RecCheckTO rcto = new RecCheckTO();
				rcto.setBoard_no(gr_seq+10);
				rcto.setBoard_seq(grb_seq);
				rcto.setM_seq(m_seq);
				
				rec_insert = recCheckDAO.insert(rcto);
				
				if(!uploads[0].isEmpty()) {
					for(MultipartFile upload : uploads) {
	
						String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
						String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
						UUID uuidFileImg = UUID.randomUUID(); 
						String gr_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
						
						try {
							upload.transferTo(new File(gr_file_img_path));
							
							GroupBoardFileTO fto = new GroupBoardFileTO();
							
							fto.setGRB_SEQ(grb_seq);
							fto.setGR_SEQ(gr_seq);
							fto.setGRB_FILE_IMG_PATH(gr_file_img_path);
							
							file_flag = groupBoardFileDAO.writeOk(fto);
							
							if(file_flag == 1) {
								groupBoardFileDAO.deleteOk(fto);
								groupBoardDAO.deleteOk(to);
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

	@RequestMapping("/mygroup_board_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			modelAndView.setViewName("group_views/mygroup_board_view");
			
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			Cookie[] cookies = request.getCookies();
			
			Cookie viewCookie = null;
			Cookie listCookie = null;
			
			Cookie newCookie = null;
			
			if(cookies != null && cookies.length > 0) {
				for(int i = 0; i< cookies.length; i++) {
					if(cookies[i].getName().equals("fb_" + grb_seq + "_chk")) {///확인해보기
						viewCookie = cookies[i];
					}
					
					if(cookies[i].getName().equals("fb_cmt_list_flag")) {
						listCookie = cookies[i];
					}
				}
			}
			
			if(request.getParameter("order_flag") != null) {
				if(request.getParameter("order_flag").equals("rec")) {
					newCookie = new Cookie("fb_cmt_list_flag", "rec");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
				}else {
					newCookie = new Cookie("fb_cmt_list_flag", "latest");
					
					listCookie = newCookie;
					
					response.addCookie(listCookie);
				}
			}
			
			if(viewCookie == null) {
				newCookie = new Cookie("fb_" + grb_seq + "_chk", "visited_" + grb_seq);
				response.addCookie(newCookie);
				groupBoardDAO.upHit(to);
			}
			
			if(listCookie == null) {
				newCookie = new Cookie("fb_cmt_list_flag", "latest");
				listCookie = newCookie;
					
				response.addCookie(listCookie);
			}
			
			GroupBoardFileTO fto = new GroupBoardFileTO();
			fto.setGRB_SEQ(grb_seq);
			fto.setGR_SEQ(gr_seq);
			
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGRB_SEQ(grb_seq);
			cto.setGR_SEQ(gr_seq);
			
			GroupBoardTO data = new GroupBoardTO();
			data = groupBoardDAO.view(to);
			
			List<GroupBoardFileTO> file_list = groupBoardFileDAO.view(fto);
			List<GroupBoardCommentTO> cmt_list = new ArrayList<>();
			
			if(listCookie.getValue().equals("latest")) {
				cmt_list = groupBoardCommentDAO.latestList(cto);
			}else {
				cmt_list = groupBoardCommentDAO.recList(cto);
			}
			
			request.setAttribute("data", data);
			request.setAttribute("file_list", file_list);
			request.setAttribute("cmt_list", cmt_list);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_view_rec.do")
	public ModelAndView upRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		}else{	
			modelAndView.setViewName("group_views/mygroup_board_view2");
			
			int m_seq =  Integer.parseInt(userData.getM_seq());
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_seq =  Integer.parseInt(request.getParameter("gr_seq"));
			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			int rec_flag = 1;
			int rec_insert = 1; 
			
			RecCheckTO rcto = new RecCheckTO();
			rcto.setBoard_no(gr_seq+10);
			rcto.setBoard_seq(grb_seq);
			rcto.setM_seq(m_seq);
			
			if(recCheckDAO.check(rcto) == null) {
				rec_flag = groupBoardDAO.upRec(to);
				
				rec_insert = recCheckDAO.insert(rcto);
				
			}
			
			request.setAttribute("rec_flag", rec_flag);
			request.setAttribute("rec_insert", rec_insert);
		}
		
		return modelAndView;
	}
	
	
	//view2 해결하기 gr_seq reqeust 잘 기억하기
	@RequestMapping("/mygroup_board_view_cmt_write.do")
	public ModelAndView cmtWrite(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			 
			modelAndView.setViewName("group_views/mygroup_board_view2");

			int m_seq =  Integer.parseInt(userData.getM_seq());
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			//String fb_cmt_content = request.getParameter("fb_cmt_content").replaceAll("/n", "<br>");
			String gr_co_content = request.getParameter("gr_co_content").replaceAll("/n", "<br>");
	
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGR_SEQ(gr_seq);
			cto.setGRB_SEQ(grb_seq);
			cto.setM_SEQ(m_seq);
			cto.setGR_CO_CONTENT(gr_co_content);
			
			int cmt_write_flag = groupBoardCommentDAO.writeOk(cto);
			
			int grb_cmt_flag = 1;
			int rec_check_flag = 1;
			
			if(cmt_write_flag == 0) {
				grb_cmt_flag = groupBoardDAO.upCmt(to);
				
				if(grb_cmt_flag == 0) {
					int gr_co_seq = groupBoardCommentDAO.latestList(cto).get(0).getGR_CO_SEQ();
					
					RecCmtCheckTO rccto = new RecCmtCheckTO();
					rccto.setBoard_no(gr_seq+10);
					rccto.setBoard_seq(grb_seq);
					rccto.setCmt_seq(gr_co_seq);
					rccto.setM_seq(m_seq);
					
					rec_check_flag = recCmtCheckDAO.insert(rccto);
				}
			}
			request.setAttribute("rec_cmt_flag", rec_check_flag);
			request.setAttribute("cmt_write_flag", cmt_write_flag);
			request.setAttribute("grb_cmt_flag", grb_cmt_flag);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_view_cmt_rec.do")
	public ModelAndView upCoRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{	
			modelAndView.setViewName("group_views/mygroup_board_view2");
			
			int m_seq =  Integer.parseInt(userData.getM_seq());
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_co_seq = Integer.parseInt(request.getParameter("gr_co_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGR_SEQ(gr_seq);
			to.setGRB_SEQ(grb_seq);
			
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGR_SEQ(gr_seq);
			cto.setGRB_SEQ(grb_seq);
			cto.setGR_CO_SEQ(gr_co_seq);
			
			int cmt_rec_flag = 1;
			int rec_cmt_check_flag = 1;
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(gr_seq+10);
			rccto.setBoard_seq(grb_seq);
			rccto.setCmt_seq(gr_co_seq);
			rccto.setM_seq(m_seq);
			
			if(recCmtCheckDAO.check(rccto) == null) {
				cmt_rec_flag = groupBoardCommentDAO.upRec(cto);
				
				rec_cmt_check_flag = recCmtCheckDAO.insert(rccto);
			}
			
			request.setAttribute("cmt_rec_flag", cmt_rec_flag);
			request.setAttribute("rec_cmt_check_flag", rec_cmt_check_flag);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("mygroup_board_view_cmt_no_rec.do")
	public ModelAndView upCmtNoRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{	
			modelAndView.setViewName("group_views/mygroup_board_view2");
			
			int m_seq =  Integer.parseInt(userData.getM_seq());
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_co_seq = Integer.parseInt(request.getParameter("gr_co_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGR_SEQ(gr_seq);
			to.setGRB_SEQ(grb_seq);
			
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGR_SEQ(gr_seq);
			cto.setGRB_SEQ(grb_seq);
			cto.setGR_CO_SEQ(gr_co_seq);
			
			int cmt_no_rec_flag = 1;
			int rec_cmt_check_flag = 1;
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(gr_seq+10);
			rccto.setBoard_seq(grb_seq);
			rccto.setCmt_seq(gr_co_seq);
			rccto.setM_seq(m_seq);
			
			if(recCmtCheckDAO.check(rccto) == null) {
				cmt_no_rec_flag = groupBoardCommentDAO.upNoRec(cto);
				
				rec_cmt_check_flag = recCmtCheckDAO.insert(rccto);
			}
			
			request.setAttribute("cmt_no_rec_flag", cmt_no_rec_flag);
			request.setAttribute("rec_cmt_check_flag", rec_cmt_check_flag);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_view_cmt_del.do")
	public ModelAndView deleteCo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			modelAndView.setViewName("group_views/mygroup_board_view2");
			
			int m_seq =  Integer.parseInt(userData.getM_seq());
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_co_seq = Integer.parseInt(request.getParameter("gr_co_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));

			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGRB_SEQ(grb_seq);
			cto.setGR_SEQ(gr_seq);
			cto.setGR_CO_SEQ(gr_co_seq);
			
			int cmt_del_flag = 1;
			int rec_cmt_del_check_flag = 1;
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(gr_seq+10);
			rccto.setBoard_seq(grb_seq);
			rccto.setCmt_seq(gr_co_seq);
			
			cmt_del_flag = groupBoardCommentDAO.deleteOk(cto);
		
			if(cmt_del_flag == 0) {
				groupBoardDAO.downCmt(to);
			}
			
			request.setAttribute("cmt_del_flag", cmt_del_flag);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			modelAndView.setViewName("group_views/mygroup_board_delete_ok");
			
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			
			GroupBoardFileTO fto = new GroupBoardFileTO();
			fto.setGRB_SEQ(grb_seq);
			fto.setGR_SEQ(gr_seq);
			
			GroupBoardCommentTO cto = new GroupBoardCommentTO();
			cto.setGRB_SEQ(grb_seq);
			cto.setGR_SEQ(gr_seq);
			
			RecCheckTO rcto = new RecCheckTO();
			rcto.setBoard_no(gr_seq+10);
			rcto.setBoard_seq(grb_seq);
			
			RecCmtCheckTO rccto = new RecCmtCheckTO();
			rccto.setBoard_no(gr_seq+10);
			rccto.setBoard_seq(grb_seq);
			
			int flag = 1;
			int file_flag = 1;
			int cmt_flag = 1;
			int rec_del_flag = 1;
			int rec_cmt_del_flag = 1;
			
			file_flag =	groupBoardFileDAO.deleteOk(fto);
			
			cmt_flag = groupBoardCommentDAO.deleteAllOk(cto);
			
			flag = groupBoardDAO.deleteOk(to);
			
			rec_del_flag = recCheckDAO.deleteAll(rcto);
			
			rec_cmt_del_flag = recCmtCheckDAO.postDeleteAll(rccto);
			
			request.setAttribute("flag", flag);
			request.setAttribute("file_flag", file_flag);
			request.setAttribute("cmt_flag", cmt_flag);
		 }
		
		return modelAndView;
	}
	
	@RequestMapping("/mygroup_board_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
 		if(userData == null){
			modelAndView.setViewName("default_bar/alert_please_login");
		 }else{
			modelAndView.setViewName("group_views/mygroup_board_modify");
			
			int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
			int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
			
			GroupBoardTO to = new GroupBoardTO();
			to.setGRB_SEQ(grb_seq);
			to.setGR_SEQ(gr_seq);
			
			GroupBoardTO data = new GroupBoardTO();
			data = groupBoardDAO.modify(to);
			
			request.setAttribute("data", data);
		 }
			
			return modelAndView;
		}
		
		@RequestMapping("/mygroup_board_modify_ok.do")
		public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
			ModelAndView modelAndView = new ModelAndView();
			MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	 		if(userData == null){
				modelAndView.setViewName("default_bar/alert_please_login");
			 }else{
				modelAndView.setViewName("group_views/mygroup_board_modify_ok");
				
				int grb_seq = Integer.parseInt(request.getParameter("grb_seq"));
				int gr_seq = Integer.parseInt(request.getParameter("gr_seq"));
				
				String subject = request.getParameter("subject");
				String content = request.getParameter("content").replace("\n", "<br>");
				
				GroupBoardTO to = new GroupBoardTO();
				to.setGR_SEQ(gr_seq);
				to.setGRB_SEQ(grb_seq);
				to.setGR_SUBJECT(subject);
				to.setGR_CONTENT(content);
				
				int flag = 1;
				int file_flag = 1;
				
				GroupBoardFileTO fto = new GroupBoardFileTO();
				fto.setGRB_SEQ(grb_seq);
				
				List<GroupBoardFileTO> old_file_img_path_list = groupBoardFileDAO.view(fto);
				
				flag = groupBoardDAO.modifyOk(to);
				
				if(uploads[0].getOriginalFilename().trim().equals("")) {
					file_flag = 0;
				}else {
					int old_file_del_flag = groupBoardFileDAO.deleteOk(fto);
					
					if(old_file_del_flag == 0) {
						for(GroupBoardFileTO old_file_img_path : old_file_img_path_list) {
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
						String grb_file_img_path = filename + "_" + uuidFileImg + ext;
								
						try {
							upload.transferTo(new File(grb_file_img_path));
							
							System.out.println("수정된 파일 업로드 성공");
						} catch (IllegalStateException e) {
							System.out.println("에러 :" + e.getMessage());
						} catch (IOException e) {
							System.out.println("에러 :" + e.getMessage());
						}
						
						fto.setGRB_FILE_IMG_PATH(grb_file_img_path);
						
						file_flag = groupBoardFileDAO.writeOk(fto);		
					}
					
				}
				
				request.setAttribute("flag", flag);
				request.setAttribute("file_flag", file_flag);
			  }
			
		return modelAndView;
	}
}
