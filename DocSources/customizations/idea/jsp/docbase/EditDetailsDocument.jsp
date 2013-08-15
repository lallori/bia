<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="editDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do"/>
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditDetailsDocumentForm" action="${editDetailsDocumentURL}" method="post" cssClass="edit">
	<fieldset>
			<legend><b><fmt:message key="docbase.editDetailsDocument.title.documentDetails"/></b></legend>
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.docid"/>'>?</a>
						<form:label id="entryIdLabel" for="entryId" path="entryId" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.docId"/></form:label>
					</div>
					<div class="col_l"><span class="docId">${command.entryId}</span></div>
					<div class="col_r"><form:label id="volumeLabel" for="volume" path="volume" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.volumeMdp"/></form:label></div>
					<div class="col_r"><form:input id="volume" path="volume" cssClass="input_5c" maxlength="5"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.insert"/>'>?</a>
						<form:label id="insertNumLabel" for="insertNum" path="insertNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.insert"/></form:label>
					</div>
					<div class="col_l">
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:input id="insertNum" path="insertNum" class="input_5c" />
							<select id="insertType" name="insertType" class="selectform_medium2">
			                    <option value="libro">libro</option>
			                    <option value="fascicolo">fascicolo</option>
			                </select>
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:input id="insertNum" path="insertNum" class="input_4c_disabled" disabled="true" />
						</c:if>
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.part"/>'>?</a>
						<form:label id="insertLetLabel" for="insertLet" path="insertLet" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.part"/></form:label>
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
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documentstartsatfolio"/>'>?</a>
						<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.documentStartsAtFolio"/></form:label>
					</div>
					<div class="col_l"><form:input id="folioNum" path="folioNum" class="input_5c" /></div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.iffolioaddenda"/>'>?</a>
						<form:label id="folioModLabel" for="folioMod" path="folioMod" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.ifFolioAddenda"/></form:label>
					</div>
					<div class="col_r"><form:input id="folioMod" path="folioMod" class="input_5c" /></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documentfoliorectoverso"/>'>?</a>
						<form:label id="folioRectoVersoLabel" for="folioRectoVerso" path="folioRectoVerso" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.folioRectoVerso"/></form:label>
					</div>
					<div class="col_l">
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:input id="folioRectoVerso" path="folioRectoVerso" class="input_2c" maxlength="1" />
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:input id="folioRectoVerso" path="folioRectoVerso" class="input_4c_disabled" disabled="true" />
						</c:if>
					</div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.unpaginated"/>'>?</a>
						<form:label id="unpagedLabel" for="unpaged" path="unpaged" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.unpaginated"/></form:label>
						<c:if test="${fromTranscribe == null || !fromTranscribe}">
							<form:checkbox id="unpaged" path="unpaged"/>
						</c:if>
						<c:if test="${fromTranscribe != null && fromTranscribe}">
							<form:checkbox id="unpaged" path="unpaged" disabled="true"/>
						</c:if>
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.nonconsecutive"/>'>?</a>
						<form:label id="contDiscLabel" for="contDisc" path="contDisc" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.nonconsecutive"/></form:label>
						<form:checkbox id="contDisc" path="contDisc"/>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documenttypology"/>'>?</a>
					<form:label id="docTypologyLabel" for="docTypology"  path="docTypology" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.documentTypology"/></form:label>
					<form:input id="docTypology" path="docTypology" class="input_45c"/>
				</div>
			</div>

			<hr />
			
			<div class="listForm">
				<b><fmt:message key="docbase.editDetailsDocument.date"/>:</b>
				<br />
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.date"/>'>?</a>
						<form:label id="DocYearLabel" for="docYear" path="docYear" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.year"/></form:label>
					</div>
					<div class="col_l"><form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/></div>
					<div class="col_r"><form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.month"/></form:label></div>
					<div class="col_l"><form:select id="docMonthNum" path="docMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
					<div class="col_r"><form:label  for="docDay" id="docDayLabel" path="docDay" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.day"/></form:label></div>
					<div class="col_r"><form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.moderndating"/>'>?</a>
						<form:label id="yearModernLabel" for="yearModern" path="yearModern" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.modernDating"/></form:label>
					</div>
					<div class="col_l"><form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/></div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<form:label  id="dateUnsLabel" for="dateUns" path="dateUns"><fmt:message key="docbase.editDetailsDocument.dateUncertain"/></form:label>
						<form:checkbox id="dateUns" path="dateUns"/>
					</div>
					<div class="col_r">
						<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated"><fmt:message key="docbase.editDetailsDocument.dateUndated"/></form:label>
						<form:checkbox  id="dateUndated" path="dateUndated"/>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.datenotes"/>'>?</a>
					<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes"><fmt:message key="docbase.editDetailsDocument.dateNotes"/></form:label>
					<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
				</div>
			</div>
			
			<form:hidden id="transcribeFolioNum" path="transcribeFolioNum" />
			<form:hidden id="transcribeFolioMod" path="transcribeFolioMod" />
			<form:hidden id="dateCreated" path="dateCreated" />
			<form:hidden id="entryId" path="entryId" />
			
			<form:errors path="folioNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="volume" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="docYear" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="docDay" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="yearModern" cssClass="inputerrors" htmlEscape="false"/>

			<div style="margin-top:5px">
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save"/>
			</div>
			<c:if test="${fromTranscribe == null || !fromTranscribe}">
				<input type="hidden" value="" id="modify" />
			</c:if>
			<c:if test="${fromTranscribe != null && fromTranscribe}">
				<input type="hidden" value="1" id="modify" />
				<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
					<c:param name="volNum"	value="${document.volume.volNum}" />
					<c:param name="volLetExt"	value="${document.volume.volLetExt}" />
					<c:param name="imageOrder"	value="${command.imageOrder}" />
					<c:param name="total"	value="${command.total}" />
					<c:param name="totalRubricario"	value="${command.totalRubricario}" />
					<c:param name="totalCarta"	value="${command.totalCarta}" />
					<c:param name="totalAppendix"	value="${command.totalAppendix}" />
					<c:param name="totalGuardia"	value="${command.totalGuardia}" />
					<c:param name="totalOther"	value="${command.totalOther}" />
				</c:url>
				<input type="hidden" id="returnToManuscriptViewer" value="${ShowDocumentInManuscriptViewerURL}" />
			</c:if>
		</fieldset>	
		<input type="hidden" name="summaryId" value="${document.volume.summaryId}">
		<input type="hidden" id="folioNumStored" value=""/>

	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do"/>
	
	<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do" />
	
	<c:url var="ShowDocumentAlreadyURL" value="/src/docbase/CompareDocument.do" />
	
	<c:url var="ShowDocumentsAlreadyURL" value="/src/docbase/ShowSameFolioDocuments.do" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditDetailsDocumentForm");
			
			$j("#folioNumStored").val($j("#folioNum").val());
			
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
								$j("#volume\\.errors").remove();
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
			
			//MD: If we check on document ready
// 			 $j("#EditDetailsDocumentForm :input").keyup(showVolumeExplorer);
			
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
							//MD: In this case we have a document with the same volume and folio and the link open in a tab its record.
							if (data.countAlreadyEntered == 1) {
								if ($j("#alreadyDigitized").length == 0) {
										$j("#close").before("<span class=\"inputerrorsAlreadyDigitized\" id=\"alreadyDigitized\"><font color=\"#FF0000\">A document with this 'start folio' is already present in the database. <br />Are you sure you want to create another document record ? Click <a class=\"compareDoc\" style=\"color:red\" href=\"${ShowDocumentAlreadyURL}?entryId=" + data.entryId +  "\"><u>here</u></a> to view the document already indexed.<br></font></span>");
										$j('.compareDoc').click(function(){
											var tabName = "" + $j("#volume").val() + " / " + $j("#folioNum").val();
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
											var tabExist = false;
											$j("#tabs ul li a").each(function(){
												if(!tabExist){
													if(this.text != ""){
														numTab++;
													}
												}
												if(this.text == tabName){
													tabExist = true;
												}
											});
												
											if(!tabExist){
												$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
												$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
												return false;
											}else{
												$j("#tabs").tabs("select", numTab);
												return false;
											}
											});
											return false;
										
								}
// 								$j("#save").attr("disabled","true");
								return data.entryId;
							} else if (data.countAlreadyEntered > 1){
								if ($j("#alreadyDigitized").length == 0) {
									$j("#close").before("<span class=\"inputerrorsAlreadyDigitized\" id=\"alreadyDigitized\"><font color=\"#FF0000\">More than 1 document. <br />Are you sure you want to create another document record ? Click <a class=\"compareDocs\" style=\"color:red\" href=\"${ShowDocumentsAlreadyURL}?volNum=" + data.volNum +  "&volLetExt=" + data.volLetExt + "&folioNum=" + data.folioNum + "&folioMod=" + data.folioMod + "\"><u>here</u></a> to view the dataTable.<br></font></span>");
									$j('.compareDocs').click(function(){
										var tabName = "" + $j("#volume").val() + " / " + $j("#folioNum").val() + " Documents"
										var numTab = 0;
										var tabExist = false;
										$j("#tabs ul li a").each(function(){
											if(!tabExist){
												if(this.text != ""){
													numTab++;
												}
											}
											if(this.text == tabName){
												tabExist = true;
											}
										});
											
										if(!tabExist){
											$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
											$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
											return false;
										}else{
											$j("#tabs").tabs("select", numTab);
											return false;
										}
										});
									return false;
								}
							} else {
								if ($j("#alreadyDigitized").length > 0) {
									$j("#alreadyDigitized").remove();
								}
// 								$j("#save").removeAttr("disabled");
								
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
				//MD: The next instruction is for spring to assign the volume value, else it remains null
				if($j("#volume").attr("disabled") == 'disabled'){
					$j("#volume").removeAttr("disabled");
				}
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
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
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
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
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
			if($j("#returnToManuscriptViewer").length > 0){
				window.open($j("#returnToManuscriptViewer").val(),'BIA Manuscript Viewer', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=no');
			}
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
							
			return false; 
		}); 
     	
	});
</script>