<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMessagesByCategoryPaginationURL" value="/community/ShowMessagesByCategoryPagination.json">
		<c:param name="category" value="INBOX"/>
	</c:url>
	
	<c:url var="ShowMyInboxURL" value="/community/ShowMyMessageBox.do">
		<c:param name="category" value="INBOX"/>
	</c:url>
	
	<c:url var="ShowMyOutboxURL" value="/community/ShowMyMessageBox.do">
		<c:param name="category" value="OUTBOX"/>
	</c:url>
	
	<h2>Messages</h2>
	
	<div id="tabs">
		<ul>
			<li><a href="${ShowMyInboxURL}">Inbox</a></li>
			<li><a href="${ShowMyOutboxURL}">Outbox</a></li>
			<li><a href="<c:url value="/community/ComposeMessage.do"/>">Compose message</a></li>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				<li><a href="<c:url value="/community/EmailMessage.do"/>">Email message</a></li>
			</security:authorize>
		</ul>
	</div>
	
	<div id="deleteMessagesModal" title="Delete" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Are you sure you want to delete this/these message/s?
	</p>
	</div>  

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">

		document.title= "My Messages";
		$j(document).ready(function() {
			$j( "#tabs" ).tabs({
				ajaxOptions: {
					error: function( xhr, status, index, anchor ) {
						$j( anchor.hash ).html(
							"Couldn't load this tab. We'll try to fix this as soon as possible. " );
					}
				}
			});
			$j( "#tabs span.ui-icon-close" ).live( "click", function() {
// 				var index = $j( "li", this ).index( $j( this ).parent() );
// 				$j( "#tabs" ).tabs( "remove", index );
				var tab = $j( this ).parent().remove();
				var panelId = tab.attr( "aria-controls" );
				$j( "#" + panelId ).remove();
				$j( "tabs" ).tabs( "refresh" );
				$j("#tabs").tabs("option", "active", 0);
			});
			
			/*
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
						
			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
				"aoColumns" : [
				{ sWidth : "50px" },
				{ sWidth : "50px" },
				{ sWidth : "50px" },
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
				"sAjaxSource": "${ShowMessagesByCategoryPaginationURL}",                                           
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					// Add some extra data to the sender 
					aoData.push( { "name": "more_data", "value": "xxx" } );
					$j.getJSON( sSource, aoData, function (json) { 
						// Do whatever additional processing you want on the callback, then tell DataTables 
						fnCallback(json)
					}); 					
				}
			});

			// We need to remove any previous live function
			$j('.searchResult').die();
			
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				return false;
			}); 

			$j(".dataTables_filter").css('visibility', 'hidden');
			*/
		} );
	</script>

<%-- 	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}"> --%>
<!-- 		<thead> -->
<!-- 			<tr></tr> -->
<!-- 		</thead> -->
<!-- 		<tbody> -->
<!-- 			<tr> -->
<!-- 				<td colspan="5" class="dataTables_empty">Loading data from server</td> -->
<!-- 			</tr> -->
<!-- 		</tbody> -->
<!-- 	</table> -->