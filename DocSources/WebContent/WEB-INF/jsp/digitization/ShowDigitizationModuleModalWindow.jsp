<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="menuDigitizationURL" value="menuDigitization.html"/>
 
<div id="digitizationModalDiv">
	<div id="CreateSchedoneDiv">
        <a class="helpIcon" title="<fmt:message key="digitization.modalmenu.createschedoni"></fmt:message>">?</a>
        <a id="CreateSchedone" class="button_Xlarge" href="<c:url value="/digitization/CreateSchedone.do"/>"><fmt:message key="digitization.showDigitizationModuleModalWindow.createNew"/></a>
	</div>
    
     <div id="BrowseFilzeDiv">
        <a class="helpIcon" title="<fmt:message key="digitization.modalmenu.searchschedoni"></fmt:message>">?</a>
        <a id="BrowseFilze" class="button_Xlarge" href="<c:url value="/digitization/ShowSearchDigitizedVolumes.do"/>"><fmt:message key="digitization.showDigitizationModuleModalWindow.browse"/></a>
	</div>
    
    <div id="ActivateFilzaDiv">
        <a class="helpIcon" title="<fmt:message key="digitization.modalmenu.activatedeactivatedigitizedvolume"></fmt:message>">?</a>
        <a id="ActivateFilza" class="button_Xlarge" href="<c:url value="/digitization/ShowSearchActivatedVolumes.do"/>"><fmt:message key="digitization.showDigitizationModuleModalWindow.activateDisactivate"/></a>
    </div>
    
	<input id="close" class="button_small" type="submit" title="Close Digitization Module window" value="Close" style="margin:22px 0 0 145px"/>
</div>

<script>
	$j(document).ready(function() {
		$j("#CreateSchedone").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			$j("#menu_actions").load("${menuDigitizationURL}");
			Modalbox.hide(); 
			return false;
		});

		$j("#BrowseFilze").open({width: 500, height: 130, scrollbars: "yes"});
		$j("#BrowseFilze").click(function(){
			Modalbox.hide();
			return false;
		});
		
		$j("#ActivateFilza").open({width: 500, height: 160, scrollbars: "yes"});
		$j("#ActivateFilza").click(function(){
			Modalbox.hide();
			return false;
		});
		
		
		$j("#close").click(function(){
			Modalbox.hide();
			return false;
		});
		
		$j('.helpIcon').tooltip({track: true, fade: 350});
	});
</script>

