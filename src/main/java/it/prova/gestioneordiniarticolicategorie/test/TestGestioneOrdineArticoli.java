package it.prova.gestioneordiniarticolicategorie.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.loading.PrivateClassLoader;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.articolo.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.categoria.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.ordine.OrdineService;

public class TestGestioneOrdineArticoli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrdineService ordineService = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloService = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaService = MyServiceFactory.getCategoriaServiceInstance();

		try {

			testInserimentoOrdine(ordineService, articoloService, categoriaService);

			testAggiornaOrdine(ordineService, articoloService, categoriaService);

			testInserimentoArticolo(ordineService, articoloService, categoriaService);

			testAggiornamentoArticolo(ordineService, articoloService, categoriaService);

			testEliminazioneArticoloDopoScollegamento(ordineService, articoloService, categoriaService);

			testInserimentoNuovaCategoria(ordineService, articoloService, categoriaService);

			testAggiornamentoCategoria(ordineService, articoloService, categoriaService);

			testAggiungiArticoloACategoria(ordineService, articoloService, categoriaService);

			testAggiungiCategoriaAdArticolo(ordineService, articoloService, categoriaService);

			testRimuoviArticoloDopoScollegamento(ordineService, articoloService, categoriaService);

			testRimuoviCategoriaDopoScollegamento(ordineService, articoloService, categoriaService);

			testRimuoviOrdine(ordineService, articoloService, categoriaService);

			testOrdiniDaCategoria(ordineService, articoloService, categoriaService);

			testCategorieDistinteDaOrdine(ordineService, articoloService, categoriaService);

			testSommaDiTuttiIPrezziDiArticoliDiUnaCategoria(ordineService, articoloService, categoriaService);

			testOrdinePiuRecenteDaCategoria(ordineService, articoloService, categoriaService);
			
			testListaCodiciDistintiDiCategoriaDiOrdiniEffettuatiDuranteUnMese(ordineService, articoloService, categoriaService); 
		
			testSommaTotDiTuttiIPrezziDegliArticoliIndirizzatiAdUnDestinatyario(ordineService, articoloService, categoriaService);
		
			testListaDistintaDiIndirizziCheContengonoNelNumeroSerialeUnaDeterminataStringa(ordineService, articoloService, categoriaService);
		
			testOrdiniinSituazioniStrane(ordineService, articoloService, categoriaService);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	// MYTOOL===============================
	private static Date stringToDate(String input) throws ParseException {
		Date result = new SimpleDateFormat("dd/MM/yyyy").parse(input);
		return result;
	}

	private static void intro(String titolo) {
		System.out.println("......." + titolo + " inizio.............");
	}

	private static void outro(String titolo) {
		System.out.println("......." + titolo + " fine: PASSED.............");
	}

	private static Ordine nuovoOrdine(String nome, String indirizzo, Date dataScadenza, OrdineService ordineService)
			throws Exception {

		// inserimento ordine
		Ordine nuovOrdine = new Ordine(nome, indirizzo, new Date(), dataScadenza);
		ordineService.insert(nuovOrdine);
		if (nuovOrdine.getId() == null) {
			throw new RuntimeException("testInserimentoOrdine: inserimento fallito");
		}
		return nuovOrdine;
	}

	private static Articolo nuovoArticolo(String descrizione, String numeroSeriale, Integer prezzo, Ordine ordine,
			ArticoloService articoloService) throws Exception {
		Articolo nuovoArticolo = new Articolo(descrizione, numeroSeriale, prezzo, new Date());
		nuovoArticolo.setOrdine(ordine);
		articoloService.insert(nuovoArticolo);
		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testInserimentoArticolo: articolo non inserito");
		}
		return nuovoArticolo;
	}

	private static Categoria nuovaCategoria(String descrizione, String codice, CategoriaService categoriaService)
			throws Exception {
		Categoria nuovaCategoria = new Categoria(descrizione, codice);
		categoriaService.insert(nuovaCategoria);
		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testInserimentoNuovaCategoria: FALLITA");
		}
		return nuovaCategoria;
	}

	private static void controlloListaOrdiniVuoto(String nomeDestinatario, String indirizzo, Date dataScadenza,
			OrdineService ordineService) throws ParseException, Exception {
		if (ordineService.list().isEmpty()) {
			System.err.println("testInserimentoArticolo: lista ordini vuota");
			Ordine nuovOrdine = nuovoOrdine(nomeDestinatario, indirizzo, dataScadenza, ordineService);
		}
	}

	private static void controlloListaArticoloVuoto(String descrizione, String codiceSeriale, Integer prezzo,
			ArticoloService articoloService, OrdineService ordineService) throws Exception {
		if (articoloService.list().isEmpty()) {
			System.err.println("testAggiornamentoArticolo: lista articolo vuota");
			Articolo nuovoArticolo = nuovoArticolo(descrizione, codiceSeriale, prezzo, ordineService.list().get(0),
					articoloService);
		}
	}

	private static void controlloListaCategoriaVuoto(String descrizione, String codice,
			CategoriaService categoriaService) throws Exception {
		if (categoriaService.list().isEmpty()) {
			System.err.println(" lista categoria vuota");
			Categoria nuovaCategoria = nuovaCategoria(descrizione, codice, categoriaService);
		}
	}

	private static void removeLink(Articolo articolo, Categoria categoria) {
		categoria.getArticolos().remove(articolo);
		articolo.getCategorias().remove(categoria);
		if (!(categoria.getArticolos().isEmpty() || articolo.getCategorias().isEmpty())) {
			throw new RuntimeException("disaccoppiamento fallito");
		}
	}

	private static void addLink(Articolo articolo, Categoria categoria) {
		categoria.setArticolos(new HashSet<>(Arrays.asList(articolo)));
		articolo.setCategorias(new HashSet<>(Arrays.asList(categoria)));
		if (categoria.getArticolos().isEmpty() || articolo.getCategorias().isEmpty()) {
			throw new RuntimeException(" accoppiamento fallito");
		}
	}

	// METODO================================
	// Inserimento ordine
	private static void testInserimentoOrdine(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {

		String titolo = "testInserimentoOrdine";
		intro(titolo);

		// inserimento ordine
		Ordine nuovOrdine = new Ordine("Mario", "Via di Casa Sua", new Date(), stringToDate("05/06/1999"));
		ordineService.insert(nuovOrdine);
		if (ordineService.get(nuovOrdine.getId()) == null) {
			throw new RuntimeException("testInserimentoOrdine: inserimento fallito");
		}
		outro(titolo);
	}

	// Aggiornamente ordine
	private static void testAggiornaOrdine(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {

		String titolo = "testAggiornaOrdine";
		intro(titolo);

		if (ordineService.list().isEmpty()) {
			System.err.println("testAggiornaOrdine: lista ordini vuota");
			Ordine nuovOrdine = nuovoOrdine("Mario", "Piazza Caterina Sforza", stringToDate("01/01/2021"),
					ordineService);
		}

		Ordine modificoOrdine = new Ordine("Lucia", ordineService.list().get(0).getIndirizzoSpedizione(),
				ordineService.list().get(0).getDataSpedizione(), ordineService.list().get(0).getDataScadenza());
		modificoOrdine.setId(ordineService.list().get(0).getId());
		if (!(ordineService.list().get(0).getId().equals(modificoOrdine.getId())
				|| ordineService.list().get(0).getNomeDestinatario().equals(modificoOrdine.getNomeDestinatario()))) {
			throw new RuntimeException("testAggiornaOrdine: FALLITO");
		}
		outro(titolo);
	}

	// Inserimento nuovo articolo
	private static void testInserimentoArticolo(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testInserimentoArticolo";
		intro(titolo);

		if (ordineService.list().isEmpty()) {
			System.err.println("testInserimentoArticolo: lista ordini vuota");
			Ordine nuovOrdine = nuovoOrdine("Mario", "Piazza Caterina Sforza", stringToDate("01/01/2021"),
					ordineService);
		}

		Articolo nuovoArticolo = new Articolo("LCD 60'", "r7y3978", 1000, new Date());
		nuovoArticolo.setOrdine(ordineService.list().get(0));
		articoloService.insert(nuovoArticolo);
		if (articoloService.get(nuovoArticolo.getId()) == null) {
			throw new RuntimeException("testInserimentoArticolo: articolo non inserito");
		}

		outro(titolo);
	}

	// Aggiornamento articolo esistente
	private static void testAggiornamentoArticolo(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testAggiornamentoArticolo";
		intro(titolo);

		if (ordineService.list().isEmpty()) {
			System.err.println("testInserimentoArticolo: lista ordini vuota");
			Ordine nuovOrdine = nuovoOrdine("Mario", "Piazza Caterina Sforza", stringToDate("01/01/2021"),
					ordineService);
		}
		if (articoloService.list().isEmpty()) {
			System.err.println("testAggiornamentoArticolo: lista articolo vuota");
			Articolo nuovoArticolo = nuovoArticolo("Ferrari", "5t9n8", 990000, ordineService.list().get(0),
					articoloService);
		}

		Articolo modificoArticolo = new Articolo("Maserati", articoloService.list().get(0).getNumeroSeriale(),
				articoloService.list().get(0).getPrezzoSingolo(), articoloService.list().get(0).getDataInserimento());
		modificoArticolo.setId(articoloService.list().get(0).getId());
		if (!(articoloService.list().get(0).getId().equals(modificoArticolo.getId())
				|| articoloService.list().get(0).getDescrizione().equals(modificoArticolo.getDescrizione()))) {
			throw new RuntimeException("testAggiornamentoArticolo: FALLITO");
		}
		outro(titolo);
	}

	// Scollega un articolo da un ordine ed eliminalo
	private static void testEliminazioneArticoloDopoScollegamento(OrdineService ordineService,
			ArticoloService articoloService, CategoriaService categoriaService) throws Exception {
		String titolo = "testEliminazioneArticoloDopoScollegamento";
		intro(titolo);

		Ordine nuovOrdine = nuovoOrdine("Luca", "Via mosca", stringToDate("28/10/2022"), ordineService);
		Articolo nuovoArticolo = nuovoArticolo("Forbice", "fwgehk", 3, nuovOrdine, articoloService);
		if (ordineService.findByIdFetchingOrdine(nuovOrdine.getId()).getArticolos() == null) {
			throw new RuntimeException("testEliminazioneArticoloDopoScollegamento: non funziona");
		}
		articoloService.delete(nuovoArticolo);
		if (articoloService.get(nuovoArticolo.getId()) != null) {
			throw new RuntimeException("testEliminazioneArticoloDopoScollegamento: FALLITO");
		}
		outro(titolo);
	}

	// Inserimento nuova categoria
	private static void testInserimentoNuovaCategoria(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testInserimentoNuovaCategoria";
		intro(titolo);

		Categoria nuovaCategoria = new Categoria("Ferramenta", "5t4r8yehd");
		categoriaService.insert(nuovaCategoria);
		if (categoriaService.get(nuovaCategoria.getId()) == null) {
			throw new RuntimeException("testInserimentoNuovaCategoria: FALLITA");
		}
		outro(titolo);
	}

	// Aggiornamento categoria esistente
	private static void testAggiornamentoCategoria(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testAggiornamentoCategoria";
		intro(titolo);

		if (categoriaService.list().isEmpty()) {
			System.err.println(titolo + ": lista categoria vuota");
			Categoria nuovaCategoria = nuovaCategoria("Giardinaggio", "codice2", categoriaService);
		}

		Categoria modificoCategoria = new Categoria("Cucina", categoriaService.list().get(0).getCodice());
		modificoCategoria.setId(categoriaService.list().get(0).getId());
		if (!(categoriaService.list().get(0).getId().equals(modificoCategoria.getId())
				|| categoriaService.list().get(0).getDescrizione().equals(modificoCategoria.getDescrizione()))) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	// Aggiungi articolo a categoria
	private static void testAggiungiArticoloACategoria(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testAggiungiArticoloACategoria";
		intro(titolo);

		controlloListaOrdiniVuoto("Toni", "Dove abita", stringToDate("29/10/2022"), ordineService);
		controlloListaArticoloVuoto("Tazza", "reydjk", 5, articoloService, ordineService);
		controlloListaCategoriaVuoto("Porcellana", "categoria3", categoriaService);

		articoloService.list().get(0).setCategorias(new HashSet<>(Arrays.asList(categoriaService.list().get(0))));
		if (articoloService.findByIdFetchingArticolo(articoloService.list().get(0).getId()).getCategorias()
				.equals(categoriaService.list().get(0))) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	// Aggiungi categoria ad un articolo
	private static void testAggiungiCategoriaAdArticolo(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testAggiungiCategoriaAdArticolo";
		intro(titolo);

		controlloListaOrdiniVuoto("Toni", "Dove abita", stringToDate("29/10/2022"), ordineService);
		controlloListaArticoloVuoto("Tazza", "reydjk", 5, articoloService, ordineService);
		controlloListaCategoriaVuoto("Porcellana", "categoria3", categoriaService);

		Articolo nuovoArticolo = nuovoArticolo("Coltelli da prosciutto", "8e7j", 10, ordineService.list().get(0),
				articoloService);
		categoriaService.addLink(nuovoArticolo, categoriaService.list().get(0));
		if (categoriaService.findByIdFetchingCategoria(categoriaService.list().get(0).getId()).getArticolos()
				.contains(nuovoArticolo)) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	// Rimozione articolo (previo scollegamento dalle categorie)
	private static void testRimuoviArticoloDopoScollegamento(OrdineService ordineService,
			ArticoloService articoloService, CategoriaService categoriaService) throws Exception {
		String titolo = "testRimuoviArticoloDopoScollegamento";
		intro(titolo);

		Categoria nuovaCategoria = nuovaCategoria("Abbigliamento", "codice 435", categoriaService);
		Articolo nuovoArticolo = nuovoArticolo("Maglietta", "3u98", 15, ordineService.list().get(0), articoloService);

		articoloService.addLink(nuovoArticolo, nuovaCategoria);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": accoppiamento fallito");
		}

		articoloService.removeLink(nuovoArticolo, nuovaCategoria);
		if (!(articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty())) {
			throw new RuntimeException(titolo + ":disaccoppiamento fallito");
		}

		articoloService.delete(nuovoArticolo);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()) != null) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	// Rimozione categoria (previo scollegamento dagli articoli)
	private static void testRimuoviCategoriaDopoScollegamento(OrdineService ordineService,
			ArticoloService articoloService, CategoriaService categoriaService) throws Exception {
		String titolo = "testRimuoviCategoriaDopoScollegamento";
		intro(titolo);

		Categoria nuovaCategoria = nuovaCategoria("Abbigliamento", "codice 435", categoriaService);
		Articolo nuovoArticolo = nuovoArticolo("Maglietta", "3u98", 15, ordineService.list().get(0), articoloService);

		categoriaService.addLink(nuovoArticolo, nuovaCategoria);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": accoppiamento fallito");
		}

		categoriaService.removeLink(nuovoArticolo, nuovaCategoria);
		if (!(articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty())) {
			throw new RuntimeException(titolo + ":disaccoppiamento fallito");
		}

		categoriaService.delete(nuovaCategoria);
		if (categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()) != null) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	/*
	 * Rimozione ordine (nel caso in cui sia collegato ad ad almeno un articolo
	 * lanciare eccezione custom)
	 */
	private static void testRimuoviOrdine(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testRimuoviOrdine";
		intro(titolo);

		Ordine nuovOrdine = nuovoOrdine("Azzurra", "Ponte Mammolo", stringToDate("29/10/2022"), ordineService);
		Categoria nuovaCategoria = nuovaCategoria("Abbigliamento", "codice 435", categoriaService);
		Articolo nuovoArticolo = nuovoArticolo("Maglietta", "3u98", 15, nuovOrdine, articoloService);

		ordineService.addLink(nuovoArticolo, nuovOrdine);
		articoloService.addLink(nuovoArticolo, nuovaCategoria);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": accoppiamento fallito");
		}

		for (Articolo element : ordineService.findByIdFetchingOrdine(nuovOrdine.getId()).getArticolos()) {
			articoloService.delete(element);
		}
		if (!ordineService.findByIdFetchingOrdine(nuovOrdine.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": rimozione degli articoli dall'ordine fallita");
		}

		ordineService.delete(nuovOrdine);
		if (ordineService.get(nuovOrdine.getId()) != null) {
			throw new RuntimeException(titolo + ": FALLITO");
		}
		outro(titolo);
	}

	// Tutti gli ordini da una determinata categoria
	private static void testOrdiniDaCategoria(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testOrdiniDaCategoria";
		intro(titolo);

		Ordine nuovOrdine1 = nuovoOrdine("Lucia", "Casa sua", stringToDate("31/10/2022"), ordineService);
		Articolo nuovoArticolo1 = nuovoArticolo("Vestiti", "gw48", 20, nuovOrdine1, articoloService);
		Ordine nuovOrdine2 = nuovoOrdine("Mario", "Casa sua", stringToDate("31/10/2022"), ordineService);
		Articolo nuovoArticolo2 = nuovoArticolo("Scarpe", "f8rio", 30, nuovOrdine2, articoloService);
		Categoria nuovaCategoria = nuovaCategoria("Abbigliamento", "Codice 7", categoriaService);
		categoriaService.addLink(nuovoArticolo2, nuovaCategoria);
		categoriaService.addLink(nuovoArticolo1, nuovaCategoria);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo1.getId()).getCategorias().isEmpty()
				|| articoloService.findByIdFetchingArticolo(nuovoArticolo2.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": accoppiamento fallito");
		}

		if (categoriaService.findOrdineByCategoria(nuovaCategoria.getId()).size() != 2) {
			throw new RuntimeException(titolo + ": FALLITO");
		}

		outro(titolo);
	}

	// Voglio tutte le categorie distinte di un determinato ordine
	private static void testCategorieDistinteDaOrdine(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testCategorieDistinteDaOrdine";
		intro(titolo);

		Ordine nuovOrdine = nuovoOrdine("Lucia", "Casa sua", stringToDate("31/10/2022"), ordineService);
		Articolo nuovoArticolo1 = nuovoArticolo("Vestiti", "gw48", 20, nuovOrdine, articoloService);
		Articolo nuovoArticolo2 = nuovoArticolo("Telefono", "f8rio", 30, nuovOrdine, articoloService);
		Categoria nuovaCategoria1 = nuovaCategoria("Abbigliamento", "Codice 7", categoriaService);
		Categoria nuovaCategoria2 = nuovaCategoria("Elettronica", "codice 8", categoriaService);
		categoriaService.addLink(nuovoArticolo2, nuovaCategoria2);
		categoriaService.addLink(nuovoArticolo1, nuovaCategoria1);
		if (articoloService.findByIdFetchingArticolo(nuovoArticolo1.getId()).getCategorias().isEmpty()
				|| articoloService.findByIdFetchingArticolo(nuovoArticolo2.getId()).getCategorias().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria1.getId()).getArticolos().isEmpty()
				|| categoriaService.findByIdFetchingCategoria(nuovaCategoria2.getId()).getArticolos().isEmpty()) {
			throw new RuntimeException(titolo + ": accoppiamento fallito");
		}

		if (ordineService.findCategorieDistinteByOrdine(nuovOrdine.getId()).size() != 2) {
			throw new RuntimeException(titolo + ": FALLITO");
		}

		outro(titolo);
	}

	// Voglio la somma dei prezzi di tutti gli articoli assegnati ad una categoria
	private static void testSommaDiTuttiIPrezziDiArticoliDiUnaCategoria(OrdineService ordineService,
			ArticoloService articoloService, CategoriaService categoriaService) throws Exception {
		String titolo = "testSommaDiTuttiIPrezziDiArticoliDiUnaCategoria";
		intro(titolo);

		Categoria nuovaCategoria = nuovaCategoria("Ferramenta", "Codice 4", categoriaService);
		Articolo nuovoArticolo1 = nuovoArticolo("Trapano", "bvgajwefnm", 50, ordineService.list().get(0),
				articoloService);
		Articolo nuovoArticolo2 = nuovoArticolo("Set chiavi", "t3834", 30, ordineService.list().get(0),
				articoloService);
		categoriaService.addLink(nuovoArticolo2, nuovaCategoria);
		categoriaService.addLink(nuovoArticolo1, nuovaCategoria);
		if (categoriaService.totPrezziArticoloDiCategoria(nuovaCategoria.getId()) != 80) {
			throw new RuntimeException(titolo + ": FALLITO");
		}

		outro(titolo);
	}

	// Voglio l'ordine con la spedizione piu recente data una categoria
	private static void testOrdinePiuRecenteDaCategoria(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testOrdinePiuRecenteDaCategoria";
		intro(titolo);

		
		if (categoriaService
				.ordineConSpedizionePiuRecenteDaCategoria(categoriaService.list().get(0).getId())==null) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		
		outro(titolo);
	}
	
	//Voglio la lista distinta di codici di categoria degli ordini effettuati durante l'arco di un mese
	private static void testListaCodiciDistintiDiCategoriaDiOrdiniEffettuatiDuranteUnMese(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testListaCodiciDistintiDiCategoriaDiOrdiniEffettuatiDuranteUnMese";
		intro(titolo);

		
		if (ordineService.codiciDiCategoriaDiOrdiniDiUnMese(stringToDate("01/10/2022"))==null) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		
		outro(titolo);
	}
	
	//Voglio la somma totale dei prezzi di tutti gli articoli indirizzati ad un destinatario
	private static void testSommaTotDiTuttiIPrezziDegliArticoliIndirizzatiAdUnDestinatyario(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testSommaTotDiTuttiIPrezziDegliArticoliIndirizzatiAdUnDestinatyario";
		intro(titolo);

		if (ordineService.totPrezziDiArticoloDiUnDestinatario("mario")==52310) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		outro(titolo);
	}

	//Voglio la lista di indirizzi distinti di ordini che contengono una detereminata stringa nel numero seriale nei relativi articoli
	private static void testListaDistintaDiIndirizziCheContengonoNelNumeroSerialeUnaDeterminataStringa(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testListaDistintaDiIndirizziCheContengonoNelNumeroSerialeUnaDeterminataStringa";
		intro(titolo);

		if (ordineService.listaDistintaDiIndirizziContenentiUnaCertaStringaNelLoroNumeroSeriale("8").isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		outro(titolo);
	}

	//Voglio la lista di articoli in situazioni strane vale a dire che l'ordine e' stato spedito oltre la data di scadenza
	private static void testOrdiniinSituazioniStrane(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		String titolo = "testOrdiniinSituazioniStrane";
		intro(titolo);

		if (ordineService.listaArticoliSituazioniStrane().isEmpty()) {
			throw new RuntimeException(titolo+": FALLITO");
		}
		outro(titolo);
	}
}
