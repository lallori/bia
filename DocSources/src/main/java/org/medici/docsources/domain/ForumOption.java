/*
 * ForumOption.java
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
package org.medici.docsources.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class represents entity Forum.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForumOption\"" ) 
public class ForumOption implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 23669109119824327L;
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"id\"")
	private Forum forum;
	@Column (name="\"canHaveSubCategory\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canHaveSubCategory;
	@Column (name="\"canHaveSubForum\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canHaveSubForum;
	@Column (name="\"canHaveThreads\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canHaveThreads;
	@Column (name="\"canView\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canView;
	@Column (name="\"canOnlyViewOwnThreads\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canOnlyViewOwnThreads;
	@Column (name="\"canDownloadAttachments\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canDownloadAttachments;
	@Column (name="\"canPostReplys\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canPostReplys;
	@Column (name="\"canPostAttachments\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canPostAttachments;
	@Column (name="\"canRateThreads\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canRateThreads;
	@Column (name="\"canEditPosts\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canEditPosts;
	@Column (name="\"canDeletePosts\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canDeletePosts;
	@Column (name="\"canDeleteThreads\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean canDeleteThreads;

	/**
	 * Default constructor. 
	 * 
	 * Every permission is setted to false
	 */
	public ForumOption() {
		super();

		setCanHaveSubCategory(Boolean.FALSE);
		setCanHaveSubForum(Boolean.FALSE);
		setCanHaveThreads(Boolean.FALSE);
		setCanView(Boolean.FALSE);
		setCanOnlyViewOwnThreads(Boolean.FALSE);
		setCanDownloadAttachments(Boolean.FALSE);
		setCanPostReplys(Boolean.FALSE);
		setCanPostAttachments(Boolean.FALSE);
		setCanRateThreads(Boolean.FALSE);
		setCanEditPosts(Boolean.FALSE);
		setCanDeletePosts(Boolean.FALSE);
		setCanDeleteThreads(Boolean.FALSE);	
	}

	/**
	 * Constructor with forum Identifier key.
	 * 
	 * Every permission is setted to false
	 * 
	 * @param forumId
	 */
	public ForumOption(Integer forumId) {
		super();
		if (forumId != null) {
			setForum(new Forum(forumId));
		}

		setCanHaveSubCategory(Boolean.FALSE);
		setCanHaveSubForum(Boolean.FALSE);
		setCanHaveThreads(Boolean.FALSE);
		setCanView(Boolean.FALSE);
		setCanOnlyViewOwnThreads(Boolean.FALSE);
		setCanDownloadAttachments(Boolean.FALSE);
		setCanPostReplys(Boolean.FALSE);
		setCanPostAttachments(Boolean.FALSE);
		setCanRateThreads(Boolean.FALSE);
		setCanEditPosts(Boolean.FALSE);
		setCanDeletePosts(Boolean.FALSE);
		setCanDeleteThreads(Boolean.FALSE);
	}

	/**
	 * Constructor with forum object.
	 * 
	 * @param forum
	 */
	public ForumOption(Forum forum) {
		super();
		if (forum != null) {
			setForum(forum);
		}

		setCanHaveSubCategory(Boolean.FALSE);
		setCanHaveSubForum(Boolean.FALSE);
		setCanHaveThreads(Boolean.FALSE);
		setCanView(Boolean.FALSE);
		setCanOnlyViewOwnThreads(Boolean.FALSE);
		setCanDownloadAttachments(Boolean.FALSE);
		setCanPostReplys(Boolean.FALSE);
		setCanPostAttachments(Boolean.FALSE);
		setCanRateThreads(Boolean.FALSE);
		setCanEditPosts(Boolean.FALSE);
		setCanDeletePosts(Boolean.FALSE);
		setCanDeleteThreads(Boolean.FALSE);
	}

	/**
	 * @return the forum
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/**
	 * @return the canView
	 */
	public Boolean getCanView() {
		return canView;
	}

	/**
	 * @param canView the canView to set
	 */
	public void setCanView(Boolean canView) {
		this.canView = canView;
	}

	/**
	 * @return the canHaveCategory
	 */
	public Boolean getCanHaveSubCategory() {
		return canHaveSubCategory;
	}

	/**
	 * @param canHaveCategory the canHaveCategory to set
	 */
	public void setCanHaveSubCategory(Boolean canHaveSubCategory) {
		this.canHaveSubCategory = canHaveSubCategory;
	}

	/**
	 * @return the canHaveSubForum
	 */
	public Boolean getCanHaveSubForum() {
		return canHaveSubForum;
	}

	/**
	 * @param canHaveSubForum the canHaveSubForum to set
	 */
	public void setCanHaveSubForum(Boolean canHaveSubForum) {
		this.canHaveSubForum = canHaveSubForum;
	}

	/**
	 * @return the canHaveThreads
	 */
	public Boolean getCanHaveThreads() {
		return canHaveThreads;
	}

	/**
	 * @param canHaveThreads the canHaveThreads to set
	 */
	public void setCanHaveThreads(Boolean canHaveThreads) {
		this.canHaveThreads = canHaveThreads;
	}

	/**
	 * @return the canOnlyViewOwnThreads
	 */
	public Boolean getCanOnlyViewOwnThreads() {
		return canOnlyViewOwnThreads;
	}

	/**
	 * @param canOnlyViewOwnThreads the canOnlyViewOwnThreads to set
	 */
	public void setCanOnlyViewOwnThreads(Boolean canOnlyViewOwnThreads) {
		this.canOnlyViewOwnThreads = canOnlyViewOwnThreads;
	}

	/**
	 * @return the canDownloadAttachments
	 */
	public Boolean getCanDownloadAttachments() {
		return canDownloadAttachments;
	}

	/**
	 * @param canDownloadAttachments the canDownloadAttachments to set
	 */
	public void setCanDownloadAttachments(Boolean canDownloadAttachments) {
		this.canDownloadAttachments = canDownloadAttachments;
	}

	/**
	 * @return the canPostReplys
	 */
	public Boolean getCanPostReplys() {
		return canPostReplys;
	}

	/**
	 * @param canPostReplys the canPostReplys to set
	 */
	public void setCanPostReplys(Boolean canPostReplys) {
		this.canPostReplys = canPostReplys;
	}

	/**
	 * @return the canPostAttachments
	 */
	public Boolean getCanPostAttachments() {
		return canPostAttachments;
	}

	/**
	 * @param canPostAttachments the canPostAttachments to set
	 */
	public void setCanPostAttachments(Boolean canPostAttachments) {
		this.canPostAttachments = canPostAttachments;
	}

	/**
	 * @return the canRateThreads
	 */
	public Boolean getCanRateThreads() {
		return canRateThreads;
	}

	/**
	 * @param canRateThreads the canRateThreads to set
	 */
	public void setCanRateThreads(Boolean canRateThreads) {
		this.canRateThreads = canRateThreads;
	}

	/**
	 * @return the canEditPosts
	 */
	public Boolean getCanEditPosts() {
		return canEditPosts;
	}

	/**
	 * @param canEditPosts the canEditPosts to set
	 */
	public void setCanEditPosts(Boolean canEditPosts) {
		this.canEditPosts = canEditPosts;
	}

	/**
	 * @return the canDeletePosts
	 */
	public Boolean getCanDeletePosts() {
		return canDeletePosts;
	}

	/**
	 * @param canDeletePosts the canDeletePosts to set
	 */
	public void setCanDeletePosts(Boolean canDeletePosts) {
		this.canDeletePosts = canDeletePosts;
	}

	/**
	 * @return the canDeleteThreads
	 */
	public Boolean getCanDeleteThreads() {
		return canDeleteThreads;
	}

	/**
	 * @param canDeleteThreads the canDeleteThreads to set
	 */
	public void setCanDeleteThreads(Boolean canDeleteThreads) {
		this.canDeleteThreads = canDeleteThreads;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append("id=");
		if (getForum() ==null)
		stringBuffer.append((getForum() ==null) ? null : getForum().getId());
		stringBuffer.append(", canHaveSubCategory=");
		stringBuffer.append(getCanHaveSubCategory());
		stringBuffer.append(", canHaveSubForum=");
		stringBuffer.append(getCanHaveSubForum());
		stringBuffer.append(", canHaveThreads=");
		stringBuffer.append(getCanHaveThreads());
		stringBuffer.append(", canView=");
		stringBuffer.append(getCanView());
		stringBuffer.append(", canOnlyViewOwnThreads=");
		stringBuffer.append(getCanOnlyViewOwnThreads());
		stringBuffer.append(", canOnlyViewOwnThreads=");
		stringBuffer.append(getCanOnlyViewOwnThreads());
		stringBuffer.append(", canDownloadAttachments=");
		stringBuffer.append(getCanDownloadAttachments());
		stringBuffer.append(", canPostReplys=");
		stringBuffer.append(getCanPostReplys());
		stringBuffer.append(", canPostAttachments=");
		stringBuffer.append(getCanPostAttachments());
		stringBuffer.append(", canRateThreads=");
		stringBuffer.append(getCanRateThreads());
		stringBuffer.append(", canEditPosts=");
		stringBuffer.append(getCanEditPosts());
		stringBuffer.append(", canDeletePosts=");
		stringBuffer.append(getCanDeletePosts());
		stringBuffer.append(", canDeleteThreads=");
		stringBuffer.append(getCanDeleteThreads());
		stringBuffer.append("]");

		return stringBuffer.toString();
	}
}

