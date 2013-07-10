<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<style type="text/css">
		#volumeTitle {
			margin:10px 0 20px 5px;
		}
    </style>

	<script type="text/javascript" src="<c:url value="/scripts/mview/jquery-ui-1.8.9.custom.min.js"/>"></script>
	
    <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="true" />
	</security:authorize>
	
    <security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="false" />
	</security:authorize>
	
    <c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	<c:url var="HomeURL" value="/Home.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShowLoginFirstDialogURL" value="/menu/ShowLoginFirstModalWindow.do" />
	    
    <div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_EN/all.js#xfbml=1";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
        

	<a href="${ShowVolumeURL}" id="moreInfoButton" class="button_medium" title='<fmt:message key="volbase.shareVolume.help.moreInfo"/>' target="_blank"><fmt:message key="volbase.shareVolume.moreInfo"/></a>
	
	<ul id="network">
       <div class="fb-like" data-send="false" data-layout="button_count" data-width="500" data-show-faces="false" style="display:inline;"></div>
	   <div style="display:inline;"><a href="https://twitter.com/share" class="twitter-share-button" data-text=" "><fmt:message key="volbase.shareVolume.tweet"/></a></div>
	   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
	   <div class="g-plusone" data-size="medium" style="display:inline"></div>
	   <script type="text/javascript">
  	   	(function() {
    		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    		po.src = 'https://apis.google.com/js/plusone.js';
    		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  		})();
		</script>
	</ul>
	
	<div id="volumeTitle">
		<div id="text">
			<h3><fmt:message key="volbase.shareVolume.volumeDescription"/> ${volume.volNum}${volume.volLetExt}</h3>
			<h4>${volume.serieList}</h4>
			<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
			<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
				<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.shareVolume.documentsRelated"/>: <font color="#900">${volDocsRelated}</font></p>
			</c:if>
			<c:if test="${volDocsRelated == 0}">
				<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.shareVolume.documentsRelated"/>: <font color="#900">0</font></p>
			</c:if>
			<c:if test="${volDocsRelated == 1}">
				<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.shareVolume.documentsRelated"/>: <font color="#900">${volDocsRelated}</font></p>
			</c:if>
		</div>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:if test="${not empty image}">
			<div id="SpineVolumeDigitDiv">
				<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
				<b><fmt:message key="volbase.shareVolume.volumeSpine"/></b>
				<a id="ShowVolumeInManuscriptViewer" title="<fmt:message key="volbase.shareVolume.help.showInManuscript"/>" href="${ShowVolumeInManuscriptViewerURL}"></a>
				<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="<fmt:message key="volbase.shareVolume.showPreview"/>"></a>
			</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == false}">
				<div id="SpineVolumeNotDigitDiv">
					<span><fmt:message key="volbase.shareVolume.toBeDigitized"/></span>
				</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == true}">
				<div id="SpineVolumeNotDigitDiv">
					<span><fmt:message key="volbase.shareVolume.spineNotAvailable"/></span>						
				</div>
			</c:if>
		</security:authorize>
			
		<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<div id="SpineVolumeNotDigitDiv">
				<span class="register"><fmt:message key="volbase.shareVolume.register"/></span>
			</div>
		</security:authorize>
	</div>
	
	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.shareVolume.title.details"/></h5>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.volume"/></div>
				<div class="value">${volume.volNum}${volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.startDate"/></div>
				<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.endDate"/></div>
				<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
			</div>
			<div class="row">	
				<div class="item"><fmt:message key="volbase.shareVolume.dateNotes"/></div>
				<div class="value">${volume.dateNotes}</div>
			</div>
		</div>
	</div>
	
	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.shareVolume.title.description"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.organizationalCriteria"/></div>
				<div class="value">${volume.orgNotes}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.condition"/></div>
				<div class="value">${volume.ccondition}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.bound"/></div>
				<div class="value">${volume.bound ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.foliosNumbered"/></div>
				<div class="value">${volume.folsNumbrd ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.foliosCount"/></div>
				<div class="value">${volume.folioCount}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.indexOfNames"/></div>
				<div class="value">${volume.oldAlphaIndex ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.printedMaterial"/></div>
				<div class="value">${volume.printedMaterial ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.printedDrawings"/></div>
				<div class="value">${volume.printedDrawings ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.languages"/></div>
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
				<div class="item"><fmt:message key="volbase.shareVolume.cipher"/></div>
				<div class="value">${volume.cipher ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.cipherNotes"/></div>
				<div class="value">${volume.cipherNotes}</div>
			</div>
		</div>
	</div>
  
    <div id="EditCorrespondentsVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.shareVolume.title.correspondents"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.from"/></div><div class="value80"> ${volume.senders}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.to"/></div><div class="value80">${volume.recips}</div>
			</div>
		</div>
	</div>
     
    <div id="EditContextVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.shareVolume.title.context"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.context"/> </div><div class="value80">${volume.ccontext}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.shareVolume.inventarioSommarioDescription"/></div><div class="value80">${volume.inventarioSommarioDescription}</div>
			</div>
		</div>
	</div>
    
	<script type="text/javascript">
		$j(document).ready(function() {
			
			var showDialogLoginFirst = $j('<div id="DialogLoginFirst"></div>').dialog({
				resizable: false,
				width: 300,
				height: 150, 
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				open: function(event, ui) { 
            		$j(this).load('${ShowLoginFirstDialogURL}');
           		},
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				title: "LOG IN FIRST"
			});
			
			$j("#moreInfoButton").die();
			$j("#moreInfoButton").live('click', function(e){
				if (!${logged}) {
					showDialogLoginFirst.dialog('open');
					return false;
				}
				e.preventDefault();
				if (window.opener != null) {
					if (window.opener.$j("#body_left").length == 1) {
						window.opener.$j("#body_left").load($j(this).attr('href'));
						window.opener.alert('<fmt:message key="home.showRecordAlertMessage"/>');
					} else {
						// Parent window is not yet opened
						window.open("${HomeURL}","_self");
					}
				} else {
					// If there isn't BIA window
					window.open("${HomeURL}","_self");
				}
				return false;
			});
		});
	</script>