package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //어딘가에 내장이 될 수 있음, 변경불가능한 객체로 만듦(setter제거하고 초기화 생성자를 만듦)
@Getter
public class Address {

	private String city;
	private String street;
	private String zipcode;

	protected Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
