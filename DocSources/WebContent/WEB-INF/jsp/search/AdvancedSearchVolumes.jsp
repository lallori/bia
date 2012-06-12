<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<body>
	<div id="advancedSearch">
		<div id="advancedSearch_top">SEARCH FOR VOLUMES</div>
		<div id="body_left">
			<div id="customSearchFilterDiv">
				<h1 class="advSearchTitle">Create your custom search filter</h1>
					<div id="accordion">
					
					<h1 id="volumeSearch"><a>Volume</a></h1>
					<div class="volumes">
		    			<div class="listAdvSearch">
		    				<form id="volumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
										<select id="volumeType" name="volumeType" class="selectform_long">
											<option value="Exactly" selected="selected">Exactly</option>
											<option value="Between">Between</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="volume"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_l"><input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="volumeAdd" disabled="disabled">
										<input type="hidden" id="category" value="Volume">
					               	</div>
					            </div>
				            </form>
				        </div>
				        
						<hr />
						
						<div class="listAdvSearch">
							<form id="dateSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
										<select id="dateType" name="dateType" class="selectform_Llong">
											<option value="From">Written from</option>
											<option value="Before">Written before</option>
											<option value="Between">Written between</option>
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
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="visible">
										<input type="hidden" id="category" value="Date">
					               	</div>
					            </div>
					               	
					            <div class="row">
					               	<div class="col_l"><p class="invisible">and</p></div>
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
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="invisible">
					               	</div>
					            </div>
					        </form>
				        </div>
					</div>
		
					<h1 id="volumeDesc"><a>Volume Description</a></h1>
					<div class="volumes">
						<div class="listAdvSearch">
							<form id="digitizedSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
										<label for="digitized" id="digitizedLabel">Digitized</label> 
					               	</div>
					               	<div class="col_l">
					               		<select id="digitized" name="digitized" class="selectform_short">
					                    	<option value="" selected="selected"></option>
					                   		<option value="Yes">Yes</option>
					                    	<option value="No">No</option>
					                	</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
		                				<input type="hidden" id="category" value="Digitized">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="cipherSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="cipher" id="cipherLabel">Cipher</label> 
					               	</div>
					               	<div class="col_l">
					               		<select id="cipher" name="cipher" class="selectform_short">
						                    <option value="" selected="selected"></option>
						                    <option value="Yes">Yes</option>
						                    <option value="No">No</option>
						                </select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			               	 			<input type="hidden" id="category" value="Cipher">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="indexSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="index" id="indexLabel">Index of names</label>
					               	</div>
					               	<div class="col_l">
					               		<select id="index" name="index" class="selectform_short">
						                    <option value="" selected="selected"></option>
						                    <option value="Yes">Yes</option>
						                    <option value="No">No</option>
						                </select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="Index">
					               	</div>
					            </div>
				        	</form>
				        </div>
		
						<div class="listAdvSearch">
							<form id="languagesSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="languages" id="languagesLabel">Languages</label> 
					               	</div>
					               	<div class="col_r">
					               		<label for="Italian" id="italianLabel"><i>Italian</i></label>
			                			<input type="checkbox" name="italian" value="Italian"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="French" id="frenchLabel"><i>French</i></label>
			                			<input type="checkbox" name="french" value="French"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="German" id="germanLabel"><i>German</i></label>
			                			<input type="checkbox" name="german" value="German"\/>
					               	</div>
					               	<div class="col_r"></div>
					            </div>
					            
					            <div class="row">
					            	<div class="col_l"></div>
					               	<div class="col_r">
					               		<label for="Spanish" id="spanishLabel"><i>Spanish</i></label>
			                			<input type="checkbox" name="spanish" value="Spanish"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="Latin" id="latinLabel"><i>Latin</i></label>
			                			<input type="checkbox" name="latin" value="Latin"\/>
					               	</div>
					               	<div class="col_r">
					               		<label for="English" id="englishLabel"><i>English</i></label>
			                			<input type="checkbox" name="english" value="English"\/>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="Languages">
			                		</div>
					            </div>
				            </form>
				        </div> 
				        
				        <div class="listAdvSearch">
				        	<form id="otherLangSearchForm" method="post" class="edit">
				        		<div class="row">
				        			<div class="col_l">
				        				<a class="helpIcon" title="">?</a>
				        				<label for="otherLang" id="otherLangLabel">Other Languages</label>
										<input type="text" id="otherLang" class="input_24c"/><!-- AUTOCOMPLETE -->
									</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="otherLangAdd" disabled="disabled">
										<input type="hidden" id="category" value="other Lang">
					               	</div>
					            </div>
					        </form>
				        </div>
					</div>
					
					<h1 id="coorCont"><a>Correspondents and Context</a></h1>
					<div class="volumes">
						<div class="listAdvSearch">
							<form id="fromVolumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="fromVolume" id="fromLabel">From</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="fromVolume" name="fromVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="From">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="toVolumeSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="toVolume" id="toLabel">To</label> 
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="toVolume" name="toVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="To">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="contextSearchForm" method="post" class="edit">
								<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="context" id="contextLabel">Context</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="context" name="context" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="Context">
			                		</div>
					            </div>
				            </form>
				            
				            <form id="inventarioSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			                			<label for="inventario" id="inventarioLabel">Inventario Sommario Description (Italian)</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="inventario" name="inventario" class="txtarea_search"></textarea><!-- no autocompleter but word search --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
			                			<input type="hidden" id="category" value="Inventario">
					               	</div>
					            </div>
				            </form>		            
				        </div>
			         </div>
				</div>
			</div>

<c:url var="searchVolumeURL" value="/src/SearchVolume.json"/>

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

		$j("#volumeSearchForm").advancedSearchForm();
		$j("#dateSearchForm").advancedSearchForm();
		$j("#digitizedSearchForm").advancedSearchForm();
		$j("#languagesSearchForm").advancedSearchForm();
		$j("#otherLangSearchForm").advancedSearchForm();
		$j("#cipherSearchForm").advancedSearchForm();
		$j("#indexSearchForm").advancedSearchForm();
		$j("#fromVolumeSearchForm").advancedSearchForm();
		$j("#toVolumeSearchForm").advancedSearchForm();
		$j("#contextSearchForm").advancedSearchForm();
		$j("#inventarioSearchForm").advancedSearchForm();
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
		
		var $volBetweenAutocomplete = $j("#volumeBetween").autocompleteGeneral({
			serviceUrl: '${searchVolumeURL}',
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
		
		$j("#volumeBetween").blur(function(){
			$volBetweenAutocomplete.killSuggestions();
			return false;
		});
		
		var $otherLangAutocomplete = $j("#otherLang").autocompleteGeneral({
			serviceUrl: '${searchOtherLangURL}',
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
