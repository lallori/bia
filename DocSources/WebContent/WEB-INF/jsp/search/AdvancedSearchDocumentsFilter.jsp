<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchDocumentsURL" value="/src/AdvancedSearchDocuments.do"/>
	<c:url var="SaveUserSearchFilterURL" value="/src/SaveUserSearchFilter.do"/>

	<div id="yourSearchFilterDiv">
		<div class="yourSearchFilterTitle"></div>
		<div class="easySearchModeOn"></div>
		<form id="yourEasySearchFilterForm" action="${AdvancedSearchDocumentsURL}" method="post">
			<p><u>Custom Search Filter</u></p>
			<br />
			<div id="wordSearchDiv"></div>
			<div id="personSearchDiv"></div>
			<div id="placeSearchDiv"></div>
			<div id="senderSearchDiv"></div>
			<div id="fromSearchDiv"></div>
			<div id="recipientSearchDiv"></div>
			<div id="toSearchDiv"></div>
			<div id="refersToSearchDiv"></div>	
			<div id="extractSearchDiv"></div>
			<div id="synopsisSearchDiv"></div>
			<div id="topicSearchDiv"></div>
			<div id="dateSearchDiv"></div>
			<div id="volumeSearchDiv"></div>
			<br>
			<br>
			<input type="submit" title="Search" value="Search" id="advsearch">
			<input type="hidden" name="searchType" value="documents">
			<a class="saveButton" href="#">Save</a>
			<a class="saveAsButton" href="#">Save as</a>
		</form>
	</div>
	<script type="text/javascript">
		$j(document).ready(function() {
			/*$j('.remove').live('click', function() {
				alert('a');
				//e.preventDefault();
				//$j(this).parent().remove();
				return false;
			});*/

			var $dialogSaveAs = $j('<div id="DialogSaveAs"></div>').dialog({
				autoOpen: false,
				width: 250,
				height: 180,
				modal: true,
				zIndex: 3999,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				position: ['center',250],
				open: function(event, ui) { 
            		//$(this).load('${choiceSaveSearchFilterURL}');
            		//alert($j("#searchFilterForm").serialize());
            		$j.ajax({ type:"GET", url: '${SaveUserSearchFilterURL}', data: $j("#searchFilterForm").serialize(), async:false, success:function(html) { 
						$j("#DialogSaveAs").focus();
						$j("#DialogSaveAs").html(html);
						} 
					});
           		},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			});

			$j('.saveAsButton').click(function(){
				if ($dialogSaveAs.dialog("isOpen")) {
					$dialogSaveAs.dialog("close");;
					return false;
				} else {
					$dialogSaveAs.dialog("open");
					return false;
				}
				return false;
			});

			$j("#yourEasySearchFilterForm").submit(function() {
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
				window.opener.$j("#tabs").tabs("add", formSubmitURL, "Advanced Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
				return false;
			});
		});
	</script>
