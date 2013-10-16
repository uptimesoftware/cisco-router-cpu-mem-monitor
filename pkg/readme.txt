Configuring Tomcat
-------------------------------

Tomcat User/Pass
This is defined in the following file:
> TOMCAT_DIR/conf/tomcat-users.xml

You can place the following example lines to add the following user with the "manager-gui" role which is necessary for the monitor:
<role rolename="manager-gui"/>
<user username="tomcat" password="tomcat" roles="tomcat,manager-gui"/>


The Tomcat "manager webapp" must be enabled for the monitor to work.
To test whether it is turned on visit the following link (replace the hostname/port with your custom one(s) if necessary):
http://tomcatserver:8080/manager/html

The monitor will read the XML version of the status page, which is the following link:
http://tomcatserver:8080/manager/status?XML=true

If the above link works, then the monitor will as well. Make sure any firewall (iptables/tcpwrappers (/etc/hosts.allow)) is turned off or is setup to allow the up.time monitoring station to connect on the Tomcat port.


Configuring the Monitor in up.time
-------------------------------

The following configuration settings need to be set when creating a service instance based on this plug-in monitor:

    * Script Name - the name and location (on the monitoring station) of the script that runs the JAR, which in turn connects to the Tomcat Server
    * Manager URL - the URL to the Tomcat management console
    * Manager Port - the port of the Tomcat management console
    * Manager Password - the password required to access the Tomcat management console

The following metrics are used to set alerting thresholds, and can be included in Report graphs:

    * Max Memory - Maximum JVM memory
    * Total Memory - Total JVM memory
    * Thread Count - Current thread count for the connector
    * Threads Busy - Current busy thread count for the connector
    * Max Threads - Maximum number of threads for the connector
    * Bytes Received - Number of bytes received by the connector
    * Bytes Sent - Number of bytes sent by the connector
    * Request Count - Number of connections for the connector
    * Max Time - Maximum request processing time for the connector
    * Processing Time - Processing time for the connector
