<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditContextVolume" value="/de/volbase/EditContextVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditContextVolumeDiv">
		<h5>Context </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditContextVolume" href="${EditContextVolume}">edit</a><span id="loading"/>
	</security:authorize>
		<hr id="lineSeparator"/>

		<ul>
			<li><b>Context: </b>${volume.ccontext}</li>
			<li><b>Inventario Sommario Description: </b>${volume.inventarioSommarioDescription}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditContextVolume").css('visibility', 'visible');
		        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
		        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
				$j("#EditDetailsVolume").css('visibility', 'visible'); 

				$j("#EditContextVolume").click(function(){
					$j(this).next().css('visibility', 'visible');
					$j("#EditContextVolumeDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</security:authorize>