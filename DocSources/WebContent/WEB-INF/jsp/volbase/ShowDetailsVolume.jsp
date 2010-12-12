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
		<c:url var="FindVolume" value="/de/volbase/FindVolume.json">
			<c:param name="volNum"   value="${volume.volNum}" />
			<c:param name="volLetExt"   value="${volume.volLetExt}" />
		</c:url>
		<c:url var="ShowExplorerVolume" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="flashVersion" value="true" />
		</c:url>
	</security:authorize>
	
	<div id="EditDetailsVolumeDiv">
		<div id="EditPortraitPersonDiv">
			<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
			<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></security:authorize></p>
		</div>
		<div id="createdby"><h6>CREATED BY ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></h6></div>
		<h5>VOLUME DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsVolume" href="${EditDetailsVolume}">edit</a></security:authorize></h5>
		<hr id="lineSeparator"/>

		<h2 class="titlepeople">${volume.serieList}</h2>
		<ul>
			<li><b>Volume/Filsa (MDP): </b> ${volume.volNum}${volume.volLetExt}</li>
			<li><b>Start Date: </b> ${volume.startYear} ${volume.startMonth} ${volume.startDay}</li>
			<li><b>End Date: </b> ${volume.endYear} ${volume.endMonth} ${volume.endDay}</li>
			<li><b>Date Notes: </b> ${volume.dateNotes}</li>
		</ul>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#EditContextVolume").attr('href', "${EditContextVolume}");
			 $("#EditCorrespondentsVolume").attr('href', "${EditCorrespondentsVolume}");
			 $("#EditDescriptionVolume").attr('href', "${EditDescriptionVolume}");
			 $("#EditDetailsVolume").attr('href', "${EditDetailsVolume}");

			 $("#EditDetailsVolume").volumeExplorer( {  
				volNum      : "${volume.volNum}",
				volLetExt   : "${volume.volLetExt}",
				checkVolumeURL : "${FindVolume}",
				target : $("#body_right"), 
				remoteUrl : "${ShowExplorerVolume}",
				zIndex: 9999
			});  
			$("#EditDetailsVolume").click(function(){
				$("#EditDetailsVolumeDiv").load($(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>