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
	
	<c:url var="LastCourseTranscriptionPostURL" value="/teaching/getLastPostId.json">
		<c:param name="courseTopicId" value="${command.topicId}" />
		<c:param name="byCreationDate" value="true" />
	</c:url>
	
	<h6 style="margin-bottom: 10px;">AVAILABLE ACTIONS</h6>

	<c:if test="${not closed}">
		<a href="#" id="addNewPost" class="buttonMedium button_medium">Add New Post</a>
	</c:if>
	
	<a href="#" class="buttonMedium button_medium" id="button_refresh"><span><b>Refresh</b> page</span></a>
	
	<a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Course Resources</a>
	
	<script>
		$j(document).ready(function() {
			// RR: this scheduler searches for new course transcription topic post and
			// changes the css style of the refresh button
			window.checkLastPostIdTimer = setInterval(function() {
				console.log('...check for new posts');
				var _this = this;
				$j.ajax({
					type: "GET",
					url: "${LastCourseTranscriptionPostURL}",
					async: true,
					success: function(json) {
						if (json.operation === 'OK') {
							if (json.lastPostId !== ${lastPostId}) {
								$j("#button_refresh").css('color', 'red');
								clearInterval(window.checkLastPostIdTimer);
							}
						}
					},
					error: function(json) {
						// NOP
					}
				});
			}, 20000);
		
			$j('#editPostContainer').css('height','10%');
			$j('#postsContainer').css('height','85%');
			
			if ($j("#addNewPost").length > 0) {
				$j("#addNewPost").click(function() {
					var _this = $j(this);
					
					clearInterval(window.checkLastPostIdTimer);
					
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
			
			$j("#button_refresh").click(function() {
				window.location.replace('${refreshUrl}');
				return false;
			});
			
		});
	</script>