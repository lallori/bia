<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditRoundRobinPostURL" value="/teaching/ShowEditRoundRobinPost.do">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<c:url var="ShowCourseResourcesURL" value="/community/ShowForum.do">
		<c:param name="forumId" value="${resourcesForum}" />
		<c:param name="completeDOM" value="true" />
	</c:url>
	
	<h6 style="margin-bottom: 10px;">AVAILABLE ACTIONS</h6>

	<c:if test="${not closed}">
		<a href="#" id="addNewPost" class="buttonMedium button_medium">Add New Post</a>
	</c:if>
	
	<a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Course Resources</a>
	
	<script>
		$j(document).ready(function() {
			$j('#editPostContainer').css('height','10%');
			$j('#postsContainer').css('height','85%');
			
			if ($j("#addNewPost").length > 0) {
				$j("#addNewPost").click(function() {
					var _this = $j(this);
					$j("#editPostContainer").load('${EditRoundRobinPostURL}', function(responseText, statusText, xhr) {
						if (statusText == 'success') {
							$j(_this).unbind();
							// from ShowRoundRobinCourseTranscriptionDOM
							setEditMode(true);
						} else {
							$j("#errorMsg").text('There was a server error during the page load: please refresh this page and retry!');
							$j("#errorModal").dialog('open');
						}
					});
					return false;
				});
			}
		});
	</script>