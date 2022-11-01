package it.prova.gestioneordiniarticolicategorie.service.ordine;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {
	public List<Ordine> list() throws Exception;

	public Ordine get(Long id) throws Exception;

	public void update(Ordine o) throws Exception;

	public void insert(Ordine o) throws Exception;

	public void delete(Ordine o) throws Exception;

	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public void setOrdineDAO(OrdineDAO ordineDAO);

	public Ordine findByIdFetchingOrdine(Long id) throws Exception;

	public void addLink(Articolo articolo, Ordine ordine);

	public Set<Categoria> findCategorieDistinteByOrdine(Long id);

	public Set<String> codiciDiCategoriaDiOrdiniDiUnMese(Date data);

	public int totPrezziDiArticoloDiUnDestinatario(String nome);

	public Set<String> listaDistintaDiIndirizziContenentiUnaCertaStringaNelLoroNumeroSeriale(String codice);
	
	public List<Articolo> listaArticoliSituazioniStrane();
}
