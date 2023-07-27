package com.petopia.board.freq.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.board.freq.model.QnABoardFreqDAO;
import com.petopia.board.freq.model.QnABoardFreqTO;
import com.petopia.model.MemberTO;

@RestController
@ComponentScan("com.petopia.board.freq.model")
public class QnABoardFreqController {

	@Autowired
	private QnABoardFreqDAO qnaboardfreqDAO;
	
	@RequestMapping("/freq_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("qa_freq_list_flag")) {
					listCookie = cookies[i];
					
					order_flag = cookies[i].getValue();
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("all")) {
				Cookie newCookie = new Cookie("qa_freq_list_flag", "all");
				
				listCookie = newCookie;
				
				order_flag = "all";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("member")) {
				Cookie newCookie = new Cookie("qa_freq_list_flag", "member");
				
				listCookie = newCookie;
				
				order_flag = "member";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("community")) {
				Cookie newCookie = new Cookie("qa_freq_list_flag", "community");
				
				listCookie = newCookie;
				
				order_flag = "community";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("website")) {
				Cookie newCookie = new Cookie("qa_freq_list_flag", "website");
				
				listCookie = newCookie;
				
				order_flag = "website";
				
				response.addCookie(listCookie);
			}else if(request.getParameter("order_flag").equals("eventAndPoint")) {
				Cookie newCookie = new Cookie("qa_freq_list_flag", "eventAndPoint");
				
				listCookie = newCookie;
				
				order_flag = "eventAndPoint";
				
				response.addCookie(listCookie);
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("qa_freq_list_flag", "all");
			
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
		
		QnABoardFreqTO to = new QnABoardFreqTO();
		
		List<QnABoardFreqTO> datas = null;
		
		if(option == null && searchStr == null) {
			if(listCookie.getValue().equals("all")) {
				datas = qnaboardfreqDAO.list();
			}else if(listCookie.getValue().equals("member")) {
				datas = qnaboardfreqDAO.memberList();
			}else if(listCookie.getValue().equals("community")) {
				datas = qnaboardfreqDAO.communityList();
			}else if(listCookie.getValue().equals("website")) {
				datas = qnaboardfreqDAO.websiteList();
			}else if(listCookie.getValue().equals("eventAndPoint")) {
				datas = qnaboardfreqDAO.eventAndPointList();
			}
		}else if(option.equals("subject")){
			to.setQa_freq_subject("%" + searchStr + "%");
			if(listCookie.getValue().equals("all")) {
				datas = qnaboardfreqDAO.subjectList(to);
			}else if(listCookie.getValue().equals("member")) {
				datas = qnaboardfreqDAO.memberSubjectList(to);
			}else if(listCookie.getValue().equals("community")) {
				datas = qnaboardfreqDAO.communitySubjectList(to);
			}else if(listCookie.getValue().equals("website")) {
				datas = qnaboardfreqDAO.websiteSubjectList(to);
			}else if(listCookie.getValue().equals("eventAndPoint")) {
				datas = qnaboardfreqDAO.eventAndPointSubjectList(to);
			}
		}else if(option.equals("content")){
			to.setQa_freq_content("%" + searchStr + "%");
			if(listCookie.getValue().equals("all")) {
				datas = qnaboardfreqDAO.contentList(to);
			}else if(listCookie.getValue().equals("member")) {
				datas = qnaboardfreqDAO.memberContentList(to);
			}else if(listCookie.getValue().equals("community")) {
				datas = qnaboardfreqDAO.communityContentList(to);
			}else if(listCookie.getValue().equals("website")) {
				datas = qnaboardfreqDAO.websiteContentList(to);
			}else if(listCookie.getValue().equals("eventAndPoint")) {
				datas = qnaboardfreqDAO.eventAndPointContentList(to);
			}
		}
		
		request.setAttribute("datas", datas);
		request.setAttribute("order_flag", order_flag);
		
		
		return modelAndView;
	}
	
	@RequestMapping("/freq_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/freq_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_write1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int category = Integer.parseInt(request.getParameter("category"));
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replaceAll("\n", "<br>");
			
			QnABoardFreqTO to = new QnABoardFreqTO();
			to.setQa_category_seq(category);
			to.setQa_freq_subject(subject);
			to.setQa_freq_content(content);
			
			flag = qnaboardfreqDAO.writeOk(to);
			
		}
		
		request.setAttribute("flag", flag);
		
		return modelAndView;
		
	}
	
	@RequestMapping("/freq_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;

		if(userData != null && userData.getGrade_seq().equals("1")) {
			int qa_freq_seq = Integer.parseInt(request.getParameter("qa_freq_seq"));
			
			QnABoardFreqTO to = new QnABoardFreqTO();
			to.setQa_freq_seq(qa_freq_seq);
			
			flag = qnaboardfreqDAO.deleteOk(to);
			
		}
		
		request.setAttribute("flag", flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/freq_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_modify1");
		
		int qa_freq_seq = Integer.parseInt(request.getParameter("qa_freq_seq"));
		
		QnABoardFreqTO to = new QnABoardFreqTO();
		to.setQa_freq_seq(qa_freq_seq);
		
		QnABoardFreqTO data = qnaboardfreqDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/freq_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("freq_views/board_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int qa_freq_seq = Integer.parseInt(request.getParameter("qa_freq_seq"));
			int category = Integer.parseInt(request.getParameter("category"));
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replaceAll("\n", "<br>");
			
			QnABoardFreqTO to = new QnABoardFreqTO();
			to.setQa_category_seq(category);
			to.setQa_freq_subject(subject);
			to.setQa_freq_content(content);
			to.setQa_freq_seq(qa_freq_seq);
			
			flag = qnaboardfreqDAO.modifyOk(to);
		}
		
		request.setAttribute("flag", flag);
		
		return modelAndView;
	}
}
