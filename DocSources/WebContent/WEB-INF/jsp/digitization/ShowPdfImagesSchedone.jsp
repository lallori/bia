<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditPdfImagesSchedoneURL" value="/digitization/EditPdfImagesSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditPdfImagesSchedoneDiv" class="background">
	<div class="title">
		<h5>PDF IMAGES</h5>
		<security:authorize ifAnyGranted="ROLE_DIGITIZATION_USERS">
			<c:if test="${schedone.schedoneId > 0}">
			<a id="EditPdfImagesSchedone" href="${EditPdfImagesSchedoneURL}" class="editButton" title="Edit Pdf Images"></a><span id="loading"/>
			</c:if>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Formato</div> 
			<div class="value60">PDF</div> 
		</div>
        <div class="row">
			<div class="item">Compressione</div> 
			<div class="value60">1:1</div> 
		</div>
        <div class="row">
			<div class="item">Numero totale immagini</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Dimensione media immagini</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Dimensione totale immagini</div> 
			<div class="value60"></div> 
		</div>
	</div>
</div>

<security:authorize ifAnyGranted="ROLE_DIGITIZATION_USERS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsSchedone").css('visibility', 'visible');
	        $j("#EditTiffImagesSchedone").css('visibility', 'visible'); 
	        $j("#EditJpegImagesSchedone").css('visibility', 'visible'); 
	        $j("#EditPdfImagesSchedone").css('visibility', 'visible'); 

			$j("#EditPdfImagesSchedone").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditPdfImagesSchedoneDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>