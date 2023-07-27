package com.petopia.board.notice.controller;

import java.io.File;
import java.io.IOException;
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

import com.petopia.board.notice.model.NoticeDAO;
import com.petopia.board.notice.model.NoticeFileDAO;
import com.petopia.board.notice.model.NoticeFileTO;
import com.petopia.board.notice.model.NoticeTO;
import com.petopia.model.MemberTO;

@RestController
@ComponentScan("com.petopia.board.notice.model")
public class NoticeController {

	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private NoticeFileDAO noticeFileDAO;

	@RequestMapping("/notice_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("notice_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("n_list_flag")) {
					listCookie = cookies[i];
					
					order_flag = listCookie.getValue();
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("latest")) {
				Cookie newCookie = new Cookie("n_list_flag", "latest");
				
				listCookie = newCookie;
				
				order_flag = "latest";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("hit")) {
				Cookie newCookie = new Cookie("n_list_flag", "hit");
				;
				listCookie = newCookie;
				
				order_flag = "hit";
				
				response.addCookie(listCookie);
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("n_list_flag", "latest");
			
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
		
		List<NoticeTO> datas = null;
		
		NoticeTO to = new NoticeTO();
		
		if(option == null && searchStr == null) {
			if(listCookie.getValue().equals("latest")) {
				datas = noticeDAO.latestList();
			}else if(listCookie.getValue().equals("hit")) {
				datas = noticeDAO.hitList();
			}
		}else if(option.equals("subject")){
			to.setN_subject("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = noticeDAO.subjectLatestList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = noticeDAO.subjectHitList(to);
			}
		}else if(option.equals("content")){
			to.setN_content("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = noticeDAO.contentLatestList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = noticeDAO.contentHitList(to);
			}
		}
		
		List<NoticeTO> fixedList = noticeDAO.fixedList();
		
		if(fixedList.size() != 0) {
			request.setAttribute("fixedList", fixedList);
		}
		
		request.setAttribute("datas", datas);
		request.setAttribute("order_flag", order_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_write1_ok");
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		String fix = request.getParameter("fix");
		
		NoticeTO to = new NoticeTO();
		to.setM_seq(m_seq);
		to.setN_subject(subject);
		to.setN_content(content);
		to.setN_fix(fix);
		
		int flag = 1;
		int file_flag = 1;
		
		flag = noticeDAO.writeOk(to);
		
		if(flag == 0) {
			int n_seq;
			
			if(fix.equals("Y")) {
				n_seq = noticeDAO.fixedList().get(0).getN_seq();
				
			}else {
				n_seq = noticeDAO.latestList().get(0).getN_seq();
			}

			if(!uploads[0].isEmpty()) {
				for(MultipartFile upload : uploads) {

					String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
					String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
					UUID uuidFileImg = UUID.randomUUID(); 
					String n_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
					
					try {
						upload.transferTo(new File(n_file_img_path));
						
						NoticeFileTO fto = new NoticeFileTO();
						fto.setN_seq(n_seq);
						fto.setN_file_img_path(n_file_img_path);
						
						file_flag = noticeFileDAO.writeOk(fto);
						
						if(file_flag == 1) {
							noticeFileDAO.deleteOk(fto);
							noticeDAO.deleteOk(to);
							
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
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_view1");
		
		int n_seq = Integer.parseInt(request.getParameter("n_seq"));
		
		Cookie[] cookies = request.getCookies();
		
		Cookie viewCookie = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("n_" + n_seq + "_chk")) {
					viewCookie = cookies[i];
				}
			}
		}
		
		
		NoticeTO to = new NoticeTO();
		to.setN_seq(n_seq);
		
		if(viewCookie == null) {
			Cookie newCookie = new Cookie("n_" + n_seq + "_chk", "visited" + n_seq);
			response.addCookie(newCookie);
			
			noticeDAO.upHit(to);
		}
		
		NoticeFileTO fto = new NoticeFileTO();
		fto.setN_seq(n_seq);
		
		NoticeTO data = new NoticeTO();
		data = noticeDAO.view(to);
		
		List<NoticeFileTO> file_list = noticeFileDAO.view(fto);
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");

		int flag = 1;
		int file_flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int n_seq = Integer.parseInt(request.getParameter("n_seq"));
			
			NoticeTO to = new NoticeTO();
			to.setN_seq(n_seq);
			
			NoticeFileTO fto = new NoticeFileTO();
			fto.setN_seq(n_seq);
			
			
			file_flag = noticeFileDAO.deleteOk(fto);
			
			flag = noticeDAO.deleteOk(to);
			
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_modify1");
		
		int n_seq = Integer.parseInt(request.getParameter("n_seq"));
		
		NoticeTO to = new NoticeTO();
		to.setN_seq(n_seq);
		
		NoticeTO data = new NoticeTO();
		data = noticeDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/notice_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("notice_views/board_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");

		int flag = 1;
		int file_flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int n_seq = Integer.parseInt(request.getParameter("n_seq"));
			
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			String fix = request.getParameter("fix");
			
			NoticeTO to = new NoticeTO();
			to.setN_seq(n_seq);
			to.setN_subject(subject);
			to.setN_content(content);
			to.setN_fix(fix);
			
			
			NoticeFileTO fto = new NoticeFileTO();
			fto.setN_seq(n_seq);
			
			List<NoticeFileTO> old_file_img_path_list = noticeFileDAO.view(fto);
			
			flag = noticeDAO.modifyOk(to);
			
			if(uploads[0].getOriginalFilename().trim().equals("")) {
				file_flag = 0;
			}else {
				int old_file_del_flag = noticeFileDAO.deleteOk(fto);
				
				if(old_file_del_flag == 0) {
					for(NoticeFileTO old_file_img_path : old_file_img_path_list) {
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
					String n_file_img_path = filename + "_" + uuidFileImg + ext;
					
					try {
						upload.transferTo(new File(n_file_img_path));
						
						System.out.println("수정된 파일 업로드 성공");
					} catch (IllegalStateException e) {
						System.out.println("에러 :" + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 :" + e.getMessage());
					}
					
					fto.setN_file_img_path(n_file_img_path);
					
					file_flag = noticeFileDAO.writeOk(fto);		
				}
				
			}
			
			request.setAttribute("flag", flag);
			request.setAttribute("file_flag", file_flag);
		}
		
		return modelAndView;
	}
}

