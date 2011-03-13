<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="checkVolumeDigitizedUrl" value="/src/volbase/CheckVolumeDigitized.json">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>

	<c:url var="ShowExplorerVolumeUrl" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
		<c:param name="flashVersion" value="true" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsVolume" value="/de/volbase/EditDetailsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditDetailsVolumeDiv">
		<h5>VOLUME DETAILS </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditDetailsVolume" href="${EditDetailsVolume}">edit</a><span id="loading"/>
	</security:authorize>
		<div id="createdby"><h6>CREATED BY ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></h6></div>
		<hr id="lineSeparator"/>

		<div id="SpineVolumeDiv">
			<p><b>Volume Spine</b></p>
		</div>
		<!-- <div id="SpineVolumeDiv">
			<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
			<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></security:authorize></p>
		</div> -->

		<h3>${volume.serieList}</h3>
		<ul>
			<li><b>Volume/Filza (MDP): </b> ${volume.volNum}${volume.volLetExt}</li>
			<li><b>Start Date: </b> ${volume.startYear} ${volume.startMonth} ${volume.startDay}</li>
			<li><b>End Date: </b> ${volume.endYear} ${volume.endMonth} ${volume.endDay}</li>
			<li><b>Date Notes: </b> ${volume.dateNotes}</li>
		</ul>
	</div>
	<br>
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'visible');
	        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
	        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
			$j("#EditDetailsVolume").css('visibility', 'visible'); 

			$j("#EditDetailsVolume").volumeExplorer( {
				summaryId      			: "${volume.summaryId}",
				checkVolumeDigitizedURL	: "${checkVolumeDigitizedUrl}",
				showExplorerVolumeURL	: "${ShowExplorerVolumeUrl}",
				target 					: $j("#body_right") 
			});  

			$j("#EditDetailsVolume").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsVolumeDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>