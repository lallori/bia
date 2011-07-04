<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="TopicsDescriptionWindow">
		<h1>${topic.topicTitle}</h1>
		
		<p>${topic.description}</p>
		
		<input id="close" type="submit" title="Close Topic Description window" value="" />
	</div>
	<script type="text/javascript">
		$j(document).ready(function() {			 
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditTopicsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTopicsDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
		});
	</script>
