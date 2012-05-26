/*
 * EditSchedonePropertiesCommand.java
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
package org.medici.docsources.command.admin;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class EditSchedonePropertiesCommand {
	private String istituto;
	private String fondo;
	private String legatura;
	private String supporto;
	private String tipoRipresa;
	private String coloreImmagine;
	private String nomeFiles;
	private String responsabileFotoRiproduzione;
	private String operatore;
	
	/**
	 * @return the istituto
	 */
	public String getIstituto() {
		return istituto;
	}
	/**
	 * @param istituto the istituto to set
	 */
	public void setIstituto(String istituto) {
		this.istituto = istituto;
	}
	/**
	 * @return the fondo
	 */
	public String getFondo() {
		return fondo;
	}
	/**
	 * @param fondo the fondo to set
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}
	/**
	 * @return the legatura
	 */
	public String getLegatura() {
		return legatura;
	}
	/**
	 * @param legatura the legatura to set
	 */
	public void setLegatura(String legatura) {
		this.legatura = legatura;
	}
	/**
	 * @return the supporto
	 */
	public String getSupporto() {
		return supporto;
	}
	/**
	 * @param supporto the supporto to set
	 */
	public void setSupporto(String supporto) {
		this.supporto = supporto;
	}
	/**
	 * @return the tipoRipresa
	 */
	public String getTipoRipresa() {
		return tipoRipresa;
	}
	/**
	 * @param tipoRipresa the tipoRipresa to set
	 */
	public void setTipoRipresa(String tipoRipresa) {
		this.tipoRipresa = tipoRipresa;
	}
	/**
	 * @return the coloreImmagine
	 */
	public String getColoreImmagine() {
		return coloreImmagine;
	}
	/**
	 * @param coloreImmagine the coloreImmagine to set
	 */
	public void setColoreImmagine(String coloreImmagine) {
		this.coloreImmagine = coloreImmagine;
	}
	/**
	 * @return the nomeFiles
	 */
	public String getNomeFiles() {
		return nomeFiles;
	}
	/**
	 * @param nomeFiles the nomeFiles to set
	 */
	public void setNomeFiles(String nomeFiles) {
		this.nomeFiles = nomeFiles;
	}
	/**
	 * @return the responsabileFotoRiproduzione
	 */
	public String getResponsabileFotoRiproduzione() {
		return responsabileFotoRiproduzione;
	}
	/**
	 * @param responsabileFotoRiproduzione the responsabileFotoRiproduzione to set
	 */
	public void setResponsabileFotoRiproduzione(String responsabileFotoRiproduzione) {
		this.responsabileFotoRiproduzione = responsabileFotoRiproduzione;
	}
	/**
	 * @return the operatore
	 */
	public String getOperatore() {
		return operatore;
	}
	/**
	 * @param operatore the operatore to set
	 */
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
}
