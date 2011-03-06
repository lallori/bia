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
		<h5>VOLUME DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsVolume" href="${EditDetailsVolume}">edit</a><span id="loading"/></security:authorize></h5>
		<div id="createdby"><h6>CREATED BY ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></h6></div>
		<hr id="lineSeparator"/>

		<div id="EditPortraitPersonDiv">
			<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
			<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></security:authorize></p>
		</div>

		<h3>${volume.serieList}</h3>
		<ul>
			<li><b>Volume/Filza (MDP): </b> ${volume.volNum}${volume.volLetExt}</li>
			<li><b>Start Date: </b> ${volume.startYear} ${volume.startMonth} ${volume.startDay}</li>
			<li><b>End Date: </b> ${volume.endYear} ${volume.endMonth} ${volume.endDay}</li>
			<li><b>Date Notes: </b> ${volume.dateNotes}</li>
		</ul>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			 $j("#EditContextVolume").attr('href', "${EditContextVolume}");
			 $j("#EditCorrespondentsVolume").attr('href', "${EditCorrespondentsVolume}");
			 $j("#EditDescriptionVolume").attr('href', "${EditDescriptionVolume}");
			 $j("#EditDetailsVolume").attr('href', "${EditDetailsVolume}");

			 $j("#EditDetailsVolume").volumeExplorer( {  
				volNum      : "${volume.volNum}",
				volLetExt   : "${volume.volLetExt}",
				checkVolumeURL : "${FindVolume}",
				target : $j("#body_right"), 
				remoteUrl : "${ShowExplorerVolume}",
				zIndex: 9999
			});  
			$j("#EditDetailsVolume").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsVolumeDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>