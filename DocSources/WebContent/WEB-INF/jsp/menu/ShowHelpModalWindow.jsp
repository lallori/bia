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
        	<h1>HELP SYSTEM</h1>
        </div>
        <ul>
        	<li><a href="${UserManualURL}" target="_blank">User Manual (HTML version)</a><br /></li>
            <li><a href="#">Download Manual (PDF)</a></li>
            <li><a href="#">Help Videos</a></li>
        </ul>
    </div>
    
    <div id="aboutMap">
    	<div id="aboutMapTitle">
        	<div id="aboutMapIcon"></div>
        	<h1>ABOUT MAP</h1>
        </div>
        <ul>
        	<li><a href="http://www.medici.org/news" target="_blank">News from MAP</a></li>
            <li><a href="http://www.medici.org/" target="_blank">MAP website</a></li>
            <li><a href="#">MAP online courses</a></li>
            <li><a href="#">MAP Forum</a></li>
        </ul>
    </div>
</div>
    
    <div id="credits">
    	<p class="title">CREDITS</p>
    	<p><u>Developer team:</u> Lorenzo Allori, Lorenzo Pasquinelli, Matteo Doni and Joana Amill Luzondo.</p>
        <p><u>Research team:</u> Alessio Assonitis, Maurizio Arfaioli and Roberta Piccinelli.</p>
        <p><u>Special thanks to:</u> Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    </div>
    
    <div id="closeHelp" class="button_small">Close</div>
	
	
	
	
<script type="text/javascript">
	$j(document).ready(function() {
		$j("#closeHelp").click(
			function(){
				Modalbox.hide(); return false;}
			);
	});
</script>