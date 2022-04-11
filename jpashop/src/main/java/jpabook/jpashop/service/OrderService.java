package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	//주문
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		//주문 상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문 생성
		Order order = Order.createOrder(member,delivery, orderItem);

		//주문 저장
		orderRepository.save(order); //caseCade all옵션이라 딜리버리와 오더아이템 모두 persist날려줌

		return order.getId();
	}
	/**
	 * 주문 취소
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		order.cancel(); //더티체킹으로 직접 쿼리에 증가 안날려줘도 자동으로 변경된 값을 DB에 업데이트 해줌(JPA의 장점)
	}

	// //검색
	// public List<Order> findOrder(OrderSearch orderSearch) {
	// 	return orderRepository.findAll(orderSearch);
	// }
}
