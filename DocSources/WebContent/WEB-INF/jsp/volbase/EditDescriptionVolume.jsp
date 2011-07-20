<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div>
	<form:form id="EditDescriptionVolumeForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>DESCRIPTION</b></legend>
			<div><form:label id="orgNotesLabel" for="orgNotes" path="orgNotes" cssErrorClass="error">Organizational Criteria</form:label></div>
			<div><form:textarea path="orgNotes" cssClass="txtarea_big"/><form:errors path="orgNotes" cssClass="inputerrors"/></div>
	
			<div>				
				<form:label id="conditionLabel" for="ccondition" path="ccondition" cssErrorClass="error">Condition</form:label>
				<form:input path="ccondition" cssClass="input_35c"/><form:errors path="ccondition" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="boundLabel" for="bound" path="bound" cssErrorClass="error">Bound</form:label>
				<form:select path="bound" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="bound" cssClass="inputerrors"/>
				<form:label id="folsNumbrdLabel" for="folsNumbrd" path="folsNumbrd" cssErrorClass="error">Folios Numbered</form:label>
				<form:select path="folsNumbrd" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="folsNumbrd" cssClass="inputerrors"/>
			</div>

			<div>
				<form:label id="folioCountLabel" for="folioCount" path="folioCount" cssErrorClass="error">Folio Count</form:label>
				<form:input path="folioCount" cssClass="input_10c"/><form:errors path="ccondition" cssClass="inputerrors"/>
				<form:label id="oldAlphaIndexLabel" for="oldAlphaIndex" path="oldAlphaIndex" cssErrorClass="error">Alphabetical Index</form:label>
				<form:select path="oldAlphaIndex" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="oldAlphaIndex" cssClass="inputerrors"/>
			</div>
	
			<hr />
			<div>
				<form:label id="printedMaterialLabel" for="printedMaterial" path="printedMaterial" cssErrorClass="error">Printed material</form:label>
				<form:checkbox path="printedMaterial" class="checkbox1"/><form:errors path="printedMaterial" cssClass="inputerrors"/>
				<form:label id="printedDrawingsLabel" for="printedDrawings" path="printedDrawings"  cssErrorClass="error">Printed drawings</form:label>
				<form:checkbox path="printedDrawings" class="checkbox1"/><form:errors path="printedDrawings" cssClass="inputerrors"/>
			</div>

			<hr />
				
			<div>
				<label for="languages" id="languagesLabel" >Languages:</label>
			</div>
	
			<div>
				<form:label id="italianLabel" for="italian" path="italian" cssErrorClass="error">Italian</form:label>
				<form:checkbox path="italian" cssClass="checkbox1" /><form:errors path="italian" cssClass="inputerrors"/>
				<form:label id="frenchLabel" for="french" path="french" cssErrorClass="error">French</form:label>
				<form:checkbox path="french" cssClass="checkbox1" /><form:errors path="french" cssClass="inputerrors"/>
				<form:label id="germanLabel" for="german" path="german" cssErrorClass="error">German</form:label>
				<form:checkbox path="german" cssClass="checkbox1" /><form:errors path="german" cssClass="inputerrors"/>
			</div>
			
			<br />
			<div>
				<form:label id="spanishLabel" for="spanish" path="spanish" cssErrorClass="error">Spanish</form:label>
				<form:checkbox path="spanish" cssClass="checkbox2" /><form:errors path="spanish" cssClass="inputerrors"/>
				<form:label id="latinLabel" for="latin" path="latin" cssErrorClass="error">Latin</form:label>
				<form:checkbox path="latin" cssClass="checkbox3" /><form:errors path="latin" cssClass="inputerrors"/>
				<form:label id="englishLabel" for="english" path="english" cssErrorClass="error">English</form:label>
				<form:checkbox path="english" cssClass="checkbox1" /><form:errors path="english" cssClass="inputerrors"/>
			</div>
			
			<br />
	
			<div>				
				<form:label id="otherLangLabel" for="otherLang" path="otherLang" cssErrorClass="error">Other languages</form:label>
				<form:input path="otherLang" cssClass="input_33c"/><form:errors path="otherLang" cssClass="inputerrors"/>
			</div>
	
			<hr />
	
			<div>				
				<form:label id="cipherLabel" for="cipher" path="cipher" cssErrorClass="error">Some Docs in Cipher</form:label>
				<form:select path="cipher" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="cipher" cssClass="inputerrors"/>
			</div>
			<br />
			<div><form:label id="cipherNotesLabel" for="cipherNotes" path="cipherNotes" cssErrorClass="error">Cipher Notes</form:label></div>
			<div><form:textarea path="cipherNotes" cssClass="txtarea"/><form:errors path="cipherNotes" cssClass="inputerrors"/></div>
	
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
			<form:hidden path="summaryId"/>
			<input type="hidden" value="" id="modify" />
		</fieldset>
	</form:form>
</div>

<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
	<c:param name="summaryId"   value="${command.summaryId}" />
</c:url>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditContextVolume").css('visibility', 'hidden'); 
        $j("#EditCorrespondentsVolume").css('visibility', 'hidden'); 
        $j("#EditDescriptionVolume").css('visibility', 'hidden'); 
		$j("#EditDetailsVolume").css('visibility', 'hidden'); 

		$j("#EditDescriptionVolumeForm :input").change(function(){
			$j("#modify").val(1); //set the hidden field if an element is modified
			return false;
		});

		$j('#close').click(function() {
			//if no change, the question isn't displayed
			if($j("#modify").val() == 1)
            	$j('#EditDescriptionVolumeDiv').block({ message: $j('#question') });
			else
				$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}}); 
			return false;
		});
	        
        $j("#EditDescriptionVolumeForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#EditDescriptionVolumeDiv").html(html);
			}});
			return false;
		});
	});
</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditDescriptionVolumeDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>