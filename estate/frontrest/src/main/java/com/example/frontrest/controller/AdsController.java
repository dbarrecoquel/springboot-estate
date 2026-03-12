package com.example.frontrest.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ads.dto.AdsDto;
import com.example.ads.mapper.AdsMapper;
import com.example.ads.model.Ads;
import com.example.ads.service.AdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = "*")
public class AdsController {

	private final AdsService adsService;
	private final AdsMapper adsMapper;
	public AdsController(AdsService adsService, AdsMapper adsMapper) {
		this.adsService = adsService;
		this.adsMapper = adsMapper;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllAds(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction,
	        @RequestParam(required = false) String search,
	        @RequestParam(required = false) Long adsType,
	        @RequestParam(required = false) Double minPrice,
	        @RequestParam(required = false) Double maxPrice) {

	    Sort sort = direction.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Ads> pageResult = adsService.findWithFilters(search, adsType, minPrice, maxPrice, pageable);

	    List<AdsDto> content = pageResult.getContent()
	            .stream()
	            .map(adsMapper::toDto)
	            .collect(Collectors.toList());

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", content);
	    response.put("page", pageResult.getNumber());
	    response.put("size", pageResult.getSize());
	    response.put("totalElements", pageResult.getTotalElements());
	    response.put("totalPages", pageResult.getTotalPages());
	    response.put("last", pageResult.isLast());

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdsDto> getAdsById(@PathVariable Long id) {
		
		Optional<Ads> ads = adsService.getAdsById(id);
		return ads.map(a -> {
			return ResponseEntity.ok(adsMapper.toDto(a));
		}).orElse(ResponseEntity.notFound().build());
		
	}
}
