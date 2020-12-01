package pers.dawnyang.common.util.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JschUtil {

	private static JSch jsch;

	private static Session session;

	private JschUtil() {
	}

	public static void connect(SSHConfig serverConfig) throws JSchException {
		jsch = new JSch();
		session = jsch.getSession(serverConfig.getUser(), serverConfig.getHost(), serverConfig.getPort());
		session.setPassword(serverConfig.getPassword());
		Properties properties = new Properties();
		properties.put("StrictHostKeyChecking", "no");
		session.setConfig(properties);
		session.connect();
	}

	/**
	 * 命令执行execNormalCommand
	 *
	 * @param execCmd
	 * @return
	 */
	public static String execNormalCommand(SSHConfig serverConfig, String execCmd) {
		ChannelExec channelExec = null;
		String execResult = "";
		try {
			connect(serverConfig);
			if (null != execCmd && !"".equals(execCmd.trim())) {
				channelExec = getExecChannel();
				execResult = execNormalCommand2(channelExec, execCmd);
			}
		} catch (JSchException e) {
			log.error("获取[" + serverConfig.getHost() + ":" + serverConfig.getPort() + "]主机连接信息失败...", e);
		} finally {
			if (channelExec != null) {
				channelExec.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		System.out.println(execResult);
		return execResult.replace("\n", "");
	}

	/**
	 * 命令执行execNormalCommandBatch 批量处理
	 *
	 * @param orderArr
	 * @param serverConfig
	 * @param execCmd
	 * @return
	 */
	/*    public static Map<String, Object> execNormalCommandBatch(String[] orderArr, ServerConfig serverConfig,
	        String execCmd) {
	    ChannelExec channelExec = null;
	    String execResult;
	    Map<String, Object> map = new HashMap<>(16);
	    try {
	        connect(serverConfig);
	        for (String order : orderArr) {
	            if (null != order && !"".equals(order)) {
	                String execCmdStr = execCmd.replace(AccountVisualConstant.LINUX_CMD_SYMBOL, order);
	                channelExec = getExecChannel();
	                execResult = execNormalCommand2(channelExec, execCmdStr);
	                map.put(order, execResult.replace("\n", ""));
	            }
	        }
	        return map;
	    } catch (JSchException e) {
	        log.error("获取[" + serverConfig.getHost() + ":" + serverConfig.getPort() + "]主机连接信息失败...", e);
	    } finally {
	        if (channelExec != null) {
	            channelExec.disconnect();
	        }
	        if (session != null) {
	            session.disconnect();
	        }
	    }
	    return map;
	}*/
	/**
	 * 从JSch Session，获取ChannelExec
	 *
	 * @return
	 */
	private static ChannelExec getExecChannel() throws JSchException {
		ChannelExec channelExec;
		channelExec = (ChannelExec) session.openChannel("exec");
		return channelExec;
	}

	/**
	 * 从JSch Session，获取ChannelShell
	 *
	 * @return
	 */
	public static ChannelExec getShellChannel() throws JSchException {
		ChannelExec channelExec;
		channelExec = (ChannelExec) session.openChannel("shell");
		return channelExec;
	}

	/**
	 * SSH执行普通命令行
	 *
	 * @param channelExec
	 * @param execCmd
	 * @return
	 */
	private static String execNormalCommand2(ChannelExec channelExec, String execCmd) {
		int connectTimeOut = 30000;
		InputStream inputStream;
		StringBuffer buffer = new StringBuffer();
		try {
			channelExec.setCommand(execCmd);
			inputStream = channelExec.getInputStream();
			channelExec.connect(connectTimeOut);
			byte[] tmp = new byte[1024];
			while (true) {
				while (inputStream.available() > 0) {
					int i = inputStream.read(tmp, 0, 1024);
					if (i < 0) {
						break;
					}
					buffer.append(new String(tmp, 0, i, Charset.forName("GBK")));
				}
				if (channelExec.isClosed()) {
					if (inputStream.available() > 0) {
						continue;
					}
					break;
				}
			}
		} catch (JSchException e) {
			log.error("SSH方式执行监测异常[" + execCmd + "]", e);
		} catch (IOException e) {
			log.error("SSH方式读写IO异常", e);
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		SSHConfig serverConfig = new SSHConfig();
		serverConfig.setHost("136.6.177.20");
		serverConfig.setPort(22);
		serverConfig.setUser("pfrptapp");
		serverConfig.setPassword("appCRUN20!");
		// String cmd = "free";
		// String cmd = ". ~/.bash_profile; python3
		// /jfapp/hbx/liuzx/py3/bin/Autotest.py -h";
		String cmd = "free";
		execNormalCommand(serverConfig, cmd);
	}
}
