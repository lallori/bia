/*
 * EditDetailsSchedoneCommand.java
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
package org.medici.docsources.command.digitization;

import java.util.Date;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class EditDetailsSchedoneCommand {
	private Integer schedoneId;
	private String istituto;
	private String fondo;
	private String serie;
	private Integer numeroUnita;
	private String dateEstreme;
	private String titolo;
	private String descrizioneContenuto;
	private String legatura;
	private String supporto;
	private String cartulazione;
	private String noteCartulazione;
	private String carteBianche;
	private String carteMancanti;
	private String dimensioniBase;
	private Integer dimensioniAltezza;
	private String sistemaScansione;
	private String profonditaColore;
	private String risoluzione;
	private String coloreImmagine;
	private String formato;
	private String compressione;
	private String nomeFiles;
	private Integer numeroTotaleImmagini;
	private Long dimMediaImmagini;
	private Long dimTotaleImmagini;
	private String responsabileFotoRiproduzione;
	private String tipoRipresa;
	private Date dataRipresa;
	private String operatore;
	
	/**
	 * @return the schedoneId
	 */
	public Integer getSchedoneId() {
		return schedoneId;
	}
	
	/**
	 * @param SchedoneId the schedoneId to set
	 */
	public void setSchedoneId(Integer schedoneId) {
		this.schedoneId = schedoneId;
	}
	
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
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	/**
	 * @return the numeroUnita
	 */
	public Integer getNumeroUnita() {
		return numeroUnita;
	}
	
	/**
	 * @param numeroUnita the numeroUnita to set
	 */
	public void setNumeroUnita(Integer numeroUnita) {
		this.numeroUnita = numeroUnita;
	}
	
	/**
	 * @return the dateEstreme
	 */
	public String getDateEstreme() {
		return dateEstreme;
	}
	
	/**
	 * @param dateEstreme the dateEstreme to set
	 */
	public void setDateEstreme(String dateEstreme) {
		this.dateEstreme = dateEstreme;
	}

	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}
	
	/**
	 * @param titolo the titolo to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	/**
	 * @return the descrizioneContenuto
	 */
	public String getDescrizioneContenuto() {
		return descrizioneContenuto;
	}
	
	/**
	 * @param descrizioneContenuto the descrizioneContenuto to set
	 */
	public void setDescrizioneContenuto(String descrizioneContenuto) {
		this.descrizioneContenuto = descrizioneContenuto;
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
	 * @return the cartulazione
	 */
	public String getCartulazione() {
		return cartulazione;
	}
	
	/**
	 * @param cartulazione the cartulazione to set
	 */
	public void setCartulazione(String cartulazione) {
		this.cartulazione = cartulazione;
	}
	
	/**
	 * @return the noteCartulazione
	 */
	public String getNoteCartulazione() {
		return noteCartulazione;
	}
	
	/**
	 * @param noteCartulazione the noteCartulazione to set
	 */
	public void setNoteCartulazione(String noteCartulazione) {
		this.noteCartulazione = noteCartulazione;
	}
	
	/**
	 * @return the carteBianche
	 */
	public String getCarteBianche() {
		return carteBianche;
	}
	
	/**
	 * @param carteBianche the carteBianche to set
	 */
	public void setCarteBianche(String carteBianche) {
		this.carteBianche = carteBianche;
	}
	
	/**
	 * @return the carteMancanti
	 */
	public String getCarteMancanti() {
		return carteMancanti;
	}
	
	/**
	 * @param carteMancanti the carteMancanti to set
	 */
	public void setCarteMancanti(String carteMancanti) {
		this.carteMancanti = carteMancanti;
	}
	
	/**
	 * @return the dimensioniBase
	 */
	public String getDimensioniBase() {
		return dimensioniBase;
	}
	
	/**
	 * @param dimensioniBase the dimensioniBase to set
	 */
	public void setDimensioniBase(String dimensioniBase) {
		this.dimensioniBase = dimensioniBase;
	}
	
	/**
	 * @return the dimensioniAltezza
	 */
	public Integer getDimensioniAltezza() {
		return dimensioniAltezza;
	}
	
	/**
	 * @param dimensioniAltezza the dimensioniAltezza to set
	 */
	public void setDimensioniAltezza(Integer dimensioniAltezza) {
		this.dimensioniAltezza = dimensioniAltezza;
	}
	
	/**
	 * @return the sistemaScansione
	 */
	public String getSistemaScansione() {
		return sistemaScansione;
	}
	
	/**
	 * @param sistemaScansione the sistemaScansione to set
	 */
	public void setSistemaScansione(String sistemaScansione) {
		this.sistemaScansione = sistemaScansione;
	}
	
	/**
	 * @return the profonditaColore
	 */
	public String getProfonditaColore() {
		return profonditaColore;
	}
	
	/**
	 * @param profonditaColore the profonditaColore to set
	 */
	public void setProfonditaColore(String profonditaColore) {
		this.profonditaColore = profonditaColore;
	}
	
	/**
	 * @return the risoluzione
	 */
	public String getRisoluzione() {
		return risoluzione;
	}
	
	/**
	 * @param risoluzione the risoluzione to set
	 */
	public void setRisoluzione(String risoluzione) {
		this.risoluzione = risoluzione;
	}
	
	/**
	 * @param coloreImmagine the coloreImmagine to set
	 */
	public void setColoreImmagine(String coloreImmagine) {
		this.coloreImmagine = coloreImmagine;
	}

	/**
	 * @return the coloreImmagine
	 */
	public String getColoreImmagine() {
		return coloreImmagine;
	}

	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}
	
	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	/**
	 * @return the compressione
	 */
	public String getCompressione() {
		return compressione;
	}
	
	/**
	 * @param compressione the compressione to set
	 */
	public void setCompressione(String compressione) {
		this.compressione = compressione;
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
	 * @return the numeroTotaleImmagini
	 */
	public Integer getNumeroTotaleImmagini() {
		return numeroTotaleImmagini;
	}
	
	/**
	 * @param numeroTotaleImmagini the numeroTotaleImmagini to set
	 */
	public void setNumeroTotaleImmagini(Integer numeroTotaleImmagini) {
		this.numeroTotaleImmagini = numeroTotaleImmagini;
	}
	
	/**
	 * @return the dimMediaImmagini
	 */
	public Long getDimMediaImmagini() {
		return dimMediaImmagini;
	}
	
	/**
	 * @param dimMediaImmagini the dimMediaImmagini to set
	 */
	public void setDimMediaImmagini(Long dimMediaImmagini) {
		this.dimMediaImmagini = dimMediaImmagini;
	}
	
	/**
	 * @return the dimTotaleImmagini
	 */
	public Long getDimTotaleImmagini() {
		return dimTotaleImmagini;
	}
	
	/**
	 * @param dimTotaleImmagini the dimTotaleImmagini to set
	 */
	public void setDimTotaleImmagini(Long dimTotaleImmagini) {
		this.dimTotaleImmagini = dimTotaleImmagini;
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
	 * @param tipoRipresa the tipoRipresa to set
	 */
	public void setTipoRipresa(String tipoRipresa) {
		this.tipoRipresa = tipoRipresa;
	}

	/**
	 * @return the tipoRipresa
	 */
	public String getTipoRipresa() {
		return tipoRipresa;
	}

	/**
	 * @param dataRipresa the dataRipresa to set
	 */
	public void setDataRipresa(Date dataRipresa) {
		this.dataRipresa = dataRipresa;
	}

	/**
	 * @return the dataRipresa
	 */
	public Date getDataRipresa() {
		return dataRipresa;
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
