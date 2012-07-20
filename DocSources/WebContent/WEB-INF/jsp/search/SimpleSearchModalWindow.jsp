<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchURL" value="/src/SimpleSearch.do">
		<c:param name="text" value="${yourSearch}" />
	</c:url>
	
	<c:url var="SimpleSearchModalPaginationURL" value="/src/SimpleSearchModalPagination.json">
		<c:param name="sSearch" value="${command.text}" />
	</c:url>
	
	<div id="basicSearchDiv">
		<h1>You searched for all words in "<span>${command.text}</span>"</h1>
		    <table cellpadding="0" cellspacing="0" border="0" class="display" id="basicSearchTable">
		        <thead>
		            <tr>
		                <th></th>
		                <th></th>
		            </tr>
		        </thead>
		        <tbody>
		            <tr>                                                                                              
		                <td colspan="5" class="dataTables_empty">Loading data from server</td>                        
		            </tr> 
		        </tbody>
		    </table>    
		    
		<input id="close" type="submit" title="Close window" value="Close"/>
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
			                                                           
			$j('#basicSearchTable').dataTable( {
                "aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],
                "bAutoWidth" : false,
                "bSort": false,
                        "aoColumns" : [
                        { sWidth : "150px" },
                        { sWidth : "150px" },
                        ],
                "bDestroy" : true,
                "bFilter" : false,
                "bLengthChange": false,
                "bProcessing": true,
                "bServerSide": true,
                "iDisplayLength": 10,
                "iDisplayStart": 0,
                "oSearch": {"sSearch": ""},
                "sAjaxSource": "${SimpleSearchModalPaginationURL}",
                "sDom": 'T<"clear">lfrtip',
                "bInfo":false,
                "bFilter":false,
                "sPaginationType": "two_button",
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                        /* Add some extra data to the sender */
                        aoData.push( { "name": "more_data", "value": "xxx" } );
                        $j.getJSON( sSource, aoData, function (json) {
                                /* Do whatever additional processing you want on the callback, then tell DataTables */
                                fnCallback(json)
                        } );
                }
        	});

	        // We need to remove any previous live function
	        $j('.extractResult').die();
	        // Result links have a specific class style on which we attach click live.
	        $j('.extractResult').live('click', function() {
	        	$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Extracts Simple Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
	                return false;
	        }); 
	        
	        // We need to remove any previous live function
	        $j('.synopsisResult').die();
	        // Result links have a specific class style on which we attach click live.
	        $j('.synopsisResult').live('click', function() {
	        	$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Synopsis Simple Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
	                return false;
	        }); 
	        
	        // We need to remove any previous live function
	        $j('.peopleResult').die();
	        // Result links have a specific class style on which we attach click live.
	        $j('.peopleResult').live('click', function() {
	        	$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "People Simple Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
	                return false;
	        }); 
	        
	        // We need to remove any previous live function
	        $j('.volumesResult').die();
	        // Result links have a specific class style on which we attach click live.
	        $j('.volumesResult').live('click', function() {
	        	$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Volume Simple Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
	                return false;
	        }); 
	        
	        // We need to remove any previous live function
	        $j('.placesResult').die();
	        // Result links have a specific class style on which we attach click live.
	        $j('.placesResult').live('click', function() {
	        	$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Places Simple Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				Modalbox.hide();
	                return false;
	        }); 

		});
	</script>