<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}"></c:param>
	</c:url>

	<div id="EditSynopsisDocumentDiv">
		<form:form id="EditSynopsisDocumentForm" method="post" cssClass="edit">
			<form:textarea id="synopsis" path="synopsis" rows="22"/>
			<input id="saveSynopsis" type="submit" value="Save Synopsis"/>
			<input id="saveSynopsisExit" type="submit" value="Save Synopsis and Exit"/>
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId" />
		</form:form>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditSynopsisDocumentDiv").dialog("option" , "position" , [$j("#EditExtractDocumentDiv").dialog("option" , "width") + 8 , "middle"]);
			
			$j("#saveSynopsis").click(function (){
				if (synopsisChanged) {
					$j.ajax({ type:"POST", url:$j("#EditSynopsisDocumentForm").attr("action"), data:$j("#EditSynopsisDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditSynopsisDocumentDiv").html(html);
							synopsisChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
						} 
					});
				}
				return false;
			});
			$j("#saveSynopsisExit").click(function (){
				if (synopsisChanged) {
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
		});
	</script>