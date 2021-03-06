package com.bigbox.b2csite.order.model.transformer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.entity.OrderItemEntity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OrderEntityToOrderSummaryTransformerTest {

	private OrderEntityToOrderSummaryTransformer target = null;

	@Before
	public void setup() {
		target = new OrderEntityToOrderSummaryTransformer();
	}

	@Test
	public void test_transform_success() {

		String orderNumberFixture = UUID.randomUUID().toString();

		OrderEntity orderEntityFixture = new OrderEntity();
		orderEntityFixture.setOrderNumber(orderNumberFixture);
		orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());

		OrderItemEntity itemFixture1 = new OrderItemEntity();
		itemFixture1.setQuantity(1);
		itemFixture1.setSellingPrice(new BigDecimal(10));
		orderEntityFixture.getOrderItemList().add(itemFixture1);

		OrderItemEntity itemFixture2 = new OrderItemEntity();
		itemFixture2.setQuantity(2);
		itemFixture2.setSellingPrice(new BigDecimal(15));
		orderEntityFixture.getOrderItemList().add(itemFixture2);

		OrderSummary result = target.transform(orderEntityFixture);

		assertThat(result, is(notNullValue()));
		// assertThat(null, is(nullValue()));
		assertThat(result.getOrderNumber(), is(orderNumberFixture));
		assertThat(result.getItemCount(), is(3));
		assertThat(result.getTotalAmount(), is(new BigDecimal(40)));

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_transform_input_is_null() {
		target.transform(null);
	}

	@Test
	public void test_transform_no_item_in_order() {
		String orderNumberFixture = UUID.randomUUID().toString();

		OrderEntity orderEntityFixture = new OrderEntity();
		orderEntityFixture.setOrderNumber(orderNumberFixture);
		orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());

		OrderSummary result = target.transform(orderEntityFixture);

		assertThat(result, is(notNullValue()));

		assertThat(result.getOrderNumber(), is(orderNumberFixture));
		assertThat(result.getItemCount(), is(0));
		assertThat(result.getTotalAmount(), is(new BigDecimal(0)));
	}

}
