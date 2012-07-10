<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="DocSourcesContent">
		<div id="body_left">
			<h1 class="welcome">The Medici Archive Project Scholarly Community</h1>
			<div id="colte_of_arms"></div>
		</div>
		<div id="body_right">
			<div id="tabs">
				<ul>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS">
<%-- 					<li><a href="#" title="Close all visible tabs" style="cursor:pointer;" id="removeAllTabs"><img src="<c:url value="/images/1024/button_deleteTabs.png"/>" /></a><span class="ui-icon ui-icon-close" title="Close Tab" style="display:none;"></span></li> --%>
					<li><a href="<c:url value="/Welcome.do"/>">Welcome</a><span class="ui-icon ui-icon-close" title="Close Tab">Remove Tab</span></li>
					</security:authorize>
				</ul>
			</div>
		</div>
	</div>
	
	<c:url var="LastEntryUserURL" value="/user/LastEntryUser.json" />

<script>
	$j(function() {
		$j("#tabs").tabs({
			ajaxOptions: {
				type: 'post',
				error: function( xhr, status, index, anchor ) {
					$j( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " );
				}
			},
			cache: true // load only once or with every click
		});

		$j("#tabs span.ui-icon-close" ).live("click", function() {
			var index = $j("li", $j("#tabs")).index($j(this).parent());
			$j("#tabs").tabs("remove", index);
			$j("#tabs").tabs("enable", 0);
		});
		
		//Add the tab with the trash image
		$j( "#tabs" ).tabs( "add" , "#removeTabsURL", "<a href='#' title='Close all visible tabs' style='cursor:pointer;' id='removeAllTabs'><img src='<c:url value='/images/1024/button_deleteTabs.png'/>' /></a><span class='ui-icon ui-icon-close' title='Close Tab' style='display:none;'>", 0);
		
		$j("#tabs").tabs("select", 1);
// 		$j("#tabs").tabs("disable", 0);
		
		//This function is for remove all tabs opened
		$j("#removeAllTabs").live("click", function(){
			if($j("#tabs").tabs("length") > 1){
				$j('#body_right').block({ message: $j('#removeTabs'), 
							css: { 
								border: 'none', 
								padding: '5px',
								boxShadow: '1px 1px 10px #666',
								'-webkit-box-shadow': '1px 1px 10px #666'
								} ,
								overlayCSS: { backgroundColor: '#999' }	
				}); 
			}
			return false;
		});
		
		$j.get('${LastEntryUserURL}', function(data){
			if(data.category == 'Document'){
				$j("#body_left").load('<c:url value="/src/docbase/ShowLastEntryDocument.do"/>');
				return false;
			}
			if(data.category == 'Volume'){
				$j("#body_left").load('<c:url value="/src/volbase/ShowLastEntryVolume.do"/>');
				return false;
			}
			if(data.category == 'People'){
				$j("#body_left").load('<c:url value="/src/peoplebase/ShowLastEntryPerson.do"/>');
				return false;
			}
			if(data.category == 'Place'){
				$j("#body_left").load('<c:url value="/src/geobase/ShowLastEntryPlace.do"/>');
				return false;
			}
		});
	});
</script>

<div id="removeTabs" style="display:none; cursor: default"> 
	<h1>Close All Tabs?</h1> 
	<input type="button" id="removeTabsYes" value="Yes" /> 
	<input type="button" id="removeTabsNo" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#removeTabsNo').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#removeTabs").hide();
			$j("#body_right").append($j("#removeTabs"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#removeTabsYes').click(function() { 
			$j("#tabs").tabs("select", 0);

			$j("#tabs ul li a").each(function(){
				if($j(this).attr("href") != '#removeTabsURL'){
					$j("#tabs").tabs("remove", $j(this).attr("href"));
				}
			});
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#removeTabs").hide();
			// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
			$j("#body_right").append($j("#removeTabs"));
			$j(".blockUI").remove();
			$j( "#tabs" ).tabs( "add" , "#removeTabsURL", "<a href='#' title='Close all visible tabs' style='cursor:pointer;' id='removeAllTabs'><img src='<c:url value='/images/1024/button_deleteTabs.png'/>' /></a><span class='ui-icon ui-icon-close' title='Close Tab' style='display:none;'>", 0);
			return false; 
		}); 
     	
	});
</script>
