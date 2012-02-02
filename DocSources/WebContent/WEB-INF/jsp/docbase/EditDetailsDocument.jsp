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
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditDetailsDocumentForm" action="${editDetailsDocumentURL}" method="post" cssClass="edit">
	<fieldset>
			<legend><b>DOCUMENT DETAILS</b></legend>
			<div>
				<form:label id="entryIdLabel" for="entryId" path="entryId" cssErrorClass="error">Doc ID ${command.entryId}</form:label>
				<form:label id="volumeLabel" for="volume" path="volume" cssErrorClass="error">Volume (MDP)</form:label>
				<security:authorize ifNotGranted="ROLE_ADMINISTRATORS">
					<form:input id="volume" disabled="true" path="volume" cssClass="input_5c"  maxlength="5"/>
				</security:authorize> 
				<security:authorize ifNotGranted="ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
					<form:input id="volume" path="volume" cssClass="input_5c" maxlength="5"/>
				</security:authorize>
			</div>

			<div>
				<form:label id="insertNumLabel" for="insertNum" path="insertNum" cssErrorClass="error">Insert/Part</form:label>
				<form:input id="insertNum" path="insertNum" class="input_5c" />
				<form:input id="insertLet" path="insertLet" class="input_5c" />
			</div>
			
			<div>
				<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error">Document starts at folio</form:label>
				<form:input id="folioNum" path="folioNum" class="input_5c" />
				<form:input id="folioMod" path="folioMod" class="input_5c" />
			</div>

			<div>	
				<form:label id="unpagedLabel" for="unpaged" path="unpaged" cssErrorClass="error">Unpaginated</form:label>
				<form:checkbox id="unpaged" path="unpaged" class="checkboxPers2"/>
				<form:label id="contDiscLabel" for="contDisc" path="contDisc" cssErrorClass="error">Disc. Cont'd</form:label>
				<form:checkbox id="contDisc" path="contDisc" class="checkboxPers1"/>
			</div>
			
			<hr />

			<div>
				<form:label id="docTypologyLabel" for="docTypology"  path="docTypology" cssErrorClass="error">Document Typology (other than letter)</form:label>
				<form:input id="docTypology" path="docTypology" class="input_45c"/>
			</div>
			
			<hr />
		
			<div>
				<b>Date:</b>
				<br />
				<form:label id="DocYearLabel" for="docYear" path="docYear" cssErrorClass="error">Year</form:label>
				<form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/>
				<form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum" cssErrorClass="error">Month</form:label>
				<form:select id="docMonthNum" path="docMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
				<form:label  for="docDay" id="docDayLabel" path="docDay" cssErrorClass="error">Day</form:label>
				<form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/>
			</div>
			
			<div>
				<form:label id="yearModernLabel" for="yearModern" path="yearModern" cssErrorClass="error">Modern dating</form:label>
				<form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/>
			</div>
			
			<br />
			<div>
				<form:label  id="dateUnsLabel" for="dateUns" path="dateUns">Date Uncertain or Approximate?</form:label>
				<form:checkbox id="dateUns" path="dateUns" class="checkboxDoc2"/>
				<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated">Undated</form:label>
				<form:checkbox  id="dateUndated" path="dateUndated" class="checkboxDoc2"/>
			</div>
			
			<hr />
			
			<div>
				<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes">Date notes</form:label>
			</div>
			<div>
				<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
			</div>
			
			<form:hidden id="transcribeFolioNum" path="transcribeFolioNum" />
			<form:hidden id="transcribeFolioMod" path="transcribeFolioMod" />
			<form:hidden id="dateCreated" path="dateCreated" />
			<form:hidden id="entryId" path="entryId" />

			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
			<input type="hidden" value="" id="modify" />
		</fieldset>	
		<input type="hidden" name="summaryId" value="${document.volume.summaryId}">

	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do"/>
	
	<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do" />
	
	<c:url var="ShowDocumentAlreadyURL" value="/src/docbase/ShowDocument.do" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditDetailsDocumentForm");
			
	        $j("#EditCorrespondentsDocument").css('visibility', 'hidden');
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        $j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
	        
	        
	        $j("#EditDetailsDocumentForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});

			var showVolumeExplorer = function (){
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $j("#volume").val() },
					function(data){
						if (data.summaryId == "") {
							if ($j("#volNotExist").length == 0) {
								$j("#close").before("<span class=\"inputerrorsVolumeNotExist\" id=\"volNotExist\" style=\"color:red\">Volume is not present, you cannot create this document. Save is disabled.<br></span>");
							}
							$j("#save").attr("disabled","true");
						} else {
							if ($j("#volNotExist").length > 0) {
								$j("#volNotExist").remove();
							}
							$j("#save").removeAttr("disabled");
							
							var tabName = "Volume Explorer " + data.volNum + data.volLetExt + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab"
	            			var showVolumeExplorer = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
	                    	$j("#tabs").tabs("add", "" + showVolumeExplorer, tabName);
	                    	$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
	                    	
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
			$j("#volume").change(showVolumeExplorer);
			
			var alreadyDigitized = function(){
				$j.get('<c:url value="/src/docbase/FindDocument.json" />', { volNum: $j("#volume").val(), folioNum: $j("#folioNum").val(), folioMod: $j("#folioMod").val() },
						function(data){
							if (data.entryId != "") {
								if ($j("#alreadyDigitized").length == 0) {
										$j("#close").before("<span class=\"inputerrorsAlreadyDigitized\" id=\"alreadyDigitized\"><font color=\"#FF0000\">This document has already been digitized. Click <a class=\"compareDoc\" style=\"color:red\" href=\"${ShowDocumentAlreadyURL}?entryId=" + data.entryId +  "\"><u>here</u></a> to view.<br></font></span>");
										$j('.compareDoc').click(function(){
											var tabName = "Doc " + $j("#volume").val() + "/" + $j("#folioNum").val();
											var numTab = 0;
											
											//Check if already exist a tab with this person
// 											var tabExist = false;
// 											$j("#tabs ul li a").each(function(){
// 												if(!tabExist)
// 													numTab++;
// 												if(this.text == tabName){
// 													tabExist = true;
// 												}
// 											});
											
// 											if(!tabExist){
// 												$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
// 												$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
// 												return false;
// 											}else{
// 												$j("#tabs").tabs("select", numTab-1);
// 												return false;
// 											}
											$j.ajax({ url: '${ShowDocumentAlreadyURL}?entryId=' + data.entryId, cache: false, success:function(html) { 
												$j("#body_left").html(html);
											}});
											return false;
										});
								}
								$j("#save").attr("disabled","true");
								return data.entryId;
							} else {
								if ($j("#alreadyDigitized").length > 0) {
									$j("#alreadyDigitized").remove();
								}
								$j("#save").removeAttr("disabled");
								
								return data.entryId
								
// 								var tabName = "Volume Explorer " + data.volNum + data.volLetExt + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab"
// 		            			var showVolumeExplorer = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
// 		                    	$j("#tabs").tabs("add", "" + showVolumeExplorer, tabName);
// 		                    	$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
		                    	
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
			$j("#folioNum").change(alreadyDigitized);
			$j("#folioMod").change(alreadyDigitized);
			
			var folioNotExist = function (){
				if($j("#volume").val() != ""){
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $j("#volume").val() },
					function(data){
						if (data.folioCount != "") {
							if ($j("#folioNotExist").length == 0) {
								if(parseInt($j("#folioNum").val(),10) > parseInt(data.folioCount,10)){
									$j("#close").before("<span class=\"inputerrorsFolioNotExist\" id=\"folioNotExist\">This folio number is higher than the folio count total of the volume. Save is disabled.<br></span>");
									$j("#save").attr("disabled","true");
								}
							}else {
								if ($j("#folioNotExist").length > 0 && parseInt(data.folioCount,10) >= parseInt($j("#folioNum").val(),10)) {
									$j("#folioNotExist").remove();
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
			
			/*$j("#folioNum").change(folioNotExist);
			$j("#folioMod").change(folioNotExist);*/

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
	        	if($j("#modify").val() == 1){
	        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j('#EditDetailsDocumentForm').block({ message: $j('#question') }); 
					return false;
	        	}else{
	        		$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
	    				$j("#body_left").html(html);
	    			}});
	    				
	    			return false; 
	        	}	        		
			});
	        
	        $j('.compareDoc').click(function(){
				var tabName = "Doc " + $j("#volume").val() + "/" + $j("#folioNum").val();
				var numTab = 0;
				
				<%-- Check if already exist a tab with this person --%>
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
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
			// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
			$j("#EditDetailsDocumentForm").append($j("#question"));
			$j(".blockUI").remove();
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