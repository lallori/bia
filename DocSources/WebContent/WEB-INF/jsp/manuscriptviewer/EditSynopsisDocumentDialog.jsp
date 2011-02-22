<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditSynopsisDocumentForm" method="post" cssClass="edit">
		<form:textarea id="synopsis" path="synopsis" cssClass="txtarea" rows="20" style="width: 96%; height: 96%;"/>
		<div>
			<input id="saveSynopsis" type="image" src="<c:url value="/images/saveSynopsis.png" />" alt="Save Synopsis"/>
			<input id="saveSynopsisExit" type="image" src="<c:url value="/images/saveSynopsisExit.png" />" alt="Save Synopsis and Exit"/>
		</div>
		<form:hidden path="entryId"/>
		<form:hidden path="synExtrId" />
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#saveSynopsis").click(function (){
				$j.ajax({ type:"POST", url:$j("#EditSynopsisDocumentForm").attr("action"), data:$j("#EditSynopsisDocumentForm").serialize(), async:false, success:function(html) { 
						$j("#EditExtractDocumentDiv").html(html);
					} 
				});
				return false;
			});
			$j("#saveAndEditSynopsis").click(function (){
				$j.ajax({ type:"POST", url:$j("#EditSynopsisDocumentForm").attr("action"), data:$j("#EditSynopsisDocumentForm").serialize(), async:false, success:function(html) { 
					$j("#EditExtractDocumentDiv").html(html);
						if(html.match(/inputerrors/g)){
							$j("#EditSynopsisDocumentDiv").html(html);
						} else {
							$j("#EditSynopsisDocumentDiv").html(html);
							window.close();
						}
					}
				});
				return false;
			});
		});
	</script>