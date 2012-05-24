<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="saveAsDiv">
		<form:form id="saveAsForm" method="post" cssClass="edit">
	    	<input type="radio" name="saveType" value="newSearch" checked="">
	        <label for="saveAs" id="saveAsLabel">Save as</label>
			<input id="saveAs" name="saveAs" class="input_24c" type="text" value=""/>
	        <input type="radio" name="saveType" value="replaceSearch">
	        <label for="replace" id="replaceLabel">Replace</label>
	        <select id="idSearchFilterToReplace" name="idSearchFilterToReplace" class="selectform_XXlong">
        	<c:forEach var="searchFilter" items="${searchFilters}">
				<option value="${searchFilter.id}">${searchFilter.filterName}</option>
		    </c:forEach>
	        </select>
	        <input id="saveFilter" type="submit" value="Save" title="Save your filter" />
	     </form:form>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#saveAs").change(function(){
				$j(".error").remove();
				if($j('input:radio:checked').val() == "newSearch"){
					$j("#idSearchFilterToReplace option").each(function(){
						if($j(this).text() == $j("#saveAs").val()){
							$j("#saveFilter").before("<span class=\"error\" style=\"color:red\">Name already in use<br /></span>");
							return false;
						}
					});
				}
			});
			
			$j("#saveAsForm").submit(function() {
				if($j("#saveAsForm").find(".error").length == 0){
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#saveAsDiv").html(html);
					}});
					return false;
				}
			});
		});
	</script>