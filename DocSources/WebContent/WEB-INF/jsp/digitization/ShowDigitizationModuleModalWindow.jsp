<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="digitizationModalDiv">
	<div id="CreateSchedoneDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="CreateSchedone" href="<c:url value="/digitization/CreateSchedone.do"/>"><p>Create new "Schedone"</p></a>
	</div>
    
     <div id="BrowseFilzeDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="BrowseFilze" href="<c:url value="/digitization/ShowSearchDigitizedVolumes.do"/>"><p>Browse or search "Sechedoni"</p></a>
	</div>
    
    <div id="ActivateFilzaDiv">
        <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
        <a id="ActivateFilza" href="<c:url value="/digitization/ShowSearchActivatedVolumes.do"/>"><p>Activate or deactivate Volume</p></a>
    </div>
    
	<input id="close" type="submit" title="Close Digitization Module window" value="Close" style="margin:22px 0 0 145px"/>
</div>

<script>
	$j(document).ready(function() {
		$j("#CreateSchedone").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			$j("#menu_actions").load("/DocSources/dm/menuDigitization.html");
			Modalbox.hide(); 
			return false;
		});
// 		$j("#AddNewFilza").click(function(){
// 			$j("#body_left").load($j(this).attr("href"));
// 			Modalbox.hide(); 
// 			return false;
// 		});
// 		$j("#BrowseFilze").click(function(){
// 			$j("#body_right").load($j(this).attr("href"));
// 			var tabTitle = "Browse Digitized Volumes";
// 			var numTab = 0;
// 			var tabExist = false;
// 			$j("#tabs ul li a").each(function(){
// 				var toTest = "";
// 				toTest += this.text;
// 				if(!tabExist)
// 					numTab++;
// 				if(toTest == tabTitle){
// 					tabExist = true;
// 				}
// 			});
			
// 			if(!tabExist){
// 				$j( "#tabs" ).tabs("add", $j(this).attr("href"), tabTitle + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
// 				$j("#tabs").tabs("select", $j("#tabs").tabs("length") - 1);
// 				Modalbox.hide();
// 				return false;
// 			}else{
// 				$j("#tabs").tabs("select", numTab - 1);
// 				Modalbox.hide();
// 				return false;
// 			}
// 		});

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

