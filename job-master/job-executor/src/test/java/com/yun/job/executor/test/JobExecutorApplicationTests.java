package com.yun.job.executor.test;

import com.monkey.common.response.Response;
import com.monkey.product.request.ProductSubmissionRequest;
import com.monkey.product.rpc.ProductRpc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobExecutorApplicationTests {

	@Autowired
	private ProductRpc productRpc;

	@Test
	public void test() {
		Response<String> response = this.productRpc.productSubmission(ProductSubmissionRequest.builder().build());
		System.out.println(response.code());
	}

}