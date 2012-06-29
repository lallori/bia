<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="deactivateVolume">
		<h1>Volume deactivated.</h1>
		
		<input id="close" type="submit" title="Close Actions Menu window" value="Close" />
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				var tabName = "Activate Volumes";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				$j("#tabs").tabs("load", numTab-1);
				Modalbox.hide();
				return false;
			});
		});
	</script>