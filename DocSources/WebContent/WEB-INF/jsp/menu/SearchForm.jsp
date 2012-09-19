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
										<option value="ALL" selected="selected">All</option>
										<option value="EXTRACT">Document Transcriptions</option>
										<option value="SYNOPSIS">Document Synopses</option>
										<option value="VOLUME">Volumes</option>
										<option value="PEOPLE">People</option>
										<option value="PLACE">Places</option>
								</select></div>
								<div class="col_l"><a class="helpIcon" title="<fmt:message key="basicsearch.all"></fmt:message>">?</a></div>
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
							
							if($j(this).find('option:selected').val() == 'ALL'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.all"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							if($j(this).find('option:selected').val() == 'EXTRACT'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.documents.extract"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							if($j(this).find('option:selected').val() == 'SYNOPSIS'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.documents.synopsis"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							
							if($j(this).find('option:selected').val() == 'VOLUME'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.volumes"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PEOPLE'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.people"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PLACE'){
								$j(".helpIcon").attr('title', '<fmt:message key="basicsearch.places"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
						});
						
						$j("#SearchForm").submit(function() {
							var title = $j('#simpleSearchPerimeter').find('option:selected').text();
							if(title == 'All'){
								Modalbox.show('${SimpleSearchModalURL}' + '?text=' + $j("#text").val(), {title: "BASIC SEARCH", width: 350, height: 250});
								return false;
							}
							if(title == 'Document Synopses'){
								title = "Document Synopses"
							}else{
								//title = title.replace('s','');
								title = title.replace('','');
							}
							var text = $j("#text").val();
							text = text.replace("'","%27");
							var formSubmitURL = $j(this).attr("action") + '?simpleSearchPerimeter=' + $j('#simpleSearchPerimeter').find('option:selected').val() + '&text=' + text;
							$j( "#tabs" ).tabs( "add" , formSubmitURL, title + " Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
							$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
							return false;
						});
					});
				</script>