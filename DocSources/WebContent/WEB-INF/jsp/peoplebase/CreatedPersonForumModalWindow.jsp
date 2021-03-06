<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:choose>
		<c:when test="${empty applicationThrowable}">
			<c:url var="ShowForumURL" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="CompleteDOM" value="true"/>
			</c:url>
			
			<div id="ConfirmCreateForum">
				<h1><fmt:message key="peoplebase.createdPersonForumModalWindow.discussionCreatedForPerson"/></h1>
				<a id="open" href="${ShowForumURL}" target="_blank" class="button_medium">OPEN FORUM</a>
			</div>
		
			<script>
				$j(document).ready(function() {
					$j("#open").click(function(){
						Modalbox.hide();
					});
				});
			</script>
		</c:when>
		<c:otherwise>
			<div id="ConfirmCreateForum">
				<h1><fmt:message key="peoplebase.createdPersonForumModalWindow.discussionNotCreatedForPerson"/></h1>
			</div>
		</c:otherwise>
	</c:choose>