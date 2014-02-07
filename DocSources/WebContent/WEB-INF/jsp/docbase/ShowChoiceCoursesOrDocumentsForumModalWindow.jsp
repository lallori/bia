<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="CreateRoundRobinTranscriptionURL" value="/src/docbase/CreateRoundRobinTranscription.do" />
	
	<c:url var="ShowDocumentRoundRobinTranscriptionURL" value="/teaching/ShowDocumentRoundRobinTranscription.do">
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
				<c:if test="${activeCourses > 0 && empty course && command.entryId > 0}">
					<a id="roundRobinTranscriptionButton" href="#" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.createRoundRobinTranscription"/></a>
				</c:if>
			</security:authorize>
			
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
				<c:if test="${not empty course}">
					<a id="viewRoundRobinTranscriptionButton" href="#" class="button_extra_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.showRRTranscription"/></a>
				</c:if>
			</security:authorize>
			
			<c:choose>
				<c:when test="${hasForum}">
					<a id="showDocumentForumButton" href="${forumUrlCompleteDOM}" target="_blank" class="button_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.documentDiscussion"/></a>
				</c:when>
				<c:otherwise>
					<a id="documentDiscussionButton" href="#" class="button_large"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.documentDiscussion"/></a>
				</c:otherwise>
			</c:choose>
	
			<a id="cancel" href="#" class="button_small"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.cancel"/></a>
		</div>
		
		<div id="chooseTitleContent" style="display: none;">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
				<h1><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.chooseTitle"/></h1>
				
				<div id="error" class="error" style="display: none;"></div>
				
				<input id="title" name="title" type="text" value="" />
					
				<a id="createCourseButton" class="button_extra_large" href="#"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.create"/></a>
			</security:authorize>
			<a id="abort" href="#" class="button_small"><fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.cancel"/></a>
		</div>
			
	</div>
	

	<script>
		$j(document).ready(function() {
			$j("#MB_content").css("height", "70px");
			
			$j("#close,#cancel,#abort").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#showDocumentForumButton").click(function() {
	 			Modalbox.hide();
				return true;
			});

			$j("#roundRobinTranscriptionButton").click(function() {
	 			$j("#showChoiceContent").hide();
	 			$j("#chooseTitleContent").show();
				return false;
			});

			$j("#viewRoundRobinTranscriptionButton").click(function() {
				var url = '${ShowDocumentRoundRobinTranscriptionURL}' + '&topicId=' + '${topic.topicId}';
				window.open(url, '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.roundRobinTranscription"/>', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=no');
				Modalbox.hide();
				return false;
			});

			$j("#documentDiscussionButton").click(function() {
				Modalbox.show('${ShowConfirmCreateDocumentForumURL}', {title: '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.discussions"/>', width: 470, height: 100});
				return false;
			});
			
			$j("#createCourseButton").click(function() {
				var courseTitle = $j("#title").val();
				if (!isEmpty(courseTitle)) {
					$j.ajax({
						type: "POST", 
						url: "${CreateRoundRobinTranscriptionURL}",
						cache: false,
						data: "entryId=" + ${command.entryId} + "&courseTitle=" + $j("#title").val(),
						async: false,
						success: function(data) {
							if (typeof data.error === 'undefined') {
								Modalbox.hide();
								var url = '${ShowDocumentRoundRobinTranscriptionURL}' + '&topicId=' + data.courseTopic.topicId;
								window.open(url, '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.roundRobinTranscription"/>', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=no');
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
