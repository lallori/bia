<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	
	<c:url var="ShowDocumentsVolumeURL" value="/src/volbase/ShowDocumentsVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowVolumeURL}" id="editLink${volume.summaryId}" class="showOrEditCompare button_large"><fmt:message key="volbase.compareVolume.showOrEditThisVolume"/></a>
	</div>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowVolumeURL}" id="editLink${volume.summaryId}" class="showCompare button_medium"><fmt:message key="volbase.compareVolume.showThisVolume"/></a>
	</div>
	</security:authorize>
		
	<div id="volumeDiv">
		<%-- Editing Volume Record --%>	
		<c:if test="${volume.summaryId != 0}">
		<div id="volumeTitle">
			<h3>${fn2:getApplicationProperty("schedone.fondo")} - ${volume.volNum}</h3>
			<h4>${volume.serieList}</h4>
			<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
			<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
				<p style="margin:10px 0 0 10px;"><fmt:message key="volbase.compareVolume.documentsVolume"/> <a id="num_docsCompare${volume.summaryId}" href="${ShowDocumentsVolumeURL}" class="num_docs" title="View all the documents related to this Volume record">${volDocsRelated}</a></p>
			</c:if>
			<c:if test="${volDocsRelated == 0}">
				<p style="margin:10px 0 0 10px;"><fmt:message key="volbase.compareVolume.documentsVolume"/> <span class="num_docs" title="No documents related to this Volume record">0</span></p>
			</c:if>
			<c:if test="${volDocsRelated == 1}">
				<p style="margin:10px 0 0 10px;"><fmt:message key="volbase.compareVolume.documentsVolume"/> <a id="num_docsCompare${volume.summaryId}" href="${ShowDocumentsVolumeURL}" class="num_docs" title="View the document related to this Volume record">${volDocsRelated}</a></p>
			</c:if>
		</div>
		</c:if>

	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.compareVolume.title.volumeDetails"/></h5>
		</div>
		<div class="listDetails">
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.volume"/></div>
				<div class="value">${volume.volNum}${volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.start"/></div>
				<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.end"/></div>
				<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
			</div>
			<div class="row">	
				<div class="item"><fmt:message key="volbase.compareVolume.dateNotes"/></div>
				<div class="value">${volume.dateNotes}</div>
			</div>
		</div>
	</div>
	<br />
	<br />
	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.compareVolume.title.description"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.criteria"/></div>
				<div class="value">${volume.orgNotes}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.condition"/></div>
				<div class="value">${volume.ccondition}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.bound"/></div>
				<div class="value">${volume.bound ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.foliosNumbered"/></div>
				<div class="value">${volume.folsNumbrd ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.foliosCount"/></div>
				<div class="value">${volume.folioCount}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.indexOfNames"/></div>
				<div class="value">${volume.oldAlphaIndex ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.printedMaterial"/></div>
				<div class="value">${volume.printedMaterial ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.printedDrawings"/></div>
				<div class="value">${volume.printedDrawings ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.languages"/></div>
				<div class="value"> ${volume.italian ? 'Italian' : '' } 
									${volume.spanish ? 'Spanish' : ''}
									${volume.english ? 'English' : ''}
									${volume.latin ? 'Latin' : ''}
									${volume.german ? 'German' : ''}
									${volume.french ? 'French' : ''}
									${volume.otherLang}
				</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.cipher"/></div>
				<div class="value">${volume.cipher ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.cipherNotes"/></div>
				<div class="value">${volume.cipherNotes}</div>
			</div>
		</div>
	</div>

	<div id="EditCorrespondentsVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.compareVolume.title.correspondents"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.from"/></div><div class="value80"> ${volume.senders}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.to"/></div><div class="value80">${volume.recips}</div>
			</div>
		</div>
	</div>
	
	<div id="EditContextVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.compareVolume.title.context"/></h5>
		</div>

		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.context"/></div><div class="value80">${volume.ccontext}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.compareVolume.inventarioSommarioDescription"/></div><div class="value80">${volume.inventarioSommarioDescription}</div>
			</div>
		</div>
	</div>

</div>

<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${volume.summaryId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
				return false;
			});
			
			$j(".linkPeople").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				//Check if already exist a tab with this person
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j("#num_docsCompare${volume.summaryId}").click(function(){
				//var tabName = "Docs Volume ${volume.summaryId}";
				var tabName = "Docs Volume ${volume.volNum}${volume.volLetExt}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
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
		});
	</script>