package com.sharetreats.productexchangeservice;

import com.sharetreats.Config;

public class ProductExchangeServiceApplication {

	public static void main(String[] args) {
		Config config = new Config();

		System.out.println(config.productRepository().findAll());
	}
}
