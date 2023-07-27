package com.petopia.admin.model;

import lombok.Data;

@Data
public class AdminTO {
	private String m_seq;
    private String m_id;
    private String m_nickname;
    private String m_name;
    private String m_password;
    private String m_email;
    private String m_addr;
    private String m_phone;
    private String m_age;
    private String m_gender;
    private String m_date;
    private String grade_seq;
    private String m_totalPoint;
    private String m_point;
    
    private String proComment;
    private String proImg;
}

