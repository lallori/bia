<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="CreateCourseTranscriptionURL" value="/teaching/CreateCourseTranscriptionFromImage.do" />
	
	<c:url var="ShowCourseTranscriptionURL" value="/teaching/ShowCourseTranscription.do">
		<c:param name="completeDOM" value="true" />
	</c:url>

	<div id="modalContent">

		<div id="chooseTitleContent">
			<h1>Please write the title of the Course Transcription.</h1>
			
			<div id="error" class="error" style="display: none;"></div>
			
			<input id="title" name="title" type="text" value=""/>
			
			<c:choose>
				<c:when test="${not empty activeCourse}">
					<div>
						<span>linked to Course</span>
						<span>${activeCourse.forum.title}</span>
					</div>
				</c:when>
				<c:otherwise>
					<div>
						<span>linked to Course</span>
						<select id="linkedCourses">
							<c:forEach items="${activeCourses}" var="course">
								<option value="${course.courseId}">${course.forum.title}</option>
							</c:forEach>
						</select>
					</div>
				</c:otherwise>
			</c:choose>
				
			<a id="createCourseButton" class="button_small" href="#">Create</a>
			<a id="abort" href="#" class="button_small">Cancel</a>
		</div>
	
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {

			var linkedCourse;

			var showError = function(err) {
				$j("#error").html(err);
				$j("#error").show();
				Modalbox.resize(0, 0);
			};

			if (${empty activeCourses and not empty activeCourse}) {
				linkedCourse = '${activeCourse.courseId}';
			}

			$j("#abort").die();
			$j("#abort").click(function() {
				Modalbox.hide();
				return false;
			});
			
			$j("#createCourseButton").die();
			$j("#createCourseButton").click(function() {
				var courseTitle = $j("#title").val();
				if (!courseTitle.match(/^ * *$/)) {
					// TODO: handle course typo
					var mode = "I";
					$j.ajax({
						type: "POST", 
						url: "${CreateCourseTranscriptionURL}",
						cache: false,
						data: "imageId=" + ${command.imageId} + "&courseTitle=" + $j("#title").val() + "&courseId=" + (typeof linkedCourse === 'undefined' || linkedCourse == null ? $j("#linkedCourses").find(":selected").val() : linkedCourse) + "&transcriptionMode=" + mode,
						async: false,
						success: function(data) {
							if (typeof data.error === 'undefined') {
								Modalbox.hide();
								// var url = '${ShowDocumentRoundRobinTranscriptionURL}' + '&topicId=' + data.courseTopicId;
								var url = '${ShowCourseTranscriptionURL}' + '&entryId=' + data.entryId + '&topicId=' + data.courseTopicId + '&trancriptionMode=' + mode;
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
			
		});
	</script>