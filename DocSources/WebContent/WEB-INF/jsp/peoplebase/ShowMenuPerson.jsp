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
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	
	<c:url var="AddMarkedListPersonURL" value="/src/peoplebase/AddMarkedListPerson.do">
		<c:param name="personId"	  value="${person.personId}" />
	</c:url>
	<c:url var="RemoveMarkedListPersonURL" value="/src/peoplebase/RemoveMarkedListPerson.do">
		<c:param name="personId"	  value="${person.personId}" />
	</c:url>
	
<%-- Create new Volume Record --%>
<c:if test="${person.personId == 0}">
	<div id="topBodyLeftMenuDiv">
		<div id="createdby"><fmt:message key=“people.showMenuPerson.createdBy”/> ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
	</div>
</c:if>

<%-- Existing Volume Record --%>
<c:if test="${person.personId != 0}">
	<div id="topBodyLeftMenuDiv">
		<div id="createdby"><fmt:message key=“people.showMenuPerson.createdBy”/> ${person.createdBy.firstName} ${person.createdBy.lastName} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
		<div id="id"><fmt:message key=“people.showMenuPerson.personId”/> ${person.personId}</div>
		<input type="hidden" id="currentUrl" value="${ShowPersonURL}" />
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="<fmt:message key="peoplebase.showMenuPerson.help.goback"></fmt:message>" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="<fmt:message key="peoplebase.showMenuPerson.help.gonext"></fmt:message>" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
			<a id="vettingHistory" class="button_bodyleft_medium" title="<fmt:message key="peoplebase.showMenuPerson.help.vettinghistory"></fmt:message>" href="${ShowVettingChronologyPersonURL}">Vetting History</a>
		</security:authorize>
		<span id="commentsOn"></span>
		<a id="comments" class="button_bodyleft_medium" title="<fmt:message key="peoplebase.showMenuPerson.help.discussions"></fmt:message>" href="#">Forum</a>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:if test="${!person.logicalDelete}">
			<a id="deleteAction" class="button_bodyleft_small" title="<fmt:message key="peoplebase.showMenuPerson.help.delete"></fmt:message>" href="${DeletePersonURL}">Delete</a>
		</c:if>	
		<c:if test="${person.logicalDelete}">
			<a id="undeleteAction" class="button_bodyleft_small" title="<fmt:message key="peoplebase.showMenuPerson.help.undelete"></fmt:message>" href="${UndeletePersonURL}">Undelete</a>
		</c:if>	
		</security:authorize>
		<a id="buttonPrint" title="<fmt:message key="peoplebase.showMenuPerson.help.print"></fmt:message>" href="${PrintPersonURL}"></a>
		<c:if test="${inMarkedList == 'false'}">
			<a id="buttonMarkedList" class="addMarkedList" href="${AddMarkedListPersonURL}" title="<fmt:message key="peoplebase.showMenuPerson.help.markedlist"></fmt:message>"></a>
		</c:if>
		<c:if test="${inMarkedList == 'true'}">
			<a id="buttonMarkedList" class="removeMarkedList" href="${RemoveMarkedListPersonURL}" title="<fmt:message key="peoplebase.showMenuPerson.help.alreadymarkedlist"></fmt:message>" style="opacity:0.5"></a>
		</c:if>
		<a id="buttonShareLink" class="button_bodyleft_medium2" href="${SharePersonURL}" title="<fmt:message key="peoplebase.showMenuPerson.help.sharelink"></fmt:message>">Share/Link</a>
	</div>
</c:if>
	
	<script type="text/javascript">
	$j(document).ready(function() {
// 		$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
// 			if (json.isPresent == 'true' && json.logicalDelete == 'false') {
// 				$j("#comments").attr('href', json.forumUrlCompleteDOM);
// 				$j("#comments").attr('target', '_blank');
// 				return false;
// 			}
// 		}});

		$j("#commentsOn").stop();
		clearInterval($timerId);

		var animazione = function animazioneOn(){
			$j("#commentsOn").animate({ opacity: 0.6 }, 3000, animazioneOff());
			return false;
		}
		
		function animazioneOff(){
			$j("#commentsOn").animate({ opacity: 0 }, 3000);
			return false;
		}
		
		$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
			if (json.isPresent == 'true') {
				$j("#comments").attr('href', json.forumUrlCompleteDOM);
				$j("#comments").attr('target', '_blank');
				if(json.discussions > 0){
					$j("#comments").css('color', 'red');
					$timerId = setInterval(animazione, 2000);
				}
				
				return false;
			}
		}});
		
		
		$j('#comments').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#comments").click(function(e) {
// 			e.preventDefault();
// 			$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
// 				if((json.isPresent == 'true' && json.logicalDelete == 'true') || json.isPresent == 'false'){
// 					Modalbox.show('${ShowConfirmCreatePersonForumURL}', {title: "COMMENTS", width: 470, height: 100});
// 					return false;
// 				}else if(json.isPresent == 'true' && json.logicalDelete == 'false'){
// 					$j("#comments").attr('href', json.forumUrlCompleteDOM);
// 					$j("#comments").attr('target', '_blank');
// 					window.open($j("#comments").attr('href'), '_blank');
// 					return false;
// 				}
// 			}});
			if($j(this).attr('href') == '#'){
				Modalbox.show('${ShowConfirmCreatePersonForumURL}', {title: "COMMENTS", width: 470, height: 100});
				return false;
			}
		});
		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PERSON','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});

		
		$j('#buttonPrint').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PERSON','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});

		$j('#buttonMarkedList').tooltip({track: true, fade: 350, showURL: false });
		
		$j(".addMarkedList").click(function() {	
			if($j(this).attr("href") != '#'){
				if ($j("#DialogMarkedList").length > 0) {
					$j("#DialogMarkedList").dialog("close");
					return false;
				} else {
					var $dialogMarkedList = $j('<div id="DialogMarkedList"></div>').dialog({
						autoOpen: false,
						width: 250,
						height: 130,
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
			       		close: function(event, ui) {
			       			$j("#body_left").load("${ShowPersonURL}");
			       			return false;
			       		},
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedList.dialog("open");
				}
			}
			return false;
		});
		
		$j(".removeMarkedList").click(function() {	
			if($j(this).attr("href") != '#'){
				if ($j("#DialogMarkedListRemove").length > 0) {
					$j("#DialogMarkedListRemove").dialog("close");
					return false;
				} else {
					var $dialogMarkedListRemove = $j('<div id="DialogMarkedListRemove"></div>').dialog({
						autoOpen: false,
						width: 250,
						height: 130,
						modal: true,
						zIndex: 3999,
						overlay: {
							backgroundColor: '#000',
							opacity: 0.5
						},
						position: ['center',250],
						open: function(event, ui) { 
			        		$j.ajax({ type:"GET", url: '${RemoveMarkedListPersonURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedListRemove").focus();
								$j("#DialogMarkedListRemove").html(html);
								} 
							});
			       		},
			       		close: function(event, ui) {
			       			$j("#body_left").load("${ShowPersonURL}");
			       			return false;
			       		},
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedListRemove.dialog("open");
				}
			}
			return false;
		});
		
		$j('#deleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE PERSON MENU", width: 400, height: 120});return false;
		});	
		
		$j('#undeleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE PERSON MENU", width: 400, height: 120});return false;
		});	
		
		$j('#lastRecord').tooltip({track: true, fade: 350, showURL: false });

		$j('#lastRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
		
		$j('nextRecord').tooltip({track: true, fade: 350, showURL: false });

		$j('#nextRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
		
		$j('#vettingHistory').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#vettingHistory").click(function() {	
			Modalbox.show($j(this).attr("href"), {title: "VETTING HISTORY", width: 760, height: 415});
			return false;
		});
	});
	</script>
