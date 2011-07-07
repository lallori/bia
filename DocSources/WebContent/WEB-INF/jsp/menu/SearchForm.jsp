<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/src/SimpleSearch.do"/>" method="post">
						Search in <select id="searchType" name="searchType" class="select">
										<option value="documents" selected>Documents</option>
										<option value="volumes">Volumes</option>
										<option value="people">People</option>
										<option value="places">Places</option>
								</select>
						<br/>
						for <input id="text" name="text" type="text" value=""/>
						<br/>
						<input id="search" type="submit" value="" title="submit form"/>
					</form>
				</div>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#SearchForm").submit(function() {
							var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
							$j( "#tabs" ).tabs( "add" , formSubmitURL, $j('#searchType').find('option:selected').text() + " Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
							$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
							return false;
						});
					});
				</script>