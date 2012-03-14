<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div>
		<a href="${ShowVolumeURL}" id="editLink${volume.summaryId}" class="showOrEditCompare">Show or Edit this Volume</a>
	</div>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_GUESTS">
	<div>
		<a href="${ShowVolumeURL}" id="editLink${volume.summaryId}" class="showCompare">Show this Volume</a>
	</div>
	</security:authorize>
		
	<div id="volumeDiv">
		<%-- Create new Volume Record --%>
		<c:if test="${volume.summaryId == 0}">
		<div id="volumeTitle">
			<h2>ADD New - Volume Record</h2>
		</div>
		</c:if>	
		<%-- Editing Volume Record --%>	
		<c:if test="${volume.summaryId != 0}">
		<div id="volumeTitle" class="background">
			<div class="title">
  				 <h5>VOLUME</h5>
 			</div>
			<h3>Mediceo del Principato Volume ${volume.volNum}</h3>
			<h4>${volume.serieList}</h4>
			<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
			<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
				<p style="margin-left:28px;">Documents related to this Volume record: <a href="${ShowDocumentsVolumeURL}" class="num_docs" title="Click here to view all the documents related to this Volume record">${volDocsRelated}</a></p>
			</c:if>
			<c:if test="${volDocsRelated == 0}">
				<p style="margin-left:28px;">Documents related to this Volume record: <span class="num_docs" title="No documents related to this Volume record">0</span></p>
			</c:if>
			<c:if test="${volDocsRelated == 1}">
				<p style="margin-left:28px;">Documents related to this Volume record: <a href="${ShowDocumentsVolumeURL}" class="num_docs" title="Click here to document related to this Volume record">${volDocsRelated}</a></p>
			</c:if>
		</div>
		</c:if>

	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5>VOLUME DETAILS </h5>
		</div>
		<div class="listDetails">
			<div class="row">
				<div class="item">Volume/Filza (MDP)</div>
				<div class="value">${volume.volNum}${volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item">Start Date</div>
				<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
			</div>
			<div class="row">
				<div class="item">End Date</div>
				<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
			</div>
			<div class="row">	
				<div class="item">Date Notes</div>
				<div class="value">${volume.dateNotes}</div>
			</div>
		</div>
	</div>
	<br />
	<br />
	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5>DESCRIPTION </h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">Organizational Criteria</div>
				<div class="value">${volume.orgNotes}</div>
			</div>
			<div class="row">
				<div class="item">Condition</div>
				<div class="value">${volume.ccondition}</div>
			</div>
			<div class="row">
				<div class="item">Bound</div>
				<div class="value">${volume.bound ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Folios Numbered</div>
				<div class="value">${volume.folsNumbrd ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Folios Count</div>
				<div class="value">${volume.folioCount}</div>
			</div>
			<div class="row">
				<div class="item">Index of Names</div>
				<div class="value">${volume.oldAlphaIndex ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Printed material</div>
				<div class="value">${volume.printedMaterial ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Printed drawings</div>
				<div class="value">${volume.printedDrawings ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Languages</div>
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
				<div class="item">Some Documents in Cipher</div>
				<div class="value">${volume.cipher ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Cipher Notes</div>
				<div class="value">${volume.cipherNotes}</div>
			</div>
		</div>
	</div>

	<div id="EditCorrespondentsVolumeDiv" class="background">
		<div class="title">
			<h5>CORRESPONDENTS</h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">From </div><div class="value80"> ${volume.senders}</div>
			</div>
			<div class="row">
				<div class="item">To </div><div class="value80">${volume.recips}</div>
			</div>
		</div>
	</div>
	
	<div id="EditContextVolumeDiv" class="background">
		<div class="title">
			<h5>CONTEXT</h5>
		</div>

		<div class="list">
			<div class="row">
				<div class="item">Context </div><div class="value80">${volume.ccontext}</div>
			</div>
			<div class="row">
				<div class="item"> Inventario Sommario Description </div><div class="value80">${volume.inventarioSommarioDescription}</div>
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
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>