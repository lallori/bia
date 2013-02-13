<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}"></c:param>
	</c:url>

	<div id="EditSynopsisDocumentDiv">
		<%-- Loading div when saving the form --%>
		<div id="loadingDiv"></div>
		<form:form id="EditSynopsisDocumentForm" method="post" cssClass="edit">
			<form:textarea id="synopsis" path="synopsis" rows="22"/>
			<input id="saveSynopsis" type="submit" class="button_small" value="Save"/>
			<input id="saveSynopsisExit" type="submit" class="button_medium" value="Save and Exit"/>
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId" />
		</form:form>
	</div>
	
	<div id="saveSynSuccess" title="Alert" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your synopsis has been saved!</p>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditExtractDocumentDiv").dialog().dialogExtend("minimize" , true);
			
			$j("#EditSynopsisDocumentDiv").dialog("option" , "position" , ["center" , "middle"]);
			
			$j("#EditSynopsisDocumentForm :input").change(function(){
				$j("#editSynopsisModify").val(1);
				return false;
			});
			
			$j("#saveSynopsis").click(function (){
				if (synopsisChanged) {
					$j("#editSynopsisModify").val(0);
					$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
					$j("#loadingDiv").css('width', $j("#loadingDiv").parent().width());
		        	$j("#loadingDiv").css('visibility', 'visible');
					$j.ajax({ type:"POST", url:$j("#EditSynopsisDocumentForm").attr("action"), data:$j("#EditSynopsisDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditSynopsisDocumentDiv").html(html);
							synopsisChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
							$j("#saveSynSuccess").dialog("open");
						} 
					});
				}
				return false;
			});
			$j("#saveSynopsisExit").click(function (){
				if (synopsisChanged) {
					$j("#editSynopsisModify").val(0);
					if($j("#editExtractModify").val() == 1){
						$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
						} 
					});
					}
					$j.ajax({ type:"POST", url:$j("#EditSynopsisDocumentForm").attr("action"), data:$j("#EditSynopsisDocumentForm").serialize(), async:false, success:function(html) { 
						$j("#EditSynopsisDocumentDiv").html(html);
						synopsisChanged=false;
						window.opener.$j("#body_left").load('${ShowDocumentURL}');
						window.close();
					}
					});
				} else {
					window.close();
				}
				
				return false;
			});
			
			$j("#synopsis").change(function(){
				synopsisChanged=true;
			});
			
			$j("#saveSynSuccess").dialog({
				resizable: false,
				height:150,
				modal: true,
				autoOpen : false,
				title: 'SYNOPSIS SAVED',
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