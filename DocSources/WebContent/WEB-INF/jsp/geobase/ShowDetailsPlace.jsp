<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/peoplebase/EditDetailsPlaceU.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div id="CreatedSharePrintDiv">
		<div id="createdby">CREATED BY ${place.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${place.dateEntered}" /></div>
		<a id="vettingChronology" href="/DocSources/de/VettingChronology.html"></a>
		<a id="menuActions" href="/DocSources/de/geobase/ActionsMenu.html"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<div id="buttonShareLink">
			<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a>
			<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
		</div>
	</div>

	<div class="background" id="EditDetailsPlaceDiv">
		<div class="title">
			<h5>PLACE DETAILS</h5>
			<a title="Edit TGN Place Details" href="${EditDetailsPlaceURL}" class="editButton" id="EditDetailsPlace"></a>
		</div>
	
		<div class="list">
			<div class="row">
				<div class="item">Place ID</div> 
				<div class="value">${place.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item">Place name</div>
				<div class="value">${place.placeName}</div>
			</div>
			<div class="row">
				<div class="item">Place type</div>
				<div class="value">${place.plType}</div>
			</div>
			<div class="row">
				<div class="item">Place Parent</div>
				<div class="value">${place.plParent}</div>
			</div>
			<div class="row">
				<div class="item">Place Notes</div>
				<div class="value"></div>
			</div>
		</div>

	<br><br>
	
		<h5>HIERARCHY</h5>
		<br><br>
		<div class="list">
			<div class="row">
				<div class="item">Parent</div> 
				<div class="value">${place.parentPlace.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item">GParent</div>
				<div class="value">2949</div>
			</div>
			<div class="row">
				<div class="item">GGParent</div>
				<div class="value">${place. }</div>
			</div>
			<div class="row">
				<div class="item">GP2</div>
				<div class="value">Firenze/Toscana/Italia/Europa</div>
			</div>
			<div class="row">
				<div class="item">Parent_TGN_id</div>
				<div class="value">Firenze/Toscana/Italia/Europa</div>
			</div>
			<div class="row">
				<div class="item">Parent_GEOKEY</div>
				<div class="value">Firenze/Toscana/Italia/Europa</div>
			</div>
		</div>
	</div>