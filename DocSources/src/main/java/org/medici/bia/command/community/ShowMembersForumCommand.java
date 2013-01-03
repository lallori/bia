/*
 * ShowMembersForumCommand.java
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
package org.medici.bia.command.community;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ShowMembersForumCommand {
	private String letter;
	private String member;

	private Integer memberPageNumber;
	private Integer memberPageTotal;
	private Integer membersForPage;
	
	/**
	 * @return the letter
	 */
	public String getLetter() {
		return letter;
	}
	
	/**
	 * @param letter the letter to set
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	/**
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(String member) {
		this.member = member;
	}

	/**
	 * @return the memberPageNumber
	 */
	public Integer getMemberPageNumber() {
		return memberPageNumber;
	}
	
	/**
	 * @param memberPageNumber the memberPageNumber to set
	 */
	public void setMemberPageNumber(Integer memberPageNumber) {
		this.memberPageNumber = memberPageNumber;
	}
	
	/**
	 * @return the memberPageTotal
	 */
	public Integer getMemberPageTotal() {
		return memberPageTotal;
	}
	
	/**
	 * @param memberPageTotal the memberPageTotal to set
	 */
	public void setMemberPageTotal(Integer memberPageTotal) {
		this.memberPageTotal = memberPageTotal;
	}
	
	/**
	 * @return the membersForPage
	 */
	public Integer getMembersForPage() {
		return membersForPage;
	}

	/**
	 * @param membersForPage the membersForPage to set
	 */
	public void setMembersForPage(Integer membersForPage) {
		this.membersForPage = membersForPage;
	}
}
