package it.prova.gestioneordiniarticolicategorie.service.articolo;

import java.util.List;
import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloService {

	public List<Articolo> list() throws Exception;

	public Articolo get(Long id) throws Exception;

	public void update(Articolo o) throws Exception;

	public void insert(Articolo o) throws Exception;

	public void delete(Articolo o) throws Exception;

	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public void setOrdineDAO(OrdineDAO ordineDAO);

	public Articolo findByIdFetchingArticolo(Long id) throws Exception;

	public void addLink(Articolo articolo, Categoria categoria);

	public void removeLink(Articolo articolo, Categoria categoria);

}
