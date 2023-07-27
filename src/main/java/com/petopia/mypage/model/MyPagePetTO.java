package com.petopia.mypage.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MyPagePetTO {
	private String p_seq;
	private String m_seq;
	private String p_name;
	private String p_age;
	private String p_birth;
	private String p_species;
	private String p_gender;
	private String p_img;
}
