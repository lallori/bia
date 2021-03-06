<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div>
	<form:form id="EditDescriptionVolumeForm" method="post" cssClass="edit">
		<div id="loadingDiv"></div>
		<fieldset>
			<legend><b><fmt:message key="volbase.editDescriptionVolume.title"/></b></legend>
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="volbase.editDescriptionVolume.help.organizationalcriteria"/>">?</a>
					<form:label id="orgNotesLabel" for="orgNotes" path="orgNotes" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.organizationalcriteria"/></form:label>
				</div>
				<div class="row">
					<form:textarea path="orgNotes" cssClass="txtarea_big"/><form:errors path="orgNotes" cssClass="inputerrors"/>
				</div>
				<br />
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="volbase.editDescriptionVolume.help.condition"/>">?</a>
					<form:label id="conditionLabel" for="ccondition" path="ccondition" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.condition"/></form:label>
				</div>
				<div class="row">
					<form:textarea path="ccondition" id="condition" cssClass="txtarea"/><form:errors path="ccondition" cssClass="inputerrors"/>
				</div>
			</div>
	
			<div class="listForm">
				<div class="row">
					<div class="col_r"><form:label id="boundLabel" for="bound" path="bound" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.bound"/></form:label></div>
					<div class="col_l"><form:select path="bound" cssClass="selectform_short"><form:option value="true"><fmt:message key="volbase.editDescriptionVolume.yes"/></form:option><form:option value="false"><fmt:message key="volbase.editDescriptionVolume.no"/></form:option></form:select><form:errors path="bound" cssClass="inputerrors"/></div>
					<div class="col_r"><form:label id="folsNumbrdLabel" for="folsNumbrd" path="folsNumbrd" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.foliosNumbered"/></form:label></div>
					<div class="col_r"><form:select path="folsNumbrd" cssClass="selectform_short"><form:option value="true"><fmt:message key="volbase.editDescriptionVolume.yes"/></form:option><form:option value="false"><fmt:message key="volbase.editDescriptionVolume.no"/></form:option></form:select><form:errors path="folsNumbrd" cssClass="inputerrors"/></div>
				</div>
				<div class="row">
					<div class="col_r"><a class="helpIcon" title="<fmt:message key="volbase.editDescriptionVolume.help.foliocount"/>">?</a><form:label id="folioCountLabel" for="folioCount" path="folioCount" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.folioCount"/></form:label></div>
					<div class="col_l"><form:input path="folioCount" cssClass="input_10c"/><form:errors path="ccondition" cssClass="inputerrors"/></div>
					<div class="col_r"><form:label id="oldAlphaIndexLabel" for="oldAlphaIndex" path="oldAlphaIndex" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.indexOfNames"/></form:label></div>
					<div class="col_r"><form:select path="oldAlphaIndex" cssClass="selectform_short"><form:option value="true"><fmt:message key="volbase.editDescriptionVolume.yes"/></form:option><form:option value="false"><fmt:message key="volbase.editDescriptionVolume.no"/></form:option></form:select><form:errors path="oldAlphaIndex" cssClass="inputerrors"/></div>
				</div>
			</div>
	
			<hr />
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<form:label id="printedMaterialLabel" for="printedMaterial" path="printedMaterial" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.printedMaterial"/></form:label>
						<form:checkbox path="printedMaterial"/><form:errors path="printedMaterial" cssClass="inputerrors"/>
					</div>
					<div class="col_r">
						<form:label id="printedDrawingsLabel" for="printedDrawings" path="printedDrawings"  cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.printedDrawings"/></form:label>
						<form:checkbox path="printedDrawings"/><form:errors path="printedDrawings" cssClass="inputerrors"/>
					</div>
				</div>
			</div>

			<hr />
				
			<div>
				<a class="helpIcon" title="<fmt:message key="volbase.editDescriptionVolume.help.languages"/>">?</a><label for="languages" id="languagesLabel"><fmt:message key="volbase.editDescriptionVolume.languages"/>:</label>
			</div>
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<form:label id="italianLabel" for="italian" path="italian" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.italian"/></form:label>
						<form:checkbox path="italian" /><form:errors path="italian" cssClass="inputerrors"/>
					</div>
					<div class="col_r">
						<form:label id="frenchLabel" for="french" path="french" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.french"/></form:label>
						<form:checkbox path="french" /><form:errors path="french" cssClass="inputerrors"/>
					</div>
					<div class="col_r">
						<form:label id="germanLabel" for="german" path="german" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.german"/></form:label>
						<form:checkbox path="german" /><form:errors path="german" cssClass="inputerrors"/>
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<form:label id="spanishLabel" for="spanish" path="spanish" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.spanish"/></form:label>
						<form:checkbox path="spanish" /><form:errors path="spanish" cssClass="inputerrors"/>
					</div>
					<div class="col_r">
						<form:label id="latinLabel" for="latin" path="latin" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.latin"/></form:label>
						<form:checkbox path="latin" /><form:errors path="latin" cssClass="inputerrors"/>
					</div>
					<div class="col_r">
						<form:label id="englishLabel" for="english" path="english" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.languages.english"/></form:label>
						<form:checkbox path="english" /><form:errors path="english" cssClass="inputerrors"/>
					</div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<form:label id="otherLangLabel" for="otherLang" path="otherLang" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.otherLanguages"/></form:label>
						<form:input path="otherLang" cssClass="input_33c"/><form:errors path="otherLang" cssClass="inputerrors"/>
					</div>
				</div>
			</div>
	
			<hr />
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<form:label id="cipherLabel" for="cipher" path="cipher" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.someDocsInCipher"/></form:label>
						<form:select path="cipher" cssClass="selectform_short">
							<form:option value="true"><fmt:message key="volbase.editDescriptionVolume.yes"/></form:option>
							<form:option value="false"><fmt:message key="volbase.editDescriptionVolume.no"/></form:option>
						</form:select>
						<form:errors path="cipher" cssClass="inputerrors"/>
					</div>
				</div>
				
				<br />
				
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="<fmt:message key="volbase.editDescriptionVolume.help.ciphernotes"/>">?</a>
						<form:label id="cipherNotesLabel" for="cipherNotes" path="cipherNotes" cssErrorClass="error"><fmt:message key="volbase.editDescriptionVolume.cipherNotes"/></form:label>
						<form:textarea path="cipherNotes" cssClass="txtarea"/><form:errors path="cipherNotes" cssClass="inputerrors"/>
					</div>
				</div>
				
			</div>	
			
			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
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
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });

		$j('#close').click(function() {
			if($j("#modify").val() == 1)
            	$j('#EditDescriptionVolumeDiv').block({ message: $j('#question'),
            		css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
            	});
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
        
        var warningFolio = function (){
			if($j("#volume").val() != ""){
			$j.get('<c:url value="/src/docbase/CheckVolumeFolio.json" />', { summaryId: "${command.summaryId}" },
				function(data){
					if (data.folioMax != "") {
						if ($j("#folioNotCorrect").length == 0) {
							if(parseInt($j("#folioCount").val(),10) < parseInt(data.folioMax,10)){
								$j("#close").before("<span class=\"inputerrorsFolioNotCorrect\" id=\"folioNotCorrect\">WARNING: one or more documents in this volume have folio number set higher that the folio count total that you are entering. You cannot set folio count to 300 if a document has been set to 306.  Save is disabled.<br></span>");
								$j("#save").attr("disabled","true");
							}
						}else {
							if ($j("#folioNotCorrect").length > 0 && parseInt(data.folioMax,10) <= parseInt($j("#folioCount").val(),10)) {
								$j("#folioNotCorrect").remove();
							}
							$j("#save").removeAttr("disabled");
						
					} 
						
						/*var tabName = "Volume Explorer " + data.volNum + data.volLetExt + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab"
            			var showVolumeExplorer = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
                    	$j("#tabs").tabs("add", "" + showVolumeExplorer, tabName);
                    	$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);*/
                    	
                    	/*$j.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { summaryId: data.summaryId, flashVersion : false },
							function(data){
								$j("#body_right").html(data);
								return true;
							}
						);*/
					}
				}
			);
			}
 		}
        
        $j('#folioCount').change(warningFolio);
	});
</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="volbase.editDescriptionVolume.discardChangesQuestion"/></h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
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