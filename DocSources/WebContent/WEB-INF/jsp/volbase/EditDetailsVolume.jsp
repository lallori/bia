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
				<form:label id="seriesRefDescriptionLabel" for="seriesRefDescription" path="seriesRefDescription" cssErrorClass="error"><i>Series List</i></form:label>
				<form:input id="seriesRefDescriptionAutoCompleter" path="seriesRefDescription" cssClass="input_30c"/>
			</div>

			<div>
				<form:label id="volNumLabel" for="volNum" path="volNum" cssStyle="margin-left:78px" cssErrorClass="error" >Volume/Filza (MDP): </form:label><form:input id="volNum" path="volNum" cssClass="input_5c" maxlength="5"/><form:label id="volLetExtLabel" for="volLetExt" path="volLetExt" cssErrorClass="error" cssStyle="margin-left:10px">Volume extension:</form:label>
				<form:input id="volLetExt" path="volLetExt" size="1" maxlength="1"  cssClass="input_1c"/>
			</div>

			<div>
				<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Start year: </form:label><form:input id="startYear" path="startYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="startMonthLabel" for="startMonth" path="startMonth" cssStyle="margin-left:19px" cssErrorClass="error">Start month: </form:label>
				<form:select id="startMonth" path="startMonth" cssClass="selectform"><form:options items="${months}" itemValue="monthName" itemLabel="monthName"/></form:select>
				<form:label id="startDayLabel" for="startDay" path="startDay" style="margin-left:19px" cssErrorClass="error">Start day: </form:label>
				<form:input id="startDay" path="startDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssStyle="margin-left:4px" cssErrorClass="error">End year: </form:label>
				<form:input id="endYear" path="endYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="endMonthLabel" for="endMonth" path="endMonth" cssStyle="margin-left:23px" cssErrorClass="error">End month: </form:label>
				<form:select id="endMonth" path="endMonth" cssClass="selectform"><form:options items="${months}" itemValue="monthName" itemLabel="monthName"/></form:select>
				<form:label id="endDayLabel" for="endDay" path="endDay" cssStyle="margin-left:24px" cssErrorClass="error">End day: </form:label>
				<form:input id="endDay" path="endDay" cssClass="input_2c" maxlength="2"/>
			</div>

			<div style="margin:5px 0 0 0">
				<form:label id="dateNotesLabel" for="dateNotes" path="dateNotes" cssErrorClass="error">Date notes: </form:label>
			</div>

			<div style="margin:0">
				<form:textarea id="dateNotes" path="dateNotes" cssClass="txtarea"/>
			</div>

			<form:errors path="seriesRefDescription" cssClass="inputerrors"/>
			<form:errors path="volNum" cssClass="inputerrors"/>
			<form:errors path="volLetExt" cssClass="inputerrors"/>
			<form:errors path="startYear" cssClass="inputerrors"/>
			<form:errors path="startMonth" cssClass="inputerrors"/>
			<form:errors path="startDay" cssClass="inputerrors"/>
			<form:errors path="endYear" cssClass="inputerrors"/>
			<form:errors path="endMonth" cssClass="inputerrors"/>
			<form:errors path="endDay" cssClass="inputerrors"/>
			<form:errors path="dateNotes" cssClass="inputerrors"/>
			
			<form:hidden path="summaryId"/>
			<form:hidden path="seriesRefNum"/>
			<form:hidden path="dateCreated"/>

			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
		</fieldset>	
	</form:form>

	<c:url var="findSeriesUrl" value="/de/volbase/FindSeries.json"/>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<script type="text/javascript">
		$(document).ready(function() {
	        $("#EditContextVolume").removeAttr("href"); 
	        $("#EditCorrespondentsVolume").removeAttr("href"); 
	        $("#EditDescriptionVolume").removeAttr("href"); 

	        // We disable
			<c:if test="${command.summaryId == 0}"> 
				$("#volNum").attr('disabled','true');
				$("#volLetExt").attr('disabled','true');
	        </c:if>

			var showVolumeExplorer = function (){
				$.get('<c:url value="/de/volbase/FindVolume.json" />', { volNum: $("#volNum").val(), volLetExt: $("#volLetExt").val() },
					function(data){
						if (data.summaryId == "") {
							$("#save").removeAttr("disabled");
							$.get('<c:url value="/src/volbase/ShowExplorerVolume.do" />', { volNum: $("#volNum").val(), volLetExt: $("#volLetExt").val(), flashVersion : true },
								function(data){
									$("#body_right").html(data);
									return true;
								}
							);
						} else {
							$("#save").attr("disabled","true");
						}
					}
				);
	 		}
			$("#volNum").change(showVolumeExplorer);
			$("#volLetExt").change(showVolumeExplorer);	        	

			var a = $('#seriesRefDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${findSeriesUrl}',
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
				$.unblockUI();$(".blockUI").fadeOut("slow");
	            return false; 
	        }); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

			$("#EditDetailsVolumeForm").submit(function (){
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
