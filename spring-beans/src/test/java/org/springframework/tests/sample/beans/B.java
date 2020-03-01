package org.springframework.tests.sample.beans;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gorden5566
 * @date 2020/03/02
 */
public class B {
	@Autowired
	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}
