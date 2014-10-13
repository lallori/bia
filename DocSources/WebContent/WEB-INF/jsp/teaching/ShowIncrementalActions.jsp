<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditIncrementalPostURL" value="/teaching/ShowIncrementalEditPost.do">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<c:url var="ShowCurrentTranscriptionURL" value="/teaching/ShowCurrentTranscription.json">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<c:url var="ShowCourseResourcesURL" value="/community/ShowForum.do">
		<c:param name="forumId" value="${resourcesForum}" />
		<c:param name="completeDOM" value="true" />
	</c:url>
	
	<c:url var="LastCourseTranscriptionPostURL" value="/teaching/getLastPostId.json">
		<c:param name="courseTopicId" value="${command.topicId}" />
		<%-- Change the following parameter to true when the topic ordering will be by creation date --%>
		<c:param name="byCreationDate" value="false" />
	</c:url>
	
	<h6 style="margin-bottom: 10px;">AVAILABLE ACTIONS</h6>

	<c:choose>
		<c:when test="${not closed}">
			<c:choose>
				<c:when test="${postsNumber == 0}">
					<a href="#" id="addNewPost" class="buttonLarge button_large">Start Transcription</a>
				</c:when>
				<c:otherwise>
					<a href="#" id="addNewPost" class="buttonLarge button_large">Continue Transcription</a>
				</c:otherwise>
			</c:choose>
			
			<a href="#" id="showCurrentTranscription" class="buttonLarge button_large">Current Transcription</a>
			
			<a href="#" class="buttonMedium button_medium" id="button_refresh"><span><b>Refresh</b> page</span></a>
		</c:when>
		<c:otherwise>
			<a href="#" id="showCurrentTranscription" class="buttonLarge button_large">Final Transcription</a>
		</c:otherwise>
	</c:choose>
	
	<!-- <a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Course Resources</a>  -->
	
	<script>
		$j(document).ready(function() {
			// RR: this scheduler searches for new course transcription topic post and
			// changes the css style of the refresh button
			window.checkLastPostIdTimer = setInterval(function() {
				console.log('Last post identifier is ' + ${lastPostId});
				console.log('...check for new posts');
				var _this = this;
				$j.ajax({
					type: "GET",
					url: "${LastCourseTranscriptionPostURL}",
					async: true,
					success: function(json) {
						if (json.operation === 'OK') {
							console.log('Server said the last post is ' + json.lastPostId);
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
					
					$j("#editPostContainer").load('${EditIncrementalPostURL}', function(responseText, statusText, xhr) {
						if (statusText == 'success') {
							$j(_this).unbind();
							// from ShowIncrementalCourseTranscriptionDOM
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
			
			$j("#showCurrentTranscription").click(function() {
				$j.ajax({
					type: "GET",
					url: "${ShowCurrentTranscriptionURL}",
					async: false,
					success: function(data) {
						if (data.operation === 'OK') {
							if (data.transcription !== null && data.transcription !== '') {
								$j("#transcriptionContent").html(data.transcription);
							} else {
								$j("#transcriptionContent").html("{ <b>Empty transcription</b> }");
							}
							$j("#transcriptionDialog").dialog('open');
						} else {
							$j("#errorMsg").text('There was a server error during this operation: please retry!');
							$j("#errorModal").dialog('open');
						}
					},
					error: function(data) {
						$j("#errorMsg").text('There was a server error during this operation: please retry!');
						$j("#errorModal").dialog('open');
					}
				});
				return false;
			});
		});
	</script>