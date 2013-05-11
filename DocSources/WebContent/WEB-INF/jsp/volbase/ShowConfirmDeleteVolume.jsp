<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteVolumeURL" value="/de/volbase/DeleteVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	<c:url var="CheckVolumeIsDeletableURL" value="/de/volbase/CheckVolumeIsDeletable.json">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1><fmt:message key="volbase.showConfirmDeleteVolume.message"/>?</h1>
		
		<a id="yes" class="button_small" href="${DeleteVolumeURL}"><fmt:message key="volbase.showConfirmDeleteVolume.yes"/></a>
	
		<a id="no" class="button_small" href="#"><fmt:message key="volbase.showConfirmDeleteVolume.no"/></a>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckVolumeIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove from any documents related to this Volume</h1>');
						if (json.documentsVolume>0) {
							$j("#DeleteThisRecordDiv").append(json.documentsVolumeURL)
							$j("#DeleteThisRecordDiv > #showDocumentsRelated").append(" " + json.documentsVolume);

							$j("#showDocumentsRelated").die();
							$j("#showDocumentsRelated").live('click', function() {
								//var tabName = "Docs Volume ${volume.summaryId}";
								var tabName = "Docs Volume " + json.volNum + json.volLetExt;
								var numTab = 0;
								
								//Check if already exist a tab with this volume
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}

					} else {
						$j.ajax({ type:"POST", url: '${DeleteVolumeURL}', async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").html(html);
							$j("#body_left").load('${ShowVolumeURL}');
						}});
					}
				}});

				return false;
			});
		});
	</script>
