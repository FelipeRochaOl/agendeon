package br.com.agendaon.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    Optional<CategoryModel> findByCode(UUID code);

    CategoryModel findByName(String name);
}
