package uo.ri.cws.application.persistence.provider;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    /**
     * Devuelve un proveedor con un determinado nif
     * 
     * @param nif Nif deseado
     * @returnUn proveedor con el nif indicado
     * @throws SQLException
     */
    Optional<ProviderRecord> findByNIF(String nif) throws SQLException;

}
