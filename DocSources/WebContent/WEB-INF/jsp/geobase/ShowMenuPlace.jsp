<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyPlaceURL" value="/de/peoplebase/ShowVettingChronologyPlace.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
		<c:url var="ShowMenuActionsPlaceURL" value="/de/peoplebase/ShowMenuActionsPlace.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
		<c:url var="ShowShareLinkPlaceURL" value="/src/peoplebase/ShowShareLinkPlace.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
	</security:authorize>

	<div id="CreatedSharePrintDiv">
		<div id="createdby">CREATED BY ${place.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${place.dateEntered}" /></div>
		<a id="vettingChronology" href="${ShowVettingChronologyPlaceURL}"></a>
		<a id="menuActions" href="${ShowMenuActionsPlaceURL}"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<a id="buttonShareLink" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc."></a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#buttonShareLink").click(
				function() {										
					window.open('${ShowShareLinkPlaceURL}','SHARE PERSON','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
					});
	
		$j('#buttonShareLink').tooltip({
			track: true,
			fade: 350 
		});

	});
	</script>
