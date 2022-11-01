package it.prova.gestioneordiniarticolicategorie.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {
//Categoria (id, descrizione, codice, articoli )
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "codice")
	private String codice;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorias")
	private Set<Articolo> articolos = new HashSet<Articolo>();

	public Categoria() {
		super();
	}

	public Categoria(String descrizione, String codice) {
		super();
		this.descrizione = descrizione;
		this.codice = codice;
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

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Set<Articolo> getArticolos() {
		return articolos;
	}

	public void setArticolos(Set<Articolo> articolos) {
		this.articolos = articolos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, descrizione, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Categoria)) {
			return false;
		}
		Categoria other = (Categoria) obj;
		return Objects.equals(codice, other.codice) && Objects.equals(descrizione, other.descrizione)
				&& Objects.equals(id, other.id);
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
