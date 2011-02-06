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
	
	<div id="EditCorrespondentsVolumeDiv">
		<h5>Correspondents <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditCorrespondentsVolume" href="${EditCorrespondentsVolume}">edit</a></security:authorize></h5>
		<hr id="lineSeparator"/>
		
		<ul>
			<li><b>From:</b>${volume.senders}</li>
			<li><b>To:</b>${volume.recips}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$j(document).ready(function() {
				 $j("#EditContextVolume").attr('href', "${EditContextVolume}");
				 $j("#EditCorrespondentsVolume").attr('href', "${EditCorrespondentsVolume}");
				 $j("#EditDescriptionVolume").attr('href', "${EditDescriptionVolume}");
				 $j("#EditDetailsVolume").attr('href', "${EditDetailsVolume}");

				 $j("#EditCorrespondentsVolume").click(function(){
					 $j("#EditCorrespondentsVolumeDiv").load($j(this).attr("href"));
					 return false;
				});
			});
		</script>
	</security:authorize>