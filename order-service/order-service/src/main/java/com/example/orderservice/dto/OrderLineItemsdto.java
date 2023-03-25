package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsdto {

	private String skucode;
	private BigDecimal price;
	private BigInteger quantity;
	
}
