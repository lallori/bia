<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
		<c:url var="EditContextVolumeURL" value="/de/volbase/EditContextVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditContextVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.showContextVolume.title"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${volume.summaryId > 0}">
			<a id="EditContextVolume" href="${EditContextVolumeURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.showContextVolume.context"/></div><div class="value80">${volume.ccontext}</div>
			</div>
			<div class="row">
				<div class="item"> <fmt:message key="volbase.showContextVolume.inventarioSommarioDescription"/> </div><div class="value80">${volume.inventarioSommarioDescription}</div>
			</div>
		</div>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
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