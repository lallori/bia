<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
  
     <h4><fmt:message key="volbase.printVolume.documentary"/><br /><fmt:message key="volbase.printVolume.volumeReport"/></h4>

     
     <h3 class="first">${volume.serieList}</h3>
     <table>
         <tr> 
             <td width="100"><fmt:message key="volbase.printVolume.volums"/></td>
             <td width="285" class="value">${volume.volNum}${volume.volLetExt}</td>
         </tr>
         <tr>
             <td width="100"><fmt:message key="volbase.printVolume.startDate"/></td>

             <td width="285" class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</td>
         </tr>
         <tr>
             <td width="100"><fmt:message key="volbase.printVolume.endDate"/></td>
             <td width="285" class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</td>
         </tr>
         <tr>

             <td width="100"><fmt:message key="volbase.printVolume.dateNotes"/></td>
             <td width="285" class="value">${volume.dateNotes}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
     
     <h5><fmt:message key="volbase.printVolume.title.description"/></h5>
     <table>
         <tr> 
             <td width="90"<fmt:message key="volbase.printVolume.organizationalCriteria"/></td>
             <td width="330" class="value" colspan="3">${volume.orgNotes}</td>
         </tr>
         <tr>
             <td width="70"><fmt:message key="volbase.printVolume.condition"/></td>
             <td width="330" class="value" colspan="3">${volume.ccondition}</td>
         </tr>
         <tr> 
             <td width="70"><fmt:message key="volbase.printVolume.bound"/></td>
             <td width="130" class="value">${volume.bound ? 'Yes' : 'No'}</td>
             <td width="150"><fmt:message key="volbase.printVolume.foliosNumbered"/></td>
             <td width="30" class="valueRight">${volume.folsNumbrd ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70"><fmt:message key="volbase.printVolume.folioCount"/></td>
             <td width="150" class="value">${volume.folioCount}</td>

             <td width="100"><fmt:message key="volbase.printVolume.indexOfNames"/></td>
             <td width="50" class="valueRight">${volume.oldAlphaIndex ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70"><fmt:message key="volbase.printVolume.printedMaterial"/></td>
             <td width="150" class="value">${volume.printedMaterial ? 'Yes' : 'No'}</td>
             <td width="100"><fmt:message key="volbase.printVolume.printedDrawings"/></td>
             <td width="50" class="valueRight">${volume.printedDrawings ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70"><fmt:message key="volbase.printVolume.languages"/></td>
             <td width="150" class="valueLeft" colspan="3">${volume.italian ? 'Italian' : '' } 
									${volume.spanish ? 'Spanish' : ''}
									${volume.english ? 'English' : ''}
									${volume.latin ? 'Latin' : ''}
									${volume.german ? 'German' : ''}
									${volume.french ? 'French' : ''}
									${volume.otherLang}</td>
         </tr>
         <tr>
             <td width="130"><fmt:message key="volbase.printVolume.cipher"/></td>
             <td width="50" class="value">${volume.cipher ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70"><fmt:message key="volbase.printVolume.cipherNotes"/></td>
             <td width="350" class="value" colspan="3">${volume.cipherNotes}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>

     
     <h5><fmt:message key="volbase.printVolume.title.correspondents"/></h5>
     <table>
         <tr> 
           <td width="70"><fmt:message key="volbase.printVolume.from"/></td>
           <td class="value">${volume.senders}</td>
         </tr>
         <tr> 
           <td width="70"><fmt:message key="volbase.printVolume.to"/></td>
           <td class="value">${volume.recips}</td>
         </tr>
     </table>
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
     
     <h5><fmt:message key="volbase.printVolume.title.context"/></h5>
     <table>
         <tr> 
           <td width="70"><fmt:message key="volbase.printVolume.context"/></td>

           <td class="value">${volume.ccontext}</td>
         </tr>
         <tr> 
           <td width="70"><fmt:message key="volbase.printVolume.inventarioSommarioDescription"/></td>
           <td class="value">${volume.inventarioSommarioDescription}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
