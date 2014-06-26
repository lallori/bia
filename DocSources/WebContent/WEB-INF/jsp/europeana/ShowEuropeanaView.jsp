<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="GetProgressURL" value="/europeana/getProgress.do" />
	
	<c:url var="StartEuropeanaJobURL" value='/europeana/fireJob.do'/>
	<c:url var="StopEuropeanaJobURL" value='/europeana/stopJob.do'/>
	
	<div id="pageContainer" class="europeanaView">
		<img id="biaImg" src="<c:url value='/images/1024/img_mapLogin.png' />"/>
		<div id="pageTitle">Europeana: Administrator's Page</div>
	</div>
	
	<table class="europeanaTable">
		<tr>
			<td>
				<c:choose>
					<c:when test="${not empty europeanaSize}">
						<a id="download" class="button_large" title="Download" 
							href="<c:url value='/europeana/downloadEuropeanaFile.do'/>">Download Europeana File</a>
					</c:when>
					<c:otherwise>
						<div id="notifyMsg">Europeana file is not available</div>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:if test="${not empty europeanaSize}">
					<div id="notifyMsg">Europeana file is available (${europeanaSize})</div>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<a id="startJob" class="button_large" style="display: none;" title="Start Europeana Job" href="#">Fire Europeana Job Now</a>
				<a id="stopJob" class="button_large" style="display: none;" title="Stop Europeana Job" href="#">Stop Europeana Job</a>
			</td>
			<td>
				<div id="europeanaMsg" class="notifyMsg" style="display: none;">Europeana Job is running...<span id="phase"></span></div>
			</td>
			<td width="40%">
				<div id="progressBar" style="display: none;">
					<span id="progressText">...</span>
					<div id="progress"></div>
				</div>
			</td>
		</tr>
	</table>
	
	<div id="error" class="europeanaError" style="display: none;">
		<span class="errorLbl">ERROR CONDITION</span>
		<pre id="errorPre"><c:if test="${not empty error}">${error}</c:if></pre>
	</div>
	
	<div id="startJobModal" title="Start Europeana Job" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			Europeana Job takes a long time to complete: do you want to start the job?
		</p>
	</div>
	
	<div id="jobEndModal" title="Start Europeana Job" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-check" style="float:left; margin:0 7px 0 0;"></span>
			Europeana Job ended successfully!
		</p>
	</div>
	
	<script>
		$j(document).ready(function() {
			
			document.title = '${fn2:getApplicationProperty("project.name")} - Europeana Management';
			
			var progressRunning = false;
			
			function getProgress() {
				if (!progressRunning) {
					$j.ajax({ 
						type:"GET", 
						url: '${GetProgressURL}', 
						async: true, 
						success: function(json) {
							if (json.running === true) {
								$j("#phase").text('Phase ' + json.currentPhase + ' of ' + json.phases);
								$j("#progress").css('width', json.progress + '%');
								$j("#progressText").text(json.progress + '%');
							} else {
								if (json.error) {
									$j("#errorPre").text(json.error);
									setStatus('noJob');
								} else {
									$j("#jobEndModal").dialog('open');
								}
							}
							progressRunning = false;
						},
						error: function(json) {
							$j("#errorPre").text("The server failed....please retry");
							setStatus('noJob');
							progressRunning = false;
						}
					});
					progressRunning = true;
				}
			}
			
			function setupTimer(pollingTime) {
				return setInterval(function() {getProgress();}, pollingTime);
			}
			
			function setStatus(status) {
				if (status === 'job') {
					$j("#startJob").hide();
					$j("#stopJob").show();
					$j("#europeanaMsg").css("display", "inline-block");
					$j("#progressBar").css("display", "inline-block");
					$j("#errorPre").text("");
					$j("#error").hide();
					timer = setupTimer(5000);
				} else if (status === 'noJob') {
					$j("#stopJob").hide();
					$j("#startJob").show();
					$j("#europeanaMsg").css("display", "none");
					$j("#progressBar").css("display", "none");
					if ($j("#errorPre").text().length > 0) {
						$j("#error").show();
					}
					clearTimeout(timer);
				}
			}
			
			var timer; 
			
			<c:choose>
				<c:when test="${empty europeanaJob}">
					setStatus('noJob');
				</c:when>
				<c:otherwise>
					setStatus('job');
				</c:otherwise>
			</c:choose>
			
			$j("#startJob").click(function() {
				$j("#startJobModal").dialog('open');
				return false;
			});
			
			$j("#stopJob").click(function() {
				clearTimeout(timer);
				$j("#main").load('${StopEuropeanaJobURL}');
				return false;
			});
			
			$j("#startJobModal").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 350,
				height: 170,
				buttons: {
					Ok: function() {
						var error = false;
						$j.ajax({ 
							type: "POST", 
							url: "${StartEuropeanaJobURL}",
							async: false, 
							success: function(json) {
								console.log('Europeana Job is running...');
								// The server job is asynchornous so the succes function is called immediately
							},
							error: function(data) {
								error = true;
								console.log("ERROR");
								$j("#errorPre").html("The server failed....please retry");
								setStatus('noJob');
							}
						});
						$j(this).dialog('close');
						if (!error) {
							setStatus('job');
						}
						return false;	
					},
					Cancel: function() {
						$j("#startJobModal").dialog('close');
					}
				},
				close: function(event, ui) {
					
				}
			});
			
			$j("#jobEndModal").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 350,
				height: 170,
				buttons: {
					Ok: function() {
						location.reload(true);
					}
				},
				close: function(event, ui) {
					location.reload(true);
				}
			});
			
		});
	</script>