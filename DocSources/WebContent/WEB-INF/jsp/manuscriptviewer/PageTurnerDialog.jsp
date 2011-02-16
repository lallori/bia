<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="prevNextButtons">
	<div style="text-align:center; color:#6D5C4D">Flip throught</div>
	<br />
    <div id="prevButton">
    	<a href="#"><img src="images/button_prev.png" alt="prev" /></a>
	</div>
	<div id="nextButton">
		<a href="#"><img src="images/button_next.png" alt="next" /></a>
	</div>
</div>

<div id="line"></div>

<div id="rubricarioMoveTo">
	<form id="moveToRubricarioForm" action="/DocSources/de/volbase/moveToRubricario.do" method="post" class="edit">
		<label id="secondRecordLabel" for="secondRecord" >Move to rubricario (page)</label>
		<input id="secondRecord" name="secondRecord" class="input_4c" type="text" value="" />
		<input id="goR" type="image" src="images/go.png" alt="Go"/>
	</form>
</div>

<div id="folioMoveTo">
	<form id="moveToFolioForm" action="/DocSources/de/volbase/moveToFolio.do" method="post" class="edit">
		<label id="firstRecordLabel" for="firstRecord" >Move to folio (page)</label>
		<input id="firstRecord" name="firstRecord" class="input_4c" type="text" value="" />
		<input id="go" type="image" src="images/go.png" alt="Go"/>
	</form>
</div>

<div id="line2"></div>

<div id="personalNotes">
	<a href="#"><img src="images/button_perosnalNotes.png" alt="Personal Notes" /></a>
</div>

<div id="exit">
	<a href="#"><img src="images/button_exit.png" alt="Exit" /></a>
</div>