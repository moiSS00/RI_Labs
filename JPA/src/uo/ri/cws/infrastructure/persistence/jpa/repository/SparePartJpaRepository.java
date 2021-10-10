package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class SparePartJpaRepository extends BaseJpaRepository<SparePart> implements SparePartRepository {

	@Override
	public Optional<SparePart> findByCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("SparePart.findByCode", SparePart.class).setParameter(1, code).getResultStream()
				.findFirst();
	}

	@Override
	public List<SparePart> findUnderStockNotPending() {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("SparePart.findUnderStockNotPending", SparePart.class).getResultList();
	}

	@Override
	public List<SparePart> findByDescription(String description) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("SparePart.findByDescription", SparePart.class).setParameter(1, description)
				.getResultList();
	}

}
