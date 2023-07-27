package com.petopia.board.qna.controller;

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
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.board.qna.model.QnABoardDAO;
import com.petopia.board.qna.model.QnABoardFileDAO;
import com.petopia.board.qna.model.QnABoardFileTO;
import com.petopia.board.qna.model.QnABoardTO;
import com.petopia.board.recommend.model.RecCheckTO;
import com.petopia.board.recommend.model.RecCmtCheckTO;
import com.petopia.model.MemberTO;

@RestController
@ComponentScan("com.petopia.board.qna.model")
public class QnABoardController {
	
	@Autowired
	private QnABoardDAO qnaboardDAO;
	@Autowired
	private QnABoardFileDAO qnaboardFileDAO;

	@RequestMapping("/qnaboard_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("qnaboard_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("qa_list_flag")) {
					
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
			if(request.getParameter("order_flag").equals("no_secret")) {
				Cookie newCookie = new Cookie("qa_list_flag", "no_secret");
				
				listCookie = newCookie;
				
				order_flag = "no_secret";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("myList")) {
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginMember") == null) {
					order_flag = null;
					
				}else {
					Cookie newCookie = new Cookie("qa_list_flag", "myList");
					
					listCookie = newCookie;
					
					order_flag = "myList";
					
					response.addCookie(listCookie);
				}
				
			}else if(request.getParameter("order_flag").equals("answer_yet")) {
				Cookie newCookie = new Cookie("qa_list_flag", "answer_yet");
				
				listCookie = newCookie;
				
				order_flag = "answer_yet";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("all")) {
				Cookie newCookie = new Cookie("qa_list_flag", "all");
				
				listCookie = newCookie;
				
				order_flag = "all";
				
				response.addCookie(listCookie);
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("qa_list_flag", "all");
			
			listCookie = newCookie;
			
			response.addCookie(listCookie);
		}
		
		List<QnABoardTO> datas = null;
		
		
		if(listCookie.getValue().equals("all")) {
			datas = qnaboardDAO.list();
		}else if(listCookie.getValue().equals("no_secret")){
			datas = qnaboardDAO.noSecretList();
		}else if(listCookie.getValue().equals("answer_yet")) {
			datas = qnaboardDAO.noAnswerList();
		}else if(listCookie.getValue().equals("myList")) {
			HttpSession session = request.getSession();
			
			if(session.getAttribute("loginMember") != null) {

				MemberTO userData = (MemberTO)session.getAttribute("loginMember");
				
				int m_seq = Integer.parseInt(userData.getM_seq());
				
				QnABoardTO to = new QnABoardTO();
				to.setM_seq(m_seq);
				
				datas = qnaboardDAO.myList(to);
			}else {
				datas= qnaboardDAO.list();
			}
		}
		
		List<QnABoardFileTO> file_datas = qnaboardFileDAO.allList();
		
		request.setAttribute("datas", datas);
		request.setAttribute("file_datas", file_datas);
		request.setAttribute("order_flag", order_flag);
		
		System.out.println("order_flag" + order_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_write1_ok");
	
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null) {
			
			int category = Integer.parseInt(request.getParameter("category"));
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			String secret = request.getParameter("secret");
			String password = "";
			if(request.getParameter("secret").equals("Y")) {
				password = request.getParameter("password");
			}
			
			QnABoardTO to = new QnABoardTO();
			to.setQa_category_seq(category);
			to.setQa_subject(subject);
			to.setQa_content(content);
			to.setQa_secret(secret);
			to.setQa_password(password);
			to.setM_seq(m_seq);
			
			flag = qnaboardDAO.writeOk(to);
			
			if(flag == 0) {
				int qa_seq = qnaboardDAO.list().get(0).getQa_seq();
				
				if(!uploads[0].isEmpty()) {
					for(MultipartFile upload : uploads) {
						
						String ext = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
						String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().indexOf(ext));
						UUID uuidFileImg = UUID.randomUUID(); 
						String qa_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
						
						try {
							upload.transferTo(new File(qa_file_img_path));
							
							QnABoardFileTO fto = new QnABoardFileTO();
							fto.setQa_seq(qa_seq);
							fto.setQa_file_img_path(qa_file_img_path);
							
							file_flag = qnaboardFileDAO.writeOk(fto);
							
							if(file_flag == 1) {
								qnaboardFileDAO.deleteOk(fto);
								qnaboardDAO.deleteOk(to);
								
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
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		int qa_seq = Integer.parseInt(request.getParameter("qa_seq"));
		
		QnABoardTO to = new QnABoardTO();
		to.setQa_seq(qa_seq);
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			flag = qnaboardDAO.adminDeleteOk(to);
			
		}else if(userData != null){
			int m_seq = Integer.parseInt(userData.getM_seq());
			
			to.setM_seq(m_seq);
			
			flag = qnaboardDAO.deleteOk(to);
		}
		
		if(flag == 0) {
			
			QnABoardFileTO fto = new QnABoardFileTO();
			fto.setQa_seq(qa_seq);
			
			file_flag = qnaboardFileDAO.deleteOk(fto);
			
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_modify1");
		
		int qa_seq = Integer.parseInt(request.getParameter("qa_seq"));
		
		QnABoardTO to = new QnABoardTO();
		to.setQa_seq(qa_seq);
		
		QnABoardTO data = new QnABoardTO();
		data = qnaboardDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int qa_seq = Integer.parseInt(request.getParameter("qa_seq"));
			
			int category = Integer.parseInt(request.getParameter("category"));
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			String secret = request.getParameter("secret");
			String password = "";
			if(request.getParameter("secret").equals("Y")) {
				password = request.getParameter("password");
			}
			
			QnABoardTO to = new QnABoardTO();
			to.setQa_category_seq(category);
			to.setQa_subject(subject);
			to.setQa_content(content);
			to.setQa_secret(secret);
			to.setQa_password(password);
			to.setQa_seq(qa_seq);
			
			QnABoardFileTO fto = new QnABoardFileTO();
			fto.setQa_seq(qa_seq);
			
			List<QnABoardFileTO> old_file_img_path_list = qnaboardFileDAO.view(fto);
			
			flag = qnaboardDAO.modifyOk(to);
			
			if(uploads[0].getOriginalFilename().trim().equals("")) {
				file_flag = 0;
			}else {
				int old_file_del_flag = qnaboardFileDAO.deleteOk(fto);
				
				if(old_file_del_flag == 0) {
					for(QnABoardFileTO old_file_img_path : old_file_img_path_list) {
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
					String qa_file_img_path = filename + "_" + uuidFileImg + ext;
					
					try {
						upload.transferTo(new File(qa_file_img_path));
						
						System.out.println("수정된 파일 업로드 성공");
					} catch (IllegalStateException e) {
						System.out.println("에러 :" + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 :" + e.getMessage());
					}
					
					fto.setQa_file_img_path(qa_file_img_path);
					
					file_flag = qnaboardFileDAO.writeOk(fto);		
				}
				
			}
			
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_answer.do")
	public ModelAndView answer(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_answer1");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		QnABoardTO data = null;
		List<QnABoardFileTO> file_list = null;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			int qa_seq = Integer.parseInt(request.getParameter("qa_seq"));
			
			QnABoardTO to = new QnABoardTO();
			to.setQa_seq(qa_seq);
			
			data = qnaboardDAO.answer(to);
			
			QnABoardFileTO fto = new QnABoardFileTO();
			fto.setQa_seq(qa_seq);
			
			file_list = qnaboardFileDAO.view(fto);
		}
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);

		return modelAndView;
	}
	
	@RequestMapping("/qnaboard_answer_ok.do")
	public ModelAndView answerOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("qnaboard_views/board_answer1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			int qa_seq = Integer.parseInt(request.getParameter("qa_seq"));
			
			String content = request.getParameter("content").replaceAll("\n", "<br>");
			
			QnABoardTO to = new QnABoardTO();
			to.setQa_seq(qa_seq);
			to.setQa_answer_content(content);
			to.setQa_answer_check("Y");
			
			flag = qnaboardDAO.answerOk(to);
		}
		
		request.setAttribute("flag", flag);
		
		return modelAndView;
	}
	
}
