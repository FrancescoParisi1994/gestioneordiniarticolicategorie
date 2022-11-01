package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public Categoria findByIdFetchingCategoria(Long id) throws Exception;

	public void removeLink(Articolo articolo, Categoria categoria);

	public Set<Ordine> findOrdineByCategoria(Long id);
	
	public int totPrezziArticoloDiCategoria(Long id);
	
	public Ordine ordineConSpedizionePiuRecenteDaCategoria(Long id);
}
