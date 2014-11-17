<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowPersonalNotesURL" value="/teaching/ShowPersonalNotes.do" />
	
	<c:url var="SavePersonalNotesURL" value="/teaching/SavePersonalNotes.do" />

	<span id="personalNotesTitleSection">
		<span class="personalNotesTitle"><fmt:message key="teaching.showPersonalNotes.yOurPersonalNotes"/><span id="changedSign" title="changed" style="display: none;">&nbsp;*</span></span>
		<span class="personalNotesLastUpdate">
			<c:choose>
				<c:when test="${not empty userNoteUpdate}">
					<fmt:message key="teaching.showPersonalNotes.lastSaveAt"/> <span class="date"><fmt:formatDate value="${userNoteUpdate}" pattern="MM/dd/yyyy HH:mm:ss"/></span>
				</c:when>
				<c:otherwise>
					<fmt:message key="teaching.showPersonalNotes.neverSaved"/>
				</c:otherwise>
			</c:choose>
		</span>
	</span>
	
	<form id="personalNotesForm">
		<textarea class="personalNotes" name="personalNotes"><c:if test="${not empty userNote}">${userNote}</c:if></textarea>
		<input type="hidden" name="personalNotesId" value="${not empty userNoteId ? userNoteId : 0}" />
		<div id="personalNotesCommands">
			<div id="errorDiv" class="error" style="display: none;"></div>
			<div id="buttons">
				<a id="save" href="#" class="buttonSmall buttonDisabled"><fmt:message key="teaching.showPersonalNotes.save"/></a>
				<a id="restore" href="#" class="buttonSmall buttonDisabled"><fmt:message key="teaching.showPersonalNotes.restore"/></a>
				<a id="back" href="#" class="buttonMedium"><fmt:message key="teaching.showPersonalNotes.backToTheLesson"/></a>
			</div>
		</div>
	</form>
	
	<script>
		$j(document).ready(function() {
			
			var changed = false;
			
			$j("#personalNotesForm .personalNotes").bind('input', function() {
				if (!changed) {
					changed = true;
					$j("#changedSign").show();
					$j(".buttonDisabled").each(function() {
						$j(this).removeClass("buttonDisabled");
					});
				}
			});
			
			if ($j("#loadingDiv").length === 0) {
				$j("#personalNotesSection").append("<div id='loadingDiv' style='display: none; width: 100%; height: 100%;'></div>")
			}
			
			$j("#personalNotesCommands #buttons #save").click(function() {
				if (!changed) {
					return false;
				}
	        	$j("#loadingDiv").show();
	        	
				$j.ajax({
					type: "POST",
					url: '${SavePersonalNotesURL}',
					data: $j("#personalNotesForm").serialize(),
					async: false,
					success: function(json) {
						$j("#loadingDiv").hide();
						if (json.operation == 'OK') {
							$j('#personalNotesSection').load('${ShowPersonalNotesURL}');
						} else {
							$j("#personalNotesCommand #errorDiv").val("There was a server error, please retry! If problem persist please contact the admin!");
							$j("#personalNotesCommand #errorDiv").show();
						}
					},
					error: function() {
						$j("#loadingDiv").hide();
						alert('Server error...please contact the admin!');
					}
				});
				
				return false;
			});
			
			$j("#personalNotesCommands #buttons #back").click(function() {
				showSection('posts'); // from ShowIncrementalCourseTranscriptionDOM.jsp
				return false;
			});
			
			$j("#personalNotesCommands #buttons #restore").click(function() {
				if (changed) {
					$j("#loadingDiv").show();
					$j('#personalNotesSection').load('${ShowPersonalNotesURL}');
				}
				return false;
			});
			
		});
	</script>