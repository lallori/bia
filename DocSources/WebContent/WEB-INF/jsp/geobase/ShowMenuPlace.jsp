<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="GetLinkedForumURL" value="/src/geobase/GetLinkedForum.json">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="PrintPlaceURL" value="/src/geobase/PrintPlace.do">
		<c:param name="placeAllId" value="${place.placeAllId}" />
	</c:url>
	<c:url var="SharePlaceURL" value="/src/geobase/SharePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowConfirmCreatePlaceForumURL" value="/src/geobase/ShowConfirmCreatePlaceForum.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="DeletePlaceURL" value="/de/geobase/DeletePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="UndeletePlaceURL" value="/de/geobase/UndeletePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowMenuCommentsPlaceURL" value="/src/geobase/ShowMenuCommentsPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowVettingChronologyPlaceURL" value="/de/geobase/ShowVettingChronologyPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	
	<c:url var="AddMarkedListPlaceURL" value="/src/geobase/AddMarkedListPlace.do">
		<c:param name="placeAllId"	  value="${place.placeAllId}" />
	</c:url>
	<c:url var="RemoveMarkedListPlaceURL" value="/src/geobase/RemoveMarkedListPlace.do">
		<c:param name="placeAllId" value="${place.placeAllId}" />
	</c:url>
	
<c:if test="${place.placeAllId == 0}">
	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${place.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${place.dateEntered}" /></div>
	</div>	
</c:if>	

<c:if test="${place.placeAllId != 0}">
	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${place.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${place.dateEntered}" /></div>
		<div id="id">Place ID ${place.placeAllId}</div>
		<input type="hidden" id="currentUrl" value="${ShowPlaceURL}" />
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="<fmt:message key="menu.record.goback"></fmt:message>" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="<fmt:message key="menu.record.gonext"></fmt:message>" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<c:if test="${place.placeAllId != 0}">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="vettingHistory" class="button_bodyleft_medium" title="<fmt:message key="menu.record.vettinghistory"></fmt:message>" href="${ShowVettingChronologyPlaceURL}">Vetting History</a>
			</security:authorize>
			<span id="commentsOn"></span>
			<a id="comments" class="button_bodyleft_medium" title="<fmt:message key="menu.record.discussions"></fmt:message>" href="#">Discussions</a>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${!place.logicalDelete}">
				<a id="deleteAction" class="button_bodyleft_small" title="<fmt:message key="menu.record.delete"></fmt:message>" href="${DeletePlaceURL}">Delete</a>
			</c:if>	
			<c:if test="${place.logicalDelete}">
				<a id="undeleteAction" class="button_bodyleft_small" title="<fmt:message key="menu.record.undelete"></fmt:message>" href="${UndeletePlaceURL}">Undelete</a>
			</c:if>	
			</security:authorize>
			<a id="buttonPrint" title="<fmt:message key="menu.record.print"></fmt:message>"  href="${PrintPlaceURL}"></a>
			<c:if test="${inMarkedList == 'false'}">
				<a id="buttonMarkedList" class="addMarkedList" href="${AddMarkedListPlaceURL}" title="<fmt:message key="menu.record.markedlist"></fmt:message>"></a>
			</c:if>
			<c:if test="${inMarkedList == 'true'}">
				<a id="buttonMarkedList" class="removeMarkedList" href="${RemoveMarkedListPlaceURL}" title="<fmt:message key="menu.record.alreadymarkedlist"></fmt:message>" style="opacity:0.5;"></a>
			</c:if>
			<a id="buttonShareLink" class="button_bodyleft_medium2" href="${SharePlaceURL}" title="<fmt:message key="menu.record.sharelink"></fmt:message>">Share/Link</a>
		</c:if>
	</div>
</c:if>	
	
	<script type="text/javascript">
	$j(document).ready(function() {
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
					$timerId = setInterval(animazione, 6000);
				}
				
				return false;
			}
		}});

		$j('#comments').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#comments").click(function() {
// 			$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
// 				if (json.isPresent == 'true') {
// 					$j("#comments").attr('href', json.forumUrlCompleteDOM);
// 					$j("#comments").open({scrollbars: "yes"});
// 				} else {
// 					Modalbox.show('${ShowConfirmCreatePlaceForumURL}', {title: "COMMENTS", width: 470, height: 100});
// 				}
// 			}});
			if($j(this).attr('href') == '#'){
				Modalbox.show('${ShowConfirmCreatePlaceForumURL}', {title: "COMMENTS", width: 470, height: 100});
				return false;
			}
		});

		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PLACE','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});
	
		
		$j('#buttonPrint').tooltip({track: true, fade: 350, showURL: false });

		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PLACE','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
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
			        		$j.ajax({ type:"GET", url: '${AddMarkedListPlaceURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedList").focus();
								$j("#DialogMarkedList").html(html);
								} 
							});
			       		},
			       		close: function(event, ui){
			       			$j("#body_left").load("${ShowPlaceURL}");
			       			return false;
			       		},
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedList.dialog("open");
					return false;
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
			        		$j.ajax({ type:"GET", url: '${RemoveMarkedListPlaceURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedListRemove").focus();
								$j("#DialogMarkedListRemove").html(html);
								} 
							});
			       		},
			       		close: function(event, ui){
			       			$j("#body_left").load("${ShowPlaceURL}");
			       			return false;
			       		},
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedListRemove.dialog("open");
					return false;
				}
			}
			return false;
		});
		
		$j('#deleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE PLACE", width: 400, height: 120});return false;
		});	
		
		$j('#undeleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE PLACE", width: 400, height: 120});return false;
		});	

		$j('#lastRecord').tooltip({track: true, fade: 350, showURL: false });

		$j('#lastRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});

		$j('#nextRecord').tooltip({track: true, fade: 350, showURL: false });

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
