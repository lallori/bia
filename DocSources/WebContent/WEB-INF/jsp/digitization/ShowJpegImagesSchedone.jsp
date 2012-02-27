<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditJpegImagesSchedoneURL" value="/digitization/EditJpegImagesSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditJpegImagesDiv" class="background">
	<div class="title">
		<h5>JPEG IMAGES</h5>
		<security:authorize ifAnyGranted="ROLE_DIGITIZATION_USERS">
			<a id="EditJpegImagesSchedone" href="${EditJpegImagesSchedoneURL}" class="editButton" title="Edit Jpeg Images"></a><span id="loading"/>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Formato</div> 
			<div class="value60">JPEG</div> 
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
