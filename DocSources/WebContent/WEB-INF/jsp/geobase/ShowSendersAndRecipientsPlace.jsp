<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="background" id="EditSendRecipPlaceDiv">
	<div class="title">
		<h5>SENDERS and RECIPIENTS </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value">${place.senderDocuments.size()} Senders</div>
		</div>
		<div class="row">
			<div class="value">${place.recipientDocuments.size()} Recipients</div>
		</div>
	</div>
</div>

<br />
<br />