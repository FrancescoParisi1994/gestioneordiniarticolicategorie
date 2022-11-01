package it.prova.gestioneordiniarticolicategorie.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.prova.gestioneordiniarticolicategorie.model.*;

@Entity
@Table(name = "articolo")
public class Articolo {
//Articolo (id, descrizione, numeroSeriale, prezzoSingolo, dataInserimento, ordine (nullable=false), categorie )
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "numeroseriale")
	private String numeroSeriale;
	@Column(name = "prezzosingolo")
	private Integer prezzoSingolo;
	@Column(name = "dataInserimento")
	private Date dataInserimento;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordine_id", nullable = false)
	private Ordine ordine;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "articolo_categoria", joinColumns = @JoinColumn(name = "articolo_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "ID"))
	private Set<Categoria> categorias = new HashSet<Categoria>();

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Articolo() {
		super();
	}

	public Articolo(String descrizione, String numeroSeriale, Integer prezzoSingolo, Date dataInserimento) {
		super();
		this.descrizione = descrizione;
		this.numeroSeriale = numeroSeriale;
		this.prezzoSingolo = prezzoSingolo;
		this.dataInserimento = dataInserimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNumeroSeriale() {
		return numeroSeriale;
	}

	public void setNumeroSeriale(String numeroSeriale) {
		this.numeroSeriale = numeroSeriale;
	}

	public Integer getPrezzoSingolo() {
		return prezzoSingolo;
	}

	public void setPrezzoSingolo(Integer prezzoSingolo) {
		this.prezzoSingolo = prezzoSingolo;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public static void addLink(Articolo articolo, Categoria categoria) {
		categoria.setArticolos(new HashSet<>(Arrays.asList(articolo)));
		articolo.setCategorias(new HashSet<>(Arrays.asList(categoria)));
		if (categoria.getArticolos().isEmpty() || articolo.getCategorias().isEmpty()) {
			throw new RuntimeException(" accoppiamento fallito");
		}
	}


	public static void removeLink(Articolo articolo,Categoria categoria) {
		categoria.getArticolos().remove(articolo);
		articolo.getCategorias().remove(categoria);
		if (!(categoria.getArticolos().isEmpty()||articolo.getCategorias().isEmpty())) {
			throw new RuntimeException("disaccoppiamento fallito");
		}
	}

}
