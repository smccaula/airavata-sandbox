/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.filetransfer.security;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.airavata.filetransfer.utils.ServiceConstants;
import org.apache.log4j.Logger;
import org.globus.myproxy.MyProxy;
import org.ietf.jgss.GSSCredential;

public class SecurityContext {

    public static final String GRIDFTPCLIENT_PROPERTY = "airavata-gridftp-client.properties";
    private Properties properties;
    protected GSSCredential gssCredential;

    private MyProxyCredentials credentials;
    private static final Logger log = Logger.getLogger(SecurityContext.class);

    /**
     * 
     * Constructs a ApplicationGlobalContext.
     * 
     * @throws GfacGUIException
     */

    public SecurityContext() throws Exception {
        log.setLevel(org.apache.log4j.Level.INFO);
        loadConfiguration();

    }

    public static void main(String[] args) {
        try {
            SecurityContext context = new SecurityContext();
            context.login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @throws GfacException
     */
    public void login() throws Exception {
        gssCredential = credentials.getGssCredential();
    }

    public static String getProperty(String name) {
        try {
            SecurityContext context = new SecurityContext();
            return context.getProperties().getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Load the configration file
     * 
     * @throws GfacException
     */
    private void loadConfiguration() throws Exception {
        try {
            if (properties == null) {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                URL propertyFile = classLoader.getResource(GRIDFTPCLIENT_PROPERTY);

                if (propertyFile != null) {
                    File tempFile = new File(propertyFile.getFile());
                } else {
                    throw new Exception(" Not able to locate " + GRIDFTPCLIENT_PROPERTY);
                }
                InputStream propertyStream = classLoader.getResourceAsStream(GRIDFTPCLIENT_PROPERTY);
                properties = new Properties();
                if (credentials == null) {
                    this.credentials = new MyProxyCredentials();
                }
                if (propertyStream != null) {
                    properties.load(propertyStream);
                    String myproxyServerTmp = properties.getProperty(ServiceConstants.MYPROXY_SERVER);
                    if (myproxyServerTmp != null) {
                        this.credentials.setMyproxyHostname(myproxyServerTmp.trim());
                    }
                    String myproxyPortTemp = properties.getProperty(ServiceConstants.MYPROXY_PORT);
                    if (myproxyPortTemp != null && myproxyPortTemp.trim().length() > 0) {
                        this.credentials.setMyproxyPortNumber(Integer.parseInt(myproxyPortTemp.trim()));
                    } else {
                        this.credentials.setMyproxyPortNumber(MyProxy.DEFAULT_PORT);
                    }
                    String myproxyuser = properties.getProperty(ServiceConstants.MYPROXY_USERNAME);
                    if (myproxyuser != null) {
                        this.credentials.setMyproxyUserName(myproxyuser);
                    }
                    String myproxypass = properties.getProperty(ServiceConstants.MYPROXY_PASSWD);
                    if (myproxypass != null) {
                        this.credentials.setMyproxyPassword(myproxypass);
                    }
                    String myproxytime = properties.getProperty(ServiceConstants.MYPROXY_LIFETIME);
                    if (myproxytime != null) {
                        this.credentials.setMyproxyLifeTime(Integer.parseInt(myproxytime));
                    }
                    this.credentials.setHostcertsKeyFile(properties.getProperty(ServiceConstants.HOSTCERTS_KEY_FILE));
                    this.credentials.setTrustedCertsFile(properties.getProperty(ServiceConstants.TRUSTED_CERTS_FILE));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
            throw new Exception(e);
        }

    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Returns the gssCredential.
     * 
     * @return The gssCredential
     */
    public GSSCredential getGssCredential() {
        return this.gssCredential;
    }

    /**
     * Sets gssCredential.
     * 
     * @param gssCredential
     *            The gssCredential to set.
     */
    public void setGssCredential(GSSCredential gssCredential) {
        this.gssCredential = gssCredential;
    }

    public MyProxyCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(MyProxyCredentials credentials) {
        this.credentials = credentials;
    }
}