<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditSchedonePropertiesURL" value="/admin/EditSchedoneProperties.do" />

<div id="EditSchedonePropertiesDiv" class="background">
	<div class="title">
		<h5><fmt:message key="adm.showSchedoneProperties.sChedone"/></h5>
		<a id="EditSchedoneProperties" class="editButton" href="${EditSchedonePropertiesURL}" title="Edit Schedone Properties"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.instituto"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.istituto')}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.fondo"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.fondo')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.legatura"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.legatura')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.supporto"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.supporto')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.digitizationType"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.tipoRipresa')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.schemaColore"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.coloreImmagine')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.nomeFiles"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.nomeFiles')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.responsabileFoto"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.responsabileFotoRiproduzione')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showSchedoneProperties.operatore"/></div> 
			<div class="value60">${fn2:getApplicationProperty('schedone.operatore')}</div> 
		</div>
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditForumProperties").css('visibility', 'visible');
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		$j("#EditSchedoneProperties").css('visibility', 'visible');
		$j("#EditUserProperties").css('visibility', 'visible');
	
		$j("#EditSchedoneProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditSchedonePropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	});
</script>

</security:authorize>