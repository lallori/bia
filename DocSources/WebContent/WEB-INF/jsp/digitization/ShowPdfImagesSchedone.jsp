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
		<h5><fmt:message key="digitization.showPdfImagesSchedone.pDfImages"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${schedone.schedoneId > 0}">
			<a id="EditPdfImagesSchedone" href="${EditPdfImagesSchedoneURL}" class="editButton" title="Edit Pdf Images"></a><span id="loading"/>
			</c:if>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="digitization.showPdfImagesSchedone.formato"/></div> 
			<div class="value60"><fmt:message key="digitization.showPdfImagesSchedone.pDf"/></div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showPdfImagesSchedone.compressione"/></div> 
			<div class="value60">${schedone.compressionePdf}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showPdfImagesSchedone.numeroTotale"/></div> 
			<div class="value60">${schedone.numeroTotaleImmaginiPdf}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showPdfImagesSchedone.dimensioneMedia"/></div> 
			<div class="value60">${schedone.dimMediaImmaginiPdf} ${schedone.formatoMediaImmaginiPdf}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showPdfImagesSchedone.dimensioneTotal"/></div> 
			<div class="value60">${schedone.dimTotaleImmaginiPdf} ${schedone.formatoTotaleImmaginiPdf}</div> 
		</div>
	</div>
</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
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