package pers.dawnyang.common.util.ssh;

import lombok.Data;

/**
 * TODO
 * 
 * @author yangyh create 2020年10月12日下午2:51:57
 * @see
 */
@Data
public class SSHConfig {

	private String host;

	private int port;

	private String user;

	private String password;
}
