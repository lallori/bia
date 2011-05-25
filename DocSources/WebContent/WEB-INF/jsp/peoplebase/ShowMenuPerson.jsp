<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyPersonURL" value="/de/peoplebase/ShowVettingChronologyPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="ShowMenuActionsPersonURL" value="/de/peoplebase/ShowMenuActionsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="ShowShareLinkPersonURL" value="/src/peoplebase/ShowShareLinkPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
		<a id="vettingChronology" href="${ShowVettingChronologyPersonURL}"></a>
		<a id="menuActions" href="${ShowMenuActionsPersonURL}"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<a id="buttonShareLink" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc."></a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#buttonShareLink").click(
				function() {										
					window.open('${ShowShareLinkPersonURL}','SHARE PERSON','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
					});
	
		$j('#buttonShareLink').tooltip({
			track: true,
			fade: 350 
		});

	});
	</script>
		