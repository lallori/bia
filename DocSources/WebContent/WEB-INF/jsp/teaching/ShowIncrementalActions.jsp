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
		<c:param name="byCreationDate" value="true" />
	</c:url>
	
	<c:url var="AskAQuestionURL" value="/teaching/askAQuestion.json" />
	
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
			
			<a href="#" id="showCurrentTranscription" class="buttonMedium button_Medium">Current Transcription</a>
			
			<a href="#" id="askAQuestion" class="buttonLarge button_large">Classroom Discussion</a>
			
			<a href="#" id="button_refresh" class="buttonMedium button_medium"><span><b>Refresh</b> page</span></a>
		</c:when>
		<c:otherwise>
			<a href="#" id="showCurrentTranscription" class="buttonLarge button_large">Final Transcription</a>
		</c:otherwise>
	</c:choose>
	
	<a href="#" id="showPersonalNotes" class="buttonLarge button_large">Personal Notes</a>
	
	<!-- <a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Lesson Resources</a>  -->
	
	<div id="askAQuestionStep1Modal" title="Ask a Question" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 0 0;"></span>
			Are you sure you want to create a post in the Classroom Discussion forum?
		</p>
	</div>
	
	<div id="askAQuestionStep2Modal" title="Ask a Question" style="display:none"> 
		<form id="askAQuestionForm">
			<div>
				<label id="questionTitleLabel" for="questionTitle" style="display:block;">Type in here your question title</label>
				<input id="questionTitle" type="text" name="questionTitle" style="width: 98%"/>
			</div>
			<div>
				<label id="questionTextLabel" for="questionText" style="display:block;">Type in here your question</label>
				<input id="questionText" type="text" name="questionText" style="width: 98%"/>
			</div>
			<input type="hidden" id="courseTranscriptionTopicId" name="courseTranscriptionTopicId" value="${command.topicId}" />
		</form>
	</div>
	
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
			
			$j('#editPostContainer').css('height','15%');
			$j('#postsContainer').css('height','75%');
			
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
			
			$j('#askAQuestion').click(function() {
				$j("#askAQuestionStep1Modal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130, 
					buttons: {
						Ok: function() {
							$j(this).dialog("close");
							$j("#askAQuestionStep2Modal").dialog({
								autoOpen : false,
								modal: true,
								resizable: false,
								width: 300,
								height: 180, 
								buttons: {
									Ok: function() {
										$j(this).dialog("close");
										$j.ajax({
											type: "POST",
											url: "${AskAQuestionURL}",
											data: $j("#askAQuestionForm").serialize(),
											cache: false,
											async: false,
											success: function(json) {
												if (json.operation === 'OK') {
													window.location.href = json.redirectURL;
													return false;
												}
												alert('There was a problem during this operation...please contact the admin!');
											},
											error: function(data) {
												alert('Server error...please contact the admin!');
											}
										  });
										  return false;
									  },
									  Cancel: function() {
										  $j(this).dialog("close");
										  return false;
									  }
								  }
							  });
							  $j("#askAQuestionStep2Modal").dialog('open');
							  return false;
						  },
						  No: function() {
							  $j(this).dialog("close");
							  return false;
						  }
					  }
				  });
				$j("#askAQuestionStep1Modal").dialog('open');
				return false;
			});
			
			$j("#showPersonalNotes").click(function() {
				showSection('userNote'); // from ShowIncrementalCourseTranscriptionDOM.jsp
				return false;
			});
		});
	</script>