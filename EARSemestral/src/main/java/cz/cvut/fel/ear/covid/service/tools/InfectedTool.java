package cz.cvut.fel.ear.covid.service.tools;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class InfectedTool implements ToolImplementation {

    private String name = "Get acctual infected people number in Czech Republic.";

    public ResponseEntity<Map> work(){
        Map<String, String> body = new HashMap<>();
        body.put("toolName",this.name);
        body.put("infected",getCovid());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public String getCovid(){
        String result = "";
        try{
            String sURL = "https://api.apify.com/v2/key-value-stores/K373S4uCFR9W1K8ei/records/LATEST?disableRedirect=true"; //just a string

            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            result = rootobj.get("infected").getAsString(); //just grab the zipcode
            return result;
        }catch(Exception e){

        }
        return result;
    }

}
