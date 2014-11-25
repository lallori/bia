<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditDetailsSchedoneURL" value="/digitization/EditDetailsSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditDetailsSchedoneDiv" class="background">
	<div class="title">
		<h5><fmt:message key="digitization.showDetailsSchedone.sChedoneDetails"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
			<a id="EditDetailsSchedone" href="${EditDetailsSchedoneURL}" class="editButton" title="Edit Schedone details"></a><span id="loading"/>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.istituto"/></div> 
			<div class="value60">${schedone.istituto}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.fondo"/></div> 
			<div class="value60">${schedone.fondo}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.serie"/></div> 
			<div class="value60">${schedone.serie}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.nUnita"/></div>
			<div class="value60">${schedone.numeroUnita} ${schedone.volLetExt}</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.dateEstreme"/></div> 
			<div class="value60">${schedone.dataInizioAnno} ${schedone.dataInizioMese} ${schedone.dataInizioGiorno} - ${schedone.dataFineAnno} ${schedone.dataFineMese} ${schedone.dataFineGiorno}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.descrizioneContenuto"/></div> 
			<div class="value60">${schedone.descrizioneContenuto}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.contentDescription"/></div>
			<div class="value60">${schedone.descrizioneContenutoEng}</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.legatura"/></div> 
			<div class="value60">${schedone.legatura}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.supporto"/></div> 
			<div class="value60">${schedone.supporto}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.cart"/></div> 
			<div class="value60">${schedone.cartulazione}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.noteAlla"/></div>
			<div class="value60">${schedone.noteCartulazione}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.numerationNotes"/></div>
			<div class="value60">${schedone.noteCartulazioneEng}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.carteBianche"/></div>
			<div class="value60">${schedone.carteBianche}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.carteMancanti"/></div>
			<div class="value60">${schedone.carteMancanti}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.dimensioniBase"/></div>
			<div class="value60">${schedone.dimensioniBase} <fmt:message key="digitization.showDetailsSchedone.mm"/></div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.dimensioniAltezza"/></div>
			<div class="value60">${schedone.dimensioniAltezza} <fmt:message key="digitization.showDetailsSchedone.mm"/></div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.digitizationType"/></div>
			<div class="value60">${schedone.tipoRipresa}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.schemaColoreImmagini"/></div>
			<div class="value60">${schedone.coloreImmagine}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.imageResolution"/></div>
			<div class="value60">${schedone.risoluzione}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.nomeFiles"/></div>
			<div class="value60">${schedone.nomeFiles}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.responsabileFoto"/></div>
			<div class="value60">${schedone.responsabileFotoRiproduzione}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.dataRipresa"/></div>
			<div class="value60">${schedone.dataRipresaAnno} ${schedone.dataRipresaMese} ${schedone.dataRipresaGiorno}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="digitization.showDetailsSchedone.operatore"/></div>
			<div class="value60">${schedone.operatore}</div>
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

			$j("#EditDetailsSchedone").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsSchedoneDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>