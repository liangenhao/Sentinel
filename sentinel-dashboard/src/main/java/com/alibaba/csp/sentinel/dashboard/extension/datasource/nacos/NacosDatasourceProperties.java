package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.nacos.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Nacos 数据源外部化配置
 *
 * @author enhao
 */
@ConfigurationProperties(prefix = NacosDatasourceProperties.PREFIX)
public class NacosDatasourceProperties {

    public static final String PREFIX = "datasource.nacos";

    /**
     * nacos config server address.
     */
    private String serverAddr = "127.0.0.1:8848";

    /**
     * the nacos authentication username.
     */
    private String username = "nacos";

    /**
     * the nacos authentication password.
     */
    private String password = "nacos";

    /**
     * encode for nacos config content.
     */
    private String encode = "UTF-8";

    /**
     * namespace, separation configuration of different environments.
     * <p>
     * default is public
     */
    private String namespace = "";

    /**
     * timeout for get config from nacos.
     */
    private int timeout = 3000;

    /**
     * nacos config group, group is config data meta info.
     */
    private String group = "SENTINEL_RULES_CONFIG_GROUP";

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Properties assembleConfigServiceProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.setProperty(PropertyKeyConst.USERNAME, username);
        properties.setProperty(PropertyKeyConst.PASSWORD, password);
        properties.setProperty(PropertyKeyConst.ENCODE, encode);
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        return properties;
    }
}
