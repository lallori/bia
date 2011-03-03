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

	<div id="EditContextVolumeDiv">
		<h5>Context <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditContextVolume" href="${EditContextVolume}">edit</a></security:authorize></h5>
		<hr id="lineSeparator"/>

		<ul>
			<li>${volume.ccontext}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$j(document).ready(function() {
		        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
		        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
				$j("#EditDetailsVolume").css('visibility', 'visible'); 

				$j("#EditContextVolume").click(function(){
					$j("#EditContextVolumeDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</security:authorize>