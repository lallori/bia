<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<h4>Volume DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a href="#" id="editDetailsVolume">edit</a></security:authorize></h4>

		<h5>CREATED BY KH 11/28/1996</h5><br /><br />
		<h2>Carteggio dei segretari / Pier Francesco del Riccio</h2>
		<ul>
			<li><b>Volume/Filsa (MDP):</b> 1170a</li>
			<li><b>Start Date:</b> 1534 Setember 6</li>

			<li><b>End Date:</b> 1573 April 2</li>
			<li><b>Date Notes.</b> None</li>
		</ul>
		<h3>Description <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a href="#" id="editDescriptionVolume">edit</a></security:authorize></h3>
		<p>Organizational Criteria: A very loose series (including material relocated from the Miscellanea Medicea) divided into 5 inserts of varying sizes (I, II, IIa, III, IV), wich are independently numered and sometimes sub-divided. <br />

		Insert I [1534 to 1555: ff. 1-88]; Insert II [1547 to 1553; ff. 1-170]; Insert IIa [1537 to 1555; ff. 422-44]; Insert III [1545 to 1546; ff. 1-389]; Insert IV [irregulary dated: 1543 to 1667; ff. 501-710]; ff. 573/575 lost (attachment f.574 present); no ff. 645-898 (lost?).<br />
		Condition: Good<br />
		Bound: No<br />
		Folios Numbered: Yes <br />
		Alphabetical Index: No <br />
		Languages: Italian, Spanish, Latin <br />

		Some Documents in Cipher: No <br />
		Chipher Notes: None </p>
		
		<h3>Correspondents <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a href="#" id="editCorrespondentsVolume">edit</a></security:authorize></h3>
		<p>From Tuscany and abroad (Secretaries, Agents, Administrators, Artists, including: Lorenzo Pagni, Cristian Pagni, Giovanni Francesco Lottini, Niccolò Campana, Vincenzo Ferrini, Agnolo Bronzino, Bernardo Saliti, Giorgio Dati, Agnolo Divozi da Bibbiena)<br />
		To: Florence (Pierfrancesco Riccio)</p>
		
		<h3>Context <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a href="#" id="editContextVolume">edit</a></security:authorize></h3>

		<p>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>

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

		<link rel="stylesheet" href='<c:url value="/styles/jqModal.css" />' type="text/css" media="screen, projection">

		<div class="jqmWindow" id="edit1">
			Please wait... <img src="<c:url value="/images/busy_icon.gif"/>" alt="loading" />
		</div>
		<div class="jqmWindow" id="edit2">
			Please wait... <img src="<c:url value="/images/busy_icon.gif"/>" alt="loading" />
		</div>
		<div class="jqmWindow" id="edit3">
			Please wait... <img src="<c:url value="/images/busy_icon.gif"/>" alt="loading" />
		</div>
		<div class="jqmWindow" id="edit4">
			Please wait... <img src="<c:url value="/images/busy_icon.gif"/>" alt="loading" />
		</div>

		<script type="text/javascript">
			$().ready(function() {
				$('#edit1').jqm({ajax: '${EditDetailsVolume}',        modal: 'true', trigger: '#editDetailsVolume'});
				$('#edit2').jqm({ajax: '${EditDescriptionVolume}',    modal: 'true', trigger: '#editDescriptionVolume'});
				$('#edit3').jqm({ajax: '${EditCorrespondentsVolume}', modal: 'true', trigger: '#editCorrespondentsVolume'});
				$('#edit4').jqm({ajax: '${EditContextVolume}',        modal: 'true', trigger: '#editContextVolume'});
			});
		</script>
	</security:authorize>