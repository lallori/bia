<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="GetLinkedForumURL" value="/src/docbase/GetLinkedForum.json">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="PrintDocumentURL" value="/src/docbase/PrintDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>
	<c:url var="ShareDocumentURL" value="/src/docbase/ShareDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="ShowConfirmCreateDocumentForumURL" value="/src/docbase/ShowConfirmCreateDocumentForum.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="DeleteDocumentURL" value="/de/docbase/DeleteDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="UndeleteDocumentURL" value="/de/docbase/UndeleteDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="ShowVettingChronologyDocumentURL" value="/de/docbase/ShowVettingChronologyDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>

	<c:url var="AddMarkedListDocumentURL" value="/src/docbase/AddMarkedListDocument.do">
		<c:param name="entryId"	  value="${document.entryId}" />
	</c:url>
	
	<c:url var="RemoveMarkedListDocumentURL" value="/src/docbase/RemoveMarkedListDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>

		
	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
		<c:if test="${document.entryId != 0}">
			<div id="id">Doc ID ${document.entryId == 0 ? '' : document.entryId}</div>
			<input type="hidden" id="currentUrl" value="${ShowDocumentURL}" />
		</c:if>
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="<fmt:message key="menu.record.goback"></fmt:message>" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="<fmt:message key="menu.record.gonext"></fmt:message>" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
					<a id="vettingHistory" class="button_bodyleft_medium" title="<fmt:message key="menu.record.vettinghistory"></fmt:message>" href="${ShowVettingChronologyDocumentURL}">Vetting History</a>
		</security:authorize>
		<span id="commentsOn"></span>
		<a id="comments" class="button_bodyleft_medium" title="<fmt:message key="menu.record.discussions"></fmt:message>" href="#">Discussions</a>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${!document.logicalDelete}">
				<a id="deleteAction" class="button_bodyleft_small" title="<fmt:message key="menu.record.delete"></fmt:message>" href="${DeleteDocumentURL}">Delete</a>
			</c:if>	
			<c:if test="${document.logicalDelete}">
				<a id="undeleteAction" class="button_bodyleft_small"  title="<fmt:message key="menu.record.undelete"></fmt:message>" href="${UndeleteDocumentURL}">Undelete</a>
			</c:if>	
		</security:authorize>
		<a id="buttonPrint" href="${PrintDocumentURL}" title="<fmt:message key="menu.record.print"></fmt:message>"></a>
		<c:if test="${inMarkedList == 'false'}">
			<a id="buttonMarkedList" class="addMarkedList" href="${AddMarkedListDocumentURL}" title="<fmt:message key="menu.record.markedlist"></fmt:message>"></a>
		</c:if>
		<c:if test="${inMarkedList == 'true'}">
			<a id="buttonMarkedList" class="removeMarkedList" href="${RemoveMarkedListDocumentURL}" title="<fmt:message key="menu.record.alreadymarkedlist"></fmt:message>" style="opacity: 0.5;"></a>
		</c:if>
		<a id="buttonShareLink" class="button_bodyleft_medium2" href="${ShareDocumentURL}" title="<fmt:message key="menu.record.sharelink"></fmt:message>">Share/Link</a>
	</div>
	
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
					$timerId = setInterval(animazione, 2000);
				}
				
				return false;
			}
		}});
			
		$j('#comments').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#comments").click(function() {
			if($j(this).attr('href') == '#'){
				Modalbox.show('${ShowConfirmCreateDocumentForumURL}', {title: "DISCUSSIONS", width: 470, height: 100});
				return false;
			}
		});

		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });

		$j("#buttonShareLink").click(function() {										
			window.open($j(this).attr("href"),'SHARE DOCUMENT','width=510,height=550,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
			return false;
		});
		
		$j('#buttonMarkedList').tooltip({track: true, fade: 350, showURL: false });
		
		$j(".addMarkedList").click(function() {	
			if($j(this).attr('href') != '#'){
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
			        		$j.ajax({ type:"GET", url: '${AddMarkedListDocumentURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedList").focus();
								$j("#DialogMarkedList").html(html);
								} 
							});
			       		},
			       		close: function(event, ui){
			       			$j("#body_left").load("${ShowDocumentURL}");
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
			if($j(this).attr('href') != '#'){
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
			        		$j.ajax({ type:"GET", url: '${RemoveMarkedListDocumentURL}', cache:false, success:function(html) { 
								$j("#DialogMarkedListRemove").focus();
								$j("#DialogMarkedListRemove").html(html);
								} 
							});
			       		},
			       		close: function(event, ui){
			       			$j("#body_left").load("${ShowDocumentURL}");
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
		
		$j('#buttonPrint').tooltip({track: true, fade: 350, showURL: false });

		$j("#buttonPrint").click(function() {
			window.open($j(this).attr("href"),'PRINT DOCUMENT','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});

		$j('#deleteAction').tooltip({track: true, fade: 350, showURL: false });

		$j("#deleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DELETE DOCUMENT", width: 400, height: 120});return false;
		});	
		
		$j('#undeleteAction').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#undeleteAction").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "UNDELETE DOCUMENT", width: 400, height: 120});return false;
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
