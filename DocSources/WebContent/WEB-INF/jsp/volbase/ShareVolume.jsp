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
    
    <div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_EN/all.js#xfbml=1";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
        

	<a href="http://bia.medici.org" id="moreInfoButton" class="button_medium" title="Browse The Medici Archive Project Database" target="_blank">More info</a>
	
	<ul id="network">
       <div class="fb-like" data-send="false" data-layout="button_count" data-width="500" data-show-faces="false" style="display:inline;"></div>
	   <div style="display:inline;"><a href="https://twitter.com/share" class="twitter-share-button" data-text="">Tweet</a></div>
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
			<h3>Mediceo del Principato Volume ${volume.volNum}${volume.volLetExt}</h3>
			<h4>${volume.serieList}</h4>
			<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
			<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
				<p style="margin:10px 0 8px 10px;">Documents related to this Volume record: <font color="#900">${volDocsRelated}</font></p>
			</c:if>
			<c:if test="${volDocsRelated == 0}">
				<p style="margin:10px 0 8px 10px;">Documents related to this Volume record: <font color="#900">0</font></p>
			</c:if>
			<c:if test="${volDocsRelated == 1}">
				<p style="margin:10px 0 8px 10px;">Document related to this Volume record: <font color="#900">${volDocsRelated}</font></p>
			</c:if>
		</div>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:if test="${not empty image}">
			<div id="SpineVolumeDigitDiv">
				<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
				<b>Volume Spine</b>
				<a id="ShowVolumeInManuscriptViewer" title="Show in Manuscript Viewer" title="Show in Manuscript Viewer" href="${ShowVolumeInManuscriptViewerURL}"></a>
				<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="Show preview on the right screen"></a>
			</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == false}">
				<div id="SpineVolumeNotDigitDiv">
					<span>To be digitized</span>
				</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == true}">
				<div id="SpineVolumeNotDigitDiv">
					<span>Spine not available</span>						
				</div>
			</c:if>
		</security:authorize>
			
		<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<div id="SpineVolumeNotDigitDiv">
				<span class="register">To see this Volume you must register</span>
			</div>
		</security:authorize>
	</div>
	
	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5>VOLUME DETAILS</h5>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Volume/Filza</div>
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
	
	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5>DESCRIPTION</h5>
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
    
