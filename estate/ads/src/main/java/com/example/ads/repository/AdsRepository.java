package com.example.ads.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ads.model.Ads;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AdsRepository extends JpaRepository<Ads, Long>{
		Page<Ads> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
	        String name, String description, Pageable pageable);

	    Page<Ads> findByAdstypeId(Long adstypeId, Pageable pageable);

	    Page<Ads> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

	    @Query("""
	    	    SELECT a FROM Ads a
	    	    WHERE (:search IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))
	    	                           OR LOWER(a.description) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')))
	    	    AND (:adstypeId IS NULL OR a.adstypeId = :adstypeId)
	    	    AND (:minPrice IS NULL OR a.price >= :minPrice)
	    	    AND (:maxPrice IS NULL OR a.price <= :maxPrice)
	    	""")
	    Page<Ads> findWithFilters(
	        @Param("search") String search,
	        @Param("adstypeId") Long adstypeId,
	        @Param("minPrice") Double minPrice,
	        @Param("maxPrice") Double maxPrice,
	        Pageable pageable
	    );
}
