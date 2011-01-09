<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertAttribute name="details"/>

<br />

<tiles:insertAttribute name="factchecks"/>

<br />

<tiles:insertAttribute name="correspondents"/>

<br />

<tiles:insertAttribute name="extract"/>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div> 