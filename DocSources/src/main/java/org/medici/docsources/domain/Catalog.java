/*
 * Catalog.java
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

/**
 * This class represents entity Catalog. This is the unique entity in domain 
 * which has fields defined in italian language. 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Audited
@Table ( name = "\"tblCatalog\"" ) 
public class Catalog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"ID\"", length=10, nullable=false)
	private Integer id;
	@Column (name="\"ISTITUTO\"", length=50)
	private String istituto;
	@Column (name="\"FONDO\"", length=50)
	private String fondo;
	@Column (name="\"SERIE\"", length=50)
	private String serie;
	@Column (name="\"N_UNITA\"")
	private Integer nUnita;
	@Column (name="\"DATE_ESTREME\"", length=50)
	private String dateEstreme;
	@Column (name="\"TITOLO\"", length=50)
	private String titolo;
	@Column (name="\"DESCRIZIONE_CONTENUTO\"", length=50)
	private String descrizioneContenuto;
	@Column (name="\"LEGATURA\"", length=50)
	private String legatura;
	@Column (name="\"SUPPORTO\"", length=50)
	private String supporto;
	@Column (name="\"CARTULAZIONE\"", length=50)
	private String cartulazione;
	@Column (name="\"NOTE_ALLA_CARTULAZIONE\"", length=50)
	private String noteCartulazione;
	@Column (name="\"CARTE_BIANCHE\"", length=50)
	private String carteBianche;
	@Column (name="\"CARTE_MANCANTI\"", length=50)
	private String carteMancanti;
	@Column (name="\"DIMENSIONI_BASE\"", length=50)
	private String dimensioniBase;
	@Column (name="\"DIMENSIONI_ALTEZZA\"", length=50)
	private Integer dimensioniAltezza;
	@Column (name="\"SISTEMA_DI_SCANSIONE\"", length=50)
	private String sistemaScansione;
	@Column (name="\"PROFONDITA_COLORE\"", length=50)
	private String profonditaColore;
	@Column (name="\"RISOLUZIONE\"", length=50)
	private String risoluzione;
	@Column (name="\"FORMATO\"", length=50)
	private String formato;
	@Column (name="\"COMPRESSIONE\"", length=50)
	private String compressione;
	@Column (name="\"NOME_FILES\"", length=50)
	private String nomeFiles;
	@Column (name="\"NUMERO_TOTALE_IMMAGINI\"")
	private Integer numeroTotaleImmagini;
	@Column (name="\"DIM_MEDIA_IMMAGINI\"")
	private Long dimMediaImmagini;
	@Column (name="\"DIM_TOTALE_IMMAGINI\"")
	private Long dimTotaleImmagini;
	@Column (name="\"RESPONSABILE_FOTORIPRODUZIONE\"", length=50)
	private String responsabileFotoRiproduzione;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"DATA_RIPRESA\"", length=50)
	private Date data_ripresa;
	@Column (name="\"OEPRATORE\"", length=50)
	private String operatore;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the nUnita
	 */
	public Integer getnUnita() {
		return nUnita;
	}
	
	/**
	 * @param nUnita the nUnita to set
	 */
	public void setnUnita(Integer nUnita) {
		this.nUnita = nUnita;
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
	 * @return the data_ripresa
	 */
	public Date getData_ripresa() {
		return data_ripresa;
	}
	
	/**
	 * @param data_ripresa the data_ripresa to set
	 */
	public void setData_ripresa(Date data_ripresa) {
		this.data_ripresa = data_ripresa;
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
