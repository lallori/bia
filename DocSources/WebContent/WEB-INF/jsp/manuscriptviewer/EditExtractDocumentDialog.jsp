<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentUrl" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}"></c:param>
	</c:url>

	<div id="EditExtractDocumentDiv">
	<form:form id="EditExtractDocumentForm" method="post" cssClass="edit">
		<form:textarea id="extract" path="docExtract" rows="22"/>
		<input id="saveExtract" type="image" src="<c:url value="/images/mview/saveExtract.png"/>" alt="Save Extract"/>
		<input id="saveAndEditSynopsis" type="image" src="<c:url value="/images/mview/saveAndEditSynopsis.png"/>" alt="Save and edit Synopsis"/>
		<form:hidden path="entryId"/>
		<form:hidden path="synExtrId" />
	</form:form>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#saveExtract").click(function (){
				if (extractChanged) {
					$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentUrl}');
						} 
					});
				}
				return false;
			});
			$j("#saveAndEditSynopsis").click(function (){
				if (extractChanged) {
						$j.ajax({ type:"POST", url:$j("#EditExtractDocumentForm").attr("action"), data:$j("#EditExtractDocumentForm").serialize(), async:false, success:function(html) { 
							$j("#synopsis").focus();
							$j("#EditExtractDocumentDiv").html(html);
							extractChanged=false;
							window.opener.$j("#body_left").load('${ShowDocumentUrl}');
							} 
						});
				}
				return false;
			});

			$j("#extract").change(function(){
				extractChanged=true;
			});
		});
	</script>

