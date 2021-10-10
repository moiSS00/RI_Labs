package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.domain.Supply;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class SupplyJpaRepository extends BaseJpaRepository<Supply> implements SupplyRepository {

	@Override
	public Optional<Supply> findByNifAndCode(String nif, String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Supply.findByNifAndCode", Supply.class).setParameter(1, nif).setParameter(2, code)
				.getResultStream().findFirst();
	}

	@Override
	public List<Supply> findByProviderNif(String nif) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Supply.findByProviderNif", Supply.class).setParameter(1, nif).getResultList();
	}

	@Override
	public List<Supply> findBySparePartCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Supply.findBySparePartCode", Supply.class).setParameter(1, code).getResultList();
	}

}
