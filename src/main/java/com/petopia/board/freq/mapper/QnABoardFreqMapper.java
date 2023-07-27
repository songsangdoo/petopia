package com.petopia.board.freq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.freq.model.QnABoardFreqTO;

@Mapper
public interface QnABoardFreqMapper {

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq order by qa_freq_seq desc")
	public List<QnABoardFreqTO> list();

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_freq_subject like #{qa_freq_subject} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> subjectList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_freq_content like #{qa_freq_content} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> contentList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 1 order by qa_freq_seq desc")
	public List<QnABoardFreqTO> memberList();

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 1 and qa_freq_subject like #{qa_freq_subject} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> memberSubjectList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 1 and qa_freq_content like #{qa_freq_content} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> memberContentList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 2 order by qa_freq_seq desc")
	public List<QnABoardFreqTO> communityList();

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 2 and qa_freq_subject like #{qa_freq_subject} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> communitySubjectList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 2 and qa_freq_content like #{qa_freq_content} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> communityContentList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 3 order by qa_freq_seq desc")
	public List<QnABoardFreqTO> websiteList();

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 3 and qa_freq_subject like #{qa_freq_subject} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> websiteSubjectList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 3 and qa_freq_content like #{qa_freq_content} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> websiteContentList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 4 order by qa_freq_seq desc")
	public List<QnABoardFreqTO> eventAndPointList();

	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 4 and qa_freq_subject like #{qa_freq_subject} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> eventAndPointSubjectList(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.qa_category_seq = 4 and qa_freq_content like #{qa_freq_content} order by qa_freq_seq desc")
	public List<QnABoardFreqTO> eventAndPointContentList(QnABoardFreqTO to);
	
	@Insert("insert into qna_freq(qa_freq_seq, qa_category_seq, qa_freq_subject, qa_freq_content, m_seq) values(default, #{qa_category_seq}, #{qa_freq_subject}, #{qa_freq_content}, #{m_seq})")
	public int writeOk(QnABoardFreqTO to);
	
	@Delete("delete from qna_freq where qa_freq_seq = #{qa_freq_seq}")
	public int deleteOk(QnABoardFreqTO to);
	
	@Select("select qa_freq_seq, qa_freq_subject, qa_freq_content, c.qa_category_name from qna_freq a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_freq_seq = #{qa_freq_seq}")
	public QnABoardFreqTO modify(QnABoardFreqTO to);
	
	@Update("update qna_freq set qa_category_seq = #{qa_category_seq}, qa_freq_subject = #{qa_freq_subject}, qa_freq_content = #{qa_freq_content} where qa_freq_seq = #{qa_freq_seq}")
	public int modifyOk(QnABoardFreqTO to);
	
}
