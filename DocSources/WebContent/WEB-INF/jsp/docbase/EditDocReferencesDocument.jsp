<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
		
		<c:url var="AddDocReferenceURL" value="/de/docbase/EditDocReferenceDocument.do">
			<c:param name="entryIdFrom"   value="${command.entryId}" />
			<c:param name="docReferenceId"  value="0" />
		</c:url>
	</security:authorize>
	
	<form:form id="DocReferenceDocumentsForm" method="post" cssClass="edit">
		<fieldset>	
			<legend><b>DOCUMENTS REFERRED TO</b></legend>
			<p>Documents referred to:</p>
		
			<div class="row">
				<c:forEach items="${document.docReference}" var="currentDocument">
					<c:url var="DeletePersonDocumentURL" value="/de/docbase/DeletePersonDocument.do" >
						<c:param name="docReferenceId" value="${currentDocument.docReferenceId}" />
					</c:url>
					<div class="col_l"><input id="document_${currentDocument.docReferenceId}" name="document" class="input_35c_disabled" type="text" value="${currentDocument.document.entryId}" disabled="disabled"/></div>
					<div class="col_l"><a class="deleteIcon" title="Delete this entry" href="${DeletePersonDocumentURL}"></a></div>
				</c:forEach>
			</div>
			<div>
				<input id="close" type="submit" value="Close" title="Do not save changes" class="closeForm"/>
				<input id="AddNewValue" type="submit" value="Add" title="Add new Document" />
			</div>
		</fieldset>
	</form:form>
	
	
	<div id="EditDocReferenceDocumentDiv"></div>

	<script type="text/javascript"> 
	    $j(document).ready(function() { 
	    	//$j.scrollTo("#EditPersonDocumentForm");
	    	$j("#AddNewValue").click(function(){
				$j("#EditDocReferenceDocumentDiv").load("${AddDocReferenceURL}");
				return false;
			});
	    	
	    	$j('.closeForm').click(function() {
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
	    	
	    	$j(".linkDocument").live('click', function() {
				var tabName = $j(this).attr("title");
				var numTab = 0;
				var id = $j(this).attr("id");
				
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + $j(this).attr("id") + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
	    	});
		});					  
	</script>

<div id="questionDocReference" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yesDocReference" value="Yes" /> 
	<input type="button" id="noDocReference" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#noDocReference').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditDocReferenceDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yesDocReference').click(function() { 
			$j("#EditDocReferenceDocumentDiv").html('');				
				
			return false; 
		}); 
     
	});
</script>