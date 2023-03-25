package com.example.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skucode) {
		return 	inventoryRepository.findBySkucodeIn(skucode).stream()
				.map(inventory ->
					InventoryResponse.builder()
					.skucode(inventory.getSkucode())
					.isInStock(inventory.getQuantity()>0)
					.build()
				).toList();
				
				
		
		
	}
	
}
