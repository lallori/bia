<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}"></c:param>
	</c:url>
	<c:url var="EditExtractOrSynopsisDocumentModalWindowURL" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
			<c:param name="modalWindow"   value="true" />
		</c:url>
	
	<c:url var="editExtractDocumentDialogURL" value="/de/mview/EditExtractDocumentDialog.do"/>

	<div id="EditExtractDocumentDiv">
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditExtractDocumentForm" method="post" action="${editExtractDocumentDialogURL}" cssClass="edit">
		<form:textarea id="extract" path="docExtract" rows="22"/>
		
		<input id="saveExtract" class="button_small" type="submit" value="Save"/>
		<input id="saveAndExit" class="button_medium" type="submit" value="Save and Exit"/>
        <input id="saveAndEditSynopsis" class="button_medium" type="submit" value="Edit Synopsis"/>
		
		<form:hidden path="entryId"/>
		<form:hidden path="synExtrId" />
	</form:form>
	</div>
	
	<div id="saveSuccess" title="Alert" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your transcription has been saved!</p>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditExtractDocumentDiv").dialog("option" , "position" , ['center', 'middle']);
			$j("#EditExtractDocumentForm :input").change(function(){
				$j("#editModify").val(1);
				return false;
			});
			
			$j("#saveExtract").click(function (){
				if (extractChanged) {
					$j("#editModify").val(0);
					$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
					$j("#loadingDiv").css('width', $j("#loadingDiv").parent().width());
		        	$j("#loadingDiv").css('visibility', 'visible');
					$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
							$j("#saveSuccess").dialog("open");
						} 
					});
				}
				return false;
			});
			
			
			$j("#saveAndExit").click(function (){
				if (extractChanged) {
					$j("#editModify").val(0);
					$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
						} 
					});
				}	
				window.close();
				return false;
			});
			
			
			$j("#saveAndEditSynopsis").click(function (){
				if (extractChanged) {
					$j("#editModify").val(0);
						$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#synopsis").focus();
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
							$j("#EditSynopsisDocumentDiv").dialog( "open" );
							} 
						});
				}
				else {
					$j("#EditSynopsisDocumentDiv").dialog( "open" );
				}
				//$j("#EditExtractDocumentDiv").dialog("option" , "position" , ['left', 'middle']);
				//$j("#EditSynopsisDocumentDiv").dialog( "open" );

				return false;
			});
			
// 			$j("#saveAndEditSynopsis").click(function (){
// 				if (extractChanged) {
// 					$j("#editModify").val(0);
// 						$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
// 							$j("#synopsis").focus();
// 							$j("#EditExtractDocumentDiv").html(html);
// 							extractChanged=false;
// 							window.opener.$j("#body_left").load('${ShowDocumentURL}');
// 							window.opener.Modalbox.show('${EditExtractOrSynopsisDocumentModalWindowURL}', {title: "Edit Synopsis", width: 850, height:550});
// 							window.close();
// 							} 
// 						});
// 				}
// 				else {
// 					window.opener.$j("#body_left").load('${ShowDocumentURL}');
// 					window.opener.Modalbox.show('${EditExtractOrSynopsisDocumentModalWindowURL}', {title: "Edit Synopsis", width: 850, height:550});
// 					window.close();
// 				}
			
// 				return false;
// 			});

			$j("#extract").change(function(){
				extractChanged=true;
			});
			
			$j("#saveSuccess").dialog({
				resizable: false,
				height:150,
				modal: true,
				autoOpen : false,
				title: 'TRANSCRIPTION SAVED',
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				buttons: {
					OK : function() {
						$j(this).dialog('close');
					}
				}
			});
						
		});
	</script>

<span id="transcribeMode" class="transcribeMessage" style="display: none;">You are transcribing from<br />Folio: ${folioNum} MDP: ${volNum}</span>
