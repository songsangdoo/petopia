package com.petopia.board.album.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.album.model.AlbumFileTO;

@Mapper
public interface AlbumFileMapper {
	
	@Select("select ab_file_img_path from album_file where ab_seq = #{ab_seq}")
	public List<AlbumFileTO> list(AlbumFileTO to);

	@Insert("INSERT INTO ALBUM_FILE(AB_SEQ, AB_FILE_SEQ, AB_FILE_IMG_PATH) SELECT #{ab_seq}, (SELECT IFNULL(MAX(AB_FILE_SEQ) + 1, 0) FROM ALBUM_FILE WHERE AB_SEQ = #{ab_seq}), #{ab_file_img_path}")
	public int writeOk(AlbumFileTO to);
	
	@Select("select ab_file_img_path from album_file where ab_seq = #{ab_seq}")
	public List<AlbumFileTO> delete(AlbumFileTO to);
	
	@Delete("delete from album_file where ab_seq = #{ab_seq}")
	public int deleteOk(AlbumFileTO to);
}
