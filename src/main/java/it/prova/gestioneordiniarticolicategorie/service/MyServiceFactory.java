package it.prova.gestioneordiniarticolicategorie.service;

import it.prova.gestioneordiniarticolicategorie.dao.MyDaoFactory;
import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAOImp;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.service.articolo.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.articolo.ArticoloServiceImp;
import it.prova.gestioneordiniarticolicategorie.service.categoria.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.categoria.CategoriaServiceImp;
import it.prova.gestioneordiniarticolicategorie.service.ordine.OrdineService;
import it.prova.gestioneordiniarticolicategorie.service.ordine.OrdineServiceImp;

public class MyServiceFactory {

	private static ArticoloService articoloServiceInstnce = null;
	private static OrdineService ordineServiceInstance = null;
	private static CategoriaService categoriaServiceInstance = null;

	public static ArticoloService getArticoloServiceInstance() {
		if (articoloServiceInstnce == null)
			articoloServiceInstnce = new ArticoloServiceImp();

		articoloServiceInstnce.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());
		articoloServiceInstnce.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());
		articoloServiceInstnce.setOrdineDAO(MyDaoFactory.getOrdineDAOInstance());
		return articoloServiceInstnce;
	}

	public static CategoriaService getCategoriaServiceInstance() {
		if (categoriaServiceInstance == null)
			categoriaServiceInstance = new CategoriaServiceImp();

		categoriaServiceInstance.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());
		categoriaServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());
		categoriaServiceInstance.setOrdineDAO(MyDaoFactory.getOrdineDAOInstance());
		return categoriaServiceInstance;
	}

	public static OrdineService getOrdineServiceInstance() {
		if (ordineServiceInstance == null)
			ordineServiceInstance = new OrdineServiceImp();

		ordineServiceInstance.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());
		ordineServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());
		ordineServiceInstance.setOrdineDAO(MyDaoFactory.getOrdineDAOInstance());
		return ordineServiceInstance;
	}

}
