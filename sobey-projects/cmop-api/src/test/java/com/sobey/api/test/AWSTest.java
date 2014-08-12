package com.sobey.api.test;

import java.util.List;

import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

public class AWSTest {

	private AmazonEC2 ec2;

	@Test
	public void ec2() throws Exception {

		init();

		RunInstancesRequest runInstancesRequest = new RunInstancesRequest().withInstanceType("t2.micro")
				.withImageId("ami-d13845e1").withMinCount(1).withMaxCount(1).withSecurityGroupIds("default")
				.withKeyName("Lynn_Key");

		RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);

		List<Instance> instances = runInstances.getReservation().getInstances();
		for (Instance instance : instances) {
			System.out.println(instance.getInstanceId());
		}

	}

	public void init() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default] credential profile by reading from the credentials
		 * file located at (~/.aws/credentials).
		 */
		AWSCredentials credentials = null;
		try {
			// credentials = new ProfileCredentialsProvider().getCredentials();

			credentials = new AWSCredentials() {

				@Override
				public String getAWSSecretKey() {
					return "111";
				}

				@Override
				public String getAWSAccessKeyId() {
					return "222";
				}
			};

		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}

		ec2 = new AmazonEC2Client(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(usWest2);
	}

}
