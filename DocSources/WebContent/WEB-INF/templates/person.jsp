<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertAttribute name="details"/>

<br />

<tiles:insertAttribute name="names"/>

<br />

<tiles:insertAttribute name="titlesoroccupations"/>

<br />

<tiles:insertAttribute name="parents"/>

<br/>

<tiles:insertAttribute name="children"/>

<br/>

<tiles:insertAttribute name="spouses"/>

<br/>

<tiles:insertAttribute name="researchnotes"/>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div> 