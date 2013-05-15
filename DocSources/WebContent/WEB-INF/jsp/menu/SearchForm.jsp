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
								<div class="col_l"><fmt:message key="menu.simpleSearchIn.title"/></div>
								<div class="col_r"><select id="simpleSearchPerimeter" name="simpleSearchPerimeter" class="select">
										<option value="ALL" selected="selected"><fmt:message key="menu.simpleSearchIn.all"/></option>
										<option value="EXTRACT"><fmt:message key="menu.simpleSearchIn.docTranscriptions"/></option>
										<option value="SYNOPSIS"><fmt:message key="menu.simpleSearchIn.docSynopses"/></option>
										<option value="VOLUME"><fmt:message key="menu.simpleSearchIn.volumes"/></option>
										<option value="PEOPLE"><fmt:message key="menu.simpleSearchIn.people"/></option>
										<option value="PLACE"><fmt:message key="menu.simpleSearchIn.places"/></option>
								</select></div>
								<div class="col_l"><a class="helpIcon" title="<fmt:message key="menu.searchForm.help.all"></fmt:message>">?</a></div>
							</div>
						</div>
  						<div class="listSearch">
  							<div class="row">
								<div class="col_r">for</div><div class="col_r"><input id="text" name="text" type="text" value=""/></div>
								<div class="col_r"><input id="search" type="submit" title="submit form" value="Go" class="button_mini"/></div>
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
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.all"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							if($j(this).find('option:selected').val() == 'EXTRACT'){
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.documents.extract"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							if($j(this).find('option:selected').val() == 'SYNOPSIS'){
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.documents.synopsis"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							
							
							if($j(this).find('option:selected').val() == 'VOLUME'){
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.volumes"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PEOPLE'){
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.people"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
							if($j(this).find('option:selected').val() == 'PLACE'){
								$j(".helpIcon").attr('title', '<fmt:message key="menu.searchForm.help.places"></fmt:message>');
								$j('.helpIcon').tooltip({
									track: true,
									fade: 350 
								});
							}
						});
						
						$j("#SearchForm").submit(function() {
							var title = $j('#simpleSearchPerimeter').find('option:selected').text();
							if(title == 'All'){
								Modalbox.show('${SimpleSearchModalURL}' + '?text=' + $j("#text").val(), {title: "<fmt:message key="menu.simpleSearchIn.open"/>", width: 350, height: 250});
								return false;
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