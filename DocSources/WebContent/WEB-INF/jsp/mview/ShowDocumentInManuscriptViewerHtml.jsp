<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReverseProxyIIPImage" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>

	<c:url var="PageTurnerDialogUrl" value="/src/mview/PageTurnerDialog.do" >
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="modeEdit" value="false" />
	</c:url>
		
		<%-- <script>
           window.onbeforeunload = function() {
               return "Are you sure you want to leave the Manuscript Transcriber? Changes will be lost.";
           };
    	</script> --%>
		
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});

				var credit = '';	
				if ("${documentExplorer.image.imageType}" == 'R') {
					credit += '<span style=\'font-size:16px\'>' + 'index of names &nbsp;';
					
					credit+= '<span style=\'font-size:22px\'>' + "${documentExplorer.image.imageProgTypeNum}";
					
					if ("${documentExplorer.image.missedNumbering}") {
						credit += ' ' + "${documentExplorer.image.missedNumbering}";
					}
		
					if ("${documentExplorer.image.imageRectoVerso}" == 'R') {
						credit += '</span>' + ' recto' + '</span>';
					} else {
						credit += '</span>' + ' verso' + '</span>';
					}
				} 
				
				else if ("${documentExplorer.image.imageType}" == 'C') {
					credit += '<span style=\'font-size:16px\'>' + 'folio &nbsp; &nbsp;';
					
					credit+= '<span style=\'font-size:22px\'>' + "${documentExplorer.image.imageProgTypeNum}";
					
					if ("${documentExplorer.image.missedNumbering}") {
						credit += ' ' + "${documentExplorer.image.missedNumbering}";
					}
		
					if ("${documentExplorer.image.imageRectoVerso}" == 'R') {
						credit += '</span>' + ' recto' + '</span>';
					} else {
						credit += '</span>' + ' verso' + '</span>';
					}
				} 
				
				else if ("${documentExplorer.image.imageType}" == 'A') {
					credit += '<span style=\'font-size:16px\'>' + 'allegato &nbsp; &nbsp;';
					
					credit+= '<span style=\'font-size:22px\'>' + "${documentExplorer.image.imageProgTypeNum}";
					
					if ("${documentExplorer.image.missedNumbering}") {
						credit += ' ' + "${documentExplorer.image.missedNumbering}";
					}
		
					if ("${documentExplorer.image.imageRectoVerso}" == 'R') {
						credit += '</span>' + ' recto' + '</span>';
					} else {
						credit += '</span>' + ' verso' + '</span>';
					}
				}
		    
		    	else {
		    		var credit = ' ';
		    	}
				
				iip = new IIP( "targetframe", {
					server: '${ReverseProxyIIPImage}',
					prefix: '${ImagePrefixURL}',
					image: '${documentExplorer.image}',
					credit: credit,					
					navigation: true,
					showNavWindow: true,
					showNavImage: true, // this property hide navigation image
					showNavButtons: true,
					winResize: true,
					zoom: 3,
					scale: 0
				});

				var $pageTurner = $j('<div></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
					resizable: false,
					width: 470,
					height: 112,
					minWidth: 470,
					minHeight: 112,                                                                                                                                                         
					title: 'Page Turner',
					position: ['left','middle'],                                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
                		$(this).load('${PageTurnerDialogUrl}');
               		},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});

			});
		</script>
