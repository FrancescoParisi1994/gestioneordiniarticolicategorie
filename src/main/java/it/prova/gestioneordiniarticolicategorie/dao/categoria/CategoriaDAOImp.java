package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImp implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public Categoria findByIdFetchingCategoria(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articolos g left join fetch g.ordine o where c.id = :idCd",
				Categoria.class);
		query.setParameter("idCd", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		o = entityManager.merge(o);
	}

	@Override
	public void insert(Categoria o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(o);
	}

	@Override
	public void delete(Categoria o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(o));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void removeLink(Articolo articolo, Categoria categoria) {
		Query query = entityManager
				.createNativeQuery("delete from articolo_categoria where articolo_id=?1 and categoria_id=?2");
		query.setParameter(1, articolo.getId());
		query.setParameter(2, categoria.getId());
		query.executeUpdate();
	}

	public Set<Ordine> findOrdineByCategoria(Long id) {
		return new HashSet<>(entityManager.createNativeQuery(
				"select o.* from categoria c inner join articolo_categoria t on c.id=t.categoria_id inner join articolo a on a.id=t.articolo_id inner join ordine o on o.id=a.ordine_id where c.id=:setId")
				.setParameter("setId", id).getResultList());
	}

	public int totPrezziArticoloDiCategoria(Long id) {
		Query query = entityManager
				.createQuery("select sum(a.prezzoSingolo) from Categoria as c left join c.articolos a where c.id=:is");
		query.setParameter("is", id);
		return (int) (long) (Long) query.getResultStream().findFirst().orElse(0);
	}

	public Ordine ordineConSpedizionePiuRecenteDaCategoria(Long id) {
		return (Ordine) entityManager.createQuery(
				"select o from Categoria c left join  c.articolos a left join  a.ordine o where c.id=:is order by o.dataSpedizione desc")
				.setParameter("is", id).getSingleResult();

	}
}
