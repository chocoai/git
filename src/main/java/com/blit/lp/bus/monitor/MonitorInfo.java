package com.blit.lp.bus.monitor;

public class MonitorInfo {
	/** 操作系统. */
    private String osName;
    /** ip地址. */
    private String ip;
    
    /** 已使用内存. */
    private long usedMemory;
    
    /** 最大可使用内存. */
    private long maxMemory;
    
    /** 线程总数. */
    private int totalThread;
    
    /** cpu核数. */
    private int cpuCount;
    
    /** cpu使用率. */
    private double cpuRatio;

    public long getUsedMemory() {
        return usedMemory;
    }
    
    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

	public double getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
