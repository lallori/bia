<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<script type='text/javascript' src='<c:url value="/scripts/jquery.autocomplete.js"/>'></script>
	<link rel="stylesheet" href="<c:url value="/styles/jquery.autocomplete2.css" />" type="text/css" media="screen, projection">

<div>
	<!-- The class assigned to href is the default of jqModal -->
	<a href="#" class="jqmClose">Close edit Window</a>
	<form:form id="EditDetailsVolume" method="post">
		<form:label id="volNumLabel" for="volNum" path="volNum" cssErrorClass="error">Vol Num</form:label>
		<form:input id="volNum" path="volNum" cssClass="input" /><form:errors path="volNum" cssClass="inputerrors"/>
		<p>
		<form:label id="volLeTextLabel" for="volLeText" path="volLeText" cssErrorClass="error">Vol le text</form:label>
		<form:input id="volLeText" path="volLeText" cssClass="input"/><form:errors path="volLeText" cssClass="inputerrors"/>
		<p>
		<%-- Research field --%>
		<form:label id="resDescriptionLabel" for="resDescription" path="resDescription" cssErrorClass="error">Research Name and Surname : </form:label>
		<form:input id="resDescriptionAutoCompleter" path="resDescription" cssClass="input"/><form:errors path="resDescription" cssClass="inputerrors"/>
		<p>
		<%-- Serie Description suggester... --%>
		<form:label id="seriesRefDescriptionLabel" for="seriesRefDescription" path="seriesRefDescription" cssErrorClass="error">Series List</form:label>
		<form:input id="seriesRefDescriptionAutoCompleter" path="seriesRefDescription" cssClass="input"/><form:errors path="resDescription" cssClass="inputerrors"/>
		<p>

		<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">startYearLabel</form:label>
		<form:input id="startYear" path="startYear" cssClass="input"/><form:errors path="startYear" cssClass="inputerrors"/>
		<p>
		<form:label id="startMonthLabel" for="startMonth" path="startMonth" cssErrorClass="error">startMonthLabel</form:label>
		<form:select id="startMonth" path="startMonth" cssClass="input">
		<form:options items="${months}" itemValue="monthName" itemLabel="monthName"/>
		</form:select>
		<form:errors path="startMonth" cssClass="inputerrors"/>
		<p>
		<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">startDayLabel</form:label>
		<form:input id="startDay" path="startDay" cssClass="input"/><form:errors path="startDay" cssClass="inputerrors"/>
		<p>

		<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">endYearLabel</form:label>
		<form:input id="endYear" path="endYear" cssClass="input"/><form:errors path="endYear" cssClass="inputerrors"/>
		<p>
		<form:label id="endMonthLabel" for="endMonth" path="endMonth" cssErrorClass="error">endMonthLabel</form:label>
		<form:select id="endMonth" path="endMonth" cssClass="input" >
		<form:options items="${months}" itemValue="monthName" itemLabel="monthName"/>
		</form:select>
		<form:errors path="endMonth" cssClass="inputerrors"/>
		<p>
		<form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">endDayLabel</form:label>
		<form:input id="endDay" path="endDay" cssClass="input"/><form:errors path="endDay" cssClass="inputerrors"/>
		<p>

		<form:label id="dateNotesLabel" for="dateNotes" path="dateNotes" cssErrorClass="error">dateNotesLabel</form:label>
		<form:textarea id="dateNotes" path="dateNotes" cssClass="input"/><form:errors path="dateNotes" cssClass="inputerrors"/>
		<p>

		<form:label id="staffMemoLabel" for="staffMemo" path="staffMemo" cssErrorClass="error">staffMemoLabel</form:label>
		<form:textarea id="staffMemo" path="staffMemo" cssClass="input"/><form:errors path="staffMemo" cssClass="inputerrors"/>
		<p>

		<form:hidden path="summaryId"/>
		<form:hidden path="resIdNo"/>
		<form:hidden path="seriesRefNum"/>
		<form:hidden path="dateCreated"/>
		<input id="update" type="submit" value="Save" />
	</form:form>
</div>
<c:url var="findResearchersUrl" value="/de/volbase/FindResearchers.json"/>
<c:url var="findSeriesUrl" value="/de/volbase/FindSeries.json"/>

<script type="text/javascript">
	$(document).ready(function() {
		var a = $('#resDescriptionAutoCompleter').autocomplete({ 
		    serviceUrl:'${findResearchersUrl}',
		    minChars:3, 
		    delimiter: /(,|;)\s*/, // regex or character
		    maxHeight:400,
		    width:600,
		    zIndex: 9999,
		    deferRequestBy: 0, //miliseconds
		    noCache: false, //default is false, set to true to disable caching
		    onSelect: function(value, data){ $('#resIdNo').val(data); } 
		  });
		var b = $('#seriesRefDescriptionAutoCompleter').autocomplete({ 
		    serviceUrl:'${findSeriesUrl}',
		    minChars:3, 
		    delimiter: /(,|;)\s*/, // regex or character
		    maxHeight:400,
		    width:600,
		    zIndex: 9999,
		    deferRequestBy: 0, //miliseconds
		    noCache: false, //default is false, set to true to disable caching
		    onSelect: function(value, data){ $('#seriesRefNum').val(data); }
		  });
		$("#EditDetailsVolume").submit(function (){
			$.post($(this).attr("action"), $(this).serialize(), function() {
				// In questa function si definisce la sostituzione del div dove visualizzare il risultato
				// questa function rappresenta 
				alert('done!');
			});
		});
	});
</script>