<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditSchedonePropertiesURL" value="/admin/EditSchedoneProperties.do" />

<div id="EditSchedonePropertiesDiv" class="background">
	<div class="title">
		<h5>SCHEDONE</h5>
		<a id="EditSchedoneProperties" class="editButton" href="${EditSchedonePropertiesURL}" title="Edit Schedone Properties"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Istituto</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.istituto')}</div> 
		</div>
        <div class="row">
			<div class="item">Fondo</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.fondo')}</div> 
		</div>
		<div class="row">
			<div class="item">Legatura</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.legatura')}</div> 
		</div>
		<div class="row">
			<div class="item">Supporto</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.supporto')}</div> 
		</div>
		<div class="row">
			<div class="item">Digitization Type</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.tipoRipresa')}</div> 
		</div>
		<div class="row">
			<div class="item">Schema colore immagini</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.coloreImmagine')}</div> 
		</div>
		<div class="row">
			<div class="item">Nome files</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.nomeFiles')}</div> 
		</div>
		<div class="row">
			<div class="item">Responsabile fotoriproduzione</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.responsabileFotoRiproduzione')}</div> 
		</div>
		<div class="row">
			<div class="item">Operatore</div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.operatore')}</div> 
		</div>
	</div>
</div>
<br />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		$j("#EditSchedoneProperties").css('visibility', 'visible');
		
		$j("#EditSchedoneProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditSchedonePropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	});
</script>

</security:authorize>