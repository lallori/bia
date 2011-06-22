<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form id="customSearchFilterForm" method="post">
	<p><u>Custom Search Filter</u></p>
	<br />
	<div id="wordSearchDiv"></div>
	<div id="volumeSearchDiv"></div>
	<div id="dateRangeSearchDiv"></div>
</form>

<input type="hidden" name="wordSearch">
<input type="hidden" name="wordSearchType">
<input type="hidden" name="volNum">
<input type="hidden" name="volNumType">
<input type="hidden" name="volNumDateYear">
<input type="hidden" name="volNumDateMonth">
<input type="hidden" name="volNumDateDay">
<input type="hidden" name="volNumDateType">
<input type="hidden" name="extract">
<input type="hidden" name="synopsis">
<input type="hidden" name="topics">
<input type="hidden" name="person">
<input type="hidden" name="place">
<input type="hidden" name="sender">
<input type="hidden" name="from">
<input type="hidden" name="recipient">
<input type="hidden" name="to">