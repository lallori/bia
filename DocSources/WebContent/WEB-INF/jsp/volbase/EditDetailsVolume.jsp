<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


	<div id="createdby"><h6>CREATED BY ${command.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${command.dateCreated}" /></h6></div>
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
				<form:select id="startMonthNum" path="startMonthNum" cssClass="selectform"><form:options items="${months}" itemValue="monthNum" itemLabel="monthName"/></form:select>
				<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Start day: </form:label>
				<form:input id="startDay" path="startDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">End year: </form:label>
				<form:input id="endYear" path="endYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="endMonthNumLabel" for="endMonthNum" path="endMonthNum" cssErrorClass="error">End month: </form:label>
				<form:select id="endMonthNum" path="endMonthNum" cssClass="selectform"><form:options items="${months}" itemValue="monthNum" itemLabel="monthName"/></form:select>
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
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
	</form:form>

	<c:url var="searchSeriesListUrl" value="/de/volbase/SearchSeriesList.json"/>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<script type="text/javascript">
		$(document).ready(function() {
	        $("#EditContextVolume").removeAttr("href"); 
	        $("#EditCorrespondentsVolume").removeAttr("href"); 
	        $("#EditDescriptionVolume").removeAttr("href"); 

	        // We disable
			<c:if test="${command.summaryId != 0}"> 
				$("#volNum").attr("disabled","true");
				$("#volLetExt").attr("disabled","true");
	        </c:if>

			var showVolumeExplorer = function (){
				$.get('<c:url value="/de/volbase/FindVolume.json" />', { volNum: $("#volNum").val(), volLetExt: $("#volLetExt").val() },
					function(data){
						if (data.summaryId == "") {
							if ($("#volExist").length > 0) {
								$("#volExist").remove();
							}
							$("#save").removeAttr("disabled");
							$.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { volNum: $("#volNum").val(), volLetExt: $("#volLetExt").val(), flashVersion : true },
								function(data){
									$("#body_right").html(data);
									return true;
								}
							);
						} else {
							if ($("#volExist").length == 0) {
								$("#close").before("<span class=\"inputerrors\" id=\"volExist\">Volume is already present, you cannot add again this volume. Save is disabled.<br></span>");
							}
							$("#save").attr("disabled","true");
						}
					}
				);
	 		}
			$("#volNum").change(showVolumeExplorer);
			$("#volLetExt").change(showVolumeExplorer);	        	

			var a = $('#seriesRefDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchSeriesListUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#seriesRefNum').val(data); }
			});

			$('#close').click(function() {
	            $('#EditDetailsVolumeDiv').block({ message: $('#question') }); 
				return false;
			});
	        
			$('#no').click(function() { 
				$.unblockUI();
				$(".blockUI").fadeOut("slow");
				$('#question').hide();
				$('#EditDetailsVolumeDiv').append($("#question"));
				$(".blockUI").remove();
	            return false; 
	        }); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

			$("#EditDetailsVolumeForm").submit(function (){
				$("#volNum").removeAttr("disabled");
				$("#volLetExt").removeAttr("disabled");

				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					if(html.match(/inputerrors/g)){
						$("#EditDetailsVolumeDiv").html(html);
					} else {
						<c:choose> 
							<c:when test="${command.summaryId == 0}"> 
								$("#body_left").html(html);
							</c:when> 
							<c:otherwise> 
								$("#EditDetailsVolumeDiv").html(html);
							</c:otherwise> 
						</c:choose> 
					}
				}});
				return false;
			});
		});
	</script>
