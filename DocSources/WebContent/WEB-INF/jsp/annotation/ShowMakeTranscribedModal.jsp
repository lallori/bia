<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:choose>
		<c:when test="${empty annotation.transcribed or not annotation.transcribed}">
			<c:url var="markAsTranscribedURL" value="/annotation/markAsTranscribed.json" />
			
			<div id="makeAsTranscribedModalQuestion" style="display: none;">
				<p style="width: 100%; text-align: center;">Do you want to mark the relative annotation as transcribed?</p>
				<div style="width: 100%; text-align: center;  margin-top: 10px;">
					<div id="markAsTranscribedYesBtn" class="buttonSmall button_small" style="display: inline-block;">Yes</div>
					<div id="markAsTranscribedNoBtn" class="buttonSmall button_small" style="display: inline-block; margin-left: 20px;">No</div>
				</div>
			</div>
			
			<div id="makeAsTranscribedModalFormDiv" style="display: none;">
				<form:form id="makeAsTranscribedModalForm" method="post" action="${markAsTranscribedURL}">
					<div style="display: block;">
						<form:label id="annotationTitleLabel" path="annotationTitle" for="annotationTitle">Title of the transcribed annotation</form:label>
						<form:input id="annotationTitle" path="annotationTitle" cssStyle="margin-top: 3px; width: 80%; display: inline-block;" type="text"/>
						<a title="Adjust the title of the transcribed annotation. You should write the correct transcription only!"
							style="float: right; margin-right: 30px; margin-top: 5px; border-radius: 8px; font-size: 12px; padding-left: 5px; padding-right: 4px; font-weight: bold; border: 1px solid #2e83ff; color: #363636; background-color: #ebf3ff; opacity: 0.9;">?</a>
					</div>
					<c:if test="${annotation.type eq 'TEACHING'}">
						<br/>
						<div style="display: block;">
							<form:label id="exportCheckLabel" path="export" for="exportCheck">Export to 'General Questions'</form:label>
							<form:checkbox id="exportCheck" path="export" cssStyle="margin: 0 5px 0 0;" />
							<a title="Put the check to export this discussion to 'General Question' section. Note that you cannot export this twice (the exported discussion is a snapshot of the current one)!"
								style="float: right; margin-right: 30px; border-radius: 8px; font-size: 12px; padding-left: 5px; padding-right: 4px; font-weight: bold; border: 1px solid #2e83ff; color: #363636; background-color: #ebf3ff; opacity: 0.9;">?</a>
						</div>
					</c:if>
					<form:hidden path="annotationId" />
					<br/>
					<div id="serverError" style="margin-top: 5px; display: none; cursor: default; color: red;"></div>
					<div id="submitButtonContainer" style="width: 100%; text-align: center; margin-top: 10px;">
						<div id="closeBtn" class="buttonSmall button_small" style="display: inline-block;" title="abort the operation">Abort</div>
						<div id="saveBtn" class="buttonSmall button_small" style="display: inline-block; margin-left: 20px;">Save</div>
					</div>
				</form:form>
			</div>
		</c:when>
		<c:otherwise>
			<c:url var="markAsNotTranscribedURL" value="/annotation/markAsNotTranscribed.json">
				<c:param name="annotationId" value="${annotation.annotationId}" />"
			</c:url>
			
			<div id="makeAsNotTranscribedModalQuestion" style="display: none;">
				<div id="serverError" style="margin-top: 5px; display: none; cursor: default; color: red;"></div>
				<div id="makeAsNotTranscribedModalQuestionDiv">
					<c:choose>
						<c:when test="${empty annotation.exportedTo}">
							<p style="width: 100%; text-align: center;">Do you want to mark the relative annotation as not transcribed?</p>
						</c:when>
						<c:otherwise>
							<p style="width: 100%; text-align: center;">
								This discussion is already exported to 'General Questions' forum.
								<br/>
								Do you want to mark the relative annotation as not transcribed anyway?
							</p>
						</c:otherwise>
					</c:choose>
				</div>
				<div style="width: 100%; text-align: center; margin-top: 10px;">
					<div id="markAsNotTranscribedYesBtn" class="buttonSmall button_small" style="display: inline-block;">Yes</div>
					<div id="markAsNotTranscribedNoBtn" class="buttonSmall button_small" style="display: inline-block; margin-left: 20px;">No</div>
					<div id="markAsNotTranscribedCloseBtn" class="buttonSmall button_small" style="display: none;">Close</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div id="notAllowed" style="display: none;">
		<p style="width: 100%; text-align: center;">You have no privileges to do that!</p>
		<div style="width: 100%; text-align: center; margin-top: 10px;">
			<div id="notAllowedBtn" class="buttonSmall button_small" style="display: inline-block;">Close</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		<c:choose>
			<c:when test="${annotation.type eq 'TEACHING'}">
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
					var adminOk = true;
				</security:authorize>
			</c:when>
			<c:otherwise>
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					var adminOk = true;
				</security:authorize>
			</c:otherwise>
		</c:choose>
		
		$j(document).ready(function() {
			if ($j("#loadingDiv").length === 0) {
				$j("body").append("<div id='loadingDiv' style='display: none;'></div>")
			}
			
			if (typeof adminOk !== 'undefined') {
				
				<c:choose>
					<c:when test="${empty annotation.transcribed or not annotation.transcribed}">
					
						$j("#makeAsTranscribedModalQuestion").show();
						
						$j("#markAsTranscribedYesBtn").click(function() {
							$j("#makeAsTranscribedModalQuestion").hide();
							Modalbox.resize(70, 30);
							$j("#makeAsTranscribedModalFormDiv").show();
							return false;
						});
						
						$j("#saveBtn").click(function() {
							$j("#loadingDiv").css('height', $j(document).height());
							$j("#loadingDiv").css('width', $j(document).width());
				        	$j("#loadingDiv").show();
				        	
							$j.ajax({
									type: "POST",
									url: $j("#makeAsTranscribedModalForm").attr("action"),
									data: $j("#makeAsTranscribedModalForm").serialize(),
									async: false,
									success: function(json) {
										if (json.operation === 'OK') {
											window.location.replace(json.reloadURL);
										} else {
											$j("#loadingDiv").hide();
											$j("#serverError").html(json.error);
											$j("#serverError").show();
											$j("#annotationTitle").attr('disabled','disabled');
											$j("#exportCheck").attr('disabled','disabled');
											$j("#save").hide();
										}
									},
									error: function(html) {
										// Something wrong in the server
										$j("#loadingDiv").hide();
										$j("#serverError").html('There was an error on the server...please contact the admin!');
										$j("#serverError").show();
										$j("#annotationTitle").attr('disabled','disabled');
										$j("#exportCheck").attr('disabled','disabled');
										$j("#save").hide();
									}
							});
							return false;
								
						});
						
						$j("#markAsTranscribedNoBtn,#closeBtn").click(function() {
							Modalbox.hide();
							return false;
						});
					</c:when>
					<c:otherwise>
						$j("#makeAsNotTranscribedModalQuestion").show();
						
						$j("#markAsNotTranscribedYesBtn").click(function() {
							$j("#loadingDiv").css('height', $j(document).height());
							$j("#loadingDiv").css('width', $j(document).width());
				        	$j("#loadingDiv").show();
							
							$j.ajax({
									type: "POST",
									url: '${markAsNotTranscribedURL}',
									async: false,
									success: function(json) {
										if (json.operation === 'OK') {
											window.location.replace(json.reloadURL);
										} else {
											$j("#loadingDiv").hide();
											$j("#serverError").html(json.error);
											$j("#serverError").show();
											$j("#makeAsNotTranscribedModalQuestionDiv").hide();
											$j("#markAsNotTranscribedYesBtn").hide();
											$j("#markAsNotTranscribedNoBtn").hide();
											$j("#markAsNotTranscribedCloseBtn").show();
										}
									},
									error: function(html) {
										// Something wrong in the server
										$j("#loadingDiv").hide();
										$j("#serverError").html('There was an error on the server...please contact the admin!');
										$j("#serverError").show();
										$j("#makeAsNotTranscribedModalQuestionDiv").hide();
										$j("#markAsNotTranscribedYesBtn").hide();
										$j("#markAsNotTranscribedNoBtn").hide();
										$j("#markAsNotTranscribedCloseBtn").show();
									}
							});
							return false;
						});
						
						$j("#markAsNotTranscribedNoBtn,#markAsNotTranscribedCloseBtn").click(function() {
							Modalbox.hide();
							return false;
						});
					</c:otherwise>
				</c:choose>
				
			} else {
				$j("#notAllowed").show();
				
				$j("#notAllowedBtn").click(function() {
					Modalbox.hide();
					return false;
				});
			}
			
			
		});
	</script>
