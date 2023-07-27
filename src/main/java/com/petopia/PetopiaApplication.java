package com.petopia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.petopia.controller", "com.petopia.model", 
					"com.petopia.admin.controller", "com.petopia.admin.model","com.petopia.mypage.controller",
					"com.petopia.board.notice.controller", "com.petopia.board.notice.model",
					"com.petopia.board.album.controller", "com.petopia.board.album.model",
					"com.petopia.board.information.controller", "com.petopia.board.information.model",
					"com.petopia.board.free.controller", "com.petopia.board.free.model",
					"com.petopia.board.tip.controller", "com.petopia.board.tip.model",
					"com.petopia.board.missing.controller", "com.petopia.board.missing.model",
					"com.petopia.board.qna.controller", "com.petopia.board.qna.model",
					"com.petopia.board.question.controller", "com.petopia.board.question.model",
					"com.petopia.board.freq.controller", "com.petopia.board.freq.model",
					"com.petopia.pointshop.controller", "com.petopia.pointshop.model",
					"com.petopia.group.board.controller", "com.petopia.group.board.model",
					"com.petopia.api.controller", "com.petopia.api.model", "com.petopia.api.service",
					"com.petopia.group.controller", "com.petopia.group.model" } )
public class PetopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetopiaApplication.class, args);
	}
}