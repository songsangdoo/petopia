<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>펫토피아</title>
<meta content="" name="description">
<meta content="" name="keywords">
<style type="text/css">
.contentpage{
	width:0%;
	display:none;
}
.containeres{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  padding : 0px;
  .sections{
    flex-grow: 1;
    position: relative;
    display: flex;
    height: 100%;
    transition: all 0.4s;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;
    text-align: center;
    &:nth-child(1){
      background: #FFD1DA;
      background-size: cover;
      width:100%;
    }
    &:nth-child(2){
      background: #FFF0F5;
      background-size: cover;
      width:100%;
    }
    &:nth-child(3){
      background: #FFDBAA;
      background-size: cover;
      width:100%;
    }
    
    /* 
    &:nth-child(3){
      background: url("https://images3.alphacoders.com/676/676584.jpg") center;
      background-size: cover;
    }
     */
  }
  
}

.caption{
background : #fff;
 width : 500px;
 text-align: center;
 padding: 10px;
 margin: 10px;
 border-radius: 10px;
}


.containeres.comu{
.sections{
    &:nth-child(1){
    	.box {
    		.contentpage{
    			display : block;
    		}
    	}
    }
  }
}

.containeres.service{
.sections{
    &:nth-child(2){
    	.box {
    		.contentpage{
    			display : block;
    		}
    	}
    }
  }
}

.containeres.infomation{
.sections{
    &:nth-child(3){
    	.box {
    		.contentpage{
    			display : block;
    		}
    	}
    }
  }
}

</style>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>

  <script type="text/javascript">
	$(document).ready(function(){
		$("#comu").hover(function(){
	    	$(".containeres").addClass("comu");
	    	$("#service").find(".box").find("h2").css({display:'none'})
	    	$("#infomation").find(".box").find("h2").css({display:'none'})
	    	$("#service").animate({width:'300px'}, 10,'swing');
	    	$("#infomation").animate({width:'300px'}, 10,'swing');
		}, function(){
	    	$(".containeres").removeClass("comu");
	    	$("#comu").animate({width:'100%'}, 100,'swing');
	    	$("#service").animate({width:'100%'}, 100,'swing');
	    	$("#infomation").animate({width:'100%'}, 100,'swing');
	    	$("#service").find(".box").find("h2").css({display:'block'})
	    	$("#infomation").find(".box").find("h2").css({display:'block'})
		});
		
		$("#service").hover(function(){
	    	$(".containeres").addClass("service");
	    	$("#comu").find(".box").find("h2").css({display:'none'})
	    	$("#infomation").find(".box").find("h2").css({display:'none'})
	    	$("#comu").animate({width:'300px'}, 100,'swing');
	    	$("#infomation").animate({width:'300px'}, 100,'swing');
		}, function(){
	    	$(".containeres").removeClass("service");
	    	$("#comu").animate({width:'100%'}, 100,'swing');
	    	$("#service").animate({width:'100%'}, 100,'swing');
	    	$("#infomation").animate({width:'100%'}, 100,'swing');
	    	$("#comu").find(".box").find("h2").css({display:'block'})
	    	$("#infomation").find(".box").find("h2").css({display:'block'})
		});
		
		$("#infomation").hover(function(){
	    	$(".containeres").addClass("infomation");
	    	$("#service").find(".box").find("h2").css({display:'none'})
	    	$("#comu").find(".box").find("h2").css({display:'none'})
	    	$("#comu").animate({width:'300px'}, 100,'swing');
	    	$("#service").animate({width:'300px'}, 100,'swing');
		}, function(){
	    	$(".containeres").removeClass("infomation");
	    	$("#comu").animate({width:'100%'}, 100,'swing');
	    	$("#service").animate({width:'100%'}, 100,'swing');
	    	$("#infomation").animate({width:'100%'}, 100,'swing');
	    	$("#comu").find(".box").find("h2").css({display:'block'})
	    	$("#service").find(".box").find("h2").css({display:'block'})
		});
		
	});
</script>

</head>

<body>
	<jsp:include page="/WEB-INF/views/default_bar/header.jsp"/>
	
	<!-- End Header -->
	<main id="main">
		<!-- ======= Contact Section ======= -->
		<section id="contact" class="contact">
			<section class="containeres" style="padding : 0px; border : 0px">
			  <div class="sections" id="comu">
				  <div class="box">
					  <img alt="" src="../../images/guide/conversation.png" style="width:20%;">
					  <h2>다양한 커뮤니티</h2>
				  			  
					  <div class="contentpage">
		                    <div class="caption">
		                        <h3 class="md-heading"><Strong>다양한 게시판을 제공합니다.</Strong></h3>
		                        <p>펫토피아의 모든 유저와 소통할 수 있는<br/>여러가지 게시판을 사용해보세요! </p>
		                        <a href="freeboard_list.do">Read More</a>
		                    </div>
		
		                    <div class="caption">
		                        <h3 class="md-heading"><Strong>동아리에 가입해보세요!</Strong></h3>
		                        <p>같은 목적을 가진 유저들끼리 동아리를 만들 수 있습니다.<br/>내가 원하는 동아리를 만들 수 도 있습니다.</p>
		                        <a href="/group_list.do">Read More</a>
		                    </div>
		                    
		                    <div class="caption">
		                        <h3 class="md-heading"><Strong>귀여운 내 반려동물을 자랑합시다.</Strong></h3>
		                        <p>모두에게 보여주고 싶은 내 반려동물들!<br/>앨범게시판에서 잔뜩 올릴 수 있습니다. </p>
		                        <a href="/album_list.do">Read More</a>
		                    </div>
					  </div>			  
				  </div>
			  </div>
			  
			  <div class="sections" id="service">
				  <div class="box">
				  <img alt="" src="../../images/guide/pets.png" style="width:20%;">
				  <h2>반려동물관련 서비스</h2>
				  <div class="contentpage">
	                    <div class="caption">
	                        <h3 class="md-heading"><Strong>동물병원 찾기 서비스</Strong></h3>
	                        <p>검색한 주소 주변에 둥몰병원을 검색,<br/>관련 동물병원 리스트와 지도를 제공합니다.</p>
	                        <a href="/animalHospital.do">Read More</a>
	                    </div>
	
	                    <div class="caption">
	                        <h3 class="md-heading"><Strong>반려동물 여행지 리스트</Strong></h3>
	                        <p>강원도 내 반려동물과 갈 수 있는 여행지를 추천해줍니다.<br/>식음료, 숙박, 관광지, 체험, 동물병원로 구분되어있습니다.</p>
	                        <a href="/trip.do">Read More</a>
	                    </div>
	                    
	                    <div class="caption">
	                        <h3 class="md-heading"><Strong>유기동물 리스트</Strong></h3>
	                        <p>유기동물의 리스트를 보여줍니다.<br/>강아지, 고양이, 기타동물들로 구분되어 있습니다.</p>
	                        <a href="/abandonment.do">Read More</a>
	                    </div>
				  </div>
				  </div>
			  </div>
			  
			  <div class="sections" id="infomation">
				  <div class="box">
				  <img alt="" src="../../images/guide/book.png" style="width:20%;">
				  <h2>풍부한 지식정보</h2>
				  <div class="contentpage">
	                    <div class="caption">
	                        <h3 class="md-heading"><Strong>지식정보</Strong></h3>
	                        <p>반려동물을 기르면서 모르는 지식들,<br/>잘못알고 있는 지식들<br/>유용하고 정확한 정보를 제공합니다.</p>
	                        <a href="/information_list.do">Read More</a>
	                    </div>
			 	  </div>
				  </div>
			  </div>
			</section>
		</section>
		<!-- End Contact Section -->
	</main>
<jsp:include page="/WEB-INF/views/default_bar/footer.jsp"/>
</body>

</html>