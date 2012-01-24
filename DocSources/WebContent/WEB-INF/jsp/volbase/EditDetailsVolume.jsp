<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditDetailsVolumeForm" method="post" cssClass="edit">
		<div id="loadingDiv"></div>
		<fieldset>
			<legend><b>VOLUME DETAILS</b></legend>
			<div>
				<form:label id="seriesRefDescriptionLabel" for="seriesRefDescription" path="seriesRefDescription" cssErrorClass="error"><i>Carteggio</i></form:label>
				<form:input id="seriesRefDescriptionAutoCompleter" path="seriesRefDescription" cssClass="input_40c" />
			</div>

			<div>
				<form:label id="volNumLabel" for="volNum" path="volNum" cssErrorClass="error" >Volume/Filza (MDP)</form:label>
				<form:input path="volNum" cssClass="input_5c" maxlength="5"/>
				<form:label id="volLetExtLabel" for="volLetExt" path="volLetExt" cssErrorClass="error">Volume extension</form:label>
				<form:input path="volLetExt" size="1" maxlength="1"  cssClass="input_1c"/>
			</div>
			
			<hr />

			<div>
				<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Start year</form:label><form:input path="startYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="startMonthNumLabel" for="startMonthNum" path="startMonthNum" cssErrorClass="error">Start month</form:label>
				<form:select path="startMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName" />
				<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Start day</form:label>
				<form:input path="startDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">End year</form:label>
				<form:input path="endYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="endMonthNumLabel" for="endMonthNum" path="endMonthNum" cssErrorClass="error">End month</form:label>
				<form:select path="endMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
				<form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">End day</form:label>
				<form:input path="endDay" cssClass="input_2c" maxlength="2"/>
			</div>
			
			<hr />

			<div>
				<form:label id="dateNotesLabel" for="dateNotes" path="dateNotes" cssErrorClass="error">Date notes</form:label>
			</div>

			<div>
				<form:textarea path="dateNotes" cssClass="txtarea"/>
			</div>

			<form:errors path="seriesRefDescription" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="volNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="volLetExt" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="startYear" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="startMonthNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="startDay" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="endYear" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="endMonthNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="endDay" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="dateNotes" cssClass="inputerrors" htmlEscape="false"/>
			
			<form:hidden path="summaryId"/>
			<form:hidden path="seriesRefNum"/>

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" />
			</div>
		</fieldset>	
	</form:form>

	<c:url var="searchSeriesListUrl" value="/de/volbase/SearchSeriesList.json"/>

	<c:url var="CheckVolumeDigitizedURL" value="/src/volbase/CheckVolumeDigitized.json"/>
	
	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do"/>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsVolume").css('visibility', 'hidden'); 
	        $j("#EditDescriptionVolume").css('visibility', 'hidden'); 
			$j("#EditDetailsVolume").css('visibility', 'hidden'); 
			
			$j("#save").click(function(){
		       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		       	$j("#loadingDiv").css('visibility', 'visible');
		    });

	        // We disable
			<c:if test="${command.summaryId != 0}"> 
				$j("#volNum").attr("disabled","true");
				$j("#volLetExt").attr("disabled","true");
	        </c:if>

			var showVolumeExplorer = function (){
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volNum: $j("#volNum").val(), volLetExt: $j("#volLetExt").val() },
					function(data){
						if (data.summaryId == "") {
							if ($j("#volExist").length > 0) {
								$j("#volExist").remove();
							}
							$j("#save").removeAttr("disabled");

							$j.get('${CheckVolumeDigitizedURL}', { volNum: $j("#volNum").val(), volLetExt: $j("#volLetExt").val() }, function(data){
									if (data.digitized == "true") {
				            			var tabName = "Volume Explorer " + data.volNum + data.volLetExt + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab"
				            			var showVolumeExplorer = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
				                    	$j("#tabs").tabs("add", "" + showVolumeExplorer, tabName);
				                    	$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				            		}
								}
								 
							);
						} else {
							if ($j("#volExist").length == 0) {
								$j("#close").before("<span class=\"inputerrorsVolumeExist\" id=\"volExist\">Volume is already present, you cannot add again this volume. Save is disabled.<br></span>");
							}
							$j("#save").attr("disabled","true");
						}
					}
				);
	 		}
			$j("#volNum").change(showVolumeExplorer);
			$j("#volLetExt").change(showVolumeExplorer);	        	

			var a = $j('#seriesRefDescriptionAutoCompleter').autocompleteGeneral({ 
			    serviceUrl:'${searchSeriesListUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#seriesRefNum').val(data); }
			});

			$j('#close').click(function() {
	            $j('#EditDetailsVolumeDiv').block({ message: $j('#question') }); 
				return false;
			});
	        
			$j("#buttonShareLink").click(function() {
				window.open('${ShareDocumentURL}','ADD NEW PERSON','width=490,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
			});

			$j("#buttonShareLink").hover(function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").attr({"style": 'display:inline'});
				$j(this).find("span").animate({opacity: 1, top: "-60"}, {queue:false, duration:400});
			}, function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").animate({opacity: 0, top: "-50"}, {queue:false, duration:400, complete: function(){
					$j(this).attr({"style": 'display:none'});
				}});
			});

			$j("#EditDetailsVolumeForm").submit(function (){
				if($j("#endYear").val() != '' && $j("#startYear").val() !=''){
					if($j("#endYear").val() < $j("#startYear").val()){
						$j('#EditDetailsVolumeDiv').block({ message: $j('.wrongEndDate') });
						return false;
					}
					if($j("#endMonthNum").val() > 0 && $j("#startMonthNum").val() > 0){
						if(($j("#endYear").val() == $j("#startYear").val()) && ($j("#endMonthNum").val() < $j("#startMonthNum").val())){
							$j('#EditDetailsVolumeDiv').block({ message: $j('.wrongEndDate') });
							return false;
						}
						if($j("#endDay").val() != '' && $j("#startDay").val() != ''){
							if(($j("#endYear").val() == $j("#startYear").val()) && ($j("#endMonthNum").val() == $j("#startMonthNum").val()) && ($j("#endDay").val() < $j("#startDay").val())){
								$j('#EditDetailsVolumeDiv').block({ message: $j('.wrongEndDate') });
								return false;
							}							
						}
					}
				}
				if($j("#startDay").val() != '' && ($j("#startDay").val() < 1 || $j("#startDay").val() > 31)){
					$j('#EditDetailsVolumeDiv').block({ message: $j('.wrongFormatDate')});
					return false;
				}
				
				if($j("#endDay").val() != '' && ($j("#endDay").val() < 1 || $j("#endDay").val() > 31)){
					$j('#EditDetailsVolumeDiv').block({ message: $j('.wrongFormatDate')});
					return false;
				}
								
				
				$j("#volNum").removeAttr("disabled");
				$j("#volLetExt").removeAttr("disabled");

				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsVolumeDiv").html(html);
					} else {
					<c:choose> 
						<c:when test="${command.summaryId == 0}"> 
							$j("#body_left").html(html);
						</c:when> 
						<c:otherwise> 
							$j("#EditDetailsVolumeDiv").html(html);
						</c:otherwise> 
					</c:choose> 
					}
				}});
				return false;
				
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<div id="questionEndDate" class="wrongEndDate" style="display:none; cursor: default">
		<h1>Volume End Year should not be dated before the Volume Start Year</h1>
		<input type="button" id="okEndDate" value="Ok" />
</div>

<div id="questionFormatDate" class="wrongFormatDate" style="display:none; cursor: default">
		<h1>Date format is incorrect</h1>
		<input type="button" id="okFormatDate" value="Ok" />
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditDetailsVolumeDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
		
		$j("#okEndDate").click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".wrongEndDate").hide();
			$j("#EditDetailsVolumeDiv").append($j(".wrongEndDate"));
			$j(".blockUI").remove();
			return false;
		});
		
		$j("#okFormatDate").click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".wrongFormatDate").hide();
			$j("#EditDetailsVolumeDiv").append($j(".wrongFormatDate"));
			$j(".blockUI").remove();
			return false;
		});
		
		
     
	});
</script>