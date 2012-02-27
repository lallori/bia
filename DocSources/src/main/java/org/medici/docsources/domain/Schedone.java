/*
 * Schedone.java
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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * This class represents entity Schedone. This is the unique entity in domain 
 * which has fields defined in italian language. 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblSchedone\"" ) 
public class Schedone {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"ID\"", length=10, nullable=false)
	private Integer schedoneId;
	@Column (name="\"ISTITUTO\"", length=50)
	private String istituto;
	@Column (name="\"FONDO\"", length=50)
	private String fondo;
	@Column (name="\"SERIE\"", length=50)
	private String serie;
	@Column (name="\"N_UNITA\"")
	private Integer numeroUnita;
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
	@Column (name="\"COLORE_IMMAGINE\"", length=10)
	private String coloreImmagine;
	@Column (name="\"RISOLUZIONE\"", length=50)
	private String risoluzione;
	@Column (name="\"FORMATO\"", length=50)
	private String formato;
	@Column (name="\"COMPRESSIONE\"", length=50)
	private String compressione;
	@Column (name="\"NOME_FILES\"", length=50)
	private String nomeFiles;
	@Column (name="\"NUMERO_TOTALE_IMMAGINI_TIFF\"")
	private Integer numeroTotaleImmaginiTiff;
	@Column (name="\"DIM_MEDIA_IMMAGINI_TIFF\"")
	private Long dimMediaImmaginiTiff;
	@Column (name="\"DIM_TOTALE_IMMAGINI_TIFF\"")
	private Long dimTotaleImmaginiTiff;
	@Column (name="\"NUMERO_TOTALE_IMMAGINI_JPEG\"")
	private Integer numeroTotaleImmaginiJpeg;
	@Column (name="\"DIM_MEDIA_IMMAGINI_JPEG\"")
	private Long dimMediaImmaginiJpeg;
	@Column (name="\"DIM_TOTALE_IMMAGINI_JPEG\"")
	private Long dimTotaleImmaginiJpeg;
	@Column (name="\"NUMERO_TOTALE_IMMAGINI_PDF\"")
	private Integer numeroTotaleImmaginiPdf;
	@Column (name="\"DIM_MEDIA_IMMAGINI_PDF\"")
	private Long dimMediaImmaginiPdf;
	@Column (name="\"DIM_TOTALE_IMMAGINI_PDF\"")
	private Long dimTotaleImmaginiPdf;
	@Column (name="\"RESPONSABILE_FOTORIPRODUZIONE\"", length=50)
	private String responsabileFotoRiproduzione;
	@Column (name="\"TIPO_RIPRESA\"", length=50)
	private String tipoRipresa;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"DATA_RIPRESA\"", length=50)
	private Date dataRipresa;
	@Column (name="\"OPERATORE\"", length=50)
	private String operatore;
	@Column (name="\"RESID\"")
	private String researcher;
	@Column (name="\"DATA_CREAZIONE\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCreazione;
	@Column (name="\"DATA_ULTIMO_AGGIORNAMENTO\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAggiornamento;

	/**
	 * Default contructor.
	 */
	public Schedone() {
		super();
	}

	/**
	 * 
	 * @param schedoneId
	 */
	public Schedone(Integer schedoneId) {
		super();
		setSchedoneId(schedoneId);
	}

	/**
	 * @return the schedoneId
	 */
	public Integer getSchedoneId() {
		return schedoneId;
	}
	
	/**
	 * @param schedoneId the schedoneId to set
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
	 * @param numeroUnita the numeroUnita to set
	 */
	public void setNumeroUnita(Integer numeroUnita) {
		this.numeroUnita = numeroUnita;
	}

	/**
	 * @return the numeroUnita
	 */
	public Integer getNumeroUnita() {
		return numeroUnita;
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
	 * @return the numeroTotaleImmaginiTiff
	 */
	public Integer getNumeroTotaleImmaginiTiff() {
		return numeroTotaleImmaginiTiff;
	}

	/**
	 * @param numeroTotaleImmaginiTiff the numeroTotaleImmaginiTiff to set
	 */
	public void setNumeroTotaleImmaginiTiff(Integer numeroTotaleImmaginiTiff) {
		this.numeroTotaleImmaginiTiff = numeroTotaleImmaginiTiff;
	}

	/**
	 * @return the dimMediaImmaginiTiff
	 */
	public Long getDimMediaImmaginiTiff() {
		return dimMediaImmaginiTiff;
	}

	/**
	 * @param dimMediaImmaginiTiff the dimMediaImmaginiTiff to set
	 */
	public void setDimMediaImmaginiTiff(Long dimMediaImmaginiTiff) {
		this.dimMediaImmaginiTiff = dimMediaImmaginiTiff;
	}

	/**
	 * @return the dimTotaleImmaginiTiff
	 */
	public Long getDimTotaleImmaginiTiff() {
		return dimTotaleImmaginiTiff;
	}

	/**
	 * @param dimTotaleImmaginiTiff the dimTotaleImmaginiTiff to set
	 */
	public void setDimTotaleImmaginiTiff(Long dimTotaleImmaginiTiff) {
		this.dimTotaleImmaginiTiff = dimTotaleImmaginiTiff;
	}

	/**
	 * @return the numeroTotaleImmaginiJpeg
	 */
	public Integer getNumeroTotaleImmaginiJpeg() {
		return numeroTotaleImmaginiJpeg;
	}

	/**
	 * @param numeroTotaleImmaginiJpeg the numeroTotaleImmaginiJpeg to set
	 */
	public void setNumeroTotaleImmaginiJpeg(Integer numeroTotaleImmaginiJpeg) {
		this.numeroTotaleImmaginiJpeg = numeroTotaleImmaginiJpeg;
	}

	/**
	 * @return the dimMediaImmaginiJpeg
	 */
	public Long getDimMediaImmaginiJpeg() {
		return dimMediaImmaginiJpeg;
	}

	/**
	 * @param dimMediaImmaginiJpeg the dimMediaImmaginiJpeg to set
	 */
	public void setDimMediaImmaginiJpeg(Long dimMediaImmaginiJpeg) {
		this.dimMediaImmaginiJpeg = dimMediaImmaginiJpeg;
	}

	/**
	 * @return the dimTotaleImmaginiJpeg
	 */
	public Long getDimTotaleImmaginiJpeg() {
		return dimTotaleImmaginiJpeg;
	}

	/**
	 * @param dimTotaleImmaginiJpeg the dimTotaleImmaginiJpeg to set
	 */
	public void setDimTotaleImmaginiJpeg(Long dimTotaleImmaginiJpeg) {
		this.dimTotaleImmaginiJpeg = dimTotaleImmaginiJpeg;
	}

	/**
	 * @return the numeroTotaleImmaginiPdf
	 */
	public Integer getNumeroTotaleImmaginiPdf() {
		return numeroTotaleImmaginiPdf;
	}

	/**
	 * @param numeroTotaleImmaginiPdf the numeroTotaleImmaginiPdf to set
	 */
	public void setNumeroTotaleImmaginiPdf(Integer numeroTotaleImmaginiPdf) {
		this.numeroTotaleImmaginiPdf = numeroTotaleImmaginiPdf;
	}

	/**
	 * @return the dimMediaImmaginiPdf
	 */
	public Long getDimMediaImmaginiPdf() {
		return dimMediaImmaginiPdf;
	}

	/**
	 * @param dimMediaImmaginiPdf the dimMediaImmaginiPdf to set
	 */
	public void setDimMediaImmaginiPdf(Long dimMediaImmaginiPdf) {
		this.dimMediaImmaginiPdf = dimMediaImmaginiPdf;
	}

	/**
	 * @return the dimTotaleImmaginiPdf
	 */
	public Long getDimTotaleImmaginiPdf() {
		return dimTotaleImmaginiPdf;
	}

	/**
	 * @param dimTotaleImmaginiPdf the dimTotaleImmaginiPdf to set
	 */
	public void setDimTotaleImmaginiPdf(Long dimTotaleImmaginiPdf) {
		this.dimTotaleImmaginiPdf = dimTotaleImmaginiPdf;
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
	 * @return the dataRipresa
	 */
	public Date getDataRipresa() {
		return dataRipresa;
	}
	
	/**
	 * @param dataRipresa the dataRipresa to set
	 */
	public void setDataRipresa(Date dataRipresa) {
		this.dataRipresa = dataRipresa;
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

	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}

	/**
	 * @param dataCreazione the dataCreazione to set
	 */
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * @return the dataCreazione
	 */
	public Date getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * @param dataUltimoAggiornamento the dataUltimoAggiornamento to set
	 */
	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	/**
	 * @return the dataUltimoAggiornamento
	 */
	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}
}
