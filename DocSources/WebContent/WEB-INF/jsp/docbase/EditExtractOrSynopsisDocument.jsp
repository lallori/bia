<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
		
		<c:url var="AddDocReferenceURL" value="/de/docbase/EditDocReferenceDocument.do">
			<c:param name="entryIdFrom"   value="${command.entryId}" />
			<c:param name="docReferenceId"  value="0" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocumentURL" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
	</security:authorize>
<%-- Loading div when saving the form --%>
<div id="loadingDiv"></div>
	<form:form id="EditExtractOrSynopsisDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b><fmt:message key="docbase.editExtractOrSynopsisDocument.title.transcriptionSynopsis"/></b></legend>
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.editExtractOrSynopsisDocument.help.extract"></fmt:message>">?</a>
					<form:label for="docExtract" id="docExtractLabel" path="docExtract" cssErrorClass="error"><fmt:message key="docbase.editExtractOrSynopsisDocument.transcription"/></form:label>
				</div>
				<div class="row">
					<form:textarea id="docExtract" path="docExtract" class="txtarea_big" />
				</div>
			
				<br />
			
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.editExtractOrSynopsisDocument.help.synopsis"></fmt:message>">?</a>
					<form:label for="synopsis" path="synopsis" id="synopsisLabel" cssErrorClass="error"><fmt:message key="docbase.editExtractOrSynopsisDocument.synopsis"/></form:label>
				</div>
				<div class="row">
					<form:textarea id="synopsis" path="synopsis" class="txtarea_big" />
				</div>
			</div>
			
			<div>
				<input id="close_extract" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId"/>
			<input type="hidden" value="" id="modify" />
		</fieldset>	
	</form:form>
	<br />
	<div id="DocReferenceDocumentDiv">
		<form:form id="DocReferenceDocumentsForm" method="post" cssClass="edit">
			<fieldset>	
				<legend><b><fmt:message key="docbase.editDocReferencesDocument.title.documentsReferredTo"/></b></legend>
				<p><a class="helpIcon" title="<fmt:message key="docbase.editExtractOrSynopsisDocument.help.documentsreferred"></fmt:message>">?</a> &nbsp <fmt:message key="docbase.editDocReferencesDocument.documentsReferredTo"/>:</p>
			
				<c:forEach items="${command.document.activeDocReferences}" var="currentDocument">
					<div class="listForm">
						<div class="row">
							<c:url var="DeleteDocReferenceDocumentURL" value="/de/docbase/DeleteDocReferenceDocument.do" >
								<c:param name="docReferenceId" value="${currentDocument.docReferenceId}" />
								<c:param name="entryIdFrom"   value="${currentDocument.documentFrom.entryId}" />
							</c:url>
							<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do">
								<c:param name="entryId"   value="${currentDocument.documentTo.entryId}" />
							</c:url>
							<div class="col_l"><input id="document_${currentDocument.docReferenceId}" name="document" class="input_35c_disabled" type="text" value="#${currentDocument.documentTo.entryId}" disabled="disabled"/></div>
							<div class="col_l"><a class="deleteIcon" title="Delete this entry" href="${DeleteDocReferenceDocumentURL}"></a></div>
							<div class="col_l"><a title="Show this document record" id="${currentDocument.documentTo.volume.volNum}${currentDocument.documentTo.volume.volLetExt} / ${currentDocument.documentTo.folioNum}${currentDocument.documentTo.folioMod}" href="${CompareDocumentURL}" class="topicDescription"><input type="hidden" style="display:none;" class="tabId" value="docId${currentDocument.documentTo.entryId}" /></a></div>
						</div>
					</div>						
				</c:forEach>
					
				<div>
					<input id="close_docRef" type="submit" value="Close" title="Do not save changes" />
					<input id="AddNewValue" type="submit" value="Add" title="Add new Document" />
				</div>
			</fieldset>
		</form:form>
	</div>
	
	<div id="EditDocReferenceDocumentDiv"></div>	

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditExtractOrSynopsisDocumentForm");
			
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        $j("#EditExtractOrSynopsisDocumentForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
	        
	        $j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });

	        $j('#close_extract').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditExtractOrSynopsisDocumentDiv').block({ message: $j('#question'),
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
	        
	        $j('#close_docRef').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditExtractOrSynopsisDocumentDiv').block({ message: $j('#question'),
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

			$j("#EditExtractOrSynopsisDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						//$j("#EditExtractOrSynopsisDocumentDiv").html(html);
						$j("#body_left").load('${ShowDocumentURL}');
					} 
				});
				return false;
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditDocReferenceDocumentDiv").load("${AddDocReferenceURL}");
				return false;
			});
			
			$j(".topicDescription").click(function() {
				var tabName = $j(this).attr("id");
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					//Check if exist a tab with the same name or with the same name without id
					if(this.text == tabName || this.text == "DocId#" + id.substring(5, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("DocId#" + $j(this).find("input").val().substring(5, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "DocId#" + id.substring(5, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
	    	});
			
			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#DocReferenceDocumentDiv").block({ message: $j(".questionDocReference")});
				
				$j('.docReferenceNo').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".questionPerson").hide();
					$j("#DocReferenceDocumentDiv").append($j(".questionDocReference"));
					$j(".blockUI").remove();
					$j("#EditExtractOrSynopsisDocumentDiv").load('${EditExtractOrSynopsisDocumentURL}');
					return false; 
				}); 

				$j('.docReferenceYes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditExtractOrSynopsisDocumentDiv").load('${EditExtractOrSynopsisDocumentURL}');
						return false;
					}
					return false;
		        });
				});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<div class="questionDocReference" style="display:none; cursor: default"> 
		<h1>Delete this Reference to a Document entry?</h1> 
		<input type="button" class="docReferenceYes" value="Yes" /> 
		<input type="button" class="docReferenceNo" value="No" /> 
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
			$j("#EditExtractOrSynopsisDocumentDiv").append($j("#question"));
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