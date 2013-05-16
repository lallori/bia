<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="UserManualURL" value="/manual/DataEntryManual.html" />

<div id="helpDiv">
    <div id="helpFiles">
    	<div id="helpFilesTitle">
        	<div id="helpFilesIcon"></div>
        	<h1><fmt:message key="menu.showHelpModal.helpSystem.title"/></h1>
        </div>
        <ul>
<%--         	<li><a href="${UserManualURL}" target="_blank">User Manual (HTML version)</a><br /></li> --%>
<!--             <li><a href="#">Download Manual (PDF)</a></li> -->
<!--             <li><a href="#">Help Videos</a></li> -->
<!-- 			MD: Changed at the moment -->
			<li><a target="_blank" title="Soon available" style="cursor:pointer;"><fmt:message key="menu.showHelpModal.userManualHtml"/></a><br /></li>
            <li><a title="Soon available" style="cursor:pointer;"><fmt:message key="menu.showHelpModal.userManualPdf"/></a></li>
            <li><a title="Soon available" style="cursor:pointer;"><fmt:message key="menu.showHelpModal.helpVideos"/></a></li>
        </ul>
    </div>
    
    <div id="aboutMap">
    	<div id="aboutMapTitle">
        	<div id="aboutMapIcon"></div>
        	<h1><fmt:message key="menu.showHelpModal.about.title"/></h1>
        </div>
        <ul>
        	<li><a href="<fmt:message key="menu.showHelpModal.news.link"/>" target="_blank"><fmt:message key="menu.showHelpModal.news"/></a></li>
            <li><a href="<fmt:message key="menu.showHelpModal.website.link"/>" target="_blank"><fmt:message key="menu.showHelpModal.website"/></a></li>
            <li><a href="<fmt:message key="menu.showHelpModal.onlineCourses.link"/>" target="_blank"><fmt:message key="menu.showHelpModal.onlineCourses"/></a></li>
        </ul>
    </div>
</div>
    
    <div id="credits">
    	<p class="title"><fmt:message key="menu.showHelpModal.credits.title"/></p>
    	<p><u><fmt:message key="menu.showHelpModal.developingTeam"/>:</u> <fmt:message key="menu.showHelpModal.developingTeam.text"/></p>
        <p><u><fmt:message key="menu.showHelpModal.researchAndTestingTeam"/>:</u> <fmt:message key="menu.showHelpModal.researchAndTestingTeam.text"/></p>
        <p><u><fmt:message key="menu.showHelpModal.specialThanksTo"/>:</u> <fmt:message key="menu.showHelpModal.specialThanksTo.text"/></p>
    </div>
    
    <div id="closeHelp" class="button_small"><fmt:message key="menu.showHelpModal.button.close"/></div>
	
	
	
	
<script type="text/javascript">
	$j(document).ready(function() {
		$j("#closeHelp").click(
			function(){
				Modalbox.hide(); return false;}
			);
	});
</script>