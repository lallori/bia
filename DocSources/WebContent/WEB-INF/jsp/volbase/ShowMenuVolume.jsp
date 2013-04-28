<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="GetLinkedForumURL" value="/src/volbase/GetLinkedForum.json">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="PrintVolumeURL" value="/src/volbase/PrintVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShareVolumeURL" value="/src/volbase/ShareVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShowConfirmCreateVolumeForumURL" value="/src/volbase/ShowConfirmCreateVolumeForum.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="DeleteVolumeURL" value="/de/volbase/DeleteVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="UndeleteVolumeURL" value="/de/volbase/UndeleteVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShowVettingChronologyVolumeURL" value="/de/volbase/ShowVettingChronologyVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	
	<c:url var="AddMarkedListVolumeURL" value="/src/volbase/AddMarkedListVolume.do">
		<c:param name="summaryId"	  value="${volume.summaryId}" />
	</c:url>
	<c:url var="RemoveMarkedListVolumeURL" value="/src/volbase/RemoveMarkedListVolume.do">
		<c:param name="summaryId"	  value="${volume.summaryId}" />
	</c:url>

<%-- Create new Volume Record --%>
<c:if test="${volume.summaryId == 0}">
	<div id="topBodyLeftMenuDiv">
			<div id="createdby"><fmt:message key="	.showMenuVolume.created"/> ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></div>
	</div>	
</c:if>	

<%-- Existing Volume Record --%>
<c:if test="${volume.summaryId != 0}">
	 <div id="topBodyLeftMenuDiv">
		<div id="createdby"><fmt:message key="volbase.showMenuVolume.created"/> ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></div>
		<div id="id"><fmt:message key="volbase.showMenuVolume.volId"/> ${volume.summaryId}</div>
		<input type="hidden" id="currentUrl" value="${ShowVolumeURL}" />
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="<fmt:message key="volbase.showMenuVolume.help.goback"/>" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="<fmt:message key="volbase.showMenuVolume.help.gonext"/>" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="vettingHistory" class="button_bodyleft_medium" title="<fmt:message key="volbase.showMenuVolume.help.vettinghistory"/>" href="${ShowVettingChronologyVolumeURL}"><fmt:message key="volbase.showMenuVolume.vettingHistory"/></a>
		</security:authorize>
		<span id="commentsOn"></span>
		<a id="comments" class="button_bodyleft_medium" title="<fmt:message key="volbase.showMenuVolume.help.discussions"/>" href="#"><fmt:message key="volbase.showMenuVolume.discussions"/></a>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${!volume.logicalDelete}">
				<a id="deleteAction" class="button_bodyleft_small" title="<fmt:message key="volbase.showMenuVolume.help.delete"/>" href="${DeleteVolumeURL}"><fmt:message key="volbase.showMenuVolume.delete"/></a>
			</c:if>	
			<c:if test="${volume.logicalDelete}">
				<a id="undeleteAction" class="button_bodyleft_small" title="<fmt:message key="volbase.showMenuVolume.help.undelete"/>" href="${UndeleteVolumeURL}"><fmt:message key="volbase.showMenuVolume.undelete"/></a>
			</c:if>	
		</security:authorize>
		<a id="buttonPrint" href="${PrintVolumeURL}" title="<fmt:message key="volbase.showMenuVolume.help.print"/>"></a>
		<c:if test="${inMarkedList == 'false'}">
			<a id="buttonMarkedList" class="addMarkedList" href="${AddMarkedListVolumeURL}" title="<fmt:message key="volbase.showMenuVolume.help.markedlist"/>"></a>
		</c:if>
		<c:if test="${inMarkedList == 'true'}">
			<a id="buttonMarkedList" class="removeMarkedList" href="${RemoveMarkedListVolumeURL}" title="<fmt:message key="volbase.showMenuVolume.help.alreadymarkedlist"/>" style="opacity:0.5;"></a>
		</c:if>
		<a id="buttonShareLink" class="button_bodyleft_medium2" href="${ShareVolumeURL}" title="<fmt:message key="volbase.showMenuVolume.help.sharelink"/>"><fmt:message key="volbase.showMenuVolume.share"/></a>
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
// 					Modalbox.show('${ShowConfirmCreateVolumeForumURL}', {title: "DISCUSSIONS", width: 470, height: 100});
// 				}
// 			}});
			if($j(this).attr('href') == '#'){
				Modalbox.show('${ShowConfirmCreateVolumeForumURL}', {title: "DISCUSSIONS", width: 470, height: 100});
				return false;
			}
		});
		
		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });

		$j("#buttonShareLink").click(function() {										
			window.open($j(this).attr("href"),'SHARE VOLUME','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
			return false;
		});
		
		$j("#buttonPrint").tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonPrint").click(function() {
			window.open($j(this).attr("href"),'PRINT VOLUME','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});
		
		
		$j("#buttonMarkedList").tooltip({track: true, fade: 350, showURL: false });
		
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
			       		$j.ajax({ type:"GET", url: '${AddMarkedListVolumeURL}', cache:false, success:function(html) { 
							$j("#DialogMarkedList").focus();
							$j("#DialogMarkedList").html(html);
							} 
						});
			      		},
			      	close: function(event, ui){
				    	$j("#body_left").load("${ShowVolumeURL}");
				    	return false;
				    },
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				});
				}
				$dialogMarkedList.dialog("open");
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
			       		$j.ajax({ type:"GET", url: '${RemoveMarkedListVolumeURL}', cache:false, success:function(html) { 
							$j("#DialogMarkedListRemove").focus();
							$j("#DialogMarkedListRemove").html(html);
							} 
						});
			      		},
			      	close: function(event, ui){
			      		$j("#body_left").load("${ShowVolumeURL}");
			      		return false;
			      	},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				});
				}
				$dialogMarkedListRemove.dialog("open");
			}
			return false;
				
		});
		
		$j('#deleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE VOLUME", width: 400, height: 120});return false;
		});	
		
		$j('#undeleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE VOLUME", width: 450, height: 120});return false;
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
		
		$j("#vettingHistory").click(function() {	
			Modalbox.show($j(this).attr("href"), {title: "VETTING HISTORY", width: 760, height: 415});
			return false;
		});
	});
	</script>