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
	
	<h6 style="margin-bottom: 10px;">AVAILABLE ACTIONS</h6>

	<a href="#" id="addNewPost" class="buttonLarge button_large">Continue Transcription</a>
	
	<a href="#" id="showCurrentTranscription" class="buttonLarge button_large">Current Transcription</a>
	
	<!-- <a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Course Resources</a>  -->
	
	<script>
		$j(document).ready(function() {
			$j('#editPostContainer').css('height','10%');
			$j('#postsContainer').css('height','85%');
			
			$j("#addNewPost").click(function() {
				var _this = $j(this);
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