package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.ObjectUtils.Null;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import javassist.expr.NewArray;

public class OrdineDAOImp implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public Ordine findByIdFetchingOrdine(Long id) throws Exception {
		TypedQuery<Ordine> query = entityManager
				.createQuery("select c FROM Ordine c left join fetch c.articolos g where c.id = :idCd", Ordine.class);
		query.setParameter("idCd", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> list() throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery("select o from Ordine o", Ordine.class);
		return query.getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		o = entityManager.merge(o);
	}

	@Override
	public void insert(Ordine o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(o);
	}

	@Override
	public void delete(Ordine o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(o));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Set<Categoria> findCategorieDistinteByOrdine(Long id) {
		return new HashSet<>(entityManager
				.createQuery("select c from Ordine o left join o.articolos a left join a.categorias c where o.id=:is")
				.setParameter("is", id).getResultList());
	}

	public Set<String> codiciDiCategoriaDiOrdiniDiUnMese(Date data) {
		Query query = entityManager.createQuery(
				"select c.codice from Ordine o left join o.articolos a left join a.categorias c where month(o.dataSpedizione)=:month and year(o.dataSpedizione)=:year");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		int mese = calendar.get(Calendar.MONTH) + 1;
		int anno = calendar.get(Calendar.YEAR);
		query.setParameter("month", mese);
		query.setParameter("year", anno);
		return new HashSet(query.getResultList());
	}

	public int totPrezziDiArticoloDiUnDestinatario(String nome) {
		Query query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) from Ordine o left join o.articolos a where o.nomeDestinatario=:is");
		query.setParameter("is", nome);
		return (int) (long) (Long) query.getResultList().stream().findFirst().orElse(0);
	}

	public Set<String> listaDistintaDiIndirizziContenentiUnaCertaStringaNelLoroNumeroSeriale(String codice) {
		Query query = entityManager.createQuery(
				"select distinct o.indirizzoSpedizione from Ordine o left join o.articolos a where a.numeroSeriale like :is");
		query.setParameter("is", "%"+codice+"%");
		return new HashSet<>(query.getResultList());
	}

	public List<Articolo> listaArticoliSituazioniStrane(){
		TypedQuery<Articolo> query=entityManager.createQuery("select a from Ordine o left join o.articolos a where o.dataScadenza<o.dataSpedizione",Articolo.class);
		return query.getResultList();
	}

}
