package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class InvoiceJpaRepository extends BaseJpaRepository<Invoice> implements InvoiceRepository {

	@Override
	public Optional<Invoice> findByNumber(Long numero) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Invoice.findByNumber", Invoice.class).setParameter(1, numero).getResultStream()
				.findFirst();
	}

	@Override
	public Long getNextInvoiceNumber() {
		EntityManager em = Jpa.getManager(); 
		return em.createNamedQuery("Invoice.getNextInvoiceNumber",Long.class).getSingleResult();
	}

}
