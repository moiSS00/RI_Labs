package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ProviderJpaRepository extends BaseJpaRepository<Provider> implements ProviderRepository {

	@Override
	public Optional<Provider> findByNif(String nif) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findByNif", Provider.class).setParameter(1, nif).getResultStream()
				.findFirst();
	}

	@Override
	public List<Provider> findByNameMailPhone(String name, String email, String phone) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findByNameMailPhone", Provider.class).setParameter(1, name)
				.setParameter(2, email).setParameter(3, phone).getResultList();
	}

	@Override
	public List<Provider> findByName(String name) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findByName", Provider.class).setParameter(1, name).getResultList();
	}

	@Override
	public List<Provider> findBySparePartCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findBySparePartCode", Provider.class).setParameter(1, code)
				.getResultList();
	}

}
