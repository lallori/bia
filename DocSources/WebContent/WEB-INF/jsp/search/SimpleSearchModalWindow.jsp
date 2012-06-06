<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchURL" value="/src/SimpleSearch.do">
		<c:param name="text" value="${yourSearch}" />
	</c:url>
	
	<div id="basicSearchDiv">
		<h1>You searched for all words in "<span>${yourSearch}</span>"</h1>
		
		<div class="list">
			<div class="row">
				<div class="col">CATEGORY</div>
			</div>
			<div class="row">
				<a href="${SimpleSearchURL}" class="documents" id="EXTRACT"><span class="documentsExtractCount"></span>${documentsExtractCount} Documents Extract</a>
			</div>
			<div class="row">
				<a href="${SimpleSearchURL}" class="documents" id="SYNOPSIS"><span class="documentsSynopsisCount"></span>${documentsSynopsisCount} Documents Synopsis</a>
			</div>
			<div class="row">
				<a href="${SimpleSearchURL}" class="volumes" id="VOLUME"><span class="volumesCount"></span>${volumesCount} Volumes</a>
			</div>
			<div class="row">
				<a href="${SimpleSearchURL}" class="people" id="PEOPLE"><span class="peopleCount"></span>${peopleCount} People</a>
			</div>
			<div class="row">
				<a href="${SimpleSearchURL}" class="places" id="PLACE"><span class="placesCount"></span>${placesCount} Places</a>
			</div>
		</div>
		
		<input id="close" type="submit" title="Close Entry Menu window" value="Close"/>
	</div>
	
	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#SYNOPSIS").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "&simpleSearchPerimeter=" + $j(this).attr("id"), "Document Synopsis Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j("#EXTRACT").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "&simpleSearchPerimeter=" + $j(this).attr("id"), "Document Extract Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j("#VOLUME").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "&simpleSearchPerimeter=" + $j(this).attr("id"), "Volume Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j("#PEOPLE").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "&simpleSearchPerimeter=" + $j(this).attr("id"), "People Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j("#PLACE").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "&simpleSearchPerimeter=" + $j(this).attr("id"), "Place Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
		} );
	</script>