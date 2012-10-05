<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowTopicsPlaceURL" value="/src/geobase/ShowTopicsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditTopicsPlaceDiv">
	<div class="title">
		<h5>TOPICS LIST<a class="helpIcon" title="Text">?</a></h5>
	</div>
	<div class="list">	
		<div class="row">
			<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
				<div class="value"><a id="linkSearch" class="topics" href="${ShowTopicsPlaceURL}">${docInTopics} Documents on ${topicsPlace} Topics</a></div>
			</c:if>
			<c:if test="${topicsPlace == 1}">
				<div class="value"><a id="linkSearch" class="topics" href="${ShowTopicsPlaceURL}">${docInTopics} Document on ${topicsPlace} Topic</a></div>
			</c:if>
			<c:if test="${docInTopics == 1 && topicsPlace != 1}">
				<div class="value"><a id="linkSearch" class="topics" href="${ShowTopicsPlaceURL}">${docInTopics} Document on ${topicsPlace} Topics</a></div>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
				<div class="value">0 Documents on 0 Topics</div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			
		});
</script>