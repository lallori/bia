<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form id="customSearchFilterForm" method="post">
	<p><u>Custom Search Filter</u></p>
	<br />
</form>

<input type="hidden" name="words">
<input type="hidden" name="wordsTypes">
<input type="hidden" name="volumes">
<input type="hidden" name="volumesBetween">
<input type="hidden" name="volumesType">
<input type="hidden" name="datesType">
<input type="hidden" name="datesYear">
<input type="hidden" name="datesMonth">
<input type="hidden" name="datesDay">
<input type="hidden" name="extract">
<input type="hidden" name="synopsis">
<input type="hidden" name="topics">
<input type="hidden" name="topicsId">
<input type="hidden" name="person">
<input type="hidden" name="personId">
<input type="hidden" name="place">
<input type="hidden" name="placeId">
<input type="hidden" name="sender">
<input type="hidden" name="senderId">
<input type="hidden" name="from">
<input type="hidden" name="fromId">
<input type="hidden" name="recipient">
<input type="hidden" name="recipientId">
<input type="hidden" name="to">
<input type="hidden" name="toId">
<input type="hidden" name="resTo">