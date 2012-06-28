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
			<div class="listForm">
				<div class="row">
					<div class="col_r"><a class="helpIcon" title="<fmt:message key="docbase.details.edit.docid"></fmt:message>">?</a><form:label id="entryIdLabel" for="entryId" path="entryId" cssErrorClass="error">Doc ID</div>
					<div class="col_l"><span class="docId">${command.entryId}</span></div></form:label>
					<div class="col_r"><form:label id="volumeLabel" for="volume" path="volume" cssErrorClass="error">Volume (MDP)</form:label></div>
					<div class="col_r"><form:input id="volume" path="volume" cssClass="input_5c" maxlength="5"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.insert"></fmt:message>">?</a>
						<form:label id="insertNumLabel" for="insertNum" path="insertNum" cssErrorClass="error">Insert</form:label>
					</div>
					<div class="col_l">
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:input id="insertNum" path="insertNum" class="input_5c" />
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:input id="insertNum" path="insertNum" class="input_4c_disabled" disabled="true" />
						</c:if>
					</div>
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.part"></fmt:message>">?</a>
						<form:label id="insertLetLabel" for="insertLet" path="insertLet" cssErrorClass="error">Part</form:label>
					</div>
					<div class="col_r">
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:input id="insertLet" path="insertLet" class="input_5c" />
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:input id="insertLet" path="insertLet" class="input_4c_disabled" disabled="true" />
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.documentstartsatfolio"></fmt:message>">?</a>
						<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error">Document starts at folio</form:label>
					</div>
					<div class="col_l"><form:input id="folioNum" path="folioNum" class="input_5c" /></div>
					<div class="col_r"><a class="helpIcon" title="<fmt:message key="docbase.details.edit.iffolioaddenda"></fmt:message>">?</a><form:label id="folioModLabel" for="folioMod" path="folioMod" cssErrorClass="error">If folio addenda</form:label></div>
					<div class="col_r"><form:input id="folioMod" path="folioMod" class="input_5c" /></div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.unpaginated"></fmt:message>">?</a>
						<form:label id="unpagedLabel" for="unpaged" path="unpaged" cssErrorClass="error">Unpaginated</form:label>
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:checkbox id="unpaged" path="unpaged"/>
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:checkbox id="unpaged" path="unpaged" disabled="true"/>
						</c:if>
					</div>
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.nonconsecutive"></fmt:message>">?</a>
						<form:label id="contDiscLabel" for="contDisc" path="contDisc" cssErrorClass="error">Nonconsecutive</form:label>
						<form:checkbox id="contDisc" path="contDisc"/>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.details.edit.documenttypology"></fmt:message>">?</a>
					<form:label id="docTypologyLabel" for="docTypology"  path="docTypology" cssErrorClass="error">Document Typology (other than letter)</form:label>
					<form:input id="docTypology" path="docTypology" class="input_45c"/>
				</div>
			</div>

			<hr />
			
			<div class="listForm">
				<b>Date:</b>
				<br />
				<div class="row">
					<div class="col_r"><a class="helpIcon" title="<fmt:message key="docbase.details.edit.date"></fmt:message>">?</a><form:label id="DocYearLabel" for="docYear" path="docYear" cssErrorClass="error">Year</form:label></div>
					<div class="col_l"><form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/></div>
					<div class="col_r"><form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum" cssErrorClass="error">Month</form:label></div>
					<div class="col_l"><form:select id="docMonthNum" path="docMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
					<div class="col_r"><form:label  for="docDay" id="docDayLabel" path="docDay" cssErrorClass="error">Day</form:label></div>
					<div class="col_r"><form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="docbase.details.edit.moderndating"></fmt:message>">?</a>
						<form:label id="yearModernLabel" for="yearModern" path="yearModern" cssErrorClass="error">Modern dating</form:label>
					</div>
					<div class="col_l"><form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/></div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<form:label  id="dateUnsLabel" for="dateUns" path="dateUns">Date Uncertain or Approximate?</form:label>
						<form:checkbox id="dateUns" path="dateUns"/>
					</div>
					<div class="col_r">
						<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated">Undated</form:label>
						<form:checkbox  id="dateUndated" path="dateUndated"/>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.details.edit.datenotes"></fmt:message>">?</a>
					<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes">Date notes</form:label>
					<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
				</div>
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
		<form:hidden id="folioNumStored" path="folioNum" />

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
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        
	        $j("#EditDetailsDocumentForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
	        
	        <%-- We disable editing an already entered document volume number if not Administrator  --%>
	        <security:authorize ifNotGranted="ROLE_ADMINISTRATORS">
				<c:if test="${command.entryId != 0}"> 
					$j("#volume").attr("disabled","true");
    			</c:if>
			</security:authorize>

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
							
							if(data.volumeDigitized){
								var tabName = "Volume Explorer " + data.volNum + data.volLetExt + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab"
	            				var showVolumeExplorer = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
	                    		$j("#tabs").tabs("add", "" + showVolumeExplorer, tabName);
	                    		$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
	                    	
	                    	<%-- $j.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { summaryId: data.summaryId, flashVersion : false },
								function(data){
									$j("#body_right").html(data);
									return true;
								}
							); --%>
							}
						}
					}
				);
	 		}
			$j("#volume").change(showVolumeExplorer);
			
			var alreadyDigitized = function(){
				if($j("#folioNum").val() == $j("#folioNumStored").val()){
					if ($j("#alreadyDigitized").length > 0) {
						$j("#alreadyDigitized").remove();
					}
					$j("#save").removeAttr("disabled");
					return "";
				}
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
								
								return data.entryId;
								
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
			};
			$j("#folioNum").keyup(alreadyDigitized);
			$j("#folioMod").keyup(alreadyDigitized);
			$j("#folioNum").focus(function(){
				if ($j("#alreadyDigitized").length > 0) {
					$j("#alreadyDigitized").remove();
				}
				$j("#save").removeAttr("disabled");
			});
			
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
//  				var error = alreadyDigitized();
//  				alert(error);
//  				if(error != ""){
//  					return false;
//  				}
				$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsDocumentDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.entryId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#body_left").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
				return false;
			});
			
			

	        $j('#close').click(function() {
	        	if($j("#modify").val() == 1){
	        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j('#EditDetailsDocumentForm').block({ message: $j('#question'), 
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					}); 
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
	<h1>Discard changes?</h1> 
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