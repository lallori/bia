<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditExternalLinksPlaceURL" value="/de/geobase/EditExternalLinksPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="EditExtLinkPlaceDiv">
		<div class="title">
			<h5>EXTERNAL LINKS</h5>
			<c:if test="${place.placeAllId > 0 && place.prefFlag == 'P'}">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<a title="Edit External Links" href="${EditExternalLinksPlaceURL}" class="editButton" id="EditExtLinkPlace"></a>
			</security:authorize>
			</c:if>
		</div>
		
		<div class="list">
			<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">	
				<div class="row">
					<div class="value"><a id="linkSearch"  href="${currentExternalLink.externalLink}" target="_blank">${currentExternalLink.description}</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamePlace").css('visibility', 'visible');
	        $j("#EditGeoCoorPlace").css('visibility', 'visible'); 
			$j("#EditExtLinkPlace").css('visibility', 'visible');

			$j("#EditExtLinkPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditExtLinkPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>