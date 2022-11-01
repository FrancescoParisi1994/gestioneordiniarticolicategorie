package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo findByIdFetchingArticolo(Long id) throws Exception;

	public void removeLink(Articolo articolo, Categoria categoria);

}
