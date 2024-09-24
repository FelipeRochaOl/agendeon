package br.com.agendaon.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {
    List<CompanyModel> findByCategoryCode(UUID categoryCode);

    List<CompanyModel> findBySessionCode(UUID sessionCode);

    @Query(value = "SELECT tac.*, taa.*"
            + " FROM T_AGON_COMPANY tac, T_AGON_ADDRESS taa"
            + " WHERE tac.category_code = ?1"
            + " AND taa.zip = ?2"
            + " AND taa.city = ?3"
            + " AND taa.neighborhood = ?4"
            , nativeQuery = true)
    List<CompanyModel> filterCompany(UUID categoryId, String zip, String city, String neighborhood);
}
