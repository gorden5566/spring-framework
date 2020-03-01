package org.springframework.tests.sample.beans;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gorden5566
 * @date 2020/03/02
 */
public class A {
	@Autowired
	private B b;

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
}
