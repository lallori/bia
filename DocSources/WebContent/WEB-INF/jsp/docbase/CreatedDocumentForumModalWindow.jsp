<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowForumURL" value="/community/ShowForum.do">
		<c:param name="forumId"          value="${forum.forumId}" />
		<c:param name="CompleteDOM" value="true"/>
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1>Forum has been created.</h1>
		<a id="open" href="${ShowForumURL}" target="_blank" class="button_medium">OPEN FORUM</a></li>	
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
		});
	</script>
