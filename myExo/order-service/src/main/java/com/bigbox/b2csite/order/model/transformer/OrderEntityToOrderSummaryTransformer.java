package com.bigbox.b2csite.order.model.transformer;

import java.math.BigDecimal;
import java.util.UUID;

import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.entity.OrderItemEntity;



public class OrderEntityToOrderSummaryTransformer {

	
	public OrderSummary transform(OrderEntity orderEntity){
		
		if(orderEntity == null){
			throw new IllegalArgumentException(); 
		}
		
		
		OrderSummary orderSummaryResult = new OrderSummary(); 
		
		orderSummaryResult.setOrderNumber(orderEntity.getOrderNumber());
		
		int itemCount= 0; 
		BigDecimal orderAmount = new BigDecimal(0); 
		
		for(OrderItemEntity order : orderEntity.getOrderItemList()) {
			itemCount += order.getQuantity(); 
			BigDecimal quantityBD = new BigDecimal(order.getQuantity()); 
			BigDecimal itemTotal = order.getSellingPrice().multiply(quantityBD); 
			orderAmount = orderAmount.add(itemTotal); 
		}
		orderSummaryResult.setItemCount(itemCount);
		orderSummaryResult.setTotalAmount(orderAmount);
		
		
		return orderSummaryResult; 
	}
}
