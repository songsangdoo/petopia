<%@page import="com.petopia.admin.model.AdminTO"%>
<%@page import="com.petopia.model.MemberTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/default_bar/admin_header.jsp"%>

<style>
.park{
	cursor: default;
}
.park:hover{
	color : #387DDD;
	text-decoration: none;
}



</style>

<main class="content">
	<div class="container-fluid p-0">
		<h1 class="h3 mb-3">
			<strong>유저관리</strong> 대시보드
			<div class="row">
			    <div class="col-lg-12 card-margin">
			        <div class="card search-form">
			            <div class="card-body p-0">
			                <form id="search-form">
			                    <div class="row">
			                        <div class="col-12">
			                            <div class="row no-gutters">
			                                <div class="col-lg-1 col-md-3 col-sm-12 p-0">
			                                    <select class="form-control" id="getUserFilter">
			                                        <option>이름</option>
			                                        <option>닉네임</option>
			                                        <option>회원번호</option>
			                                    </select>
			                                </div>
			                                <div class="col-lg-10 col-md-6 col-sm-12 p-0">
			                                    <input type="text" placeholder="키워드를 입력해주세요..." class="form-control" id="search" name="search">
			                                </div>
			                                <div class="col-lg-1 col-md-3 col-sm-12 p-0">
			                                    <button type="submit" class="btn btn-base control-search">
			                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
			                                    </button>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
			                </form>
			            </div>
			        </div>
			    </div>
			</div>
		</h1>
		<div class="row">
			<div class="col-lg-12">
				<div class="main-box clearfix">
					<div class="table-responsive">
						<table class="table user-list">
							<thead>
								<tr>
									<th><span>회원번호</span></th>
									<th><span>닉네임</span></th>
									<th><span>생성일</span></th>
									<th class="text-center"><span>계정상태</span></th>
									<th><span>이메일</span></th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<%
								int startIndex = (currentPage - 1) * itemsPerPage;
								int endIndex = Math.min(startIndex + itemsPerPage, memberList.size());
								for (int i = startIndex; i < endIndex; i++) {
									AdminTO to = memberList.get(i);
								%>
								<tr>
									<td><span> <%=to.getM_seq()%></span></td>
									<td><img
										src='<%=(to.getProImg() != null) ? to.getProImg()
		: "https://png.pngtree.com/png-clipart/20220117/original/pngtree-cartoon-hand-drawn-default-avatar-png-image_7127563.png"%>'
										alt=''> <a class='user-link park'><%=to.getM_name()%></a> <span
										class='user-subhead'><%=to.getGrade_seq()%></span></td>
									<td><%=to.getM_date()%></td>
									<td class='text-center'><span class='label label-default'>활성화</span></td>
									<td><%=to.getM_email()%></td>
									<td style='width: 20%;'><!-- <a class='table-link userinfo'>
											<span class='fa-stack'> <i
												class='fa fa-square fa-stack-2x'></i> <i
												class='fa fa-search-plus fa-stack-1x fa-inverse'></i>
										</span>
									</a> <a id='usermodify' class='table-link'> <span
											class='fa-stack'> <i class='fa fa-square fa-stack-2x'></i>
												<i class='fa fa-pencil fa-stack-1x fa-inverse'></i>
										</span>
									</a> --> <a class='table-link danger userdelete'> <span
											class='fa-stack'> <i class='fa fa-square fa-stack-2x'></i>
												<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i>
										</span>
									</a></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
					<ul class="pagination pull-right">
						<%
						int totalPages = (int) Math.ceil((double) memberList.size() / itemsPerPage);
						int startPage = Math.max(currentPage - 5, 1);
						int endPage = Math.min(startPage + 9, totalPages);

						if (currentPage > 1) {
						%>
						<li><a href="?page=<%=currentPage - 1%>"><i
								class="fa fa-chevron-left"></i></a></li>
						<%
						} else {
						%>
						<li class="disabled"><a href="#"><i
								class="fa fa-chevron-left"></i></a></li>
						<%
						}

						for (int i = startPage; i <= endPage; i++) {
						if (i == currentPage) {
						%>
						<li class="active"><a href="#"><%=i%></a></li>
						<%
						} else {
						%>
						<li><a href="?page=<%=i%>"><%=i%></a></li>
						<%
						}
						}

						if (currentPage < totalPages) {
						%>
						<li><a href="?page=<%=currentPage + 1%>"><i
								class="fa fa-chevron-right"></i></a></li>
						<%
						} else {
						%>
						<li class="disabled"><a href="#"><i
								class="fa fa-chevron-right"></i></a></li>
						<%
						}
						%>
					</ul>
				</div>
			</div>
		</div>
	</div>
</main>
<!-- <div id="userviewmodal" class="modal fade bd-example-modal-xl"
	tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="container">
				<div class="main-body">
					Breadcrumb
					<nav aria-label="breadcrumb" class="main-breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="index.html">Home</a></li>
							<li class="breadcrumb-item"><a href="javascript:void(0)">User</a></li>
							<li class="breadcrumb-item active" aria-current="page">User
								Profile</li>
						</ol>
					</nav>
					/Breadcrumb

					<div class="row gutters-sm">
						<div class="col-md-4 mb-3">
							<div class="card">
								<div class="card-body">
									<div class="d-flex flex-column align-items-center text-center">
										<img src="https://bootdey.com/img/Content/avatar/avatar7.png"
											alt="Admin" class="rounded-circle" width="150" id="viewImg">
										<div class="mt-3">
											<h4 id="viewName">John Doe</h4>
											<p id="viewGrade" class="text-secondary mb-1">Full Stack
												Developer</p>
											<p class="text-muted font-size-sm">Bay Area, San
												Francisco, CA</p>
											<button class="btn btn-primary">활동기록 보기</button>
											<button class="btn btn-outline-primary">알림 보내기</button>
										</div>
									</div>
								</div>
							</div>
							<div class="card mt-3">
								<ul class="list-group list-group-flush">
									<li
										class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
										<h6 class="mb-0">
											<svg xmlns="http://www.w3.org/2000/svg" width="24"
												height="24" viewBox="0 0 24 24" fill="none"
												stroke="currentColor" stroke-width="2"
												stroke-linecap="round" stroke-linejoin="round"
												class="feather feather-globe mr-2 icon-inline">
															<circle cx="12" cy="12" r="10"></circle>
															<line x1="2" y1="12" x2="22" y2="12"></line>
															<path
													d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path></svg>
											이메일주소
										</h6> <span class="text-secondary">https://bootdey.com</span>
									</li>
									<li
										class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
										<h6 class="mb-0">
											<svg xmlns="http://www.w3.org/2000/svg" width="24"
												height="24" viewBox="0 0 24 24" fill="none"
												stroke="currentColor" stroke-width="2"
												stroke-linecap="round" stroke-linejoin="round"
												class="feather feather-github mr-2 icon-inline">
															<path
													d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path></svg>
											전화번호
										</h6> <span class="text-secondary">bootdey</span>
									</li>
									<li
										class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
										<h6 class="mb-0">
											<svg xmlns="http://www.w3.org/2000/svg" width="24"
												height="24" viewBox="0 0 24 24" fill="none"
												stroke="currentColor" stroke-width="2"
												stroke-linecap="round" stroke-linejoin="round"
												class="feather feather-twitter mr-2 icon-inline text-info">
															<path
													d="M23 3a10.9 10.9 0 0 1-3.14 1.53 4.48 4.48 0 0 0-7.86 3v1A10.66 10.66 0 0 1 3 4s-4 9 5 13a11.64 11.64 0 0 1-7 2c9 5 20 0 20-11.5a4.5 4.5 0 0 0-.08-.83A7.72 7.72 0 0 0 23 3z"></path></svg>
											Twitter
										</h6> <span class="text-secondary">@bootdey</span>
									</li>
									<li
										class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
										<h6 class="mb-0">
											<svg xmlns="http://www.w3.org/2000/svg" width="24"
												height="24" viewBox="0 0 24 24" fill="none"
												stroke="currentColor" stroke-width="2"
												stroke-linecap="round" stroke-linejoin="round"
												class="feather feather-instagram mr-2 icon-inline text-danger">
															<rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect>
															<path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path>
															<line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line></svg>
											Instagram
										</h6> <span class="text-secondary">bootdey</span>
									</li>
									<li
										class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
										<h6 class="mb-0">
											<svg xmlns="http://www.w3.org/2000/svg" width="24"
												height="24" viewBox="0 0 24 24" fill="none"
												stroke="currentColor" stroke-width="2"
												stroke-linecap="round" stroke-linejoin="round"
												class="feather feather-facebook mr-2 icon-inline text-primary">
															<path
													d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path></svg>
											Facebook
										</h6> <span class="text-secondary">bootdey</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="col-md-8">
							<div class="card mb-3">
								<div class="card-body">
									<div class="row">
										<div class="col-sm-3">
											<h6 class="mb-0">닉네임</h6>
										</div>
										<div id="viewNickName" class="col-sm-9 text-secondary">Kenneth
											Valdez</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-sm-3">
											<h6 class="mb-0">이메일</h6>
										</div>
										<div id="viewEmail" class="col-sm-9 text-secondary">fip@jukmuh.al</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-sm-3">
											<h6 class="mb-0">전화번호</h6>
										</div>
										<div id="viewPhone" class="col-sm-9 text-secondary">(239)
											816-9029</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-sm-3">
											<h6 class="mb-0">Address</h6>
										</div>
										<div id="viewAddr" class="col-sm-9 text-secondary">Bay
											Area, San Francisco, CA</div>
									</div>
									<hr>
								</div>
							</div>
							<div id="viewCounter"
								class="container bootstrap snippets bootdey">
								<div class="row">
									<div class="col-lg-2 col-sm-6">
										<div class="circle-tile ">
											<a href="#"><div class="circle-tile-heading dark-blue">
													<i class="fa fa-users fa-fw fa-3x"></i>
												</div></a>
											<div class="circle-tile-content dark-blue">
												<div class="circle-tile-description text-faded">게시글 수</div>
												<div class="circle-tile-number text-faded "><span id="viewWrite">265</span></div>
												<a class="circle-tile-footer" href="#">More Info<i
													class="fa fa-chevron-circle-right"></i></a>
											</div>
										</div>
									</div>

									<div class="col-lg-2 col-sm-6">
										<div class="circle-tile ">
											<a href="#"><div class="circle-tile-heading red">
													<i class="fa fa-users fa-fw fa-3x"></i>
												</div></a>
											<div class="circle-tile-content red">
												<div class="circle-tile-description text-faded">총합 포인트</div>
												<div id="number2" class="circle-tile-number text-faded "><span id="viewTotalPoint">265</span></div>
												<a class="circle-tile-footer" href="#">More Info<i
													class="fa fa-chevron-circle-right"></i></a>
											</div>
										</div>
									</div>

									<div class="col-lg-2 col-sm-6">
										<div class="circle-tile ">
											<a href="#"><div class="circle-tile-heading yellow ">
													<i class="fa fa-envelope fa-fw fa-3x"></i>
												</div></a>
											<div class="circle-tile-content yellow">
												<div class="circle-tile-description text-faded">
													보유 포인트</div>
												<div id="number3" class="circle-tile-number text-faded "><span id="viewPoint">265</span></div>
												<a class="circle-tile-footer" href="#">More Info<i
													class="fa fa-chevron-circle-right"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div> 

 <div id="usermodifymodal" class="modal fade bd-example-modal-xl"
	tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="container">
				<div class="main-body">
					<div class="row">
						<div class="col-lg-4">
							<div class="card">
								<div class="card-body">
									<div class="d-flex flex-column align-items-center text-center">
										<img src="https://bootdey.com/img/Content/avatar/avatar6.png"
											alt="Admin" class="rounded-circle p-1 bg-primary" width="110">
										<div class="mt-3">
											<h4>John Doe</h4>
											<p class="text-secondary mb-1">Full Stack Developer</p>
											<p class="text-muted font-size-sm">Bay Area, San
												Francisco, CA</p>
											<button class="btn btn-primary">Follow</button>
											<button class="btn btn-outline-primary">Message</button>
										</div>
									</div>
									<hr class="my-4">
									<ul class="list-group list-group-flush">
										<li
											class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
											<h6 class="mb-0">
												<svg xmlns="http://www.w3.org/2000/svg" width="24"
													height="24" viewBox="0 0 24 24" fill="none"
													stroke="currentColor" stroke-width="2"
													stroke-linecap="round" stroke-linejoin="round"
													class="feather feather-globe me-2 icon-inline">
																<circle cx="12" cy="12" r="10"></circle>
																<line x1="2" y1="12" x2="22" y2="12"></line>
																<path
														d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path></svg>
												Website
											</h6> <span class="text-secondary">https://bootdey.com</span>
										</li>
										<li
											class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
											<h6 class="mb-0">
												<svg xmlns="http://www.w3.org/2000/svg" width="24"
													height="24" viewBox="0 0 24 24" fill="none"
													stroke="currentColor" stroke-width="2"
													stroke-linecap="round" stroke-linejoin="round"
													class="feather feather-github me-2 icon-inline">
																<path
														d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path></svg>
												Github
											</h6> <span class="text-secondary">bootdey</span>
										</li>
										<li
											class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
											<h6 class="mb-0">
												<svg xmlns="http://www.w3.org/2000/svg" width="24"
													height="24" viewBox="0 0 24 24" fill="none"
													stroke="currentColor" stroke-width="2"
													stroke-linecap="round" stroke-linejoin="round"
													class="feather feather-twitter me-2 icon-inline text-info">
																<path
														d="M23 3a10.9 10.9 0 0 1-3.14 1.53 4.48 4.48 0 0 0-7.86 3v1A10.66 10.66 0 0 1 3 4s-4 9 5 13a11.64 11.64 0 0 1-7 2c9 5 20 0 20-11.5a4.5 4.5 0 0 0-.08-.83A7.72 7.72 0 0 0 23 3z"></path></svg>
												Twitter
											</h6> <span class="text-secondary">@bootdey</span>
										</li>
										<li
											class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
											<h6 class="mb-0">
												<svg xmlns="http://www.w3.org/2000/svg" width="24"
													height="24" viewBox="0 0 24 24" fill="none"
													stroke="currentColor" stroke-width="2"
													stroke-linecap="round" stroke-linejoin="round"
													class="feather feather-instagram me-2 icon-inline text-danger">
																<rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect>
																<path
														d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path>
																<line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line></svg>
												Instagram
											</h6> <span class="text-secondary">bootdey</span>
										</li>
										<li
											class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
											<h6 class="mb-0">
												<svg xmlns="http://www.w3.org/2000/svg" width="24"
													height="24" viewBox="0 0 24 24" fill="none"
													stroke="currentColor" stroke-width="2"
													stroke-linecap="round" stroke-linejoin="round"
													class="feather feather-facebook me-2 icon-inline text-primary">
																<path
														d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path></svg>
												Facebook
											</h6> <span class="text-secondary">bootdey</span>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-lg-8">
							<div class="card">
								<div class="card-body">
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Full Name</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control" value="John Doe">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Email</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control"
												value="john@example.com">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Phone</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control"
												value="(239) 816-9029">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Mobile</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control"
												value="(320) 380-4539">
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-sm-3">
											<h6 class="mb-0">Address</h6>
										</div>
										<div class="col-sm-9 text-secondary">
											<input type="text" class="form-control"
												value="Bay Area, San Francisco, CA">
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3"></div>
										<div class="col-sm-9 text-secondary">
											<input type="button" class="btn btn-primary px-4"
												value="Save Changes">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="card">
										<div class="card-body">
											<h5 class="d-flex align-items-center mb-3">Project
												Status</h5>
											<p>Web Design</p>
											<div class="progress mb-3" style="height: 5px">
												<div class="progress-bar bg-primary" role="progressbar"
													style="width: 80%" aria-valuenow="80" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
											<p>Website Markup</p>
											<div class="progress mb-3" style="height: 5px">
												<div class="progress-bar bg-danger" role="progressbar"
													style="width: 72%" aria-valuenow="72" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
											<p>One Page</p>
											<div class="progress mb-3" style="height: 5px">
												<div class="progress-bar bg-success" role="progressbar"
													style="width: 89%" aria-valuenow="89" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
											<p>Mobile Template</p>
											<div class="progress mb-3" style="height: 5px">
												<div class="progress-bar bg-warning" role="progressbar"
													style="width: 55%" aria-valuenow="55" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
											<p>Backend API</p>
											<div class="progress" style="height: 5px">
												<div class="progress-bar bg-info" role="progressbar"
													style="width: 66%" aria-valuenow="66" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div> -->


<div id="userdeletemodal" class="modal fade bd-example-modal-lg"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">삭제?</div>
	</div>
</div>

</div>
</div>


<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="assets/js/dashboardjs/app.js"></script>
<script src="assets/js/dashboardjs/additional.js"></script>

</body>
</html>
