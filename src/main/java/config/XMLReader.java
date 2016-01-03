/*
 * Copyright © 2015 Cisco Systems and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package config;

/**
 * Description:
 * Created by Quentin(陈坤) on 12/18/15.
 */
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class XMLReader {
    // 配置文件名
    private static String filename = "~/UDPConf/syslogsvr-config.xml";
    private static Config config;

    public static Config loadConfig() {
        if (config == null)
            config = getConfig();
        return config;
    }

    private static Config getConfig() {
        Config config = new Config();
        try {
            File f = new File(filename);
            if (!f.exists()) {
                System.out.println("  Error : Config file doesn't exist!");
                System.exit(1);
            }
            SAXReader reader = new SAXReader();
            Document doc;
            doc = reader.read(f);
            Element root = doc.getRootElement();
            Element data;
            Iterator<?> itr = root.elementIterator("value");
            data = (Element) itr.next();

            config.ipAddress = data.elementText("ip-address").trim();
            config.port = data.elementText("port").trim();
            config.transaction = data.elementText("transaction").trim();

        } catch (Exception ex) {
            System.out.println("Error : " + ex.toString());
        }
        return config;
    }
}

