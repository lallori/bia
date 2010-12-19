<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="createdby"><h6>CREATED BY ${command.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${command.dateCreated}" /></h6></div>
	<form:form id="EditDetailsDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>DOCUMENT DETAILS</b></legend>
			<div>
				<form:label id="entryIdLabel" for="entryId" path="entryId" cssErrorClass="error">Doc ID: ${command.entryId}</form:label>
				<form:label id="volumeLabel" for="volume" path="volume" cssErrorClass="error">Volume (MDP):</form:label>
				<form:input id="volume" path="volume" cssClass="input_5c" maxlength="5"/>
			</div>

			<div>
				<form:label id="insertNumLabel" for="insertNum" path="insertNum" cssErrorClass="error">Insert/Part:</form:label>
				<form:input id="insertNum" path="insertNum" class="input_5c" />
				<form:input id="insertLet" path="insertLet" class="input_5c" />
				<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error">Folio Start:</form:label>
				<form:input id="folioNum" path="folioNum" class="input_5c" />
				<form:input id="folioMod" path="folioMod" class="input_5c" />
			</div>

			<div>	
				<form:label id="unpagedLabel" for="unpaged" path="unpaged" cssErrorClass="error">Unpaginated:</form:label>
				<form:checkbox id="unpaged" path="unpaged" class="checkboxPers2"/>
				<form:label id="contDiscLabel" for="contDisc" path="contDisc" cssErrorClass="error">Disc. Cont'd:</form:label>
				<form:checkbox id="contDisc" path="contDisc" class="checkboxPers1"/>
			</div>
			
			<hr />

			<div>
				<form:label id="docTypologyLabel" for="docTypology"  path="docTypology" cssErrorClass="error">Document Typology (other than letter):</form:label>
				<form:input id="docTypology" path="docTypology" class="input_45c"/>
			</div>
			
			<hr />
		
			<div>
				<b>Date:</b>
				<br />
				<form:label id="DocYearLabel" for="docYear" path="docYear">Year:</form:label>
				<form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/>
				<form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum">Month:</form:label>
				<form:select id="docMonthNum" path="docMonthNum" cssClass="selectform"><form:options items="${months}" itemValue="monthName" itemLabel="monthName"/></form:select>
				<form:label  for="docDay" id="docDayLabel" path="docDay">Day:</form:label>
				<form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/>
			</div>
			
			<div>
				<form:label id="yearModernLabel" for="yearModern" path="yearModern">Modern dating:</form:label>
				<form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/>
			</div>
			
			<div>
				<form:label  id="dateUnsLabel" for="dateUns" path="dateUns">Date Uncertain or Approximate?</form:label>
				<form:checkbox path="dateUns" class="checkboxDoc2"/>
				<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated">Undated</form:label>
				<form:checkbox path="dateUndated" class="checkboxDoc2"/>
			</div>
			
			<hr />
			
			<div>
				<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes">Date notes:</form:label>
			</div>
			<div>
				<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
			</div>
			
			<input id="dateCreated" name="dateCreated" type="hidden" value="11/03/2010 11:51:57"/>
			
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			var showVolumeExplorer = function (){
				$.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $("#volume").val() },
					function(data){
						if (data.summaryId == "") {
							if ($("#volNotExist").length == 0) {
								$("#close").before("<span class=\"inputerrors\" id=\"volNotExist\">Volume is not present, you cannot create this document. Save is disabled.<br></span>");
							}
							$("#save").attr("disabled","true");
						} else {
							if ($("#volNotExist").length > 0) {
								$("#volNotExist").remove();
							}
							$("#save").removeAttr("disabled");
							$.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { summaryId: data.summaryId, flashVersion : true },
								function(data){
									$("#body_right").html(data);
									return true;
								}
							);
						}
					}
				);
	 		}
			$("#volume").change(showVolumeExplorer);

			$("#EditDetailsDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					$("#EditDetailsDocumentDiv").html(html);
				}});
				return false;
			});
		});
	</script>
