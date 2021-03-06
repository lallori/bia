<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json">
	</c:url>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<body class="search">
	<div id="advancedSearch">
		<div id="advancedSearch_top"><fmt:message key="search.advancedSearchVolumes.title"/></div>
		<div id="body_left">
			<div id="customSearchFilterDiv">
				<h1 class="advSearchTitle"><fmt:message key="search.advancedSearchVolumes.createCustomSearchFilter"/></h1>
					<div id="accordion">
					
					<h1 id="volumeSearch"><a><fmt:message key="search.advancedSearchVolumes.volume.title"/></a></h1>
					<div class="volumes">
		    			<div class="listAdvSearch">
		    				<form id="volumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volume.exactlyorbetween"></fmt:message>">?</a>
										<select id="volumeType" name="volumeType" class="selectform_long">
											<option value="Exactly" selected="selected"><fmt:message key="search.advancedSearchVolumes.volume.exatcly"/></option>
											<option value="Between"><fmt:message key="search.advancedSearchVolumes.volume.between"/></option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="volume"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_l"><input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>" class="volumeAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="Volume">
					               	</div>
					            </div>
				            </form>
				        </div>
				        <div class="listAdvSearch">
				        	<form id="insertSearchForm" method="post" class="edit">
				        		<div class="row">
				        			<div class="col_l">
				        				<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.insert"></fmt:message>">?</a>
				        				<span><fmt:message key="search.advancedSearchVolumes.volume.insert"/></span>
				        			</div>
				        			<div class="col_l"><input type="text" id="insert" name="insert" class="input_5c"/></div>
									<div class="col_l"></div>
									<div class="col_r">
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
										<input type="hidden" id="category" value="search.advancedSearchVolumes.volume.insert">
									</div>
				        		</div>
				        	</form>
				        </div>
				        
						<hr />
						
						<div class="listAdvSearch">
							<form id="dateSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volume.writtenwhen"></fmt:message>">?</a>
										<select id="dateType" name="dateType" class="selectform_Llong">
											<option value="From"><fmt:message key="search.advancedSearchVolumes.volume.writtenFrom"/></option>
											<option value="Before"><fmt:message key="search.advancedSearchVolumes.volume.writtenBefore"/></option>
											<option value="Between"><fmt:message key="search.advancedSearchVolumes.volume.writtenBetween"/></option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="dateYear" class="input_4c" maxlength="4" value="yyyy"/></div>
					               	<div class="col_l">
					               		<select id="dateMonth" name="dateMonth" class="selectform">
											<c:forEach items="${months}" var="month">
												<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
											</c:forEach>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>" class="visible button_small">
										<input type="hidden" id="category" value="Date">
					               	</div>
					            </div>
					               	
					            <div class="row">
					               	<div class="col_l"><p class="invisible"><fmt:message key="search.advancedSearchVolumes.volume.writtenBetweenAnd"/></p></div>
					               	<div class="col_l"><input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/></div>
					               	<div class="col_l">
					               		<select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
											<c:forEach items="${months}" var="month">
												<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
											</c:forEach>
						                </select>
					               	</div>
					               	<div class="col_l"><input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>" class="invisible button_small">
					               	</div>
					            </div>
					        </form>
				        </div>
					</div>
		
					<h1 id="volumeDesc"><a><fmt:message key="search.advancedSearchVolumes.volumeDescription.title"/></a></h1>
					<div class="volumes">
						<div class="listAdvSearch">
							<form id="digitizedSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volumedescription.digitized"></fmt:message>">?</a>
										<label for="digitized" id="digitizedLabel"><fmt:message key="search.advancedSearchVolumes.volumeDescription.digitized"/></label> 
					               	</div>
					               	<div class="col_l">
					               		<select id="digitized" name="digitized" class="selectform_short">
					                    	<option value="" selected="selected"></option>
					                   		<option value="Yes"><fmt:message key="search.advancedSearchVolumes.yes"/></option>
					                    	<option value="No"><fmt:message key="search.advancedSearchVolumes.no"/></option>
					                	</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
		                				<input type="hidden" id="category" value="Digitized">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="cipherSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.cipher"></fmt:message>">?</a>
			                			<label for="cipher" id="cipherLabel"><fmt:message key="search.advancedSearchVolumes.volumeDescription.cipher"/></label> 
					               	</div>
					               	<div class="col_l">
					               		<select id="cipher" name="cipher" class="selectform_short">
						                    <option value="" selected="selected"></option>
						                    <option value="Yes"><fmt:message key="search.advancedSearchVolumes.volumeDescription.cipherYes"/></option>
						                    <option value="No"><fmt:message key="search.advancedSearchVolumes.volumeDescription.cipherNo"/></option>
						                </select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			               	 			<input type="hidden" id="category" value="Cipher">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="indexSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="Limit search to volumes containing an index of names"></fmt:message>">?</a>
			                			<label for="index" id="indexLabel"><fmt:message key="search.advancedSearchVolumes.volumeDescription.indexOfNames"/></label>
					               	</div>
					               	<div class="col_l">
					               		<select id="index" name="index" class="selectform_short">
						                    <option value="" selected="selected"></option>
						                    <option value="Yes"><fmt:message key="search.advancedSearchVolumes.volumeDescription.indexOfNamesYes"/></option>
						                    <option value="No"><fmt:message key="search.advancedSearchVolumes.volumeDescription.indexOfNamesNo"/></option>
						                </select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="Index">
					               	</div>
					            </div>
				        	</form>
				        </div>
		
						<div class="listAdvSearch">
							<form id="languagesSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volumedescription.languages"></fmt:message>">?</a>
			                			<label for="languages" id="languagesLabel"><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages"/></label> 
					               	</div>
					               	<div class="col_r">
					               		<label for="Italian" id="italianLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.italian"/></i></label>
			                			<input type="checkbox" name="italian" value="Italian"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="French" id="frenchLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.french"/></i></label>
			                			<input type="checkbox" name="french" value="French"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="German" id="germanLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.german"/></i></label>
			                			<input type="checkbox" name="german" value="German"\/>
					               	</div>
					               	<div class="col_r"></div>
					            </div>
					            
					            <div class="row">
					            	<div class="col_l"></div>
					               	<div class="col_r">
					               		<label for="Spanish" id="spanishLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.spanish"/></i></label>
			                			<input type="checkbox" name="spanish" value="Spanish"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="Latin" id="latinLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.latin"/></i></label>
			                			<input type="checkbox" name="latin" value="Latin"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="English" id="englishLabel"><i><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.english"/></i></label>
			                			<input type="checkbox" name="english" value="English"\/>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="Languages">
			                		</div>
					            </div>
				            </form>
				        </div> 
				        
				        <div class="listAdvSearch">
				        	<form id="otherLangSearchForm" method="post" class="edit">
				        		<div class="row">
				        			<div class="col_l">
				        				<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volumedescription.otherlanguages"></fmt:message>">?</a>
				        				<label for="otherLang" id="otherLangLabel"><fmt:message key="search.advancedSearchVolumes.volumeDescription.languages.other"/></label>
										<input type="text" id="otherLang" class="input_24c"/><!-- AUTOCOMPLETE -->
									</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>" class="otherLangAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="other Lang">
					               	</div>
					            </div>
					        </form>
				        </div>
					</div>
					
					<h1 id="coorCont"><a><fmt:message key="search.advancedSearchVolumes.correspondentsAndContext"/></a></h1>
					<div class="volumes">
						<div class="listAdvSearch">
							<form id="fromVolumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.correspondentandcontext.from"></fmt:message>">?</a>
			                			<label for="fromVolume" id="fromLabel"><fmt:message key="search.advancedSearchVolumes.correspondentsAndContext.from"/></label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="fromVolume" name="fromVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="From">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="toVolumeSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.correspondentandcontext.to"></fmt:message>">?</a>
			                			<label for="toVolume" id="toLabel"><fmt:message key="search.advancedSearchVolumes.correspondentsAndContext.to"/></label> 
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="toVolume" name="toVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="To">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="contextSearchForm" method="post" class="edit">
								<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.correspondentandcontext.context"></fmt:message>">?</a>
			                			<label for="context" id="contextLabel"><fmt:message key="search.advancedSearchVolumes.correspondentsAndContext.context"/></label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="context" name="context" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="Context">
			                		</div>
					            </div>
				            </form>
				            
				            <form id="inventarioSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.correspondentandcontext.inventariosommariodescription"></fmt:message>">?</a>
			                			<label for="inventario" id="inventarioLabel"><fmt:message key="search.advancedSearchVolumes.correspondentsAndContext.inventarioSommario"/></label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="inventario" name="inventario" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
			                			<input type="hidden" id="category" value="Inventario">
					               	</div>
					            </div>
				            </form>		            
				        </div>
			         </div>
			         
			         <h1 id="volumeIdSearch"><a><fmt:message key="search.advancedSearchVolumes.volumeId.title"/></a></h1>
					 <div>
						<div class="listAdvSearch">
							<form id="volumeIdSearchForm" method="post" class="edit">
								<div class="row">
									<div class="col_l">
										<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.volumeid"></fmt:message>">?</a>
										<input id="volumeId" name="volumeId" class="input_7c" type="text" maxlength="5" />
									</div>
									<div class="col_r">
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
										<input type="hidden" id="category" value="Volume Id">
									</div>
								</div>
							</form>
						</div>
					</div>
			         
			         <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			         <h1 id="logicalDeleteSearch"><a><fmt:message key="search.advancedSearchVolumes.logicalDelete.title"/></a></h1>
			         <div class="volumes">
			         	<div class="listAdvSearch">
			         		<form id="logicalDeleteSearchForm" method="post" class="edit">
			         			<div class="row">
			         				<div class="col_l">
			         					<a class="helpIcon" title="<fmt:message key="search.advancedSearchVolumes.help.logicaldelete"></fmt:message>">?</a>
			         					<label for="logicalDelete" id="logicalDeleteLabel"><fmt:message key="search.advancedSearchVolumes.logicalDelete.title"/></label>
			         					<input type="checkbox" name="logicalDelete" value="true"/>
			         				</div>
			         				<div class="col_r">
			         					<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchVolumes.addToYourSearchFilter.alt"/>">
										<input type="hidden" id="category" value="Logical Delete">
									</div>
								</div>
							</form>
			         	</div>
			         </div>
			         </security:authorize>
				</div>
			</div>

<c:url var="searchVolumeURL" value="/src/SearchVolume.json"/>
<c:url var="searchOtherLangURL" value="/src/SearchOtherLang.json"/>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#dateMonth option:eq(0)").text("mm");
		$j("#dateMonth option:eq(0)").attr('selected', 'selected');
		$j("#dateMonthBetween option:eq(0)").text("mm");
		$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

		$j("#volumeType").change(function(){
			if(this.options[1].selected) 
				$j('#volumeBetween').css('visibility','visible'); 
		    else 
				$j('#volumeBetween').css('visibility','hidden');
		});	

		$j("#dateType").change(function(){
			if(this.options[2].selected) { 
				$j("#dateYearBetween").css('visibility','visible');
				$j("#dateMonthBetween").css('visibility','visible');
				$j("#dateDayBetween").css('visibility','visible');
				$j('.invisible').css('visibility','visible');
				$j('.visible').css('visibility','hidden');
		   } else { 
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
			}
		});
		
		$j('#dateYear').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateYearBetween').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateDay').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});
		$j('#dateDayBetween').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});

		$j("#volumeSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#insertSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#dateSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#digitizedSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#languagesSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#otherLangSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#cipherSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#indexSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#fromVolumeSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#toVolumeSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#contextSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#inventarioSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#volumeIdSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#logicalDeleteSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		
		$j('#accordion').accordion({
			active: false, 
			autoHeight: false,
			collapsible:true
			});
		$j('#volumeSearch').click(function(){
			$j.scrollTo({top:'0px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "0px"}, "slow");
			return false;
		});
		$j('#volumeDesc').click(function(){
			$j.scrollTo({top:'113px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "70px"}, "slow");
			return false;
		});
		$j('#coorCont').click(function(){
			$j.scrollTo({top:'140px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "100px"}, "slow");
			return false;
		});
		$j('#volumeIdSearch').click(function(){
			$j.scrollTo({top:'168px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "125px"}, "slow");
			return false;
		});
		
		$j("#dateSearchForm :input").keyup(function(){
			if($j("#dateType option:selected").val() == 'Between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
					
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			}
		});
		
		$j("#dateType").change(function(){
			if($j("#dateType option:selected").val() == 'Between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
					
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			}
		});
		
		$j("#dateSearchForm").submit(function(){
			$j('#dateType option[value="After"]').attr('selected', 'selected');
			$j('#dateYearBetween').css('visibility','hidden');
			$j('#dateMonthBetween').css('visibility','hidden');
			$j('#dateDayBetween').css('visibility','hidden');
			$j('.invisible').css('visibility','hidden');
			$j('.visible').css('visibility','visible');
		});
		
		var $volumeAutocomplete = $j("#volume").autocompleteGeneral({
			serviceUrl: '${searchVolumeURL}',
		    loadingImageUrl:'${LoadingImageURL}',
			minChars: 1,
			delimiter: null,
			maxHeight: 400,
			width: 200,
			zIndex: 9999,
			deferRequestBy: 0,
			noCache: true,
			onSelect: function(value, data){
				
			}
		});
		
		$j("#volume").blur(function(){
			$volumeAutocomplete.killSuggestions();
			return false;
		});
		
		$j("#volume").change(function(){
			if($j(this).val() != ''){
				$j(".volumeAdd").removeAttr("disabled");
				$j(".volumeAdd").attr("disabled");
				$j(".volumeAdd").prop("disabled", false);
			}					
		});
		
		$j(".volumeAdd").click(function(){
			if($j("#volume").val() == '')
				return false;
		});
		
// 		var $volBetweenAutocomplete = $j("#volumeBetween").autocompleteGeneral({
// 			serviceUrl: '${searchVolumeURL}',
//		    loadingImageUrl:'${LoadingImageURL}',
// 			minChars: 1,
// 			delimiter: null,
// 			maxHeight: 400,
// 			width: 200,
// 			zIndex: 9999,
// 			deferRequestBy: 0,
// 			noCache: true,
// 			onSelect: function(value, data){
				
// 			}
// 		});
		
// 		$j("#volumeBetween").blur(function(){
// 			$volBetweenAutocomplete.killSuggestions();
// 			return false;
// 		});
		
		var $otherLangAutocomplete = $j("#otherLang").autocompleteGeneral({
			serviceUrl: '${searchOtherLangURL}',
		    loadingImageUrl:'${LoadingImageURL}',
			minChars: 1,
			delimiter: null,
			maxHeight: 400,
			width: 200,
			zIndex: 9999,
			deferRequestBy: 0,
			noCache: true,
			onSelect: function(value, data){
				
			}
		});
		
		$j("#otherLang").blur(function(){
			$otherLangAutocomplete.killSuggestions();
			return false;
		});
		
		$j("#otherLang").change(function(){
			if($j(this).val() != ''){
				$j(".otherLangAdd").removeAttr("disabled");
				$j(".otherLangAdd").attr("disabled");
				$j(".otherLangAdd").prop("disabled", false);
			}					
		});
		
		$j(".otherLangAdd").click(function(){
			if($j("#otherLang").val() == '')
				return false;
		});
	});
</script>
