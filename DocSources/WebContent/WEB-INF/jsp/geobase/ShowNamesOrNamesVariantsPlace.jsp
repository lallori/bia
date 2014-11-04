<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditNamesOrNameVariantsPlaceURL" value="/de/geobase/EditNamesOrNameVariantsPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
			<c:param name="geogKey" value="${place.geogKey}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="EditNamePlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.showNamesOrNamesVariantsPlace.nameOrName"/><a class="helpIcon" title="<fmt:message key="geobase.showNamesOrNameVariantsPlace.help.name"></fmt:message>">?</a></h5>
			<c:if test="${place.placeAllId > 0}">
		 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<a id="EditNamePlace" href="${EditNamesOrNameVariantsPlaceURL}" class="editButton" title="Edit Name or Name Variants"></a><span id="loading"/>
			</security:authorize>
			</c:if>
		</div>
		
		<div class="list">
			<c:forEach items="${placeNames}" var="currentName">
				<div class="row">
					<c:if test="${currentName.prefFlag == 'P'}">
						<div class="item"><fmt:message key="geobase.showNamesOrNamesVariantsPlace.principal"/></div>
						<c:if test="${currentName.placeAllId != place.placeAllId}">
							<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
								<c:param name="placeAllId" value="${currentName.placeAllId}" />
							</c:url>
							<div class="value"><a class="linkPlace" href="${ShowPlaceURL}">${currentName.placeName}</a></div>
						</c:if>
						<c:if test="${currentName.placeAllId == place.placeAllId}">
							<div class="value">${currentName.placeName}</div>
						</c:if>
					</c:if>
					<c:if test="${currentName.prefFlag == 'V'}">
						<div class="item"><fmt:message key="geobase.showNamesOrNamesVariantsPlace.variant"/></div>
						<div class="value">${currentName.placeName}</div>
					</c:if>					
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
	        $j("#EditGeographicCoordinatesPlace").css('visibility', 'visible'); 
			$j("#EditExternalLinksPlace").css('visibility', 'visible');

			$j("#EditNamePlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamePlaceDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j(".linkPlace").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				return false;
			}); 
			
			$j('.helpIcon').tooltip({ 
				track: true, 
				fade: 350 
			});
		});
	</script>
</security:authorize>