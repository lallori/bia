<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditDetailsVolumeForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>VOLUME DETAILS</b></legend>
			<div>
				<form:label id="seriesRefDescriptionLabel" for="seriesRefDescription" path="seriesRefDescription" cssErrorClass="error"><i>Carteggio</i></form:label>
				<form:input id="seriesRefDescriptionAutoCompleter" path="seriesRefDescription" cssClass="input_30c" />
			</div>

			<div>
				<form:label id="volNumLabel" for="volNum" path="volNum" cssErrorClass="error" >Volume/Filza (MDP): </form:label><form:input id="volNum" path="volNum" cssClass="input_5c" maxlength="5"/><form:label id="volLetExtLabel" for="volLetExt" path="volLetExt" cssErrorClass="error">Volume extension:</form:label>
				<form:input id="volLetExt" path="volLetExt" size="1" maxlength="1"  cssClass="input_1c"/>
			</div>

			<div>
				<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Start year: </form:label><form:input id="startYear" path="startYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="startMonthNumLabel" for="startMonthNum" path="startMonthNum" cssErrorClass="error">Start month: </form:label>
				<form:select id="startMonthNum" path="startMonthNum" cssClass="selectform"><form:option value="">&nbsp;</form:option><form:options items="${months}" itemValue="monthNum" itemLabel="monthName"/></form:select>
				<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Start day: </form:label>
				<form:input id="startDay" path="startDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">End year: </form:label>
				<form:input id="endYear" path="endYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="endMonthNumLabel" for="endMonthNum" path="endMonthNum" cssErrorClass="error">End month: </form:label>
				<form:select id="endMonthNum" path="endMonthNum" cssClass="selectform"><form:option value="">&nbsp;</form:option><form:options items="${months}" itemValue="monthNum" itemLabel="monthName"/></form:select>
				<form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">End day: </form:label>
				<form:input id="endDay" path="endDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div>
				<form:label id="dateNotesLabel" for="dateNotes" path="dateNotes" cssErrorClass="error">Date notes: </form:label>
			</div>

			<div>
				<form:textarea id="dateNotes" path="dateNotes" cssClass="txtarea"/>
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
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<input id="save" type="submit" value="" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
	</form:form>

	<c:url var="searchSeriesListUrl" value="/de/volbase/SearchSeriesList.json"/>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsVolume").css('visibility', 'hidden'); 
	        $j("#EditDescriptionVolume").css('visibility', 'hidden'); 
			$j("#EditDetailsVolume").css('visibility', 'hidden'); 

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
							$j.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { volNum: $j("#volNum").val(), volLetExt: $j("#volLetExt").val(), flashVersion : true },
								function(data){
									$j("#body_right").html(data);
									return true;
								}
							);
						} else {
							if ($j("#volExist").length == 0) {
								$j("#close").before("<span class=\"inputerrors\" id=\"volExist\">Volume is already present, you cannot add again this volume. Save is disabled.<br></span>");
							}
							$j("#save").attr("disabled","true");
						}
					}
				);
	 		}
			$j("#volNum").change(showVolumeExplorer);
			$j("#volLetExt").change(showVolumeExplorer);	        	

			var a = $j('#seriesRefDescriptionAutoCompleter').autocomplete({ 
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

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>