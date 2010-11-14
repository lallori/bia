<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditDescriptionVolumeForm" method="post">
		<fieldset>
			<legend><b>Volume DESCRIPTION</b></legend>
			<div>				
				<form:label id="orgNotesLabel" for="orgNotes" path="orgNotes" cssErrorClass="error"><i>Organizational Criteria: </i></form:label>
				<form:textarea id="orgNotes" path="orgNotes" cssClass="txtarea"/><form:errors path="orgNotes" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="cconditionLabel" for="ccondition" path="ccondition" cssErrorClass="error"><i>Condition: </i></form:label>
				<form:textarea id="ccondition" path="ccondition" cssClass="txtarea"/><form:errors path="ccondition" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="boundLabel" for="bound" path="bound" cssErrorClass="error"><i>Bound: </i></form:label>
				<form:checkbox id="bound" path="bound" cssClass="input_1c"/><form:errors path="bound" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="folsNumbrdLabel" for="folsNumbrd" path="folsNumbrd" cssErrorClass="error"><i>Folios Numbered: </i></form:label>
				<form:checkbox id="folsNumbrd" path="folsNumbrd" cssClass="input_1c"/><form:errors path="folsNumbrd" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="oldAlphaIndexLabel" for="oldAlphaIndex" path="oldAlphaIndex" cssErrorClass="error"><i>Alphabetical Index: </i></form:label>
				<form:checkbox id="oldAlphaIndex" path="oldAlphaIndex" cssClass="input_1c"/><form:errors path="oldAlphaIndex" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="italianLabel" for="italian" path="italian" cssErrorClass="error"><i>Italian: </i></form:label>
				<form:checkbox id="italian" path="italian" cssClass="input_1c"/><form:errors path="italian" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="spanishLabel" for="spanish" path="spanish" cssErrorClass="error"><i>Spanish: </i></form:label>
				<form:checkbox id="spanish" path="spanish" cssClass="input_1c"/><form:errors path="spanish" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="englishLabel" for="english" path="english" cssErrorClass="error"><i>English: </i></form:label>
				<form:checkbox id="english" path="english" cssClass="input_1c"/><form:errors path="english" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="latinLabel" for="latin" path="latin" cssErrorClass="error"><i>Latin: </i></form:label>
				<form:checkbox id="latin" path="latin" cssClass="input_1c"/><form:errors path="latin" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="germanLabel" for="german" path="german" cssErrorClass="error"><i>German: </i></form:label>
				<form:checkbox id="german" path="german" cssClass="input_1c"/><form:errors path="german" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="frenchLabel" for="french" path="french" cssErrorClass="error"><i>French: </i></form:label>
				<form:checkbox id="french" path="french" cssClass="input_1c"/><form:errors path="french" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="otherLangLabel" for="otherLang" path="otherLang" cssErrorClass="error">Other languages: </form:label>
				<form:input id="otherLang" path="otherLang" cssClass="input_long"/><form:errors path="otherLang" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="cipherLabel" for="cipher" path="cipher" cssErrorClass="error"><i>Cipher: </i></form:label>
				<form:checkbox id="cipher" path="cipher" cssClass="input_1c"/><form:errors path="cipher" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="cipherNotesLabel" for="cipherNotes" path="cipherNotes" cssErrorClass="error">Other languages: </form:label>
				<form:input id="cipherNotes" path="cipherNotes" cssClass="input_long"/><form:errors path="cipherNotes" cssClass="inputerrors"/>
			</div>
			<div>				
			</div>
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
			<form:hidden path="summaryId"/>
		</fieldset>
	</form:form>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<script type="text/javascript">
		$(document).ready(function() {
	        $('#close').click(function() {
	        	$.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}}); 
				return false;
			});

	        $("#EditDescriptionVolumeForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					if(html.match(/inputerrors/g)){
						$("#EditDescriptionVolumeDiv").html(html);
					} else {
						$("#body_left").html(html);
					}
				}});
				return false;
			});
		});
	</script>
