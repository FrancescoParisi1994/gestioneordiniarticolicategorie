package it.prova.gestioneordiniarticolicategorie.service.categoria;

import java.util.List;
import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAOImp;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService {
	public List<Categoria> list() throws Exception;

	public Categoria get(Long id) throws Exception;

	public void update(Categoria o) throws Exception;

	public void insert(Categoria o) throws Exception;

	public void delete(Categoria o) throws Exception;

	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public void setOrdineDAO(OrdineDAO ordineDAO);

	public Categoria findByIdFetchingCategoria(Long id) throws Exception;

	public void addLink(Articolo articolo, Categoria categoria);

	public void removeLink(Articolo articolo, Categoria categoria);

	public Set<Ordine> findOrdineByCategoria(Long id);
	
	public int totPrezziArticoloDiCategoria(Long id);

	public Ordine ordineConSpedizionePiuRecenteDaCategoria(Long id);
}
