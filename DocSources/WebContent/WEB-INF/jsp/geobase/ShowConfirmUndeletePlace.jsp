<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UndeletePlaceURL" value="/de/geobase/UndeletePlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>
	<c:url var="ShowPlaceURL" value="/de/geobase/ShowPlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1><fmt:message key="geobase.showConfirmUndeletePlace.areYouSure"/></h1>
		
		<a id="yes" class="button_small" href="${UndeletePlaceURL}"><fmt:message key="geobase.showConfirmUndeletePlace.yes"/></a>
	
		<a id="no" class="button_small" href="#"><fmt:message key="geobase.showConfirmUndeletePlace.no"/></a>
			
	</div>

	<script>
		$j(document).ready(function() {
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"POST", url: '${UndeletePlaceURL}', async:false, success:function(html) {
					$j("#DeleteThisRecordDiv").html(html);
					$j("#body_left").load('${ShowPlaceURL}');
				}});
				return false;
			});
		});
	</script>
