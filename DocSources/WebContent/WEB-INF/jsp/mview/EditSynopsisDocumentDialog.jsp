<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}"></c:param>
	</c:url>

	<form:form id="EditSynopsisDocumentForm" method="post" cssClass="edit">
		<form:textarea id="synopsis" path="synopsis" rows="22"/>
		<div>
			<input id="saveSynopsis" type="image" src="<c:url value="/images/mview/saveSynopsis.png" />" alt="Save Synopsis"/>
			<input id="saveSynopsisExit" type="image" src="<c:url value="/images/mview/saveSynopsisExit.png" />" alt="Save Synopsis and Exit"/>
		</div>
		<form:hidden path="entryId"/>
		<form:hidden path="synExtrId" />
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
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
						if(html.match(/inputerrors/g)){
							$j("#EditSynopsisDocumentDiv").html(html);
							synopsisChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
						} else {
							$j("#EditSynopsisDocumentDiv").html(html);
							synopsisChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentURL}');
							window.close();
						}
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