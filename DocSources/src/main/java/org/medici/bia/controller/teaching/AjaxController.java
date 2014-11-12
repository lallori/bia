/*
 * AjaxController.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.bia.controller.teaching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.command.teaching.ShowTeachingUserSearchCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.CourseUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.CourseCheckPoint;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for the Teaching module.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller("TeachingAjaxController")
public class AjaxController {
	
	@Autowired
	private TeachingService teachingService;
	@Autowired
	private UserService userService;

	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/teaching/askAQuestion.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> askAQuestion(
			@RequestParam(value="courseTranscriptionTopicId", required=false) Integer courseTranscriptionTopicId,
			@RequestParam(value="forumContainerId", required=false) Integer forumContainerId,
			@RequestParam(value="questionTitle", required=true) String questionTitle,
			@RequestParam(value="questionText", required=true) String questionText,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			if (forumContainerId != null || courseTranscriptionTopicId != null) {
				ForumTopic courseTopic = getTeachingService().askAQuestion(
						forumContainerId, 
						courseTranscriptionTopicId,
						questionTitle, 
						questionText, 
						httpServletRequest.getRemoteAddr());
				
				model.put("redirectURL", HtmlUtils.getTeachingShowTopicForumHrefUrl(courseTopic) + "&completeDOM=true");
				model.put("operation", "OK");
			} else {
				model.put("error", "no forum container nor course transcription topic provided!");
				model.put("operation", "KO");
			}
		} catch (ApplicationThrowable th) {
			model.put("error", th.toString());
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/CreateCourseTranscription", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCourseTranscription(
			@RequestParam(value="entryId", required=true) Integer docId,
			@RequestParam(value="courseTitle", required=true) String topicTitle,
			@RequestParam(value="courseId", required=true) Integer courseId,
			@RequestParam(value="transcriptionMode", required=false) String mode,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			CourseTopicMode topicMode = null;
			try {
				topicMode = StringUtils.isNullableString(mode) ? CourseTopicMode.I : CourseTopicMode.valueOf(mode.trim());
			} catch (Exception e) {
				topicMode = CourseTopicMode.I; 
			}
			ForumTopic courseTopic = getTeachingService().addCourseTopic(
					courseId, 
					docId, 
					topicTitle.trim(), 
					topicMode, 
					httpServletRequest.getRemoteAddr());
			
			model.put("courseTopicId", courseTopic.getTopicId());
		} catch (ApplicationThrowable th) {
			model.put("error", th.toString());
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/deactivateCourse", method = RequestMethod.POST)
	public Map<String, Object> deactivateCourse(
			@RequestParam(value="courseId", required=true) Integer courseId, 
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			getTeachingService().deactivateCourse(courseId);
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/DeleteCheckPointPost", method = RequestMethod.POST)
	public Map<String, Object> deleteCheckPointPost(
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="postId", required=true) Integer postId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			CourseCheckPoint checkPoint = getTeachingService().getCheckPointByPost(postId);
			if (checkPoint != null && checkPoint.getCheckPointPost().getPost().getPostId().equals(postId)) {
				// the post to delete is a check point post: we can delete it only if there are no other posts
				// associated to that check point
				Long checkPointPostsCount = getTeachingService().countCheckPointPosts(checkPoint.getCheckPointId());
				if (checkPointPostsCount > 1) {
					model.put("error", "Cannot delete the course post because this is a check point with related posts");
				} else {
					// nothing to do: check point has to be invalidated by setting logical deletion on its post
				}
			}
			getTeachingService().deleteCourseTranscriptionPost(postId, CourseTopicMode.C);
			ForumTopic courseTopic = getTeachingService().findCourseTopic(topicId);
			model.put("topicId", topicId);
			model.put("operation", "OK");
			if (courseTopic != null){
				model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(courseTopic));
			}
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/DeleteCourseTopic.json", method = RequestMethod.POST)
	public Map<String, Object> deleteCourseTopic(
			@RequestParam(value="topicId", required=false) Integer topicId,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try{
			getTeachingService().deleteCourseTopic(topicId);
			model.put("operation", "OK");
			
			return model;		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return model;		
		}
	}
	
	@RequestMapping(value = "/teaching/DeleteIncrementalPost", method = RequestMethod.POST)
	public Map<String, Object> deleteIncrementalPost(
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="postId", required=true) Integer postId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().deleteCourseTranscriptionPost(postId, CourseTopicMode.I);
			ForumTopic courseTopic = getTeachingService().findCourseTopic(topicId);
			model.put("topicId", topicId);
			model.put("operation", "OK");
			if (courseTopic != null){
				model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(courseTopic));
			}
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/DeleteRoundRobinPost", method = RequestMethod.POST)
	public Map<String, Object> deleteRoundRobinPost(
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="postId", required=true) Integer postId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			getTeachingService().deleteCourseTranscriptionPost(postId, CourseTopicMode.R);
			ForumTopic courseTopic = getTeachingService().findCourseTopic(topicId);
			model.put("topicId", topicId);
			model.put("operation", "OK");
			if (courseTopic != null){
				model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(courseTopic));
			}
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/doActivateCourse", method = RequestMethod.POST)
	public Map<String, Object> doActivateCourse(
			@RequestParam(value="courseId", required=true) Integer courseId, 
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			getTeachingService().doActivateCourse(courseId);
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/EditCheckPointPost", method = RequestMethod.POST)
	public Map<String, Object> editCheckPointPost(
			@RequestParam(value="postId", required=false) Integer postId, 
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="subject", required=false) String subject,
			@RequestParam(value="text", required=false) String text,
			@RequestParam(value="transcription", required=false) String transcription,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRV", required=false) String folioRV,
			@RequestParam(value="checkPointPostId", required=false) Integer checkPointPostId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			CoursePostExt postExt = doEditPost(
					postId, 
					topicId, 
					subject, 
					text, 
					StringUtils.nullTrim(transcription), 
					volNum, 
					volLetExt, 
					insertNum, 
					insertLet, 
					folioNum, 
					folioMod, 
					folioRV,
					httpServletRequest.getRemoteAddr(),
					CourseTopicMode.I,
					checkPointPostId);
			model.put("topicId", postExt.getPost().getTopic().getTopicId());
			model.put("postId", postExt.getPost().getPostId());
			model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(postExt.getPost().getTopic()));
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/EditIncrementalPost", method = RequestMethod.POST)
	public Map<String, Object> editIncrementalPost(
			@RequestParam(value="postId", required=false) Integer postId, 
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="subject", required=false) String subject,
			@RequestParam(value="text", required=false) String text,
			@RequestParam(value="transcription", required=false) String transcription,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRV", required=false) String folioRV,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			CoursePostExt postExt = doEditPost(
					postId, 
					topicId, 
					subject, 
					text, 
					StringUtils.nullTrim(transcription), 
					volNum, 
					volLetExt, 
					insertNum, 
					insertLet, 
					folioNum, 
					folioMod, 
					folioRV,
					httpServletRequest.getRemoteAddr(),
					CourseTopicMode.I,
					null);
			model.put("topicId", postExt.getPost().getTopic().getTopicId());
			model.put("postId", postExt.getPost().getPostId());
			model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(postExt.getPost().getTopic()));
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/EditRoundRobinPost", method = RequestMethod.POST)
	public Map<String, Object> editRoundRobinPost(
			@RequestParam(value="postId", required=false) Integer postId, 
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="subject", required=false) String subject,
			@RequestParam(value="text", required=false) String text,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRV", required=false) String folioRV,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			CoursePostExt postExt = doEditPost(
					postId, 
					topicId, 
					subject, 
					text, 
					null, 
					volNum, 
					volLetExt, 
					insertNum, 
					insertLet, 
					folioNum, 
					folioMod, 
					folioRV,
					httpServletRequest.getRemoteAddr(),
					CourseTopicMode.R,
					null);
			model.put("topicId", postExt.getPost().getTopic().getTopicId());
			model.put("postId", postExt.getPost().getPostId());
			model.put("topicUrl", HtmlUtils.getCourseTranscriptionTopicHrefUrl(postExt.getPost().getTopic()));
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	/**
	 * 
	 * @param imageName the image name
	 * @param topicId the course topic identifier
	 * @return
	 */
	@RequestMapping(value = "/teaching/GetImageAnnotation.json", method = RequestMethod.GET)
	public Map<String, Object> getCourseTopicAnnotation(
			@RequestParam(value="imageName", required=false) String imageName,
			@RequestParam(value="resourcesForum", required=true) Integer forumId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<Annotation> annotations = getTeachingService().getTopicImageAnnotations(imageName, forumId, Annotation.Type.TEACHING);	
			List<Object> resultList = getAnnotationsForView(annotations); 
			model.put("annotations", resultList);
			model.put("adminPrivileges", isAdminOrTeacher());
			model.put("operation", "OK");
		} catch (ApplicationThrowable ath) {
			model.put("operation", "KO");
			return model;
		}
	
		return model;
	}
	
	@RequestMapping(value = "/teaching/GetFolioFragments", method = RequestMethod.GET)
	public Map<String, Object> getPostFolioLocation(
			@RequestParam(value="entryId", required=true) Integer entryId,
			@RequestParam(value="imageOrder", required=true) Integer imageOrder,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Image image = getTeachingService().getDocumentImage(entryId, imageOrder);
			if (image != null) {
				model.put("volNum", image.getVolNum()); 
				if (image.getVolLetExt() != null) {
					model.put("volLetExt", image.getVolLetExt());
				}
				if (image.getInsertNum() != null) {
					model.put("insertNum", image.getInsertNum());
					if (image.getInsertLet() != null) {
						model.put("insertLet", image.getInsertLet());
					}
				}
				model.put("folioNum", image.getImageProgTypeNum());
				if (image.getMissedNumbering() != null) {
					model.put("folioMod", image.getMissedNumbering());
				}
				if (image.getImageRectoVerso() != null) {
					model.put("folioRV", image.getImageRectoVerso().toString());
				}
				model.put("operation", "OK");
			}
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/GetExtendedFolioFragments", method = RequestMethod.GET)
	public Map<String, Object> getExtendedPostFolioLocation(
			@RequestParam(value="entryId", required=false) Integer entryId,
			@RequestParam(value="imageOrder", required=false) Integer imageOrder,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Image image = null;
			if (entryId != null && imageOrder != null) {
				image = getTeachingService().getDocumentImage(entryId, imageOrder);
			}
			if (image != null) {
				model.put("volNum", image.getVolNum());
				model.put("volLetExt", image.getVolLetExt());
				model.put("insertNum", image.getInsertNum());
				model.put("insertLet", image.getInsertLet());
				model.put("folioNum", image.getImageProgTypeNum());
				model.put("folioMod", image.getMissedNumbering());
				model.put("folioRV", image.getImageRectoVerso());
				model.put("imageFound", true);
			}
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/GrantStudentPermission.json", method = RequestMethod.POST)
	public Map<String, Object> grantStudentPermission(
			@RequestParam(value="account", required=true) String account,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().grantStudentPermission(account);
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/getLastPostId.json", method = RequestMethod.GET)
	public Map<String, Object> getLastPostId(
			@RequestParam(value="courseTopicId", required=true) Integer courseTopicId,
			@RequestParam(value="byCreationDate", required=true) Boolean byCreationDate,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			CoursePostExt postExt = getTeachingService().getLastPostOfTopic(courseTopicId, byCreationDate != null ? byCreationDate : true);
			model.put("lastPostId", postExt != null ? postExt.getPost().getPostId() : -1);
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
	
	
	@RequestMapping(value = "/teaching/MoveAllOtherStudentsToCourse.json", method = RequestMethod.POST)
	public Map<String, Object> moveAllStudentsToCourse(
			@RequestParam(value="courseId", required = true) Integer courseId,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().addAllStudentsToCourse(courseId);
			model.put("operation", "OK");
			return model;		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return model;		
		}
	}
			
	
	
	@RequestMapping(value = "/teaching/MoveStudentsToCourse.json", method = RequestMethod.POST)
	public Map<String, Object> moveStudentsToCourse(
			@RequestParam(value="courseId", required = true) Integer courseId,
			@RequestParam(value="accounts", required = true) String accounts,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			String[] splittedAccounts = org.apache.commons.lang.StringUtils.split(accounts, ",");
			getTeachingService().addCourseStudents(courseId, Arrays.asList(splittedAccounts));
			model.put("operation", "OK");
			return model;		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return model;		
		}
	}
	
	@RequestMapping(value = "/teaching/OpenCloseCourseTopic.json", method = RequestMethod.POST)
	public Map<String, Object> openCloseCourseTranscription(
			@RequestParam(value="courseTopicId", required = true) Integer courseTopicId,
			@RequestParam(value="close", required = true) Boolean close,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().openCloseCourseTopic(courseTopicId, close);
			model.put("operation", "OK");
			
			return model;		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return model;		
		}
	}
	
	@RequestMapping(value = "/teaching/RemoveAllCoursePeople.json", method = RequestMethod.POST)
	public Map<String, Object> removeAllCoursePeople(
			@RequestParam(value="courseId", required=true) Integer courseId,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().removeAllCoursePeople(courseId, null);
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
			
	@RequestMapping(value = "/teaching/RemoveCoursePeople.json", method = RequestMethod.POST)
	public Map<String, Object> removeCoursePeople(
			@RequestParam(value="courseId", required=true) Integer courseId,
			@RequestParam(value="accounts", required=false) String accounts,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			String[] splittedAccounts = org.apache.commons.lang.StringUtils.split(accounts, ",");
			getTeachingService().removeCoursePeople(courseId, Arrays.asList(splittedAccounts));
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
			
	@RequestMapping(value = "/teaching/RevokeStudentPermission.json", method = RequestMethod.POST)
	public Map<String, Object> revokeStudentPermission(
			@RequestParam(value="account", required=true) String account,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			getTeachingService().revokeStudentPermission(account);
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
	
	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/teaching/SearchUser.json", method = RequestMethod.GET)
	public ModelAndView searchUser(@RequestParam(value="fullName") String fullName,
								@RequestParam(value="userName") String userName,
								@RequestParam(value="role") String role,
								@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
					   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
					   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
					   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Page page = null;
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		User user = new User();
		if(userName != null && userName.length() > 0) {
			user.setAccount(userName);
		}

		if(fullName != null && fullName.length() > 0){
			user.setFirstName(fullName);
		}

		try {
			// Paging results...
			page = getTeachingService().getUsers(user, ShowTeachingUserSearchCommand.ALL.equals(role) ? null : ShowTeachingUserSearchCommand.STUDENT.equals(role) ? true : false, paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		// Ordering results... 
		// LP : la gestione dell'ordinamento va spostata nel blocco metodo del dao invocato nel service
		PropertyComparator.sort(page.getList(), new MutableSortDefinition("firstName", true, true));
		page.setList(Collections.unmodifiableList(page.getList()));

		List resultList = new ArrayList(0);
		for(User currentUser : (List<User>) page.getList()){
			List singleRow = new ArrayList(0);
			singleRow.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			singleRow.add(currentUser.getMail());
			singleRow.add(currentUser.getCity());
			singleRow.add(currentUser.getCountry());
			if (currentUser.getLastLoginDate() != null) {
				singleRow.add(currentUser.getLastLoginDate().toString());
			} else {
				singleRow.add("");
			}
			resultList.add(HtmlUtils.showUserForTeaching(singleRow, currentUser.getAccount()));
		}

		model.put("iEcho", 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK",model);
	}
	
	@RequestMapping(value = "/teaching/ShowCurrentTranscription", method = RequestMethod.GET)
	public Map<String, Object> showCurrentTranscription(
			@RequestParam(value="topicId", required=true) Integer topicId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			CourseCheckPoint checkPoint = getTeachingService().getLastCheckPoint(topicId);
			model.put("transcription", checkPoint != null ? checkPoint.getCheckPointPost().getTranscription() : null);
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/SubscribeForumTopic.json", method = RequestMethod.POST)
	public Map<String, Object> subscribeForumTopic(
			@RequestParam(value="topicId", required=true) Integer courseTopicId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		Boolean subscription = Boolean.FALSE;
		
		try {
			subscription = getTeachingService().subscribeCourseTopic(courseTopicId);

			model.put("topicId", courseTopicId);
			model.put("subscription", subscription);
		} catch (ApplicationThrowable th) {
			model.put("error", th.getMessage());
		}

		return model;
	}
	
	@RequestMapping(value = "/teaching/toggleCourseSubscription.json", method = RequestMethod.POST)
	public Map<String, Object> toggleCourseSubscription(
			@RequestParam(value="courseId", required=true) Integer courseId,
			@RequestParam(value="account", required=true) String account,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			getTeachingService().toggleCourseSubscription(courseId, account);
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			model.put("operation", "KO");
			model.put("error", th.getMessage());
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/UnsubscribeForumTopic.json", method = RequestMethod.POST)
	public Map<String, Object> unsubscribeForumTopic(@RequestParam(value="topicId", required=false) Integer courseTopicId) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Boolean unsubscription = Boolean.FALSE;
		
		try {
			unsubscription = getTeachingService().unsubscribeForumTopic(courseTopicId);

			model.put("topicId", courseTopicId);
			model.put("subscription", unsubscription);
		} catch (ApplicationThrowable th) {
			model.put("error", th.getMessage());
		}

		return model;
	}
	
	/**
	 * This method update the annotations of the folio presented in the manuscript viewer.
	 * 
	 * @param httpServletRequest the request
	 * @return
	 */
	@RequestMapping(value = {"/teaching/UpdateAnnotations.json"}, method = RequestMethod.POST)
	public Map<String, Object> updateAnnotations(HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Boolean isAdminOrTeacher = isAdminOrTeacher();
			
			// In this controller we get input parameter at low level beacause 
			// there is a bug in spring which construct a wrong list of 
			// annotations in case of client send 1 single annotation 
			//String imageName = httpServletRequest.getParameter("imageName");
			Integer imageId = NumberUtils.toInt(httpServletRequest.getParameter("imageId"));
			String forumContainerIdFromView = httpServletRequest.getParameter("resourcesForum");
			Integer forumContainerId = forumContainerIdFromView != null ? NumberUtils.toInt(forumContainerIdFromView) : null;
			String[] annotationsFormView = httpServletRequest.getParameterValues("annotations");
			List<Annotation> annotationsList = new ArrayList<Annotation>(0);
			List<Object> resultList = new ArrayList<Object>();

			if (annotationsFormView != null) {
				for (String string : annotationsFormView) {
					//Next code is instructed on code of javascript IIPMooViewer.annotationsAsQueryParameterString
					String[] splitted = org.apache.commons.lang.StringUtils.splitPreserveAllTokens(string, "¥");
					Annotation annotation = new Annotation();
					annotation.setAnnotationId(NumberUtils.toInt(splitted[0]));
					annotation.setX(NumberUtils.toDouble(splitted[2]));
					annotation.setY(NumberUtils.toDouble(splitted[3]));
					annotation.setWidth(NumberUtils.toDouble(splitted[4]));
					annotation.setHeight(NumberUtils.toDouble(splitted[5]));
					annotation.setType(Annotation.Type.valueOf(splitted[6].toUpperCase()));
					annotation.setTitle(splitted[7]);
					annotation.setText(splitted[8]);
					annotation.setVisible(Boolean.valueOf(splitted[11]));
					annotation.setRgbColor("none".equals(splitted[12]) ? null : splitted[12]);
					annotationsList.add(annotation);
				}
			}
			Map<Annotation, Integer> imageAnnotationsMap = getTeachingService().updateAnnotations(
																					imageId, 
																					forumContainerId, 
																					annotationsList, 
																					httpServletRequest.getRemoteAddr(), 
																					isAdminOrTeacher);
			for (Annotation currentAnnotation : imageAnnotationsMap.keySet()) {
				Map<String, Object> singleRow = new HashMap<String, Object>(0);
				if (imageAnnotationsMap.get(currentAnnotation) > -1) {
					String topicForumURL = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
					topicForumURL += "/teaching/ShowTopicForum.do?topicId=" + currentAnnotation.getForumTopic().getTopicId();
					topicForumURL += "&forumId=" + currentAnnotation.getForumTopic().getForum().getForumId();
					topicForumURL += "&completeDOM=true";
					singleRow.put("forum", topicForumURL);
					resultList.add(singleRow);
				}
			}
			// links -> only new annotations associated to a forum 
			model.put("links", resultList);
			// annotation -> all of the annotations associated to the current image
			model.put("annotations", getAnnotationsForView(imageAnnotationsMap.keySet()));
			model.put("adminPrivileges", isAdminOrTeacher);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return model;
		}
		
		model.put("operation", "OK");
		return model;
	}
	
	/* Privates */
	
	private CoursePostExt doEditPost(
			Integer postId, 
			Integer topicId,
			String subject,
			String text,
			String transcription,
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String folioRV,
			String remoteAddr,
			CourseTopicMode mode,
			Integer checkPointPostId) throws ApplicationThrowable {
		
		CoursePostExt postExt;
		if (postId == null || postId == 0) {
			postExt = getTeachingService().addCourseTranscriptionPost(
					topicId, 
					StringUtils.nullTrim(subject),
					StringUtils.nullTrim(text),
					CourseUtils.encodeCourseTranscriptionSafely(StringUtils.nullTrim(transcription)),
					volNum,
					StringUtils.nullTrim(volLetExt),
					StringUtils.nullTrim(insertNum),
					StringUtils.nullTrim(insertLet),
					folioNum,
					StringUtils.nullTrim(folioMod),
					StringUtils.nullTrim(folioRV),
					remoteAddr,
					mode,
					checkPointPostId);
		} else {
			postExt = getTeachingService().updateCourseTranscriptionPost(
					postId,
					StringUtils.nullTrim(subject),
					StringUtils.nullTrim(text),
					CourseUtils.encodeCourseTranscriptionSafely(StringUtils.nullTrim(transcription)),
					volNum,
					StringUtils.nullTrim(volLetExt),
					StringUtils.nullTrim(insertNum),
					StringUtils.nullTrim(insertLet),
					folioNum,
					StringUtils.nullTrim(folioMod),
					StringUtils.nullTrim(folioRV),
					mode);
		}
		
		return postExt;
	}
	
	/**
	 * This method creates a list of view annotations to be sent to the view level.
	 * 
	 * @param annotations a list of annotations
	 * @return a list of view annotations
	 * @throws ApplicationThrowable
	 */
	private List<Object> getAnnotationsForView(Collection<Annotation> annotations) throws ApplicationThrowable {
		String account = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		Boolean isAdminOrTeacher = isAdminOrTeacher();
		
		List<Object> resultList = new ArrayList<Object>();
		for (Annotation currentAnnotation : annotations) {
			Map<String, Object> row = new HashMap<String, Object>(0);
			row.put("annotationId", currentAnnotation.getAnnotationId());
			row.put("x", currentAnnotation.getX());
			row.put("y", currentAnnotation.getY());
			row.put("w", currentAnnotation.getWidth());
			row.put("h", currentAnnotation.getHeight());
			row.put("type", currentAnnotation.getType());
			row.put("title", currentAnnotation.getTitle());
			// RR: we do not show annotation text if it is transcribed
			row.put("text", currentAnnotation.getTranscribed() != null && currentAnnotation.getTranscribed() ? "" : currentAnnotation.getText());
			row.put("deletable", isAdminOrTeacher || getTeachingService().isDeletableAnnotation(currentAnnotation));
			row.put("updatable", account.equals(currentAnnotation.getUser().getAccount()) || isAdminOrTeacher ? true : false);
			if (isAdminOrTeacher || Boolean.TRUE.equals(currentAnnotation.getVisible())) {
				row.put("visibility", currentAnnotation.getVisible());
			}
			if (currentAnnotation.getRgbColor() != null) {
				row.put("color", currentAnnotation.getRgbColor());
			}
			if (currentAnnotation.getForumTopic() != null) {
				row.put("forumTopicURL", HtmlUtils.getTeachingShowTopicForumHrefUrl(currentAnnotation.getForumTopic()) + "&completeDOM=true");
			}
			resultList.add(row);
		}
		return resultList;
	}
	
	private boolean isAdminOrTeacher() throws ApplicationThrowable {
		try {
			String account = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
			Boolean administrator = getUserService().isAccountAdministrator(account);
			Boolean teacher = Boolean.FALSE;
			if (Boolean.FALSE.equals(administrator)) {
				teacher = getUserService().isAccountTeacher(account);
			}
			return administrator || teacher;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
}
