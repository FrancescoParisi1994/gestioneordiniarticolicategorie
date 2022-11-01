package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloDAOImp implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		o = entityManager.merge(o);
	}

	@Override
	public void insert(Articolo o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(o);
	}

	@Override
	public void delete(Articolo o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(o));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Articolo findByIdFetchingArticolo(Long id) throws Exception {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"select c FROM Articolo c left join fetch c.categorias g left join fetch c.ordine o where c.id = :idCd",
				Articolo.class);
		query.setParameter("idCd", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	public void removeLink(Articolo articolo,Categoria categoria) {
		 Query query=entityManager.createNativeQuery("delete from articolo_categoria where articolo_id=?1 and categoria_id=?2");
		query.setParameter(1, articolo.getId());
		query.setParameter(2, categoria.getId());
		query.executeUpdate();
	}
}
