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
	<c:url var="ShowVettingChronologyPlaceURL" value="/de/peoplebase/ShowVettingChronologyPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	
	<c:url var="AddMarkedListPlaceURL" value="/src/geobase/AddMarkedListPlace.do">
		<c:param name="placeAllId"	  value="${place.placeAllId}" />
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
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="Go back to your last Record" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="Go back to the next Record" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<c:if test="${place.placeAllId != 0}">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
<%-- 			<a id="vettingHistory" href="${ShowVettingChronologyPlaceURL}">Vetting History</a> --%>
			</security:authorize>
			<span id="commentsOn"></span>
			<a id="comments" href="#">Discussions</a>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${!place.logicalDelete}">
				<a id="deleteAction" href="${DeletePlaceURL}">Delete</a>
			</c:if>	
			<c:if test="${place.logicalDelete}">
				<a id="undeleteAction" href="${UndeletePlaceURL}">Undelete</a>
			</c:if>	
			</security:authorize>
			<a id="buttonPrint" title="Print this record" href="${PrintPlaceURL}"></a>
			<c:if test="${inMarkedList == 'false'}">
				<a id="buttonMarkedList" href="${AddMarkedListPlaceURL}" title="Add this record to Marked List"></a>
			</c:if>
			<c:if test="${inMarkedList == 'true'}">
				<a id="buttonMarkedList" href="#" title="Already added to Marked List" style="opacity:0.5;"></a>
			</c:if>
			<a id="buttonShareLink" href="${SharePlaceURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
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
				$j("#comments").css('color', 'red');
				$timerId = setInterval(animazione, 6000);
				
				return false;
			}
		}});

		
		$j("#comments").click(function() {
			$j.ajax({ url: '${GetLinkedForumURL}', cache: false, success:function(json) {
				if (json.isPresent == 'true') {
					$j("#comments").attr('href', json.forumUrlCompleteDOM);
					$j("#comments").open({scrollbars: "yes"});
				} else {
					Modalbox.show('${ShowConfirmCreatePlaceForumURL}', {title: "COMMENTS", width: 470, height: 100});
				}
			}});

			return false;
		});

		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PLACE','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});
	
		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PLACE','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
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
						dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
						dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
					});
					$dialogMarkedList.dialog("open");
					return false;
				}
			}
			return false;
		});
		
		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE PLACE", width: 400, height: 120});return false;
		});	
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE PLACE", width: 400, height: 120});return false;
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
