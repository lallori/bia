<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditContextVolume" value="/de/volbase/EditContextVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
		<c:url var="EditCorrespondentsVolume" value="/de/volbase/EditCorrespondentsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
		<c:url var="EditDescriptionVolume" value="/de/volbase/EditDescriptionVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
		<c:url var="EditDetailsVolume" value="/de/volbase/EditDetailsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditDescriptionVolumeDiv">
		<h5>Description <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDescriptionVolume" href="${EditDescriptionVolume}">edit</a></security:authorize></h5>
		<hr id="lineSeparator"/>
		
		<ul>
			<li><b>Organizational Criteria: </b>${volume.orgNotes}</li>
			<li><b>Condition: </b> ${volume.ccondition}</li>
			<li><b>Bound: </b>${volume.bound ? 'Yes' : 'No'}</li>
			<li><b>Folios Numbered: </b>${volume.folsNumbrd ? 'Yes' : 'No'}</li>
			<li><b>Alphabetical Index: </b>${volume.oldAlphaIndex ? 'Yes' : 'No'}</li>
			<li><b>Languages: </b>  ${volume.italian ? 'Italian' : '' } 
									${volume.spanish ? 'Italian' : ''}
									${volume.english ? 'English' : ''}
									${volume.latin ? 'Latin' : ''}
									${volume.german ? 'German' : ''}
									${volume.french ? 'French' : ''}
									${otherLang}
			</li>
			<li><b>Some Documents in Cipher: </b>${volume.cipher ? 'Yes' : 'No'}</li>
			<li><b>Cipher Notes: </b>${volume.cipherNotes}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$(document).ready(function() {
				 $("#EditContextVolume").attr('href', "${EditContextVolume}");
				 $("#EditCorrespondentsVolume").attr('href', "${EditCorrespondentsVolume}");
				 $("#EditDescriptionVolume").attr('href', "${EditDescriptionVolume}");
				 $("#EditDetailsVolume").attr('href', "${EditDetailsVolume}");
				 
				 $("#EditDescriptionVolume").click(function(){
					$("#EditDescriptionVolumeDiv").load($(this).attr("href"));
					return false;
				});
			});
		</script>
	</security:authorize>