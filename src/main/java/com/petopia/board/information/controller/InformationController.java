package com.petopia.board.information.controller;

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

import com.petopia.board.information.model.InformationFileTO;
import com.petopia.board.information.model.InformationTO;
import com.petopia.board.information.model.InformationDAO;
import com.petopia.board.information.model.InformationFileDAO;
import com.petopia.board.recommend.model.RecCheckDAO;
import com.petopia.board.recommend.model.RecCheckTO;
import com.petopia.model.MemberTO;

@RestController
@ComponentScan({"com.petopia.board.information.model", "com.petopia.board.recommend.model"})
public class InformationController {

	@Autowired
	private InformationDAO informationDAO;
	@Autowired
	private InformationFileDAO informationFileDAO;
	@Autowired
	private RecCheckDAO recCheckDAO;
	
	private int board_no = 3;
	
	@RequestMapping("/information_list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("information_views/board_list1");
		
		Cookie[] cookies = request.getCookies();
		
		Cookie listCookie = null;
		
		String order_flag = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("info_list_flag")) {
					listCookie = cookies[i];
					
					order_flag = cookies[i].getValue();
				}
			}
		}
		
		if(request.getParameter("order_flag") != null) {
			if(request.getParameter("order_flag").equals("latest")) {
				Cookie newCookie = new Cookie("info_list_flag", "latest");
				
				listCookie = newCookie;
				
				order_flag = "latest";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("rec")) {
				Cookie newCookie = new Cookie("info_list_flag", "rec");
				
				listCookie = newCookie;
				
				order_flag = "rec";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("cmt")) {
				Cookie newCookie = new Cookie("info_list_flag", "cmt");
				
				listCookie = newCookie;
				
				order_flag = "cmt";
				
				response.addCookie(listCookie);
				
			}else if(request.getParameter("order_flag").equals("hit")) {
				Cookie newCookie = new Cookie("info_list_flag", "hit");
				
				listCookie = newCookie;
				
				order_flag = "hit";
				
				response.addCookie(listCookie);
			}
		}
		
		if(listCookie == null) {
			Cookie newCookie = new Cookie("info_list_flag", "latest");
			
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
		
		InformationTO to = new InformationTO();
		
		List<InformationTO> datas = null;
		
		if(option == null && searchStr == null) {
			if(listCookie.getValue().equals("latest")) {
				datas = informationDAO.latestList();
			}else if(listCookie.getValue().equals("rec")) {
				datas = informationDAO.recList();
			}else if(listCookie.getValue().equals("cmt")) {
				datas = informationDAO.cmtList();
			}else if(listCookie.getValue().equals("hit")) {
				datas = informationDAO.hitList();
			}
		}else if(option.equals("subject")){
			to.setInfo_subject("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = informationDAO.subjectLatestList(to);
			}else if(listCookie.getValue().equals("rec")) {
				datas = informationDAO.subjectRecList(to);
			}else if(listCookie.getValue().equals("cmt")) {
				datas = informationDAO.subjectCmtList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = informationDAO.subjectHitList(to);
			}
		}else if(option.equals("content")){
			to.setInfo_content("%" + searchStr + "%");
			if(listCookie.getValue().equals("latest")) {
				datas = informationDAO.contentLatestList(to);
			}else if(listCookie.getValue().equals("rec")) {
				datas = informationDAO.contentRecList(to);
			}else if(listCookie.getValue().equals("cmt")) {
				datas = informationDAO.contentCmtList(to);
			}else if(listCookie.getValue().equals("hit")) {
				datas = informationDAO.contentHitList(to);
			}
		}
		
		request.setAttribute("datas", datas);
		request.setAttribute("order_flag", order_flag);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/information_write.do")
	public ModelAndView write() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/information_write_ok.do")
	public ModelAndView writeOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_write1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		String subject = request.getParameter("subject");
		String content = request.getParameter("content").replace("\n", "<br>");
		
		String rep_ext = rep_upload.getOriginalFilename().substring(rep_upload.getOriginalFilename().lastIndexOf("."));
		String rep_filename = rep_upload.getOriginalFilename().substring(0, rep_upload.getOriginalFilename().indexOf(rep_ext));
		UUID uuidRepImg = UUID.randomUUID(); // 파일 이름 중복방지
		String rep_img_path = rep_filename + "_" + uuidRepImg.toString() + rep_ext;
		
		InformationTO to = new InformationTO();
		to.setM_seq(m_seq);
		to.setInfo_subject(subject);
		to.setInfo_content(content);
		to.setInfo_rep_img_path(rep_img_path);
		
		int flag = 1;
		int file_flag = 1;
		int rec_insert = 1;
		
		flag = informationDAO.writeOk(to);
		
		if(flag == 0) {
			int info_seq = informationDAO.latestList().get(0).getInfo_seq();
			
			RecCheckTO rcto = new RecCheckTO();
			rcto.setBoard_no(board_no);
			rcto.setBoard_seq(info_seq);
			rcto.setM_seq(m_seq);
			
			rec_insert = recCheckDAO.insert(rcto);
			
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
					String info_file_img_path = filename + "_" + uuidFileImg.toString() + ext;
					
					try {
						upload.transferTo(new File(info_file_img_path));
						
						InformationFileTO fto = new InformationFileTO();
						fto.setInfo_seq(info_seq);
						fto.setInfo_file_img_path(info_file_img_path);
						
						file_flag = informationFileDAO.writeOk(fto);
						
						if(file_flag == 1) {
							informationFileDAO.deleteOk(fto);
							informationDAO.deleteOk(to);
							
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
	
	@RequestMapping("/information_view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_view1");
		
		int info_seq = Integer.parseInt(request.getParameter("info_seq"));

		InformationTO to = new InformationTO();
		to.setInfo_seq(info_seq);
		
		Cookie[] cookies = request.getCookies();
		
		Cookie viewCookie = null;
		
		Cookie newCookie = null;
		
		if(cookies != null && cookies.length > 0) {
			for(int i = 0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("info_" + info_seq + "_chk")) {
					viewCookie = cookies[i];
				}
			}
		}
		
		if(viewCookie == null) {
			newCookie = new Cookie("info_" + info_seq + "_chk", "visited_" + info_seq);
			response.addCookie(newCookie);
			informationDAO.upHit(to);
		}

		InformationFileTO fto = new InformationFileTO();
		fto.setInfo_seq(info_seq);
		
		InformationTO data = new InformationTO();
		data = informationDAO.view(to);
		
		List<InformationFileTO> file_list = informationFileDAO.view(fto);
		
		request.setAttribute("data", data);
		request.setAttribute("file_list", file_list);
		
		return modelAndView;
	}
	
	@RequestMapping("/information_view_rec.do")
	public ModelAndView upRec(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_view2");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int m_seq =  Integer.parseInt(userData.getM_seq());
		int info_seq = Integer.parseInt(request.getParameter("info_seq"));
		
		InformationTO to = new InformationTO();
		to.setInfo_seq(info_seq);
		
		int rec_flag = 1;
		int rec_insert = 1; 
		
		RecCheckTO rcto = new RecCheckTO();
		rcto.setBoard_no(board_no);
		rcto.setBoard_seq(info_seq);
		rcto.setM_seq(m_seq);
		
		if(recCheckDAO.check(rcto) == null) {
			rec_flag = informationDAO.upRec(to);
			
			rec_insert = recCheckDAO.insert(rcto);
		}
		
		request.setAttribute("rec_flag", rec_flag);
		request.setAttribute("rec_insert", rec_insert);
		return modelAndView;
	}
	
	@RequestMapping("/information_delete_ok.do")
	public ModelAndView deleteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_delete1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int info_seq = Integer.parseInt(request.getParameter("info_seq"));
			
			InformationTO to = new InformationTO();
			to.setInfo_seq(info_seq);
			
			InformationFileTO fto = new InformationFileTO();
			fto.setInfo_seq(info_seq);
			
			file_flag = informationFileDAO.deleteOk(fto);
			
			flag = informationDAO.deleteOk(to);
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("file_flag", file_flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/information_modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_modify1");
		
		int info_seq = Integer.parseInt(request.getParameter("info_seq"));
		
		InformationTO to = new InformationTO();
		to.setInfo_seq(info_seq);
		
		InformationTO data = new InformationTO();
		data = informationDAO.modify(to);
		
		request.setAttribute("data", data);
		
		return modelAndView;
	}
	
	@RequestMapping("/information_modify_ok.do")
	public ModelAndView modifyOk(HttpServletRequest request, MultipartFile rep_upload, MultipartFile[] uploads) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("information_views/board_modify1_ok");
		
		HttpSession session = request.getSession();
		
		MemberTO userData = (MemberTO)session.getAttribute("loginMember");
		
		int flag = 1;
		int file_flag = 1;
		
		if(userData != null && userData.getGrade_seq().equals("1")) {
			
			int info_seq = Integer.parseInt(request.getParameter("info_seq"));
			
			String subject = request.getParameter("subject");
			String content = request.getParameter("content").replace("\n", "<br>");
			
			InformationTO to = new InformationTO();
			to.setInfo_seq(info_seq);
			to.setInfo_subject(subject);
			to.setInfo_content(content);
			
			InformationTO old_data = informationDAO.view(to);
			String old_rep_img_path = old_data.getInfo_rep_img_path();
			
			InformationFileTO fto = new InformationFileTO();
			fto.setInfo_seq(info_seq);
			
			List<InformationFileTO> old_file_img_path_list = informationFileDAO.view(fto);
			
			if(rep_upload.getOriginalFilename().trim().equals("")) {
				flag = informationDAO.modifyOkNoRep(to);
			}else {
				String rep_ext = rep_upload.getOriginalFilename().substring(rep_upload.getOriginalFilename().lastIndexOf("."));
				String rep_filename = rep_upload.getOriginalFilename().substring(0, rep_upload.getOriginalFilename().indexOf(rep_ext));
				UUID uuidRepImg = UUID.randomUUID();
				String info_rep_img_path = rep_filename + "_" + uuidRepImg.toString() + rep_ext;
				
				to.setInfo_rep_img_path(info_rep_img_path);
				
				flag = informationDAO.modifyOk(to);
				
				if(flag == 0) {
					try {
						rep_upload.transferTo(new File(info_rep_img_path));
						
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
				int old_file_del_flag = informationFileDAO.deleteOk(fto);
				
				if(old_file_del_flag == 0) {
					for(InformationFileTO old_file_img_path : old_file_img_path_list) {
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
					String info_file_img_path = filename + "_" + uuidFileImg + ext;
					
					try {
						upload.transferTo(new File(info_file_img_path));
						
						System.out.println("수정된 파일 업로드 성공");
					} catch (IllegalStateException e) {
						System.out.println("에러 :" + e.getMessage());
					} catch (IOException e) {
						System.out.println("에러 :" + e.getMessage());
					}
					
					fto.setInfo_file_img_path(info_file_img_path);
					
					file_flag = informationFileDAO.writeOk(fto);		
				}
				
			}
			
			request.setAttribute("flag", flag);
			request.setAttribute("file_flag", file_flag);
		}
		
		return modelAndView;
	}
}
