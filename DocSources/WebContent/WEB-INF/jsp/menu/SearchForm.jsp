<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchModalURL" value="/src/SimpleSearchModal.do" />

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/src/SimpleSearch.do"/>" method="post">
						<div class="listSearch">
							<div class="row">
								<div class="col_l">Simple Search in</div>
								<div class="col_r"><select id="simpleSearchPerimeter" name="simpleSearchPerimeter" class="select">
										<option value="ALL">All</option>
										<option value="EXTRACT"  selected="selected">Document Extracts</option>
										<option value="SYNOPSIS">Document Synopses</option>
										<option value="VOLUME">Volumes</option>
										<option value="PEOPLE">People</option>
										<option value="PLACE">Places</option>
								</select></div>
								<div class="col_l"><a class="helpIcon" title="<fmt:message key="basicsearch"></fmt:message>">?</a></div>
							</div>
						</div>
  						<div class="listSearch">
  							<div class="row">
								<div class="col_r">for</div><div class="col_r"><input id="text" name="text" type="text" value=""/></div>
								<div class="col_r"><input id="search" type="submit" title="submit form" value="Go"/></div>
							</div>
						</div>
					</form>
				</div>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j('.helpIcon').tooltip({
							track: true,
							fade: 350 
						});
						
						$j("#simpleSearchPerimeter").change(function(){
							
							if($j(this).find('option:selected').val() == 'EXTRACT'){
								$j(".helpIcon").attr('title', 'Perform your search in Document Extracts');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							if($j(this).find('option:selected').val() == 'SYNOPSIS'){
								$j(".helpIcon").attr('title', 'Perform your search in Document Synopsis');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							
							if($j(this).find('option:selected').val() == 'VOLUME'){
								$j(".helpIcon").attr('title', 'Which Volume fields are we looking into?');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PEOPLE'){
								$j(".helpIcon").attr('title', 'Which Person fields are we looking into?');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PLACE'){
								$j(".helpIcon").attr('title', 'Which Place fields are we looking into?');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
						});
						
						$j("#SearchForm").submit(function() {
							var title = $j('#simpleSearchPerimeter').find('option:selected').text();
							if(title == 'All'){
								Modalbox.show('${SimpleSearchModalURL}' + '?text=' + $j("#text").val(), {title: "BASIC SEARCH", width: 500, height: 260});
								return false;
							}
							if(title == 'Document Synopsis'){
								title = "Document Synopsis"
							}else{
								title = title.replace('s','');
							}
							var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
							$j( "#tabs" ).tabs( "add" , formSubmitURL, title + " Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
							$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
							return false;
						});
					});
				</script>