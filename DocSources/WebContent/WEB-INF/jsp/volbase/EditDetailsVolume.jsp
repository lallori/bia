<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<script type='text/javascript' src='<c:url value="/scripts/jquery.autocomplete.js"/>'></script>
	<link rel="stylesheet" href="<c:url value="/styles/jquery.autocomplete2.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/styles/style_editform.css" />" type="text/css" media="screen, projection">

	<h6>CREATED BY ${command.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${command.dateCreated}" /></h6>
	<form:form id="EditDetailsVolumeForm" class="edit" method="post">
		<fieldset>
			<legend><b>Volume DETAILS</b></legend>
			<div>
				<form:label id="seriesRefDescriptionLabel" for="seriesRefDescription" path="seriesRefDescription" cssErrorClass="error"><i>Series List</i></form:label>
				<form:input id="seriesRefDescriptionAutoCompleter" path="seriesRefDescription" cssClass="input_long"/><form:errors path="seriesRefDescription" cssClass="inputerrors"/>
			</div>
			<div>
				<form:label id="volNumLabel" for="volNum" path="volNum" cssClass="error" cssErrorClass="error" >Volume/Filza (MDP) :</form:label><form:input id="volNum" path="volNum" cssClass="input_5c" /><form:errors path="volNum" cssClass="inputerrors"/><form:label id="volLeTextLabel" for="volLeText" path="volLeText" cssErrorClass="error">Volume extension:</form:label>
				<form:input id="volLeText" path="volLeText" size="1" cssClass="input_1c"/><form:errors path="volLeText" cssClass="inputerrors"/>
			</div>
			<div>
				<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Start year:</form:label><form:input id="startYear" path="startYear" cssClass="input_4c" maxlength="4"/><form:errors path="startYear" cssClass="inputerrors"/>
				<form:label id="startMonthLabel" for="startMonth" path="startMonth" cssErrorClass="error">Start month:</form:label><form:select id="startMonth" path="startMonth" cssClass="selectform"><form:options items="${months}" itemValue="monthName" itemLabel="monthName"/></form:select><form:errors path="startMonth" cssClass="inputerrors"/>
				<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Start day:</form:label><form:input id="startDay" path="startDay" cssClass="input_2c" maxlength="2"/><form:errors path="startDay" cssClass="inputerrors"/>
			</div>
			<div>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">End year:</form:label><form:input id="endYear" path="endYear" cssClass="input_4c" maxlength="4" cssStyle="margin-left:8px"/><form:errors path="endYear" cssClass="inputerrors"/>
				<form:label id="endMonthLabel" for="endMonth" path="endMonth" cssErrorClass="error">End month:</form:label><form:select id="endMonth" path="endMonth" cssClass="selectform" cssStyle="margin-left:8px"><form:options items="${months}" itemValue="monthName" itemLabel="monthName"/></form:select><form:errors path="endMonth" cssClass="inputerrors"/>
				<form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">End day:</form:label><form:input id="endDay" path="endDay" cssClass="input_2c" maxlength="2" cssStyle="margin-left:10px"/><form:errors path="endDay" cssClass="inputerrors"/>
			</div>
			<div style="margin:0">
				<form:label id="dateNotesLabel" for="dateNotes" path="dateNotes" cssErrorClass="error">Date notes:</form:label>
			</div>
			<div style="margin:0">
				<form:textarea id="dateNotes" path="dateNotes" cssClass="txtarea"/><form:errors path="dateNotes" cssClass="inputerrors"/>
			</div>
			<form:hidden path="summaryId"/>
			<form:hidden path="seriesRefNum"/>
			<form:hidden path="dateCreated"/>
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close edit window" class="button" /><input id="save" type="submit" value="Save" style="margin-left:235px" class="button"/>
			</div>
		</fieldset>	
	</form:form>

	<c:url var="findResearchersUrl" value="/de/volbase/FindResearchers.json"/>
	<c:url var="findSeriesUrl" value="/de/volbase/FindSeries.json"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
			var a = $('#seriesRefDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${findSeriesUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#seriesRefNum').val(data); }
			  });
			$("#EditDetailsVolumeForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditDetailsVolumeDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>