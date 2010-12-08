<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<script type='text/javascript' src='<c:url value="/scripts/jquery.autocomplete.js"/>'></script>
	<link rel="stylesheet" href="<c:url value="/styles/jquery.autocomplete2.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/styles/style_editform.css" />" type="text/css" media="screen, projection">

	<div id="createdby"><h6>CREATED BY ${command.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${command.dateCreated}" /></h6></div>
	<form:form id="EditDetailsDocumentForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>DOCUMENT DETAILS</b></legend>
			<div>
				<label for="entryId" id="entryIdLabel" style="margin-left:40px">Doc ID:</label>
				<form:input id="entryIdAutoCompleter" path="entryId" cssClass="input_2c"/><form:errors path="entryId" cssClass="inputerrors"/>
				<input id="" name="docId" class="input_2c" type="text" value="" />
				<label for="volNum" id="volNumLabel">Volume (MDP):</label>
				<input id="volNumAutoCompleter" name="volNum" class="input_5c" type="text" value="" maxlength="5"/>
				<label for="insertPart" id="insertPartLabel">Insert/Part:</label>
				<input id="insertPart" name="insertPart" class="input_5c" type="text" value=""/>
			</div>
			
			<div>
				<label for="folioStart" id="folioStartLabel">Folio Start:</label>
				<input id="folioStart" name="folioStart" class="input_5c" type="text" value=""/>
				<label for="unpaginated" id="unpaginatedLabel" style="margin-left:6px">Unpaginated:</label>
				<input type="checkbox" name="unpaginated" style="margin:0px 15px 0px 7px" class="checkbox"/>
				<label for="discontd" id="discontdLabel" style="margin-left:27px">Disc. Cont'd:</label>
				<input type="checkbox" name="discontd" style="margin:0px 0px 0px 7px" class="checkbox"/>
			</div>
			
			<div>
				<label for="modernDate" id="modernDateLabel">Modern Date:</label>
				<input id="modernDate" name="modernDate" class="input_4c" type="text" value="" maxlength="4"/>
				<label for="recordedDate" id="recordedDateLabel" style="margin-left:2px">Recorded Date:</label>
				<input id="recordedDate" name="recordedDate" class="input_4c" type="text" value="" maxlength="4"/>
				
			</div>
			
			<div style="margin:0"><label for="dateNotes" id="dateNotesLabel">Date notes:</label></div>
			<div style="margin:0"><textarea id="dateNotes" name="dateNotes" class="txtarea"></textarea></div>
			
			<input id="summaryId" name="summaryId" type="hidden" value="0"/>
			<input id="resIdNo" name="resIdNo" type="hidden" value=""/>
			<input id="seriesRefNum" name="seriesRefNum" type="hidden" value=""/>
			<input id="dateCreated" name="dateCreated" type="hidden" value="11/03/2010 11:51:57"/>
			
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditDetailsDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditDetailsDocumentDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
