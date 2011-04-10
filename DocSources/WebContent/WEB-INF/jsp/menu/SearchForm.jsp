<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/src/SimpleSearch.do"/>" method="post">
						Search in <select name="searchType" class="select" style="margin-left:8px">
										<option value="documents" selected>Documents</option>
										<option value="volumes">Volumes</option>
										<option value="people">People</option>
										<option value="places">Places</option>
								</select>
						<br/>
						for <input id="text" name="text" type="text" value="" class="input_search" style="margin-top:5px"/>
						<br/>
						<input id="search" type="submit" value="" title="submit form"/>
					</form>
				</div>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#SearchForm").submit(function() {
							$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
									$j("#body_right").html(html);
								}
							});
						return false;});
					});
				</script>