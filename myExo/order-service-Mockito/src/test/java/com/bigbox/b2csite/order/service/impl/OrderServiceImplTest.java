package com.bigbox.b2csite.order.service.impl;



import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoAssertionError;

import com.bigbox.b2csite.common.DataAccessException;
import com.bigbox.b2csite.common.ServiceException;
import com.bigbox.b2csite.order.dao.OrderDao;
import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.transformer.OrderEntityToOrderSummaryTransformer;

public class OrderServiceImplTest {

	private final static long CUSTOMER_ID = 1l; 
	
	
	@Test 
	public void test_getOrderSummary_success() throws ServiceException, DataAccessException{
		
		
		//setup
		OrderServiceImpl target = new OrderServiceImpl(); 
		
		OrderDao mockOrderDao = Mockito.mock(OrderDao.class);
		target.setOrderDao(mockOrderDao);
		
		OrderEntityToOrderSummaryTransformer mockTransformer = Mockito.mock(OrderEntityToOrderSummaryTransformer.class); 
		target.setTransformer(mockTransformer);
		
		
		OrderEntity orderEntityFixture = new OrderEntity(); 
		List<OrderEntity> orderEntityListFixture = new LinkedList<>(); 
		orderEntityListFixture.add(orderEntityFixture); 
		
		//mock des appels
		Mockito.when(mockOrderDao.findOrdersByCustomer(CUSTOMER_ID)).thenReturn(orderEntityListFixture); 
		
		OrderSummary orderSummaryFixture = new OrderSummary(); 
		Mockito.when(mockTransformer.transform(orderEntityFixture)).thenReturn(orderSummaryFixture); 
		
		//execution
		List<OrderSummary> result = target.getOrderSummary(CUSTOMER_ID); 
		
		//verif mockito
		Mockito.verify(mockOrderDao).findOrdersByCustomer(CUSTOMER_ID); 
		Mockito.verify(mockTransformer).transform(orderEntityFixture); 
		
		//verif
		assertThat(result,  is(notNullValue()));
		assertThat(result.size(),  is(1)); 
		assertThat(result.get(0), is(orderSummaryFixture)); 
	}
	
}
