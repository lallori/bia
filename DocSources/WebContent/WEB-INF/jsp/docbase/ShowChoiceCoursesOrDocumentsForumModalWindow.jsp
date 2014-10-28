<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="CreateCourseTranscriptionURL" value="/teaching/CreateCourseTranscription.do" />
	
	<c:url var="ShowCourseFragmentContainerURL" value="/community/ShowForum.do">
		<c:param name="forumId" value="${courseFragmentContainer}" />
		<c:param name="completeDOM" value="true" />
	</c:url>
	
	<c:url var="ShowCourseTranscriptionURL" value="/teaching/ShowCourseTranscription.do">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="completeDOM" value="true" />
	</c:url>
	
	<c:url var="ShowConfirmCreateDocumentForumURL" value="/src/docbase/ShowConfirmCreateDocumentForum.do">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>
	
	<div id="modalContent">
	
		<div id="showChoiceContent">
			<h1><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.selectOption"/></h1>
			
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
				<c:if test="${(not empty activeCourses or activeCourse != null ) and empty courses and command.entryId > 0}">
					<div class="choiceContentFragment">
						<a id="createCourseTranscriptionButton" href="#" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.createCourseTranscription"/></a>
					</div>
				</c:if>
				<c:if test="${(not empty activeCourses or activeCourse != null ) and not empty courses and command.entryId > 0}">
					<div class="choiceContentFragment">
						<a id="createCourseTranscriptionButton" href="#" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.createAnotherTranscription"/></a>
					</div>
				</c:if>
			</security:authorize>
			
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
				<!--
				<c:if test="${not empty courses}">
					<a id="showCourseTranscriptionButton" href="${ShowCourseFragmentContainerURL}" target="_blank" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.showCourseTranscription"/></a>
				</c:if>
				-->
				<c:if test="${not empty courses}">
					<div class="choiceContentFragment">
						<a id="showDocumentCourseFragments" href="#" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.showCourseTranscriptions"/></a>
					</div>
				</c:if>
			</security:authorize>
			
			<c:choose>
				<c:when test="${hasForum}">
					<div class="choiceContentFragment">
						<a id="showDocumentForumButton" href="${forumUrlCompleteDOM}" target="_blank" class="button_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.documentDiscussion"/></a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="choiceContentFragment">
						<a id="documentDiscussionButton" href="#" class="button_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.documentDiscussion"/></a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div id="chooseExistentCourseFragment" style="display: none;">
			<h1><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.theCurrentDocumentsLinked"/></h1>
			<div class="fixedTable">
				<div class="head">
					<table>
						<thead>
							<tr>
								<th class="firstColumn"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.course0"/></th>
								<th class="secondColumn"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.transcription0"/></th>
								<th class="thirdColumn"></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="body">
					<table>
						<c:forEach items="${extendedTopics}" var="currentTopic" varStatus="status">
							<c:url var="ShowFragmentContainerURL" value="/community/ShowForum.do">
								<c:param name="forumId" value="${currentTopic.courseTopic.forum.forumId}" />
								<c:param name="completeDOM" value="true" />
							</c:url>
							<c:url var="ShowCourseFragmentURL" value="/teaching/ShowCourseTranscription.do">
								<c:param name="entryId" value="${command.entryId}" />
								<c:param name="topicId" value="${currentTopic.courseTopic.topicId}" />
								<c:param name="transcriptionMode" value="${currentTopic.mode}" />
								<c:param name="completeDOM" value="true" />
							</c:url>
							<tr>
								<td class="firstColumn">${currentTopic.courseTopic.forum.forumParent.title}</td>
								<td class="secondColumn"><a class="action" href="${ShowCourseFragmentURL}" target="_blank">${currentTopic.courseTopic.subject}</a></td>
								<td class="thirdColumn"><a class="action" href="${ShowFragmentContainerURL}" target="_blank"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.viewResources"/></a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		
		<div id="chooseTitleContent" style="display: none;">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
				<h1><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.chooseTitle"/></h1>
				
				<div id="error" class="error" style="display: none;"></div>
				
				<input id="title" name="title" type="text" value=""/>
				
				<c:choose>
					<c:when test="${not empty activeCourse}">
						<div>
							<span><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.linkedToCourse"/></span>
							<span>${activeCourse.forum.title}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<span><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.linkedToCourse"/></span>
							<select id="linkedCourses">
								<c:forEach items="${activeCourses}" var="course">
									<option value="${course.courseId}">${course.forum.title}</option>
								</c:forEach>
							</select>
						</div>
					</c:otherwise>
				</c:choose>
					
				<a id="createCourseButton" class="button_small" href="#"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.create"/></a>
			</security:authorize>
			<a id="abort" href="#" class="button_small"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.cancel"/></a>
		</div>
			
	</div>
	
	<script>
		$j(document).ready(function() {
			//$j("#MB_content").css("height", "150px");
			var linkedCourse;
			if (${empty activeCourses and not empty activeCourse}) {
				linkedCourse = '${activeCourse.courseId}';
			}
			
			$j("#close,#cancel,#abort").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#showDocumentForumButton,#showCourseTranscriptionButton,.action").click(function() {
	 			Modalbox.hide();
				return true;
			});

			$j("#createCourseTranscriptionButton").click(function() {
	 			$j("#showChoiceContent").hide();
	 			$j("#chooseTitleContent").show();
				return false;
			});
			
			$j("#showDocumentCourseFragments").click(function() {
	 			$j("#showChoiceContent").hide();
	 			Modalbox.resize(200, 0);
	 			$j("#chooseExistentCourseFragment").show();
				return false;
			});

			$j("#documentDiscussionButton").click(function() {
				Modalbox.show('${ShowConfirmCreateDocumentForumURL}', {title: '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.discussions"/>', width: 470, height: 100});
				return false;
			});
			
			$j("#createCourseButton").click(function() {
				var courseTitle = $j("#title").val();
				if (!isEmpty(courseTitle)) {
					// TODO: handle course typo
					var mode = "I";
					$j.ajax({
						type: "POST", 
						url: "${CreateCourseTranscriptionURL}",
						cache: false,
						data: "entryId=" + ${command.entryId} + "&courseTitle=" + $j("#title").val() + "&courseId=" + (typeof linkedCourse === 'undefined' || linkedCourse == null ? $j("#linkedCourses").find(":selected").val() : linkedCourse) + "&transcriptionMode=" + mode,
						async: false,
						success: function(data) {
							if (typeof data.error === 'undefined') {
								Modalbox.hide();
								// var url = '${ShowDocumentRoundRobinTranscriptionURL}' + '&topicId=' + data.courseTopicId;
								var url = '${ShowCourseTranscriptionURL}' + '&topicId=' + data.courseTopicId + '&trancriptionMode=' + mode;
								var tab = window.open(url, '_blank');
								tab.focus();
							} else {
								showError(data.error);
							}
						},
						error: function() {
							showError('<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.error.serverError"/>');
						}
					});
				} else {
					showError('<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.error.emptyTitle"/>');
				}
				return false;
			});
			
			var showError = function(err) {
				$j("#error").html(err);
				$j("#error").show();
				Modalbox.resize(0, 0);
			}
			
			var isEmpty = function(input) {
				var onlySpaces = /^ * *$/;
				return input.match(onlySpaces);
			};
		});
	</script>
