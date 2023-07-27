package com.petopia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class MemberTO {

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
    
}
