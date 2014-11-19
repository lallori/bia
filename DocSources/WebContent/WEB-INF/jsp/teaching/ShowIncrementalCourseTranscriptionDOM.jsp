<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowCourseTranscriptionURL" value="/teaching/ShowCourseTranscription.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="completeDOM" value="false" />
	</c:url>
	
	<c:url var="ShowCourseTranscriptionActionsURL" value="/teaching/ShowCourseTranscriptionActions.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="transcriptionMode" value="I" />
		<c:param name="canPartecipate" value="${isCoursePerson}" />
	</c:url>
	
	<c:url var="ShowPersonalNotesURL" value="/teaching/ShowPersonalNotes.do" />
	
	<c:url var="ShowTeachingManuscriptViewerURL" value="/teaching/ShowManuscriptViewer.do">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="resourcesForum" value="${resourcesForum}" />
		<c:param name="canPartecipate" value="${isCoursePerson}" />
	</c:url>

	<!-- <div class="ui-layout-north"></div>  -->
	
	<div id="roundRobinPosts" class="ui-layout-west" style="overflow: auto;">
	
		<div id="postsContainer">
		</div>
		
		<hr id="postRuler" />
		
		<div id="editPostContainer">
		</div>
		
		<div id="personalNotesSection" style="display: none; height: 95%; max-width: 800px; min-width: 300px;">
		</div>
		
	</div>
	
	
	<div id="deletePostModal" title="Delete post" style="display: none;"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="teaching.showIncrementalCourseTranscriptionDOM.areYouSure"/>
		</p>
	</div>
	
	<div id="errorModal" title="Error" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<span id="errorMsg"></span>
		</p>
	</div>
	
	<div id="transcriptionDialog" title="Current transcription" style="display: none;">
		<span id="transcriptionContent"></span>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto"
		src="${ShowTeachingManuscriptViewerURL}"></iframe>
		
		
	<script>
		$j(document).ready(function() {
			this.title = 'Course Transcription';
			$j('#postsContainer').load('${ShowCourseTranscriptionURL}');
			$j('#editPostContainer').load('${ShowCourseTranscriptionActionsURL}');
			$j('#personalNotesSection').load('${ShowPersonalNotesURL}');
			
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
			
			window.showSection = function(section) {
				if (section === 'userNote') {
					$j("#postsContainer").hide();
					$j("#postRuler").hide();
					$j("#editPostContainer").hide();
					$j("#personalNotesSection").show();
				} else {
					$j("#personalNotesSection").hide();
					$j("#postsContainer").show();
					$j("#postRuler").show();
					$j("#editPostContainer").show();
				}
			}
			
			$j("#errorModal").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 300,
				height: 130, 
				buttons: {
					Ok: function() {
						$j(this).dialog("close");
					}
				}
			});
			
			$j("#transcriptionDialog").dialog({
				autoOpen : false,
				modal: true,
				resizable: true,
				width: 400,
				height: 330, 
				buttons: {
					Ok: function() {
						$j(this).dialog("close");
					}
				}
			});
		});
		
		
	</script>