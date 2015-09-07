
package fish.payara.examples.jcachecdi;

import java.io.Serializable;
import java.util.Random;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author ahmed
 * Bean class that does the logic for producing some random text and adding it to the cache
 */
@RequestScoped
public class Model implements Serializable {
    
    private StringBuilder builder;
    private String m;
    
    public Model(){
        builder = new StringBuilder();
        m = "abcdefghijklmnopqrstuvwxyz";
    }
    /**
     * 
     * @return Stringbuilder to generate string
     */
    public StringBuilder getBuilder(){
        return builder;
    }
    /**
     * 
     * @return string consisting of all the alphabets
     */
    public String getM(){
        return m;
    }
    /**
     * 
     * @param buildaa 
     */
    public void setBuilder(StringBuilder buildaa){
        this.builder = buildaa;
    }
    /**
     * 
     * @param s string 
     */
    public void setM(String s){
        this.m = s;
    }
    /**
     * 
     * @param number the length of text to generate
     * @return return generated string
     */
    @CacheResult(cacheName = "cache")
    public String slowOperation(int number){
        try{
            
            Thread.currentThread().sleep(2000);
            for(int i =0;i <number;i++){
                builder.append(m.charAt(new Random().nextInt(m.length())));
            }
         
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        
            
       return builder.toString();
    }
    /**
     * Empty contents of the cache
     */
    @CacheRemoveAll(cacheName = "cache")
    public void removeCache(){
        
    }
        
        
}
