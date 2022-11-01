package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	public Ordine findByIdFetchingOrdine(Long id) throws Exception;

	public Set<Categoria> findCategorieDistinteByOrdine(Long id);

	public Set<String> codiciDiCategoriaDiOrdiniDiUnMese(Date data);

	public int totPrezziDiArticoloDiUnDestinatario(String nome);

	public Set<String> listaDistintaDiIndirizziContenentiUnaCertaStringaNelLoroNumeroSeriale(String codice);
	
	public List<Articolo> listaArticoliSituazioniStrane();
}
