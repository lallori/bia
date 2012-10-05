<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowBirthPeoplePlaceURL" value="/src/geobase/ShowBirthPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowDeathPeoplePlaceURL" value="/src/geobase/ShowDeathPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveStartPeoplePlaceURL" value="/src/geobase/ShowActiveStartPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveEndPeoplePlaceURL" value="/src/geobase/ShowActiveEndPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5>BIRTH and DEATH PLACE<a class="helpIcon" title="Text">?</a></h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value"><c:if test="${birthPlace != 0}"><a id="linkSearch" class="birth" href="${ShowBirthPeoplePlaceURL}">${birthPlace} Births</a></c:if><c:if test="${birthPlace == 0}">0 Births</c:if>      <c:if test="${activeStartPlace != 0}"><a id="linkSearch" class="activeStart" href="${ShowActiveStartPeoplePlaceURL}">${activeStartPlace} Active Starts</a></c:if><c:if test="${activeStartPlace == 0}">0 Active Starts</c:if></div>
		</div>
		<div class="row">
			<div class="value"><c:if test="${deathPlace != 0}"><a id="linkSearch" class="death" href="${ShowDeathPeoplePlaceURL}">${deathPlace} Deaths</a></c:if><c:if test="${deathPlace == 0}">0 Deaths</c:if>      <c:if test="${activeEndPlace != 0}"><a id="linkSearch" class="activeEnd" href="${ShowActiveEndPeoplePlaceURL}">${activeEndPlace} Active Ends</a></c:if><c:if test="${activeEndPlace == 0}">0 Active Ends</c:if></div>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			
		});
</script>