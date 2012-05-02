<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="digitizationModalDiv">
	<div id="CreateCatalogDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="CreateCatalog" href="<c:url value="/digitization/CreateSchedone.do"/>"><p>Create new "Schedone"</p></a>
	</div>
    
    <div id="AddNewFilzaDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="AddNewFilza" href="<c:url value="/digitization/AddDigitizedVolume.do"/>"><p>Add new Digitized Volume (Filza)</p></a>
    </div>
    
    <div id="BrowseFilzeDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="BrowseFilze" href="<c:url value="/digitization/BrowseDigitizedVolumes.do"/>"><p>Browse Digitized Volumes (Filze)</p></a>
	</div>
	<input id="close" type="submit" title="Close Digitization Module window" value="Close" style="margin:22px 0 0 145px"/>
</div>

<script>
	$j(document).ready(function() {
		$j("#CreateCatalog").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			$j("#menu_actions").load("/DocSources/dm/menuDigitization.html");
			Modalbox.hide(); 
			return false;
		});
		$j("#AddNewFilza").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide(); 
			return false;
		});
		$j("#BrowseFilze").click(function(){
			$j("#body_right").load($j(this).attr("href"));
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

