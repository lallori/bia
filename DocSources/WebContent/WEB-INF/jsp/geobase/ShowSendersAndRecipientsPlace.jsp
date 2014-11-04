<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowSenderDocumentsPlaceURL" value="/src/geobase/ShowSenderDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowRecipientDocumentsPlaceURL" value="/src/geobase/ShowRecipientDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditSendRecipPlaceDiv">
	<div class="title">
		<h5><fmt:message key="geobase.showSendersAndRecipientsPlace.sendersAndRecipients"/><a class="helpIcon" title="Text">?</a></h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1}">
				<div class="value"><a id="linkSearch" class="sender" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} <fmt:message key="geobase.showSendersAndRecipientsPlace.senders"/></a></div>
			</c:if>
			<c:if test="${senderPlace == 1 }">
				<div class="value"><a id="linkSearch" class="sender" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} <fmt:message key="geobase.showSendersAndRecipientsPlace.sender"/></a></div>
			</c:if>
			<c:if test="${senderPlace == 0 || senderPlace == null}">
				<div class="value"><fmt:message key="geobase.showSendersAndRecipientsPlace.zeroSenders"/></div>
			</c:if>
		</div>
		<div class="row">
			<c:if test="${recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
				<div class="value"><a id="linkSearch" class="recipient" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} <fmt:message key="geobase.showSendersAndRecipientsPlace.recipients"/></a></div>
			</c:if>
			<c:if test="${recipientPlace == 1}">
				<div class="value"><a id="linkSearch" class="recipient" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} <fmt:message key="geobase.showSendersAndRecipientsPlace.recipient"/></a></div>
			</c:if>
			<c:if test="${recipientPlace == 0 || recipientPlace == null}">
				<div class="value"><fmt:message key="geobase.showSendersAndRecipientsPlace.zeroRecipient"/></div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			
		});
</script>