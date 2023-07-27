<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/default_bar/admin_header.jsp" %>

			<main class="content">
				<div class="container-fluid p-0">
					<h1 class="h3 mb-3">
						<strong>이벤트</strong> 대시보드
						<input type="submit" class="text-right" value="저장하기">
					</h1>
					<div class="bootstrap snippets bootdey">
						<div id="sortable-list" class="col-md-9">
							<!-- Bordered Funny Boxes -->
							<div class="funny-boxes funny-boxes-top-sea">
								<div class="row">
									<div class="col-md-4 funny-boxes-img process_product">
										<img class="img-responsive" src="//via.placeholder.com/360x360.jpg" alt="">
									</div>
									<div class="col-md-8 process_info">
										<div class="process_sub_tit dh_blue hide-767">Banner</div>
										<div class="process_tit exo_font hide-767">01. 이벤트 제목</div>
										<div class="process_desc">이벤트 내용 1</div>
									</div>
								</div>
							</div>
							<!-- End Bordered Funny Boxes -->
							<div class="funny-boxes funny-boxes-top-sea">
								<div class="row">
									<div class="col-md-4 funny-boxes-img process_product">
										<img class="img-responsive" src="//via.placeholder.com/360x360.jpg" alt="">
									</div>
									<div class="col-md-8 process_info">
										<div class="process_sub_tit dh_blue hide-767">Banner</div>
										<div class="process_tit exo_font hide-767">02. 이벤트 제목</div>
										<div class="process_desc">이벤트 내용 2</div>
									</div>
								</div>
							</div>
							<!-- End Bordered Funny Boxes -->
							<div class="funny-boxes funny-boxes-top-sea">
								<div class="row">
									<div class="col-md-4 funny-boxes-img process_product">
										<img class="img-responsive" src="//via.placeholder.com/360x360.jpg" alt="">
									</div>
									<div class="col-md-8 process_info">
										<div class="process_sub_tit dh_blue hide-767">Banner</div>
										<div class="process_tit exo_font hide-767">02. 이벤트 제목</div>
										<div class="process_desc">이벤트 내용 3</div>
									</div>
								</div>
							</div>
							<!-- End Bordered Funny Boxes -->
						</div>
					</div>
				</div>
			</main>
			<footer class="footer">
				<div class="container-fluid">
					<div class="row text-muted">
						<div class="col-6 text-start">
							<p class="mb-0">
								<a class="text-muted" href="https://adminkit.io/"
									target="_blank"><strong>AdminKit</strong></a> - <a
									class="text-muted" href="https://adminkit.io/" target="_blank"><strong>Bootstrap
										Admin Template</strong></a> &copy;
							</p>
						</div>
						<div class="col-6 text-end">
							<ul class="list-inline">
								<li class="list-inline-item"><a class="text-muted"
									href="https://adminkit.io/" target="_blank">Support</a></li>
								<li class="list-inline-item"><a class="text-muted"
									href="https://adminkit.io/" target="_blank">Help Center</a></li>
								<li class="list-inline-item"><a class="text-muted"
									href="https://adminkit.io/" target="_blank">Privacy</a></li>
								<li class="list-inline-item"><a class="text-muted"
									href="https://adminkit.io/" target="_blank">Terms</a></li>
							</ul>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="assets/js/dashboardjs/app.js"></script>
	<script src="assets/js/dashboardjs/additional.js"></script>