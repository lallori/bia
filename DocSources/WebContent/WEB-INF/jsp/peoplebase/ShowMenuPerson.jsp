<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="GetLinkedForumURL" value="/src/peoplebase/GetLinkedForum.json">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="PrintPersonURL" value="/src/peoplebase/PrintPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	<c:url var="SharePersonURL" value="/src/peoplebase/SharePerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="ShowConfirmCreatePersonForumURL" value="/src/peoplebase/ShowConfirmCreatePersonForum.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="DeletePersonURL" value="/de/peoplebase/DeletePerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="UndeletePersonURL" value="/de/peoplebase/UndeletePerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="ShowVettingChronologyPersonURL" value="/de/peoplebase/ShowVettingChronologyPerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="AddMarkedListPersonURL" value="/src/peoplebase/AddMarkedListPerson.do">
		<c:param name="personId"	  value="${person.personId}" />
	</c:url>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="Go back to your last Record" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="Go back to the next Record" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="vettingHistory" href="${ShowVettingChronologyPersonURL}">Vetting History</a>
		</security:authorize>
		<a id="comments" href="#">Comments</a>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:if test="${!person.logicalDelete}">
			<a id="deleteAction" href="${DeletePersonURL}">Delete</a>
		</c:if>	
		<c:if test="${person.logicalDelete}">
			<a id="undeleteAction" href="${UndeletePersonURL}">Undelete</a>
		</c:if>	
		</security:authorize>
		<a id="buttonPrint" title="Print this record" href="${PrintPersonURL}"></a>
		<c:if test="${inMarkedList == 'false'}">
			<a id="buttonMarkedList" href="${AddMarkedListPersonURL}" title="Add this record to Marked List"></a>
		</c:if>
		<c:if test="${inMarkedList == 'true'}">
			<a id="buttonMarkedList" href="#" title="Already added to Marked List" style="opacity:0.5"></a>
		</c:if>
		<a id="buttonShareLink" href="${SharePersonURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#comments").click(function() {
			$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
				if (json.isPresent == 'true') {
					$j("#comments").attr('href', json.forumUrlCompleteDOM);
					$j("#comments").open({scrollbars: "yes"});
				} else {
					Modalbox.show('${ShowConfirmCreatePersonForumURL}', {title: "COMMENTS", width: 450, height: 150});
				}
			}});
			return false;
		});
		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PERSON','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});
	
		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PERSON','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});

		$j("#buttonMarkedList").click(function() {	
			if($j(this).attr("href") != '#'){
				if ($j("#DialogMarkedList").length > 0) {
					$j("#DialogMarkedList").dialog("close");
					return false;
				} else {
					var $dialogMarkedList = $j('<div id="DialogMarkedList"></div>').dialog({
						autoOpen: false,
						width: 250,
						height: 180,
						modal: true,
						zIndex: 3999,
						overlay: {
							backgroundColor: '#000',
							opacity: 0.5
						},
						position: ['center',250],
						open: function(event, ui) { 
			        		$j.ajax({ type:"GET", url: '${AddMarkedListPersonURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedList").focus();
								$j("#DialogMarkedList").html(html);
								} 
							});
			       		},
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedList.dialog("open");
				}
			}
			return false;
		});
		
		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE PERSON MENU", width: 400, height: 160});return false;
		});	
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE PERSON MENU", width: 400, height: 160});return false;
		});	

		$j('#lastRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
		$j('#nextRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
		
		$j("#vettingHistory").click(function() {	
			Modalbox.show($j(this).attr("href"), {title: "VETTING HISTORY", width: 760, height: 415});
			return false;
		});
	});
	</script>
