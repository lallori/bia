<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditTiffImagesSchedoneURL" value="/digitization/EditTiffImagesSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditTiffImagesSchedoneDiv" class="background">
	<div class="title">
		<h5><fmt:message key="digitization.showTiffImagesSchedone.tIffImages"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${schedone.schedoneId > 0}">
			<a id="EditTiffImagesSchedone" href="${EditTiffImagesSchedoneURL}" class="editButton" title="Edit Tiff Images"></a><span id="loading"/>
			</c:if>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="digitization.showTiffImagesSchedone.formato"/></div> 
			<div class="value60"><fmt:message key="digitization.showTiffImagesSchedone.tIff"/></div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showTiffImagesSchedone.compressione"/></div> 
			<div class="value60">${schedone.compressioneTiff}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showTiffImagesSchedone.numeroTotale"/></div> 
			<div class="value60">${schedone.numeroTotaleImmaginiTiff}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showTiffImagesSchedone.dimensioneMedia"/></div> 
			<div class="value60">${schedone.dimMediaImmaginiTiff} ${schedone.formatoMediaImmaginiTiff}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showTiffImagesSchedone.dimensioneTotal"/></div> 
			<div class="value60">${schedone.dimTotaleImmaginiTiff} ${schedone.formatoTotaleImmaginiTiff}</div> 
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

			$j("#EditTiffImagesSchedone").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTiffImagesSchedoneDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>