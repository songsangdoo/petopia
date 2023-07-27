package com.petopia.board.missing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.missing.model.MissingTO;

@Mapper
public interface MissingMapper {
	
	@Select("select ms_seq, ms_pet_name, ms_pet_age, ms_pet_gender, ms_subject, ms_missing_date, ms_missing_loc, ms_rep_img_path, m.m_seq, ms_hit, ms_cmt_cnt, m_nickname from missing a inner join member m where a.m_seq = m.m_seq order by ms_seq desc")
	public List<MissingTO> list();
	
	@Insert("INSERT INTO missing(ms_SEQ, ms_pet_name, ms_pet_age, ms_pet_gender, ms_subject, ms_content, ms_missing_date, ms_missing_loc, ms_rep_img_path, ms_open_phone, ms_dt, m_seq, ms_hit, ms_cmt_cnt) VALUES (DEFAULT, #{ms_pet_name}, #{ms_pet_age}, #{ms_pet_gender}, #{ms_subject}, #{ms_content}, #{ms_missing_date}, #{ms_missing_loc}, #{ms_rep_img_path}, #{ms_open_phone}, default, #{m_seq}, default, default)")
	public int write(MissingTO to);
		
	@Select("select * from missing a inner join member m where ms_seq = #{ms_seq} and a.m_seq = m.m_seq")
	public MissingTO view(MissingTO to);
	
	@Select("select ms_rep_img_path from missing where ms_seq = #{ms_seq}")
	public MissingTO delete(MissingTO to);
	
	@Delete("delete from missing where ms_seq = #{ms_seq} and m_seq = #{m_seq}")
	public int deleteOk(MissingTO to);
	
	@Delete("delete from missing where ms_seq = #{ms_seq}")
	public int adminDeleteOk(MissingTO to);
	
	@Update("update missing set ms_hit = ms_hit + 1 where ms_seq= #{ms_seq}")
	public int upHit(MissingTO to);
	
	@Update("update missing set ms_cmt_cnt = ms_cmt_cnt + 1 where ms_seq = #{ms_seq}")
	public int upCmt(MissingTO to);
	
	@Update("update missing set ms_cmt_cnt = ms_cmt_cnt - 1 where ms_seq = #{ms_seq}")
	public int downCmt(MissingTO to);
	
	@Select("select ms_seq, ms_pet_name, ms_pet_age, ms_pet_gender, ms_subject, ms_content, ms_missing_date, ms_missing_loc, ms_open_phone, m_seq from missing where ms_seq = #{ms_seq}")
	public MissingTO modify(MissingTO to);
	
	@Update("update missing set ms_pet_name = #{ms_pet_name}, ms_pet_age= #{ms_pet_age}, ms_pet_gender = #{ms_pet_gender}, ms_subject = #{ms_subject}, ms_content = #{ms_content}, ms_missing_date = #{ms_missing_date}, ms_missing_loc = #{ms_missing_loc}, ms_rep_img_path = #{ms_rep_img_path}, ms_open_phone = #{ms_open_phone} where ms_seq = #{ms_seq}")
	public int modifyOk(MissingTO to);
	
	@Update("update missing set ms_pet_name = #{ms_pet_name}, ms_pet_age= #{ms_pet_age}, ms_pet_gender = #{ms_pet_gender}, ms_subject = #{ms_subject}, ms_content = #{ms_content}, ms_missing_date = #{ms_missing_date}, ms_missing_loc = #{ms_missing_loc}, ms_open_phone = #{ms_open_phone} where ms_seq = #{ms_seq}")
	public int modifyOkNoRep(MissingTO to);
}
