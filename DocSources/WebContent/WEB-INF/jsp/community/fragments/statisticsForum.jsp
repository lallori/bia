<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

					<div id="statisticsDiv">
						<h1>STATISTICS</h1>
						<p>Total posts <span id="totalPosts">${statisticsHashMap['postsNumber']}</span> &#8226; Total topics <span id="totalTopics">${statisticsHashMap['postsNumber']}</span> &#8226; Total members <span id="totalMembers">${statisticsHashMap['totalMembers']}</span> &#8226; Our newest member <a id="newestMember" class="link">${statisticsHashMap['newestMember']}</a></p>
					</div>
