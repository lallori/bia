<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h2>Welcome back <security:authentication property="principal.firstName"/>. <br />From your last log on:</h2>
			<ul>
				<li>you have <b><li><b>${archiveStatistics['Message']}</b> new messages</li>
	
				<li><b>${archiveStatistics['Volume']}</b> new volumes have been entered</li>
				<li><b>${archiveStatistics['Document']}</b> new documents have been entered</li>
				<li><b>${archiveStatistics['People']}</b> new people have been entered</li>
				<li><b>${archiveStatistics['Place']}</b>  new places and/or locations have been entered</li>
	
			</ul>				
	</div>
	
