<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowCourseTranscriptionURL" value="/teaching/ShowCourseTranscription.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="completeDOM" value="false" />
		<c:param name="transcriptionMode" value="R" />
	</c:url>
	
	<c:url var="ShowCourseTranscriptionActionsURL" value="/teaching/ShowCourseTranscriptionActions.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="transcriptionMode" value="R" />
	</c:url>
	
	<c:url var="ShowTeachingManuscriptViewerURL" value="/teaching/ShowManuscriptViewer.do">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="resourcesForum" value="${resourcesForum}" />
	</c:url>

	<!-- <div class="ui-layout-north"></div>  -->
	
	<div id="roundRobinPosts" class="ui-layout-west" style="overflow: auto;">
	
		<div id="postsContainer" style="width:100%; height:85%; min-height: 300px; overflow:auto;">
		</div>
		
		<hr />
		
		<div id="editPostContainer" style="width:100%; height:10%; min-height: 55px; overflow:auto;">
		</div>
		
	</div>
	
	<div id="deletePostModal" title="Delete post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			Are you sure you want to delete this post?
		</p>
	</div>
	
	<div id="errorModal" title="Error" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<span id="errorMsg"></span>
		</p>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto"
		src="${ShowTeachingManuscriptViewerURL}"></iframe>
		
		
	<script>
		$j(document).ready(function() {
			this.title = '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.courseTranscription"/>';
			$j('#postsContainer').load('${ShowCourseTranscriptionURL}');
			$j('#editPostContainer').load('${ShowCourseTranscriptionActionsURL}');
			
			window.setEditMode = function(editMode) {
				if (typeof editMode !== 'undefined') {
					if (editMode) {
						$j(".notEditMode").each(function() {
							$j(this).css('display', 'none');
						})
						$j("#clientEditing").val(true);
					} else {
						$j(".notEditMode").each(function() {
							$j(this).removeAttr('style');
						})
						$j("#clientEditing").val(false);
					}
				}
			}
		});
		
		
	</script>