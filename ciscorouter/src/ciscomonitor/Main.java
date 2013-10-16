/*
 * Main.java
 *
 * Created on August 27, 2007, 12:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ciscomonitor;
import snmpwalk.SnmpWalk;
import com.ireasoning.protocol.snmp.*;
import java.util.*;
/**
 *
 * @author Chris
 */
public class Main {
    
    static HashMap results = new HashMap();
    static HashMap map = new HashMap();
    
    static SnmpWalk snmpwalk;
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap comments = new HashMap();
        SnmpWalk snmpwalk = new SnmpWalk();
        
        
        String[] overrideArgs = new String[2];
        overrideArgs[0] = "-v";
        overrideArgs[1] = "2";
        
        snmpwalk.parseOptions(overrideArgs, "snmpwalk", false, false);
        snmpwalk._host = args[0];
        snmpwalk._community = args[1];
        
        snmpwalk._version = SnmpConst.SNMPV2;
        
        
        map.clear();
        
        // ROUTER MEMORY CHECK
        map.put("memused", "1.3.6.1.4.1.9.9.48.1.1.1.5");
        map.put("memfree", "1.3.6.1.4.1.9.9.48.1.1.1.6");
        map.put("memfreecontig", "1.3.6.1.4.1.9.9.48.1.1.1.7");
        comments.put("1","processor");
        comments.put("2","iofast");
        
        Set memKeys = map.keySet();
        Iterator memIter = memKeys.iterator();
        
        while (memIter.hasNext()) {
            Object mapKey = memIter.next();
            Object mapValue = map.get(mapKey);
            
            snmpwalk._oids[0] = mapValue.toString();
            results = snmpwalk.walk();
            wprint(results,(String)mapKey,true,false,comments);
        }
        
        map.clear();
        comments.clear();
        
        
        // END ROUTER MEMORY

        
        snmpwalk._oids[0] = "1.3.6.1.2.1.1.1";
        results = snmpwalk.walk();
        
        Set keys = results.keySet();
        Iterator iter = keys.iterator();
        
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = results.get(key);
            String s = (String)results.get("0");
            
            if ((s.indexOf("Version_12.0") > -1) || (s.indexOf("Version_12.1") > -1) || (s.indexOf("Version_12.2") > -1)) {
                
                map.put("cpu5sec", "1.3.6.1.4.1.9.9.109.1.1.1.1.3");
                map.put("cpu1min", "1.3.6.1.4.1.9.9.109.1.1.1.1.4");
                map.put("cpu5min", "1.3.6.1.4.1.9.9.109.1.1.1.1.5");
                
                Set mapKeys = map.keySet();
                Iterator mapIter = mapKeys.iterator();
                
                while (mapIter.hasNext()) {
                    Object mapKey = mapIter.next();
                    Object mapValue = map.get(mapKey);
                    
                    snmpwalk._oids[0] = mapValue.toString();
                    results = snmpwalk.walk();
                    wprint(results, "." + (String)mapKey,true,false,comments);
                }
                map.clear();
            } else {
                // Assume new version
                map.put("cpu5sec", "1.3.6.1.4.1.9.9.109.1.1.1.1.6");
                map.put("cpu1min", "1.3.6.1.4.1.9.9.109.1.1.1.1.7");
                map.put("cpu5min", "1.3.6.1.4.1.9.9.109.1.1.1.1.8");
                
                Set mapKeys = map.keySet();
                Iterator mapIter = mapKeys.iterator();
                
                while (mapIter.hasNext()) {
                    Object mapKey = mapIter.next();
                    Object mapValue = map.get(mapKey);
                    
                    snmpwalk._oids[0] = mapValue.toString();
                    results = snmpwalk.walk();
                    wprint(results, "." + (String)mapKey,true,false,comments);
                }
                map.clear();
            }
        }
    }
    
    static private void wprint(HashMap r, String tag, boolean showkey, boolean label, HashMap c) {
        
        Set keys = r.keySet();
        Iterator iter = keys.iterator();
        
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = r.get(key);
            if (showkey) {
                if (c.containsKey(key)) {
                    Object tkey = c.get(key);
                    
                    if (label) {
                        Object key1 = tkey + "[" + key + "]";
                        key = key1;
                    } else {
                        key = tkey;
                    }
                }
                
                System.out.println(key + tag + " " + value);
            }else {
                System.out.println(tag + " " + value);
            }
            
            
        }
    }
    
}
