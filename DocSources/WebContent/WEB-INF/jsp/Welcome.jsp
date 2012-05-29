<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h2>Welcome back <security:authentication property="principal.firstName"/>. <br />From your last log on:</h2>
			<ul>
				<c:if test="${archiveStatistics['Message'] == 0 || archiveStatistics['Message'] > 1}">
							<b><li>you have <li><b>${archiveStatistics['Message']}</b> new messages</li>
				</c:if>
				<c:if test="${archiveStatistics['Message'] == 1}">
							<b><li>you have <li><b>${archiveStatistics['Message']}</b> new message</li>
				</c:if>
				
				<c:if test="${archiveStatistics['Volume'] == 0 || archiveStatistics['Volume'] > 1}">
							<li><b>${archiveStatistics['Volume']}</b> new volumes have been entered</li>
				</c:if>
				<c:if test="${archiveStatistics['Volume'] == 1}">
							<li><b>${archiveStatistics['Volume']}</b> new volume has been entered</li>
				</c:if>
				
				<c:if test="${archiveStatistics['Document'] == 0 || archiveStatistics['Document'] > 1}">
							<li><b>${archiveStatistics['Document']}</b> new documents have been entered</li>
				</c:if>
				<c:if test="${archiveStatistics['Document'] == 1}">
							<li><b>${archiveStatistics['Document']}</b> new document has been entered</li>
				</c:if>
	
				<c:if test="${archiveStatistics['People'] == 0 || archiveStatistics['People'] > 1}">
							<li><b>${archiveStatistics['People']}</b> new bios have been entered</li>
				</c:if>
				<c:if test="${archiveStatistics['People'] == 1}">
							<li><b>${archiveStatistics['People']}</b> new bio has been entered</li>
				</c:if>
				
				<c:if test="${archiveStatistics['Place'] == 0 || archiveStatistics['Place'] > 1}">
							<li><b>${archiveStatistics['Place']}</b> new places have been entered</li>
				</c:if>
				<c:if test="${archiveStatistics['Place'] == 1}">
							<li><b>${archiveStatistics['Place']}</b> new places has been entered</li>
				</c:if>
				
				<c:if test="${archiveStatistics['Comments'] == 0 || archiveStatistics['Comments'] > 1}">
							<li><b>${archiveStatistics['Comments']}</b> new community comments have been entered</li>
				</c:if>
				<c:if test="${archiveStatistics['Comments'] == 1}">
							<li><b>${archiveStatistics['Comments']}</b> new community comment has been entered</li>
				</c:if>
				
	
			</ul>				
	</div>
	
