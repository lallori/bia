<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="editDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do"/>
	
	<form:form id="EditDetailsDocumentForm" action="${editDetailsDocumentURL}" method="post" cssClass="edit">
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
			</div>
			
			<div>
				<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error">Document starts at folio :</form:label>
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
				<form:label id="DocYearLabel" for="docYear" path="docYear" cssErrorClass="error">Year:</form:label>
				<form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/>
				<form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum" cssErrorClass="error">Month:</form:label>
				<form:select id="docMonthNum" path="docMonthNum" cssClass="selectform" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
				<form:label  for="docDay" id="docDayLabel" path="docDay" cssErrorClass="error">Day:</form:label>
				<form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/>
			</div>
			
			<div>
				<form:label id="yearModernLabel" for="yearModern" path="yearModern" cssErrorClass="error">Modern dating:</form:label>
				<form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/>
			</div>
			
			<div>
				<form:label  id="dateUnsLabel" for="dateUns" path="dateUns">Date Uncertain or Approximate?</form:label>
				<form:checkbox id="dateUns" path="dateUns" class="checkboxDoc2"/>
				<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated">Undated</form:label>
				<form:checkbox  id="dateUndated" path="dateUndated" class="checkboxDoc2"/>
			</div>
			
			<hr />
			
			<div>
				<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes">Date notes:</form:label>
			</div>
			<div>
				<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
			</div>
			
			<form:hidden id="transcribeFolioNum" path="transcribeFolioNum" />
			<form:hidden id="transcribeFolioMod" path="transcribeFolioMod" />
			<form:hidden id="dateCreated" path="dateCreated" />
			<form:hidden id="entryId" path="entryId" />

			<div style="margin-top:5px">
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<input id="save" type="submit" value="" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
		<input type="hidden" name="summaryId" value="${document.volume.summaryId}">
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'hidden');
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');

			var showVolumeExplorer = function (){
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $j("#volume").val() },
					function(data){
						if (data.summaryId == "") {
							if ($j("#volNotExist").length == 0) {
								$j("#close").before("<span class=\"inputerrors\" id=\"volNotExist\">Volume is not present, you cannot create this document. Save is disabled.<br></span>");
							}
							$j("#save").attr("disabled","true");
						} else {
							if ($j("#volNotExist").length > 0) {
								$j("#volNotExist").remove();
							}
							$j("#save").removeAttr("disabled");
							$j.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { summaryId: data.summaryId, flashVersion : true },
								function(data){
									$j("#body_right").html(data);
									return true;
								}
							);
						}
					}
				);
	 		}
			$j("#volume").change(showVolumeExplorer);

			if ($j("#transcribeFolioNum").val().length>0) {
				$j("#EditDetailsDocument").volumeExplorer( {
					summaryId				: "${document.volume.summaryId}",
					transcribeFolioNum		: "${command.transcribeFolioNum}",
					checkVolumeDigitizedURL	: "${checkVolumeDigitizedURL}",
					showExplorerVolumeURL	: "${ShowExplorerVolumeURL}",
					target 					: $j("#body_right") 
				});  
			}

			$j("#EditDetailsDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsDocumentDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.entryId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#EditDetailsDocumentDiv").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
				return false;
			});

	        $j('#close').click(function() {
				$j('#EditDetailsDocumentDiv').block({ message: $j('#question') }); 
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
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>