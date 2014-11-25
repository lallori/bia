<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditJpegImagesSchedoneURL" value="/digitization/EditJpegImagesSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditJpegImagesSchedoneDiv" class="background">
	<div class="title">
		<h5><fmt:message key="digitization.showJpegImagesSchedone.jPegImages"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${schedone.schedoneId > 0}">
			<a id="EditJpegImagesSchedone" href="${EditJpegImagesSchedoneURL}" class="editButton" title="Edit Jpeg Images"></a><span id="loading"/>
			</c:if>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="digitization.showJpegImagesSchedone.formato"/></div> 
			<div class="value60"><fmt:message key="digitization.showJpegImagesSchedone.jPeg"/></div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showJpegImagesSchedone.compressione"/></div> 
			<div class="value60">${schedone.compressioneJpeg}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showJpegImagesSchedone.numeroTotale"/></div> 
			<div class="value60">${schedone.numeroTotaleImmaginiJpeg}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showJpegImagesSchedone.dimensioneMedia"/></div> 
			<div class="value60">${schedone.dimMediaImmaginiJpeg} ${schedone.formatoMediaImmaginiJpeg}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showJpegImagesSchedone.dimensioneTotal"/></div> 
			<div class="value60">${schedone.dimTotaleImmaginiJpeg} ${schedone.formatoTotaleImmaginiJpeg}</div> 
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

			$j("#EditJpegImagesSchedone").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditJpegImagesSchedoneDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>