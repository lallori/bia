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
	
	<div id="EditDetailsVolumeDiv">
		<div id="createdby"><h6>CREATED BY ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></h6></div>
		<h5>VOLUME DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsVolume" href="${EditDetailsVolume}">edit</a></security:authorize></h5>
		<h3>${volume.serieList}</h3>
		<ul>
			<li><b>Volume/Filsa (MDP): </b> ${volume.volNum}${volume.volLeText}</li>
			<li><b>Start Date: </b> ${volume.startYear} ${volume.startMonth} ${volume.startDay}</li>
			<li><b>End Date: </b> ${volume.endYear} ${volume.endMonth} ${volume.endDay}</li>
			<li><b>Date Notes: </b> ${volume.dateNotes}</li>
		</ul>
	</div>

	<br />

	<div id="EditDescriptionVolumeDiv">
		<h5>Description <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDescriptionVolume" href="${EditDescriptionVolume}">edit</a></security:authorize></h5>
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
		
	<br />

	<div id="EditCorrespondentsVolumeDiv">
		<h5>Correspondents <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditCorrespondentsVolume" href="${EditCorrespondentsVolume}">edit</a></security:authorize></h5>
		<ul>
			<li><b>From:</b>${volume.senders}</li>
			<li><b>To:</b>${volume.recips}</li>
		</ul>
	</div>
		
	<br />
	
	<div id="EditContextVolumeDiv">
		<h5>Context <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditContextVolume" href="${EditContextVolume}">edit</a></security:authorize></h5>
		<ul>
			<li>${volume.ccontext}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$().ready(function() {
				$(document).ready(function() {
					$("#EditDetailsVolume").click(function(){$("#EditDetailsVolumeDiv").load($(this).attr("href"));return false;});
					$("#EditDescriptionVolume").click(function(){$("#EditDescriptionVolumeDiv").load($(this).attr("href"));return false;});
					$("#EditCorrespondentsVolume").click(function(){$("#EditCorrespondentsVolumeDiv").load($(this).attr("href"));return false;});
					$("#EditContextVolume").click(function(){$("#EditContextVolumeDiv").load($(this).attr("href"));return false;});
				});
			});
		</script>
	</security:authorize>