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
		<h5>SCHEDONE DETAILS</h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
			<a id="EditDetailsSchedone" href="${EditDetailsSchedoneURL}" class="editButton" title="Edit Schedone details"></a><span id="loading"/>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Istituto</div> 
			<div class="value60">${schedone.istituto}</div> 
		</div>
        <div class="row">
			<div class="item">Fondo</div> 
			<div class="value60">${schedone.fondo}</div> 
		</div>
        <div class="row">
			<div class="item">Serie</div> 
			<div class="value60">${schedone.serie}</div> 
		</div>
		<div class="row">
			<div class="item">N. Unità</div>
			<div class="value60">${schedone.numeroUnita} ${schedone.volLetExt}</div>
		</div>
        <div class="row">
			<div class="item">Date estreme</div> 
			<div class="value60">${schedone.dataInizioAnno} ${schedone.dataInizioMese} ${schedone.dataInizioGiorno} - ${schedone.dataFineAnno} ${schedone.dataFineMese} ${schedone.dataFineGiorno}</div> 
		</div>
        <div class="row">
			<div class="item">Descrizione contenuto</div> 
			<div class="value60">${schedone.descrizioneContenuto}</div> 
		</div>
		<div class="row">
			<div class="item">Content description</div>
			<div class="value60">${schedone.descrizioneContenutoEng}</div>
		</div>
        <div class="row">
			<div class="item">Legatura</div> 
			<div class="value60">${schedone.legatura}</div> 
		</div>
        <div class="row">
			<div class="item">Supporto</div> 
			<div class="value60">${schedone.supporto}</div> 
		</div>
        <div class="row">
			<div class="item">Cartulazione</div> 
			<div class="value60">${schedone.cartulazione}</div> 
		</div>
		<div class="row">
			<div class="item">Note alla Cartulazione</div>
			<div class="value60">${schedone.noteCartulazione}</div>
		</div>
		<div class="row">
			<div class="item">Notes for the Cartulazione</div>
			<div class="value60">${schedone.noteCartulazioneEng}</div>
		</div>
		<div class="row">
			<div class="item">Carte bianche</div>
			<div class="value60">${schedone.carteBianche}</div>
		</div>
		<div class="row">
			<div class="item">Carte Mancanti</div>
			<div class="value60">${schedone.carteMancanti}</div>
		</div>
		<div class="row">
			<div class="item">Dimensioni Base</div>
			<div class="value60">${schedone.dimensioniBase}</div>
		</div>
		<div class="row">
			<div class="item">Dimensioni Altezza</div>
			<div class="value60">${schedone.dimensioniAltezza}</div>
		</div>
		<div class="row">
			<div class="item">Digitization Type</div>
			<div class="value60">${schedone.tipoRipresa}</div>
		</div>
		<div class="row">
			<div class="item">Schema colore immagini</div>
			<div class="value60">${schedone.coloreImmagine}</div>
		</div>
		<div class="row">
			<div class="item">Image Resolution</div>
			<div class="value60">${schedone.risoluzione}</div>
		</div>
		<div class="row">
			<div class="item">Nome files</div>
			<div class="value60">${schedone.nomeFiles}</div>
		</div>
		<div class="row">
			<div class="item">Responsabile Fotoriproduzione</div>
			<div class="value60">${schedone.responsabileFotoRiproduzione}</div>
		</div>
		<div class="row">
			<div class="item">Da ripresa</div>
			<div class="value60">${schedone.dataRipresaAnno} ${schedone.dataRipresaMese} ${schedone.dataRipresaGiorno}</div>
		</div>
		<div class="row">
			<div class="item">Operatore</div>
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