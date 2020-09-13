package com.brunot.swtp.driver;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import net.sf.T0rlib4j.controller.network.JavaTorRelay;

public class IWebDriver implements WebDriver {

	private JavaTorRelay node;
	
	private WebDriver driver;
	
	private boolean enableTorProxy;
	
	public IWebDriver(boolean enableTorProxy, ChromeOptions options) throws Exception {
		this.enableTorProxy = !enableTorProxy;
        setEnableTorProxy(enableTorProxy, options);
	}
	
	public IWebDriver(boolean enableTorProxy) throws Exception {
		this.enableTorProxy = !enableTorProxy;
        setEnableTorProxy(enableTorProxy, null);

	}
	
	private void setEnableTorProxy(boolean enable, ChromeOptions options) throws Exception {
		if (enable == this.enableTorProxy) {
			return;
		}
		
		this.enableTorProxy = enable;
		String currentUrl = null;
		if (enable) {
			this.node = new JavaTorRelay(new File("torfiles"));
			options = new ChromeOptions();
			options.addArguments("--proxy-server=socks5://127.0.0.1:" + this.node.getLocalPort());
			if (this.driver != null) {
				currentUrl = getCurrentUrl();
			}
		} else {
			if (this.node != null) {
				this.node.ShutDown();
				this.node = null;
			}
		}
		restartDriver(options);
		if (currentUrl != null) {
			navigate().to(currentUrl);
		}
	}
	
	
	private void restartDriver(ChromeOptions options) {
		if (options == null) {
			this.driver = new ChromeDriver();
		} else {
			this.driver = new ChromeDriver(options);
		}
	}

	public boolean isEnableTorProxy() {
		return this.enableTorProxy;
	}
	
	@Override
	public void get(String url) {
		this.driver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return this.driver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return this.driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return this.driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return this.driver.findElement(by);
	}

	@Override
	public String getPageSource() {
		return this.driver.getPageSource();
	}

	@Override
	public void close() {
		this.driver.close();
	}

	@Override
	public void quit() {
		this.driver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		return this.driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		return this.driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return this.driver.switchTo();
	}

	@Override
	public Navigation navigate() {
		return this.driver.navigate();
	}

	@Override
	public Options manage() {
		return this.driver.manage();
	}

	public void stopTor() throws Exception {
		if (this.node != null && this.enableTorProxy) {
			this.node.ShutDown();
			this.node = null;
		}
	}

}
