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
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
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
