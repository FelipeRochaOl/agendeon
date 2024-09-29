package br.com.agendaon.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {
    List<CompanyModel> findByCategoryCode(UUID categoryCode);
    List<CompanyModel> findBySessionCode(UUID sessionCode);
    Optional<CompanyModel> findByUserId(UUID userId);

    @Query(value = "SELECT tac.* FROM \"AGENDEON\".\"T_AGON_COMPANY\" tac"
            + " INNER JOIN \"AGENDEON\".\"T_AGON_ADDRESS\" taa ON taa.id = tac.address_id"
            + " WHERE taa.zip = :zip"
            , nativeQuery = true)
    List<CompanyModel> filterByCEP(@Param("zip") String zip);
}
