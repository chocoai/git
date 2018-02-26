package com.blit.lp.bus.monitor;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MonitorService {
	private static final int CPUTIME = 30;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    /**
     * 获得当前的监控对象.
     * @return 返回构造好的监控对象
     * @throws Exception
     */
    public static MonitorInfo getMonitorInfo() throws Exception {
        int Kb = 1024 ;
        
        // 可使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / Kb;
        // 剩余内存
        long freeMemory = Runtime.getRuntime().freeMemory() / Kb;
        // 最大可使用内存
        long maxMemory = Runtime.getRuntime().maxMemory() / Kb;

        // 操作系统
        String osName = System.getProperty("os.name");
     
        // 获得线程总数
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent())
            ;
        int totalThread = parentThread.activeCount();
        
        int cpuCount = Runtime.getRuntime().availableProcessors();
        double cpuRatio = 0;
        if (osName.toLowerCase().startsWith("windows")) {
            cpuRatio = getCpuRatioForWindows();
        }
        
        
        // 构造返回对象
        MonitorInfo infoBean = new MonitorInfo();
        infoBean.setUsedMemory(totalMemory - freeMemory);
        infoBean.setMaxMemory(maxMemory);
        infoBean.setOsName(osName);

        infoBean.setTotalThread(totalThread);
        infoBean.setCpuCount(cpuCount);
        infoBean.setCpuRatio(cpuRatio);
        return infoBean;
    }

    /**
     * 获得CPU使用率.
     * @return 返回cpu使用率
     */
    private static double getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir")
                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(
                        PERCENT * (busytime) / (busytime + idletime))
                        .doubleValue();
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    /**
     * 读取CPU信息.
     * @param proc
     * @return
     */
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = bytessubstring(line, capidx, cmdidx - 1)
                        .trim();
                String cmd = bytessubstring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                // log.info("line="+line);
                if (caption.equals("System Idle Process")
                        || caption.equals("System")) {
                    idletime += Long.valueOf(
                    		bytessubstring(line, kmtidx, rocidx - 1).trim())
                            .longValue();
                    idletime += Long.valueOf(
                    		bytessubstring(line, umtidx, wocidx - 1).trim())
                            .longValue();
                    continue;
                }

                kneltime += Long.valueOf(
                		bytessubstring(line, kmtidx, rocidx - 1).trim())
                        .longValue();
                usertime += Long.valueOf(
                		bytessubstring(line, umtidx, wocidx - 1).trim())
                        .longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static String bytessubstring(String src, int start_idx, int end_idx){
        byte[] b = src.getBytes();
        String tgt = "";
        for(int i=start_idx; i<=end_idx; i++){
            tgt +=(char)b[i];
        }
        return tgt;
    }
    

    public static void main(String[] args) throws Exception {
        MonitorInfo monitorInfo = MonitorService.getMonitorInfo();
        System.out.println("cpu使用率=" + monitorInfo.getCpuRatio());
        System.out.println("cpu核数=" + monitorInfo.getCpuCount());
        System.out.println("服务器操作系统=" + monitorInfo.getOsName());
        System.out.println("服务器IP=" + monitorInfo.getOsName());
        System.out.println("最大内存=" + monitorInfo.getMaxMemory() + "Kb");
        System.out.println("已用内存=" + monitorInfo.getUsedMemory() + "Kb");
        System.out.println("线程总数=" + monitorInfo.getTotalThread());
    }
}
