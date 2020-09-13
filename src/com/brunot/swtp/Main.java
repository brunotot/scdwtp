package com.brunot.swtp;

import org.openqa.selenium.By;

import com.brunot.swtp.driver.IWebDriver;

public class Main {

	public static void main(String[] args) {
		IWebDriver driver = null;
		boolean enableTorProxy = true;
		try {
			driver = new IWebDriver(enableTorProxy);
			driver.get("https://check.torproject.org/");
			String isTorConfiguredMessage = driver.findElement(By.xpath("/html/body/div[2]/h1")).getText();
			String ipAddress = driver.findElement(By.xpath("/html/body/div[2]/p[1]/strong")).getText();
			System.out.println(isTorConfiguredMessage);
			System.out.println("IP address: " + ipAddress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				try {
					driver.stopTor();
					driver.quit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
