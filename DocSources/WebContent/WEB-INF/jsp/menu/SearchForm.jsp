<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/src/SimpleSearch.do"/>" method="post">
						Search in <select id="simpleSearchPerimeter" name="simpleSearchPerimeter" class="select">
										<option value="EXTRACT" selected>Document Extracts</option>
										<option value="SYNOPSIS" selected>Document Synopses</option>
										<option value="VOLUME">Volumes</option>
										<option value="PEOPLE">People</option>
										<option value="PLACE">Places</option>
								</select>
						<a class="helpIcon" title="Documents">?</a>
  						<br/>
						for <input id="text" name="text" type="text" value=""/>
						<input id="search" type="submit" title="submit form" value="Go"/>
					</form>
				</div>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#SearchForm").submit(function() {
							var title = $j('#searchType').find('option:selected').text();
							title = title.replace('s','');
							var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
							$j( "#tabs" ).tabs( "add" , formSubmitURL, title + " Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
							$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
							return false;
						});
					});
				</script>