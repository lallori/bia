<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditContextVolume" value="/de/volbase/EditContextVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
	  		<c:param name="volNum"    value="${volume.volNum}" />
	  		<c:param name="volLeText" value="${volume.volLeText}" />
		</c:url>
		<c:url var="EditCorrespondentsVolume" value="/de/volbase/EditCorrespondentsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
	  		<c:param name="volNum"    value="${volume.volNum}" />
	  		<c:param name="volLeText" value="${volume.volLeText}" />
		</c:url>
		<c:url var="EditDescriptionVolume" value="/de/volbase/EditDescriptionVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
	  		<c:param name="volNum"    value="${volume.volNum}" />
	  		<c:param name="volLeText" value="${volume.volLeText}" />
		</c:url>
		<c:url var="EditDetailsVolume" value="/de/volbase/EditDetailsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
	  		<c:param name="volNum"    value="${volume.volNum}" />
	  		<c:param name="volLeText" value="${volume.volLeText}" />
		</c:url>
	</security:authorize>
	
	<div id="EditDetailsVolumeForm">
		<h5>Volume DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsVolume" href="${EditDetailsVolume}">edit</a></security:authorize></h5>
		<h6>CREATED BY KH 11/28/1996</h6><br /><br />
		<h3>Carteggio dei segretari / Pier Francesco del Riccio</h3>
		<ul>
			<li><b>Volume/Filsa (MDP):</b> 1170a</li>
			<li><b>Start Date:</b> 1534 Setember 6</li>

			<li><b>End Date:</b> 1573 April 2</li>
			<li><b>Date Notes.</b> None</li>
		</ul>
	</div>

	<br />

	<div id="EditDetailsVolumeForm">
		<h5>Description <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDescriptionVolume" href="${EditDescriptionVolume}">edit</a></security:authorize></h5>
		<ul>
			<li><b>Organizational Criteria: </b>A very loose series (including material relocated from the Miscellanea Medicea) divided into 5 inserts of varying sizes (I, II, IIa, III, IV), wich are independently numered and sometimes sub-divided. <br />
				Insert I [1534 to 1555: ff. 1-88]; Insert II [1547 to 1553; ff. 1-170]; Insert IIa [1537 to 1555; ff. 422-44]; Insert III [1545 to 1546; ff. 1-389]; Insert IV [irregulary dated: 1543 to 1667; ff. 501-710]; ff. 573/575 lost (attachment f.574 present); no ff. 645-898 (lost?).</li>
			<li><b>Condition:</b> Good</li>
			<li><b>Bound: </b>No</li>
			<li><b>Folios Numbered: </b>Yes</li>
			<li><b>Alphabetical Index: </b>No</li>
			<li><b>Languages: </b>Italian, Spanish, Latin</li>
			<li><b>Some Documents in Cipher: </b>No</li>
			<li><b>Chipher Notes: </b>None</li>
		</ul>
	</div>
		
	<br />

	<div id="EditCorrespondentsVolumeForm">
		<h5>Correspondents <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditCorrespondentsVolume" href="${EditCorrespondentsVolume}">edit</a></security:authorize></h5>
		<ul>
			<li><b>From:</b> Tuscany and abroad (Secretaries, Agents, Administrators, Artists, including: Lorenzo Pagni, Cristian Pagni, Giovanni Francesco Lottini, Niccolò Campana, Vincenzo Ferrini, Agnolo Bronzino, Bernardo Saliti, Giorgio Dati, Agnolo Divozi da Bibbiena)s</li>
			<li><b>To:</b> Florence (Pierfrancesco Riccio)</li>
		</ul>
	</div>
		
	<br />
	
	<div id="EditContextVolumeForm">
		<h5>Context <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditContextVolume" href="${EditContextVolume}">edit</a></security:authorize></h5>
		<ul>
			<li>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$().ready(function() {
				$(document).ready(function() {
					$("#EditDetailsVolume").click(function(){$("#EditDetailsVolumeForm").load($(this).attr("href"));return false;});
					$("#EditDescriptionVolume").click(function(){$("#EditDescriptionVolumeForm").load($(this).attr("href"));return false;});
					$("#EditCorrespondentsVolume").click(function(){$("#EditCorrespondentsVolumeForm").load($(this).attr("href"));return false;});
					$("#EditContextVolume").click(function(){$("#EditContextVolumeForm").load($(this).attr("href"));return false;});
				});
			});
		</script>
	</security:authorize>