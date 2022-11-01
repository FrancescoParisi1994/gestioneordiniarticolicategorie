package it.prova.gestioneordiniarticolicategorie.dao;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAOImp;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAOImp;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAOImp;

public class MyDaoFactory {

	private static ArticoloDAO articoloDAOInstance = null;
	private static OrdineDAO ordineDAOInstnce = null;
	private static CategoriaDAO categoriaDAOInstance = null;

	public static ArticoloDAO getArticoloDAOInstance() {
		if (articoloDAOInstance == null)
			articoloDAOInstance = new ArticoloDAOImp();

		return articoloDAOInstance;
	}

	public static OrdineDAO getOrdineDAOInstance() {
		if (ordineDAOInstnce == null)
			ordineDAOInstnce = new OrdineDAOImp();

		return ordineDAOInstnce;
	}

	public static CategoriaDAO getCategoriaDAOInstance() {
		if (categoriaDAOInstance == null)
			categoriaDAOInstance = new CategoriaDAOImp();

		return categoriaDAOInstance;
	}

}
