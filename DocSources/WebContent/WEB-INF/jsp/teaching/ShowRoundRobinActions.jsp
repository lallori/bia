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
	
	<c:url var="AskAQuestionURL" value="/teaching/askAQuestion.json" />
	
	<h6 style="margin-bottom: 10px;"><fmt:message key="teaching.showRoundRobinActions.aVailableActions"/></h6>

	<c:choose>
		<c:when test="${not closed and canPartecipate}">
			<a href="#" id="addNewPost" class="buttonMedium button_medium"><fmt:message key="teaching.showRoundRobinActions.addNewPost"/></a>
			
			<a href="#" id="askAQuestion" class="buttonMedium button_medium"><b><fmt:message key="teaching.showRoundRobinActions.askAQuestion"/></b></a>
			
			<a href="#" class="buttonMedium button_medium" id="button_refresh"><span><b><fmt:message key="teaching.showRoundRobinActions.refresh"/></b> <fmt:message key="teaching.showRoundRobinActions.page"/></span></a>
		</c:when>
		<c:when test="${not closed and not canPartecipate}">
			<a href="#" class="buttonMedium button_medium" id="button_refresh"><span><b><fmt:message key="teaching.showRoundRobinActions.refresh"/></b> <fmt:message key="teaching.showRoundRobinActions.page"/></span></a>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium"><fmt:message key="teaching.showRoundRobinActions.lessonResources"/></a>
	
	<div id="askAQuestionStep1Modal" title="Ask a Question" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="teaching.showRoundRobinActions.areYouSureAsk"/>
		</p>
	</div>
	
	<div id="askAQuestionStep2Modal" title="Ask a Question" style="display:none"> 
		<form id="askAQuestionForm">
			<div>
				<label id="questionTitleLabel" for="questionTitle" style="display:block;"><fmt:message key="teaching.showRoundRobinActions.typeTheTitle"/></label>
				<input id="questionTitle" type="text" name="questionTitle" style="width: 98%"/>
			</div>
			<div>
				<label id="questionTextLabel" for="questionText" style="display:block;"><fmt:message key="teaching.showRoundRobinActions.typeTheQuestion"/></label>
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
			
			if ($j("#button_refresh").length > 0) {
				$j("#button_refresh").click(function() {
					window.location.replace('${refreshUrl}');
					return false;
				});
			}
			
			if ($j('#askAQuestion').length > 0) {
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
			}
		});
	</script>