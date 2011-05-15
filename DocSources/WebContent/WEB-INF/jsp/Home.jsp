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
					<li><a href="<c:url value="/Welcome.do"/>">Welcome</a><span class="ui-icon ui-icon-close" title="Close Tab">Remove Tab</span></li>
				</ul>
			</div>
		</div>
	</div>

<script>
	$j(function() {
		$j( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$j( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " );
				}
			}
		});

		$j( "#tabs span.ui-icon-close" ).live( "click", function() {
			var index = $j( "li", this ).index( $j( this ).parent() );
			$j( "#tabs" ).tabs( "remove", index );
		});

	});
</script>



