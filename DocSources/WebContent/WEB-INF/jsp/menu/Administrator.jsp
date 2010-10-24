<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				<div id="adm_menu">
					<div id="admmenu" class="menuadm">
						<ul>
							<li><a href="#">User Management</a>

								<ul>
									<li><a href="#">Browse users</a></li>
									<li><a href="#">Activate User Accounts</a></li>
									<li><a href="#">Add new User</a></li>
									<li><a href="#">Edit User</a></li>
								</ul>
							</li>

							<li><a href="#">Reports and Revisions</a>
								<ul>
									<li><a href="#">Activy report</a></li>
									<li><a href="#">Comments report</a></li>
									<li><a href="#">Statistics</a></li>
									<li><a href="#">Revisions Database</a>

										<ul>
											<li><a href="#">Documents revisions</a></li>
											<li><a href="#">People revisions</a></li>
											<li><a href="#">Volume revisions</a></li>
											<li><a href="#">Places revisions</a></li>
										</ul>
									</li>

								</ul>
							</li>
							<li><a href="#">System Management</a>
								<ul>
									<li><a href="#">Servers status</a></li>
									<li><a href="#">Backup and Restore</a>
										<ul>

											<li><a href="#">Scheduled Backups</a></li>
											<li><a href="#">Manual Backup</a></li>
											<li><a href="#">Browse Data Backups</a></li>
											<li><a href="#">Browse Logs Backup</a></li>
										</ul>
									</li>
								</ul>		
							</li>

						</ul>
						<br style="clear: left" />
					</div>
				</div>
			</security:authorize>