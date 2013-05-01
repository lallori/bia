<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchURL" value="/src/SimpleSearch.do">
<%-- 		<c:param name="text" value="${yourSearch}" /> --%>
	</c:url>
	
	<div id="basicSearchDiv">
		<h1>You searched for "<span>${yourSearch}</span>" in ALL categories:</h1>
		
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="basicSearchTable">
        <thead>
            <tr>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <tr class="rowModalSearch" id="Document Extract Search">   
            	<td><a href="${SimpleSearchURL}" class="EXTRACT">Document Transcriptions</a></td>                                                                                           
                <td><a href="${SimpleSearchURL}" class="EXTRACT">${documentsExtractCount}</a></td>                        
            </tr>
            <tr class="rowModalSearch" id="Document Synopsis Search">
            	<td><a href="${SimpleSearchURL}" class="SYNOPSIS">Document Synopses</a></td>
            	<td><a href="${SimpleSearchURL}" class="SYNOPSIS"> ${documentsSynopsisCount}</a></td>
            </tr>
            <tr class="rowModalSearch" id="Volume Search">
            	<td><a href="${SimpleSearchURL}" class="VOLUME">Volumes</a></td>
            	<td><a href="${SimpleSearchURL}" class="VOLUME">${volumesCount}</a></td>
            </tr>
            <tr class="rowModalSearch" id="People Search">
            	<td><a href="${SimpleSearchURL}" class="PEOPLE">People</a></td>
            	<td><a href="${SimpleSearchURL}" class="PEOPLE">${peopleCount}</a></td>
            </tr>
            <tr class="rowModalSearch" id="Place Search">
            	<td><a href="${SimpleSearchURL}" class="PLACE">Places</a></td>
            	<td><a href="${SimpleSearchURL}" class="PLACE">${placesCount}</a></td>
            </tr>
        </tbody>
    </table>
    
    <input type="text" value="${textSearch}" id="textSearch" style="display:none;"/>
		
<!-- 		<div class="list"> -->
<!-- 			<div class="row"> -->
<!-- 				<div class="col">CATEGORY</div> -->
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<a href="${SimpleSearchURL}" class="documents" id="EXTRACT"><span class="documentsExtractCount"></span>${documentsExtractCount} Document Extracts</a> --%>
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<a href="${SimpleSearchURL}" class="documents" id="SYNOPSIS"><span class="documentsSynopsisCount"></span>${documentsSynopsisCount} Document Synopses</a> --%>
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<a href="${SimpleSearchURL}" class="volumes" id="VOLUME"><span class="volumesCount"></span>${volumesCount} Volumes</a> --%>
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<a href="${SimpleSearchURL}" class="people" id="PEOPLE"><span class="peopleCount"></span>${peopleCount} People</a> --%>
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<a href="${SimpleSearchURL}" class="places" id="PLACE"><span class="placesCount"></span>${placesCount} Places</a> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		
		<input id="close" class="button_small" type="submit" title="Close Entry Menu window" value="Close"/>
	</div>
	
	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j('#basicSearchTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
					{ sWidth : "150px" },
					{ sWidth : "150px" },
					],
				"bInfo": false
				                                                                       
			} );    
			
			$j(".dataTables_filter").css('display', 'none');
			
			$j(".SYNOPSIS").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "?simpleSearchPerimeter=" + $j(this).attr("class") + "&text=" + $j("#textSearch").val(), "Document Synopsis Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j(".EXTRACT").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "?simpleSearchPerimeter=" + $j(this).attr("class") + "&text=" + $j("#textSearch").val(), "Document Transcriptions Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j(".VOLUME").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "?simpleSearchPerimeter=" + $j(this).attr("class") + "&text=" + $j("#textSearch").val(), "Volume Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j(".PEOPLE").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "?simpleSearchPerimeter=" + $j(this).attr("class") + "&text=" + $j("#textSearch").val(), "People Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j(".PLACE").click(function(){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href") + "?simpleSearchPerimeter=" + $j(this).attr("class") + "&text=" + $j("#textSearch").val(), "Place Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
			
			$j(".rowModalSearch").click(function(){
				var link = $j(this).children().children();
				$j( "#tabs" ).tabs( "add" , $j(link).attr("href") + "?simpleSearchPerimeter=" + $j(link).attr("class") + "&text=" + $j("#textSearch").val(), $j(this).attr("id") + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
				return false;
			});
		} );
	</script>