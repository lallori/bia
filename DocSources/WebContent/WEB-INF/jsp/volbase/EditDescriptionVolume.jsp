<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div>
		<form:form id="EditDescriptionVolumeForm" method="post" cssClass="edit">
			<fieldset>
				<legend><b>Volume DESCRIPTION</b></legend>
				<div><form:label id="orgNotesLabel" for="orgNotes" path="orgNotes" cssErrorClass="error">Organizational Criteria: </form:label></div>
				<div><form:textarea id="orgNotes" path="orgNotes" cssClass="txtarea"/><form:errors path="orgNotes" cssClass="inputerrors"/></div>
		
				<div>				
					<form:label id="cconditionLabel" for="ccondition" path="ccondition" cssErrorClass="error">Condition: </form:label>
					<form:input id="ccondition" path="ccondition" cssClass="input_35c"/><form:errors path="ccondition" cssClass="inputerrors"/>
				</div>
				<div>				
					<form:label id="boundLabel" for="bound" path="bound" cssErrorClass="error">Bound: </form:label>
					<form:select id="bound" path="bound" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="bound" cssClass="inputerrors"/>
					<form:label id="folsNumbrdLabel" for="folsNumbrd" path="folsNumbrd" cssErrorClass="error">Folios Numbered: </form:label>
					<form:select id="folsNumbrd" path="folsNumbrd" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="folsNumbrd" cssClass="inputerrors"/>
				</div>
	
				<div>
					<form:label id="folioCountLabel" for="folioCount" path="folioCount" cssErrorClass="error">Folio Count:</form:label>
					<form:input id="folioCount" path="folioCount" cssClass="input_10c"/><form:errors path="ccondition" cssClass="inputerrors"/>
					<form:label id="oldAlphaIndexLabel" for="oldAlphaIndex" path="oldAlphaIndex" cssErrorClass="error">Alphabetical Index: </form:label>
					<form:select id="oldAlphaIndex" path="oldAlphaIndex" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="oldAlphaIndex" cssClass="inputerrors"/>
				</div>
	
				<hr />

				<div>
					<form:label id="printedMaterialLabel" for="printedMaterial" path="printedMaterial" cssErrorClass="error">Printed material</form:label>
					<form:checkbox id="printedMaterial" path="printedMaterial" class="checkbox1"/><form:errors path="printedMaterial" cssClass="inputerrors"/>
					<form:label id="printedDrawingsLabel" for="printedDrawings" path="printedDrawings"  cssErrorClass="error">Printed drawings</form:label>
					<form:checkbox id="printedDrawings" path="printedDrawings" class="checkbox1"/><form:errors path="printedDrawings" cssClass="inputerrors"/>
				</div>

				<hr />
				
				<div>
					<label for="languages" id="languagesLabel" >Languages:</label>
				</div>
	
				<div>
					<form:label id="italianLabel" for="italian" path="italian" cssErrorClass="error">Italian</form:label>
					<form:checkbox id="italian" path="italian" cssClass="checkbox" /><form:errors path="italian" cssClass="inputerrors"/>
					<form:label id="frenchLabel" for="french" path="french" cssErrorClass="error">French</form:label>
					<form:checkbox id="french" path="french" cssClass="checkbox" /><form:errors path="french" cssClass="inputerrors"/>
					<form:label id="germanLabel" for="german" path="german" cssErrorClass="error">German</form:label>
					<form:checkbox id="german" path="german" cssClass="checkbox" /><form:errors path="german" cssClass="inputerrors"/>
				</div>
				
				<div>
					<form:label id="spanishLabel" for="spanish" path="spanish" cssErrorClass="error">Spanish</form:label>
					<form:checkbox id="spanish" path="spanish" cssClass="checkbox" /><form:errors path="spanish" cssClass="inputerrors"/>
					<form:label id="latinLabel" for="latin" path="latin" cssErrorClass="error">Latin</form:label>
					<form:checkbox id="latin" path="latin" cssClass="checkbox" /><form:errors path="latin" cssClass="inputerrors"/>
					<form:label id="englishLabel" for="english" path="english" cssErrorClass="error">English</form:label>
					<form:checkbox id="english" path="english" cssClass="checkbox" /><form:errors path="english" cssClass="inputerrors"/>
				</div>
	
				<div>				
					<form:label id="otherLangLabel" for="otherLang" path="otherLang" cssErrorClass="error">Other languages:</form:label>
					<form:input id="otherLang" path="otherLang" cssClass="input_33c"/><form:errors path="otherLang" cssClass="inputerrors"/>
				</div>
	
				<hr />
	
				<div>				
					<form:label id="cipherLabel" for="cipher" path="cipher" cssErrorClass="error">Some Docs in Cipher : </form:label>
					<form:select id="cipher" path="cipher" cssClass="selectform_short"><form:option value="true">Yes</form:option><form:option value="false">No</form:option></form:select><form:errors path="cipher" cssClass="inputerrors"/>
				</div>
			
				<div><form:label id="cipherNotesLabel" for="cipherNotes" path="cipherNotes" cssErrorClass="error">Cipher Notes:</form:label></div>
				<div><form:textarea id="cipherNotes" path="cipherNotes" cssClass="txtarea"/><form:errors path="cipherNotes" cssClass="inputerrors"/></div>
	
				<div>
					<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
				</div>
				<form:hidden path="summaryId"/>
			</fieldset>
		</form:form>
	</div>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#close').click(function() {
	            $j('#EditDescriptionVolumeDiv').block({ message: $j('#question') }); 
				return false;
			});
	        
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j('#question').hide();
				$j('#EditDescriptionVolumeDiv').append($j("#question"));
				$j(".blockUI").remove();
	            return false; 
	        }); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

	        $j("#EditDescriptionVolumeForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditDescriptionVolumeDiv").replaceWith(html);
				}});
				return false;
			});

			$j("#EditContextVolume").removeAttr("href");
	        $j("#EditCorrespondentsVolume").removeAttr("href"); 
			$j("#EditDetailsVolume").removeAttr("href"); 
		});
	</script>
