<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowUserProfileURL" value="/community/ShowUserProfileForum.do">
	<c:param name="account" value="${statisticsHashMap['newestMember']}" />
</c:url>

<div id="statisticsDiv">
	<h1>STATISTICS</h1>
	<p>Total posts <span id="totalPosts">${statisticsHashMap['postsNumber']}</span> &#8226; Total topics <span id="totalTopics">${statisticsHashMap['topicsNumber']}</span> &#8226; Total members <span id="totalMembers">${statisticsHashMap['totalMembers']}</span> &#8226; Our newest member <a id="newestMember" class="link" href="${ShowUserProfileURL}">${statisticsHashMap['newestMember']}</a></p>
</div>

<script text="text/javascript">
	$j(document).ready(function(){
		$j("#newestMember").click(function(){
			$j("#main").load($j(this).attr('href'));
			return false;
		});
	});
</script>
