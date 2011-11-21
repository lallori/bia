<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowSenderDocumentsPlaceURL" value="/de/geobase/ShowSenderDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowRecipientDocumentsPlaceURL" value="/de/geobase/ShowRecipientDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditSendRecipPlaceDiv">
	<div class="title">
		<h5>SENDERS and RECIPIENTS </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<c:if test="${place.senderDocuments != null && place.senderDocuments.size() != 0}">
				<div class="value"><a id="linkSearch" class="sender" href="${ShowSenderDocumentsPlaceURL}">${place.senderDocuments.size()} Senders</a></div>
			</c:if>
			<c:if test="${place.senderDocuments.size() == 0 || place.senderDocuments == null}">
				<div class="value">0 Sender</div>
			</c:if>
		</div>
		<div class="row">
			<c:if test="${place.recipientDocuments != null && place.recipientDocuments.size() != 0}">
				<div class="value"><a id="linkSearch" class="recipient" href="${ShowRecipientDocumentsPlaceURL}">${place.recipientDocuments.size()} Recipients</a></div>
			</c:if>
			<c:if test="${place.recipientDocuments.size() == 0 || place.recipientDocuments == null}">
				<div class="value">0 Recipient</div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			$j(".sender").click(function(){
				var tabName = "Senders ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".recipient").click(function(){
				var tabName = "Recipients ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
</script>